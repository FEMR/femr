package femr.ui.controllers;
import femr.common.models.*;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

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

import femr.business.services.core.IEncounterService;
import femr.business.services.core.IPhotoService;
import femr.business.services.core.ISearchService;
import femr.data.models.mysql.PatientEncounter;
import femr.ui.controllers.PhotoController;
import femr.business.services.core.ISessionService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.data.models.mysql.PatientEncounterPhoto;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;

//import javafx.scene.image.*;
import femr.ui.models.history.IndexEncounterMedicalViewModel;
import femr.ui.models.history.IndexEncounterPharmacyViewModel;
import femr.ui.models.history.IndexEncounterViewModel;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


import java.io.ByteArrayOutputStream;
import java.util.List;


//import static play.modules.pdf.PDF.*;

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


    @Inject
    public PDFConversionDemo(ISearchService searchService, IEncounterService encounterService) {

        this.searchService = searchService;
        this.encounterService= encounterService;

    }

    public Result index(int id, int Eid) {
        response().setContentType("application/pdf");

        return ok(main(id, Eid));
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
        BIVtable.addCell(StyledPhrase("Nurse: ", encounter.getNurseEmailAddress()));
        BIVtable.addCell(StyledPhrase("Blood Pressure", GetBloodPressure(P_vitals)));
        BIVtable.addCell(StyledPhrase("Address: ", outputStringOrNA(P_item.getAddress())));


        BIVtable.addCell(StyledPhrase("Heart Rate: ", fromVitalMap("heartRate", P_vitals)));
        BIVtable.addCell(StyledPhrase("Respiration Rate: ", fromVitalMap("respiratoryRate", P_vitals)));
        BIVtable.addCell(StyledPhrase("Oxygen Saturation: ", fromVitalMap("oxygenSaturation", P_vitals)));
        BIVtable.addCell(StyledPhrase("Glucose: ", fromVitalMap("glucose", P_vitals)));

        BIVtable.addCell(StyledPhrase("Nurse: ", encounter.getNurseEmailAddress()));
        BIVtable.addCell(StyledPhrase("Pharmacist: ", encounter.getPharmacistEmailAddress()));
        BIVtable.addCell(StyledPhrase("Physician: ", encounter.getPhysicianEmailAddress()));
        BIVtable.addCell(StyledPhrase("", ""));

        BIVtable.addCell(StyledPhrase("Triage Visit: ", encounter.getTriageDateOfVisit()));
        BIVtable.addCell(StyledPhrase("Medical Visit: ", encounter.getMedicalDateOfVisit()));
        BIVtable.addCell(StyledPhrase("Pharmacy Visit: ", encounter.getPharmacyDateOfVisit()));
        BIVtable.addCell(StyledPhrase("Pregnancy Status: ", outputIntOrNA(encounter.getWeeksPregnant())));


        return (BIVtable);
    }

    private PdfPTable getAssessments(TabFieldMultiMap treatmentFields){

        PdfPTable assesmenttable = new PdfPTable(new float[]{0.25f, 0.25f, 0.3f, 0.2f});


        assesmenttable.setWidthPercentage(100);
        assesmenttable.setSpacingBefore(4);
        assesmenttable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);



        assesmenttable.addCell(StyledPhrase("Onset: ", outputStringOrNA(treatmentFields.getMostRecent("onset", null))));
        assesmenttable.addCell(StyledPhrase("quality: ", outputStringOrNA(treatmentFields.getMostRecent("quality", null))));
        assesmenttable.addCell(StyledPhrase("severity: ", outputStringOrNA(treatmentFields.getMostRecent("severity", null))));

        assesmenttable.addCell(StyledPhrase("radiation: ", outputStringOrNA(treatmentFields.getMostRecent("radiation", null))));
        assesmenttable.addCell(StyledPhrase("provokes: ", outputStringOrNA(treatmentFields.getMostRecent("provokes", null))));
        assesmenttable.addCell(StyledPhrase("palliates: ", outputStringOrNA(treatmentFields.getMostRecent("palliates", null))));
        assesmenttable.addCell(StyledPhrase("timeOfDay: ", outputStringOrNA(treatmentFields.getMostRecent("timeOfDay", null))));
        assesmenttable.addCell(StyledPhrase("narrative: ", outputStringOrNA(treatmentFields.getMostRecent("narrative", null))));

        assesmenttable.addCell(StyledPhrase("physicalExamination: ", outputStringOrNA(treatmentFields.getMostRecent("physicalExamination", null))));
        assesmenttable.addCell(StyledPhrase("physicalExamination: ", outputStringOrNA(treatmentFields.getMostRecent("physicalExamination", null))));
        assesmenttable.addCell(StyledPhrase("assessment: ", outputStringOrNA(treatmentFields.getMostRecent("assessment", null))));
        assesmenttable.addCell(StyledPhrase("treatment: ", outputStringOrNA(treatmentFields.getMostRecent("treatment", null))));

        assesmenttable.addCell(StyledPhrase("medicalSurgicalHistory: ", outputStringOrNA(treatmentFields.getMostRecent("medicalSurgicalHistory", null))));
        //assesmenttable.addCell(StyledPhrase("socialHistory", outputStringOrNA(treatmentFields.getMostRecent("socialHistory", null))));
        assesmenttable.addCell("");
        assesmenttable.addCell(StyledPhrase("currentMedications", outputStringOrNA(treatmentFields.getMostRecent("currentMedications", null))));
        assesmenttable.addCell(StyledPhrase("familyHistory", outputStringOrNA(treatmentFields.getMostRecent("familyHistory", null))));




        PdfPCell cell1 = new PdfPCell(new Phrase(StyledPhrase("medicalSurgicalHistory: ", outputStringOrNA(treatmentFields.getMostRecent("medicalSurgicalHistory", null)))));
        cell1.setColspan(2);
        cell1.setPadding(5);
        PdfPCell cell2 = new PdfPCell(new Phrase(StyledPhrase("currentMedications: ", outputStringOrNA(treatmentFields.getMostRecent("currentMedications", null)))));
        cell2.setColspan(2);
        assesmenttable.addCell(cell1);
        assesmenttable.addCell(cell2);
        assesmenttable.completeRow();


        PdfPCell cell3 = new PdfPCell(new Phrase(StyledPhrase("medicalSurgicalHistory: ", outputStringOrNA(treatmentFields.getMostRecent("medicalSurgicalHistory", null)))));
        cell1.setColspan(2);
        cell1.setPadding(5);
        PdfPCell cell4 = new PdfPCell(new Phrase(StyledPhrase("currentMedications: ", outputStringOrNA(treatmentFields.getMostRecent("currentMedications", null)))));
        cell2.setColspan(2);
        assesmenttable.addCell(cell3);
        assesmenttable.addCell(cell4);
        assesmenttable.completeRow();



        return (assesmenttable);
    }



    private byte[] main(int id, int Eid) {

        //   PatientEncounterItem patientEncounter;
        //  ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.findRecentPatientEncounterItemByPatientId(id);

        PatientEncounterItem patientEncounterONE;
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponseONE = searchService.findPatientEncounterItemByEncounterId(Eid);

        //  PatientItem patientItem;
        //   ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByPatientId(id);

        PatientItem patientItem;
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.findPatientItemByEncounterId(Eid);

        VitalMultiMap patientVitals;
        ServiceResponse<VitalMultiMap> VitalMultiMapServiceResponse = searchService.getVitalMultiMap(Eid);

        // GET Assesments

        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = searchService.getTabFieldMultiMap(Eid);
        if (patientEncounterTabFieldResponse.hasErrors()) {
            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();
//////////////////////

/**       // GET PROBLEMS
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.findProblemItems(Eid);
        if (problemItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }

**/




        //extract the most recent treatment fields
     //   Map<String, String> treatmentFields = new HashMap<>();
      //  treatmentFields.put("assessment", tabFieldMultiMap.getMostRecent("assessment", null));
       // treatmentFields.put("treatment", tabFieldMultiMap.getMostRecent("treatment", null));




            if (patientItemServiceResponse.hasErrors() || patientEncounterItemServiceResponseONE.hasErrors()) {
                throw new RuntimeException();
            }

            //  patientEncounter = patientEncounterItemServiceResponse.getResponseObject();
            patientItem = patientItemServiceResponse.getResponseObject();
            patientVitals = VitalMultiMapServiceResponse.getResponseObject();
            patientEncounterONE = patientEncounterItemServiceResponseONE.getResponseObject();
            // Assess = ServiceResponse.;


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

                Paragraph Patient_FirstName = new Paragraph("First Name : " + patientItem.getFirstName());
                Paragraph Patient_LastName = new Paragraph("Last Name : " + patientItem.getLastName());
                Paragraph Patient_Birthday = new Paragraph("Birthday     : " + patientItem.getFriendlyDateOfBirth());
                Paragraph Patient_Age = new Paragraph("Age            : " + patientItem.getAge());
                Paragraph Patient_Sex = new Paragraph("Sex            : " + patientItem.getSex());


                document.add(Title);
                document.add(new Paragraph((" ")));

                document.add(line);
                document.add(new Paragraph(" "));

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


                // Adding Image to Document
                // String imageUrl = "http://jenkov.com/images/" + "20081123-20081123-3E1W7902-small-portrait.jpg";
                // Image image = Image.getInstance(new URL(imageUrl));
                //  image.setAbsolutePosition(450f, 665f); //absolute positioning (float absoluteX, float absoluteY);
                // document.add(image);


                //document.add(withSpacingBefore(centeredParagraph("Assessment", new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK)), 15));


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
