package femr.ui.controllers;
import femr.business.services.core.*;
import femr.common.models.*;
import femr.data.models.mysql.*;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.Object;
import java.net.URL;
import java.security.Key;
import java.util.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import com.google.inject.Inject;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;

import femr.ui.controllers.PhotoController;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;

//import javafx.scene.image.*;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import femr.ui.models.history.IndexEncounterPharmacyViewModel;
import femr.ui.models.history.IndexEncounterViewModel;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import femr.util.dependencyinjection.providers.PatientPrescriptionProvider;
import org.omg.CORBA.*;
import org.springframework.beans.factory.parsing.Problem;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


import java.io.ByteArrayOutputStream;
import java.util.List;




import com.itextpdf.text.pdf.PdfContentByte;
import scala.xml.PrettyPrinter;

import static femr.util.ItextUtils.centeredParagraph;
import static femr.util.ItextUtils.withSpacingBefore;
import static femr.util.stringhelpers.StringUtils.*;

/**
 * Created by Andre on 2/14/2015.
 */
@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHARMACIST, Roles.NURSE})

public class PDFConversionDemo extends Controller {

    private final ISearchService searchService;
    private final IEncounterService encounterService;
    private final ITabService tabService;
    private final IVitalService vitalService;
    private final IMedicationService medicationService;
    @Inject

    public PDFConversionDemo(ISearchService searchService, IEncounterService encounterService, ITabService tabService, IVitalService vitalService, IMedicationService medicationService) {

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
        PdfPTable BIVtable = new PdfPTable(new float[]{0.25f, 0.25f, 0.3f, 0.2f});


        BIVtable.setWidthPercentage(100);
        BIVtable.setSpacingBefore(4);
        BIVtable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        //  BIVtable.addCell(StyledPhrase("First name: ", P_item.getFirstName()));
        //   BIVtable.addCell(StyledPhrase("Pregnancy Status: " , outputIntOrNA(P_item.getWeeksPregnant())));

        //  BIVtable.addCell(StyledPhrase("Temperature: " , fromVitalMap("temperature", P_vitals)));


        BIVtable.addCell(StyledPhrase("Blood Pressure: ", "156"));
        BIVtable.addCell(StyledPhrase("Temperature: ", fromVitalMap("temperature", P_vitals)));
        BIVtable.addCell(StyledPhrase("Height: ", outputHeightOrNA(P_item.getHeightFeet(), P_item.getHeightInches())));
        BIVtable.addCell(StyledPhrase("Weight: ", outputFloatOrNA(P_item.getWeight()) + " lbs"));

        BIVtable.addCell(StyledPhrase("Address: ", outputStringOrNA(P_item.getAddress())));
        BIVtable.addCell(StyledPhrase("Nurse: ",  outputStringOrNA(encounter.getNurseEmailAddress())));
        BIVtable.addCell(StyledPhrase("Blood Pressure", GetBloodPressure(P_vitals)));
        BIVtable.addCell(StyledPhrase("Address: ", outputStringOrNA(P_item.getAddress())));


        BIVtable.addCell(StyledPhrase("Heart Rate: ", fromVitalMap("heartRate", P_vitals)));
        BIVtable.addCell(StyledPhrase("Respiration Rate: ", fromVitalMap("respiratoryRate", P_vitals)));
        BIVtable.addCell(StyledPhrase("Oxygen Saturation: ", fromVitalMap("oxygenSaturation", P_vitals)));
        BIVtable.addCell(StyledPhrase("Glucose: ", fromVitalMap("glucose", P_vitals)));

        BIVtable.addCell(StyledPhrase("Nurse: ", outputStringOrNA(encounter.getNurseEmailAddress())));
        BIVtable.addCell(StyledPhrase("Pharmacist: ", outputStringOrNA(encounter.getPharmacistEmailAddress())));
        BIVtable.addCell(StyledPhrase("Physician: ", outputStringOrNA(encounter.getPhysicianEmailAddress())));
        BIVtable.addCell(StyledPhrase("", ""));



        BIVtable.addCell(StyledPhrase("Triage Visit: ", outputStringOrNA(encounter.getTriageDateOfVisit())));
        BIVtable.addCell(StyledPhrase("Medical Visit: ", outputStringOrNA(encounter.getMedicalDateOfVisit())));
        BIVtable.addCell(StyledPhrase("Pharmacy Visit: ", outputStringOrNA(encounter.getPharmacyDateOfVisit())));
        BIVtable.addCell(StyledPhrase("Pregnancy Status: ", outputIntOrNA(encounter.getWeeksPregnant())));


        return (BIVtable);
    }

    private PdfPTable getAssessments(TabFieldMultiMap treatmentFields){

        PdfPTable assesmentTable = new PdfPTable(new float[]{0.25f, 0.25f, 0.3f});

        assesmentTable.setWidthPercentage(100);
        assesmentTable.setSpacingBefore(4);
        assesmentTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);


        /**
         * Extracts the most recent custom fields from the tabfieldmultimap.

         */
           CustomField(treatmentFields);


