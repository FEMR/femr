package femr.ui.controllers;
import femr.business.services.core.*;
import femr.common.models.*;
import femr.data.models.mysql.*;
import femr.common.dtos.ServiceResponse;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import static femr.util.ItextUtils.centeredParagraph;
import static femr.util.stringhelpers.StringUtils.*;

import java.lang.Object;
import java.util.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;

import com.google.inject.Inject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.PdfContentByte;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Andre on 2/14/2015.
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHARMACIST, Roles.NURSE})

public class PDFConversion extends Controller {

    private final ISearchService searchService;
    private final IEncounterService encounterService;
    private final ITabService tabService;
    private final IVitalService vitalService;
    private final IMedicationService medicationService;
    @Inject

    public PDFConversion(ISearchService searchService, IEncounterService encounterService, ITabService tabService, IVitalService vitalService, IMedicationService medicationService) {

        this.searchService = searchService;
        this.encounterService= encounterService;
        this.tabService = tabService;
        this.vitalService = vitalService;
        this.medicationService= medicationService;
    }

    public Result index(int id, int Eid) {
        response().setContentType("application/pdf");

        return ok(main(id, Eid));
    }
    private Map<String, String> CustomField(TabFieldMultiMap tabFieldMultiMap) {

        Map<String, String> customFields = new HashMap<>();
        List<String> customFieldNames = tabFieldMultiMap.getCustomFieldNameList();
        for (String customField : customFieldNames) {

            customFields.put(customField, tabFieldMultiMap.getMostRecentOrEmpty(customField, null).getValue());
        }
        return customFields;
    }

    protected Phrase StyledPhrase(String Label, Object value) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(Label, new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK)));
        phrase.add(new Chunk(value.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL, BaseColor.BLACK)));
        return phrase;
    }

    private String fromVitalMap(String key, VitalMultiMap vitalMap) {
        StringBuilder vitalData = new StringBuilder();
        for (int dateIndex = 1; dateIndex <= vitalMap.getDateListChronological().size(); dateIndex++) {
            vitalData.append(outputStringOrNA(vitalMap.get(key, vitalMap.getDate(dateIndex - 1))));
            vitalData.append(" ");
        }
        return vitalData.toString();
    }

    private String GetBloodPressure(VitalMultiMap vitalMap) {
        StringBuilder Bpressure = new StringBuilder();
        for (int dateIndex = 1; dateIndex <= vitalMap.getDateListChronological().size(); dateIndex++) {
            Bpressure.append(
                    outputBloodPressureOrNA(
                            vitalMap.get("bloodPressureSystolic", vitalMap.getDate(dateIndex - 1)),
                            vitalMap.get("bloodPressureDiastolic", vitalMap.getDate(dateIndex - 1))
                    )
            );
        }
        return Bpressure.toString();
    }

    private PdfPTable getBasicInfoAndVitalsTable(PatientItem P_item, PatientEncounterItem encounter, VitalMultiMap P_vitals) {


        PdfPTable BIVtable = new PdfPTable(new float[]{0.25f, 0.25f, 0.3f});


        BIVtable.setWidthPercentage(100);
        BIVtable.setSpacingBefore(3);
        BIVtable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);


        BIVtable.addCell(StyledPhrase("Blood Pressure: ", GetBloodPressure(P_vitals)));
        BIVtable.addCell(StyledPhrase("Temperature: ", fromVitalMap("temperature", P_vitals)));
        BIVtable.addCell(StyledPhrase("Glucose: ", fromVitalMap("glucose", P_vitals)));

        BIVtable.addCell(StyledPhrase("Heart Rate: ", fromVitalMap("heartRate", P_vitals)));
        BIVtable.addCell(StyledPhrase("Respiration Rate: ", fromVitalMap("respiratoryRate", P_vitals)));
        BIVtable.addCell(StyledPhrase("Oxygen Saturation: ", fromVitalMap("oxygenSaturation", P_vitals)));

        BIVtable.addCell(StyledPhrase("Pregnancy Status: ", outputIntOrNA(encounter.getWeeksPregnant())));
        BIVtable.completeRow();

        BIVtable.addCell(StyledPhrase("Physician: ", outputStringOrNA(encounter.getPhysicianEmailAddress())));
        BIVtable.addCell(StyledPhrase("Nurse: ", outputStringOrNA(encounter.getNurseEmailAddress())));
        BIVtable.addCell(StyledPhrase("Pharmacist: ", outputStringOrNA(encounter.getPharmacistEmailAddress())));

        BIVtable.addCell(StyledPhrase("Triage Visit:                           ", outputStringOrNA(encounter.getTriageDateOfVisit())));
        BIVtable.addCell(StyledPhrase("Medical Visit:                           ", outputStringOrNA(encounter.getMedicalDateOfVisit())));
        BIVtable.addCell(StyledPhrase("Pharmacy Visit:                          ", outputStringOrNA(encounter.getPharmacyDateOfVisit())));



        return (BIVtable);
    }

    public PdfPTable createTable1(PatientItem P_item) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(288 / 5.23f);
        table.setWidths(new int[]{4, 4, 5});


        PdfPCell cell;

        cell = new PdfPCell(StyledPhrase("Address: ", outputStringOrNA(P_item.getAddress())));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(StyledPhrase("Birthday: " , outputStringOrNA(P_item.getFriendlyDateOfBirth())));
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell(StyledPhrase("Height: ", outputHeightOrNA(P_item.getHeightFeet(), P_item.getHeightInches())));
        table.addCell(StyledPhrase("Weight: ", outputFloatOrNA(P_item.getWeight()) + " lbs"));
        table.addCell(StyledPhrase("Sex: "    , outputStringOrNA(P_item.getSex())));
        table.addCell(StyledPhrase("Age: "    , outputStringOrNA((P_item.getAge()))));
        return table;
    }

    private PdfPTable getAssessments(TabFieldMultiMap treatmentFields,  List<String> prescriptions , List<String> problems){

        Boolean NoChiefComplaint = treatmentFields.getChiefComplaintList().size() == 0;
        Boolean ChiefComplaint = treatmentFields.getChiefComplaintList().size() >=1;

        PdfPTable assesmentTable = new PdfPTable(new float[]{0.25f, 0.25f, 0.3f});

        assesmentTable.setWidthPercentage(100);
        assesmentTable.setSpacingBefore(4);
        assesmentTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        CustomField(treatmentFields);
        /**
         * Extracts the most recent custom fields from the tabfieldmultimap.

         */

        PdfPCell cellMSH = new PdfPCell(new Phrase(StyledPhrase("Medical Surgical History: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("medicalSurgicalHistory", null).getValue()))));
        cellMSH.setColspan(2);
        cellMSH.setPadding(5);
        assesmentTable.addCell(cellMSH);

        PdfPCell cellCM = new PdfPCell(new Phrase(StyledPhrase("Medication: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("currentMedication", null).getValue()))));
        cellCM.setColspan(1);
        cellCM.setPadding(5);
        assesmentTable.addCell(cellCM);

        assesmentTable.completeRow();




        PdfPCell cellSH = new PdfPCell(new Phrase(StyledPhrase("Social History: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("socialHistory", null).getValue()))));
        cellSH.setColspan(2);
        cellSH.setPadding(5);
        assesmentTable.addCell(cellSH);


        PdfPCell cellAssesment = new PdfPCell(new Phrase(StyledPhrase("Assessment: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("assessment", null).getValue()))));
        cellAssesment.setColspan(1);
        cellAssesment.setPadding(5);
        assesmentTable.addCell(cellAssesment);

        assesmentTable.completeRow();



        PdfPCell cellFH = new PdfPCell(new Phrase(StyledPhrase("Family History: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("familyHistory", null).getValue()))));
        cellFH.setColspan(2);
        cellFH.setPadding(5);
        assesmentTable.addCell(cellFH);


        PdfPCell cellTreatment = new PdfPCell(new Phrase(StyledPhrase("Treatment: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("treatment", null).getValue()))));
        cellTreatment.setColspan(1);
        cellTreatment.setPadding(5);
        assesmentTable.addCell(cellTreatment);

        assesmentTable.completeRow();


        assesmentTable.addCell(" ");
        assesmentTable.completeRow();

        // Get Prescriptions
        for (String prescriptionName : prescriptions ){

            assesmentTable.addCell(StyledPhrase("Prescription: ", outputStringOrNA(prescriptionName)));

        }

        // Get Problems
        for (String Problems : problems) {
            assesmentTable.addCell(StyledPhrase("Problems: ", outputStringOrNA(Problems)));
        }

        assesmentTable.completeRow();


        assesmentTable.addCell(" ");
        assesmentTable.completeRow();

        // No chief Complaints
        if(NoChiefComplaint)
        {
            assesmentTable.addCell(StyledPhrase("Onset: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("onset", null).getValue())));
            assesmentTable.addCell(StyledPhrase("Quality: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("quality", null).getValue())));
            assesmentTable.addCell(StyledPhrase("Severity: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("severity", null).getValue())));


            assesmentTable.addCell(StyledPhrase("Provokes: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("provokes", null).getValue())));
            assesmentTable.addCell(StyledPhrase("Palliates: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("palliates", null).getValue())));
            assesmentTable.addCell(StyledPhrase("TimeOfDay: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("timeOfDay", null).getValue())));

            assesmentTable.addCell(StyledPhrase("Assessment: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("assessment", null).getValue())));
            assesmentTable.addCell(StyledPhrase("Treatment: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("treatment", null).getValue())));
            assesmentTable.addCell(StyledPhrase("Radiation: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("radiation", null).getValue())));


            assesmentTable.completeRow();

            Map<String, String> customFields = CustomField(treatmentFields);
            Iterator it = customFields.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                String key = (String) pair.getKey();
                String val = (String) pair.getValue();

                assesmentTable.addCell(StyledPhrase(key, outputStringOrNA(val)));
                assesmentTable.completeRow();

            }

        }


        if (ChiefComplaint) {


            for (String chiefComplaint : treatmentFields.getChiefComplaintList()) {

                PdfPCell cellNarrative = new PdfPCell(new Phrase(StyledPhrase("Narrative: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("narrative", chiefComplaint).getValue()))));
                PdfPCell cellPE = new PdfPCell(new Phrase(StyledPhrase("Physical Examination: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("physicalExamination", chiefComplaint).getValue()))));

                cellPE.setColspan(3);
                cellPE.setPadding(5);
                cellPE.setBorder(PdfPCell.NO_BORDER);

                cellNarrative.setColspan(3);
                cellNarrative.setPadding(5);
                cellNarrative.setBorder(PdfPCell.NO_BORDER);



                String thechiefcomplaint = chiefComplaint;
                PdfPCell cellCC = new PdfPCell(new Phrase(StyledPhrase("Chief Complaint: ", outputStringOrNA(chiefComplaint))));
                cellCC.setColspan(3);
                cellCC.setPaddingBottom(5);

                assesmentTable.addCell(cellCC);
                assesmentTable.completeRow();

                assesmentTable.addCell(StyledPhrase("Onset: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("onset", chiefComplaint).getValue())));
                assesmentTable.addCell(StyledPhrase("Quality: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("quality", chiefComplaint).getValue())));
                assesmentTable.addCell(StyledPhrase("Severity: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("severity", chiefComplaint).getValue())));

                assesmentTable.addCell(StyledPhrase("Provokes: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("provokes", chiefComplaint).getValue())));
                assesmentTable.addCell(StyledPhrase("Palliates: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("palliates", chiefComplaint).getValue())));
                assesmentTable.addCell(StyledPhrase("TimeOfDay: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("timeOfDay", chiefComplaint).getValue())));

                assesmentTable.addCell(StyledPhrase("Radiation: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("radiation", chiefComplaint).getValue())));


                Map<String, String> customFields = CustomField(treatmentFields);
                Iterator it = customFields.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    String key = (String) pair.getKey();
                    String val = (String) pair.getValue();

                    assesmentTable.addCell(StyledPhrase(key +" :", outputStringOrNA(val)));
                    assesmentTable.completeRow();

                }


                assesmentTable.completeRow();
                assesmentTable.addCell(cellPE); // Add Physical Examination ( created on top , added to the bottom of the table)
                assesmentTable.addCell(cellNarrative); // Add Narrative     (..................................................)
                assesmentTable.completeRow();

                assesmentTable.addCell(" ");
                assesmentTable.completeRow();


            }
        }




        return (assesmentTable);
    }



    private byte[] main(int id, int Eid) {

        PatientEncounterItem patientEncounterONE;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponseONE = searchService.retrievePatientEncounterItemByEncounterId(Eid);

        PatientItem patientItem;
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByEncounterId(Eid);

        VitalMultiMap patientVitals;
        ServiceResponse<VitalMultiMap> VitalMultiMapServiceResponse = vitalService.retrieveVitalMultiMap(Eid);

        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap(Eid);

        List<String> prescriptions = new ArrayList<>();
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.retrieveDispensedPrescriptionItems(Eid);
        if (prescriptionItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }




        for (PrescriptionItem prescriptionItem : prescriptionItemServiceResponse.getResponseObject()){
            prescriptions.add(prescriptionItem.getName());

        }



        List<String> problems = new ArrayList<>();
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.retrieveProblemItems(Eid);
        if (problemItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        for (ProblemItem pi : problemItemServiceResponse.getResponseObject()){
            problems.add(pi.getName());
        }




            if (patientItemServiceResponse.hasErrors() || patientEncounterItemServiceResponseONE.hasErrors()
                    || patientEncounterTabFieldResponse.hasErrors() || VitalMultiMapServiceResponse.hasErrors()) {
                throw new RuntimeException();
            }


            patientItem = patientItemServiceResponse.getResponseObject();
            patientVitals = VitalMultiMapServiceResponse.getResponseObject();
            patientEncounterONE = patientEncounterItemServiceResponseONE.getResponseObject();
            TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();



            ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();


            try {

                Document document = new Document(PageSize.A4, 10, 10, 10, 10);
                PdfWriter docWriter = PdfWriter.getInstance(document, baosPDF);


                document.open();

                document.addAuthor("femr");
                document.addCreationDate();
                document.addCreator("femr");
                document.addTitle("Patient Report");


                //Prepare Line to add to document
                LineSeparator line = new LineSeparator(1, 100, BaseColor.RED, Element.ALIGN_CENTER, 10);

                //Create Paragraph For header
                Paragraph Title = new Paragraph("Medical Record", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));

                // Add the Patient Id to the Top Right corner using Content Byte.
                PdfContentByte cb = docWriter.getDirectContent();
                BaseFont bf = BaseFont.createFont();

                //Absolute Positioning to align Patient ID to Top Right Corner.
                cb.beginText();
                cb.setFontAndSize(bf, 12);
                cb.moveText(450f, 800);
                cb.showText("Encounter ID: " + patientEncounterONE.getId());
                cb.endText();


                PdfPTable PatientInfo = new PdfPTable(2);

                PatientInfo.setWidthPercentage(100);
                PatientInfo.setSpacingBefore(4);
                PatientInfo.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

                PatientInfo.addCell(StyledPhrase("First Name : "  , patientItem.getFirstName()));
                PatientInfo.addCell(StyledPhrase("Last Name  : "    , patientItem.getLastName()));

                Paragraph Patient_ID = new Paragraph(StyledPhrase("Patient ID : "  , patientItem.getId()));
                Paragraph Patient_FirstName = new Paragraph(StyledPhrase("First Name : "  , patientItem.getFirstName()));
                Paragraph Patient_LastName = new Paragraph(StyledPhrase("Last Name  : "    , patientItem.getLastName()));


                document.add(Title);
                document.add(new Paragraph((" ")));

                document.add(line);
                document.add(new Paragraph(" "));


                document.add(Patient_ID);
                document.add(Patient_FirstName);
                document.add(Patient_LastName);
                document.add(createTable1(patientItem));

                document.add(new Paragraph(" "));
                document.add(centeredParagraph("Basic Information and Vitals", new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK)));

                document.add(new Paragraph(" "));
                document.add(new LineSeparator(1, 45, BaseColor.BLACK, Element.ALIGN_CENTER, 10));
                document.add(getBasicInfoAndVitalsTable(patientItem, patientEncounterONE, patientVitals));

                document.add(new Paragraph(" "));
                document.add(centeredParagraph("Assessments", new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK)));

                document.add(new Paragraph(" "));
                document.add(new LineSeparator(1, 45, BaseColor.BLACK, Element.ALIGN_CENTER, 10));

                document.add(getAssessments(tabFieldMultiMap, prescriptions, problems));
                document.add(Chunk.NEWLINE);



                document.close();
                docWriter.close();
                baosPDF.writeTo(baosPDF);
                baosPDF.flush();

            } catch (Exception e) {

                e.printStackTrace();

            }

            return baosPDF.toByteArray();


        }


    }