        // ** Added by Ken
        // These fields are mapped via Chief Complaint and can be listed multiple times per encounter
        // -- use the chief complaint to  get these fields
        // Add Physical Examination to this section - it is also mapped via Chief Complaint
        // Make table 3 columns instead of 4
        // Make Physical Examination and Narrative span 3 columns in their own row
        for( String chiefComplaint : treatmentFields.getChiefComplaintList() ) {

            PdfPCell cellNarrative = new PdfPCell(new Phrase(StyledPhrase("Narrative: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("narrative", chiefComplaint).getValue()))));
            PdfPCell cellPE = new PdfPCell(new Phrase(StyledPhrase("Physical Examination: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("physicalExamination", chiefComplaint).getValue()))));
            PdfPCell cellProb = new PdfPCell(new Phrase(StyledPhrase("Problems: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("problem", null).getValue()))));

            cellPE.setColspan(3);
            cellPE.setPadding(5);
            cellPE.setBorder(PdfPCell.NO_BORDER);

            cellNarrative.setColspan(3);
            cellNarrative.setPadding(5);
            cellNarrative.setBorder(PdfPCell.NO_BORDER);

            cellProb.setColspan(2);
            cellProb.setPadding(5);
            cellProb.setBorder(PdfPCell.NO_BORDER);

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
            assesmentTable.addCell(StyledPhrase("palliates: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("palliates", chiefComplaint).getValue())));
            assesmentTable.addCell(StyledPhrase("TimeOfDay: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("timeOfDay", chiefComplaint).getValue())));

            assesmentTable.addCell(StyledPhrase("Assessment: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("assessment", null).getValue())));
            assesmentTable.addCell(StyledPhrase("Treatment: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("treatment", null).getValue())));
            assesmentTable.addCell(StyledPhrase("MEDS: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("currentMedication", null).getValue())));

            assesmentTable.addCell(StyledPhrase("Radiation: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("radiation", chiefComplaint).getValue())));


            Map<String, String> customFields = CustomField(treatmentFields);
            Iterator it = customFields.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                String key = (String)pair.getKey();
                String val = (String)pair.getValue();

                assesmentTable.addCell(StyledPhrase(key, outputStringOrNA(val)));
                assesmentTable.completeRow();

            }




            assesmentTable.addCell(cellProb);

            assesmentTable.completeRow();
            assesmentTable.addCell(cellPE);
            assesmentTable.addCell(cellNarrative);



            assesmentTable.completeRow();

           // assesmentTable.addCell(StyledPhrase("Physical Examination: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("physicalExamination", chiefComplaint).getValue())));

            //assesmentTable.addCell(StyledPhrase("narrative: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("narrative", chiefComplaint).getValue())));
        }


        PdfPCell cellMSH = new PdfPCell(new Phrase(StyledPhrase("Medical Surgical History: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("medicalSurgicalHistory", null).getValue()))));
        cellMSH.setColspan(2);
        cellMSH.setPadding(5);
       // assesmentTable.addCell(cellMSH);

        PdfPCell cellFH = new PdfPCell(new Phrase(StyledPhrase("Family History: ", outputStringOrNA(treatmentFields.getMostRecentOrEmpty("familyHistory", null).getValue()))));
        cellFH.setColspan(2);
        cellFH.setPadding(5);


      //  assesmentTable.addCell(cellFH);
        assesmentTable.completeRow();


        return (assesmentTable);
    }



    private byte[] main(int id, int Eid) {

        PatientEncounterItem patientEncounterONE;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponseONE = searchService.findPatientEncounterItemByEncounterId(Eid);

        PatientItem patientItem;
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByEncounterId(Eid);

        VitalMultiMap patientVitals;
        ServiceResponse<VitalMultiMap> VitalMultiMapServiceResponse = vitalService.findVitalMultiMap(Eid);

        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.findTabFieldMultiMap(Eid);


//////////////////////

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
                //cb.showText("Patient ID: " + patientItem.getId());
                cb.endText();

                Paragraph Patient_ID = new Paragraph(StyledPhrase("Patient ID : "  , patientItem.getId()));
                Paragraph Patient_FirstName = new Paragraph(StyledPhrase("First Name : "  , patientItem.getFirstName()));
                Paragraph Patient_LastName = new Paragraph(StyledPhrase("Last Name  : "    , patientItem.getLastName()));
                Paragraph Patient_Birthday = new Paragraph(StyledPhrase("Birthday     : " , patientItem.getFriendlyDateOfBirth()));
                Paragraph Patient_Age = new Paragraph(StyledPhrase("Age              : "    , patientItem.getAge()));
                Paragraph Patient_Sex = new Paragraph(StyledPhrase("Sex               : "    , patientItem.getSex()));


                document.add(Title);
                document.add(new Paragraph((" ")));

                document.add(line);
                document.add(new Paragraph(" "));

                document.add(Patient_ID);
                document.add(Patient_FirstName);
                document.add(Patient_LastName);
                document.add(Patient_Sex);
                document.add(Patient_Age);
                document.add(Patient_Birthday);

                document.add(new Paragraph(" "));


                document.add(new Paragraph(" "));
                document.add(centeredParagraph("Basic Information and Vitals", new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK)));

                document.add(new Paragraph(" "));
                document.add(new LineSeparator(1, 45, BaseColor.BLACK, Element.ALIGN_CENTER, 10));
                document.add(getBasicInfoAndVitalsTable(patientItem, patientEncounterONE, patientVitals));

                document.add(new Paragraph(" "));
                document.add(centeredParagraph("Assessments", new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK)));

                document.add(new Paragraph(" "));
                document.add(new LineSeparator(1, 45, BaseColor.BLACK, Element.ALIGN_CENTER, 10));

                document.add(getAssessments(tabFieldMultiMap));
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
