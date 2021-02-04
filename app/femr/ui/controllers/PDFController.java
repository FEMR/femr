/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.ui.controllers;

import com.itextpdf.tool.xml.html.pdfelement.HtmlCell;
import femr.business.services.core.*;
import femr.common.models.*;
import femr.data.models.mysql.*;
import femr.common.dtos.ServiceResponse;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.util.DataStructure.Mapping.VitalMultiMap;
import static femr.util.stringhelpers.StringUtils.*;
import femr.util.DataStructure.Mapping.TabFieldMultiMap;
import com.google.inject.Inject;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.PHYSICIAN, Roles.PHARMACIST, Roles.NURSE})

public class PDFController extends Controller {

    private final ISearchService searchService;
    private final IEncounterService encounterService;
    private final ITabService tabService;
    private final IVitalService vitalService;


    @Inject
    public PDFController(ISearchService searchService,
                         IEncounterService encounterService,
                         ITabService tabService,
                         IVitalService vitalService) {

        this.searchService = searchService;
        this.encounterService= encounterService;
        this.tabService = tabService;
        this.vitalService = vitalService;
    }

    public Result index(int encounterId) {

        return ok(buildPDF(encounterId)).as("application/pdf");
    }

    /**
     * Take the patient encounter and build a pdf
     *
     * @param encounterId The id of the encounter to
     * @return The the PDF stream to output to the browser
     */
    private byte[] buildPDF(int encounterId) {

        // Get Patient Encounter
        ServiceResponse<PatientEncounterItem> patientEncounterItemServiceResponse = searchService.retrievePatientEncounterItemByEncounterId(encounterId);
        if (patientEncounterItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        PatientEncounterItem patientEncounter = patientEncounterItemServiceResponse.getResponseObject();

        // Get Patient
        ServiceResponse<PatientItem> patientItemServiceResponse = searchService.retrievePatientItemByEncounterId(encounterId);
        if (patientItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        PatientItem patientItem = patientItemServiceResponse.getResponseObject();

        // Get Vitals for Encounter
        ServiceResponse<VitalMultiMap> vitalMultiMapServiceResponse = vitalService.retrieveVitalMultiMap(encounterId);
        if (vitalMultiMapServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        VitalMultiMap patientVitals = vitalMultiMapServiceResponse.getResponseObject();

        // Get TabFields for Encounter
        ServiceResponse<TabFieldMultiMap> patientEncounterTabFieldResponse = tabService.retrieveTabFieldMultiMap(encounterId);
        if (patientEncounterTabFieldResponse.hasErrors()){
            throw new RuntimeException();
        }
        TabFieldMultiMap tabFieldMultiMap = patientEncounterTabFieldResponse.getResponseObject();

        // Get Prescriptions
        ServiceResponse<List<PrescriptionItem>> prescriptionItemServiceResponse = searchService.retrieveDispensedPrescriptionItems(encounterId);
        if (prescriptionItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<PrescriptionItem> prescriptions = prescriptionItemServiceResponse.getResponseObject();

        // Get Problems
        ServiceResponse<List<ProblemItem>> problemItemServiceResponse = encounterService.retrieveProblemItems(encounterId);
        if (problemItemServiceResponse.hasErrors()){
            throw new RuntimeException();
        }
        List<ProblemItem> problems = problemItemServiceResponse.getResponseObject();

        // Will eventually output the PDF -- all 3 lines below are needed
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 10, 10, 10, 10);

        try {

            PdfWriter docWriter = PdfWriter.getInstance(document, pdfStream);
            document.open();

            // PDF Author Info
            document.addAuthor("fEMR");
            document.addCreationDate();
            document.addCreator("fEMR");
            document.addTitle("Patient Report");

            // Add the header to the page
            document.add(createHeaderTable());

            // Add basic patient info
            document.add(createPatientInfoTable(patientItem));

            // Add Encounter Info Table
            document.add(createEncounterInfoTable(patientEncounter));

            // Add HPI and Chief Complaints Table
            document.add(getHPIAndChiefComplaintTable(tabFieldMultiMap));

            // Add Vitals Table
            document.add(createVitalsTable(patientEncounter, patientVitals));

            // Add Assessments Table
            document.add(getAssessments(tabFieldMultiMap, prescriptions, problems));

            document.close();
            docWriter.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return pdfStream.toByteArray();


    }

    /**
     * Builds the page header - Title and Empty cell (for border)
     *
     * @return PdfPTable the itext table to add to the document
     */
    public PdfPTable createHeaderTable(){

        PdfPTable table = new PdfPTable(2);
        table.setSpacingAfter(10);
        table.setWidthPercentage(100);

        Paragraph title = new Paragraph("Medical Record", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
        PdfPCell cell = new PdfPCell(title);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderWidthBottom(1);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        //Paragraph encounterId = new Paragraph("Encounter ID: " + patientEncounter.getId(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
        cell = new PdfPCell(table.getDefaultCell());
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setBorderColorBottom(BaseColor.BLACK);
        cell.setBorderWidthBottom(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        return table;
    }

    /**
     * Builds the Patient Info Table - Info unique to the patient
     *
     * @param patientItem the patient item
     * @return PdfPTable the itext table to add to the document
     * @throws DocumentException
     */
    public PdfPTable createPatientInfoTable(PatientItem patientItem) throws DocumentException {

        PdfPTable table = getDefaultTable(3);

        table.addCell(getStyledPhrase("Patient ID: ", Integer.toString(patientItem.getId())));
        table.completeRow();

        // Row 1
        table.addCell(getStyledPhrase("Name: ", patientItem.getFirstName() + " " + patientItem.getLastName()));
        table.addCell(getStyledPhrase("DOB: ", outputStringOrNA(patientItem.getFriendlyDateOfBirth())));
        table.addCell(getStyledPhrase("Age: ", outputStringOrNA((patientItem.getAge()))));
        table.completeRow();

        // Row 2
        table.addCell(getStyledPhrase("Sex: ", outputStringOrNA(patientItem.getSex())));
        table.addCell(getStyledPhrase("City: ", outputStringOrNA(patientItem.getCity())));
        table.addCell(getStyledPhrase("Address: ", outputStringOrNA(patientItem.getAddress())));
        table.completeRow();

        // Row 3
        table.addCell(getStyledPhrase("Phone Number: ", outputStringOrNA(patientItem.getPhoneNumber())));
        table.completeRow();

        return table;
    }

    /**
     * Builds the Encounter Info Table - The names and dates of each stage of the encounter
     *
     * @param encounter the encounter item
     * @return PdfPTable the itext table to add to the document
     */
    private PdfPTable createEncounterInfoTable(PatientEncounterItem encounter) {

        PdfPTable table = getDefaultTable(3);

        table.addCell(getDefaultHeaderCell("Encounter Information", 3));

//        // Nurse
//        PdfPCell cell = new PdfPCell(table.getDefaultCell());
//        Paragraph title = new Paragraph("Nurse:", getTitleFont());
//        Paragraph value = new Paragraph(outputStringOrNA(encounter.getNurseEmailAddress()), getValueFont());
//        cell.addElement(title);
//        cell.addElement(value);
//        table.addCell(cell);
//
//        // Physician
//        cell = new PdfPCell(table.getDefaultCell());
//        title = new Paragraph("Physician:", getTitleFont());
//        value = new Paragraph(outputStringOrNA(encounter.getPhysicianEmailAddress()), getValueFont());
//        cell.addElement(title);
//        cell.addElement(value);
//        table.addCell(cell);
//
//        // Pharmacist
//        cell = new PdfPCell(table.getDefaultCell());
//        title = new Paragraph("Pharmacist:", getTitleFont());
//        value = new Paragraph(outputStringOrNA(encounter.getPharmacistEmailAddress()), getValueFont());
//        cell.addElement(title);
//        cell.addElement(value);
//        table.addCell(cell);

        // Nurse
        PdfPCell cell = new PdfPCell(table.getDefaultCell());
        Paragraph title = new Paragraph("Nurse:", getTitleFont());
        Paragraph value = new Paragraph(outputStringOrNA(encounter.getNurseFullName()), getValueFont()); //Andrew Change
        cell.addElement(title);
        cell.addElement(value);
        table.addCell(cell);

        // Physician
        cell = new PdfPCell(table.getDefaultCell());
        title = new Paragraph("Physician:", getTitleFont());
        value = new Paragraph(outputStringOrNA(encounter.getPhysicianFullName()), getValueFont()); //Andrew Change
        cell.addElement(title);
        cell.addElement(value);
        table.addCell(cell);

        // Pharmacist
        cell = new PdfPCell(table.getDefaultCell());
        title = new Paragraph("Pharmacist:", getTitleFont());
        value = new Paragraph(outputStringOrNA(encounter.getPharmacistFullName()), getValueFont()); //Andrew Change
        cell.addElement(title);
        cell.addElement(value);
        table.addCell(cell);


        // Triage
        cell = new PdfPCell(table.getDefaultCell());
        title = new Paragraph("Triage Visit:", getTitleFont());
        value = new Paragraph(outputStringOrNA(encounter.getTriageDateOfVisit()), getValueFont());
        cell.addElement(title);
        cell.addElement(value);
        table.addCell(cell);

        // Medical
        cell = new PdfPCell(table.getDefaultCell());
        title = new Paragraph("Medical Visit:", getTitleFont());
        value = new Paragraph(outputStringOrNA(encounter.getMedicalDateOfVisit()), getValueFont());
        cell.addElement(title);
        cell.addElement(value);
        table.addCell(cell);

        // Pharmacy
        cell = new PdfPCell(table.getDefaultCell());
        title = new Paragraph("Pharmacy Visit:", getTitleFont());
        value = new Paragraph(outputStringOrNA(encounter.getPharmacyDateOfVisit()), getValueFont());
        cell.addElement(title);
        cell.addElement(value);
        table.addCell(cell);

        return table;
    }

    /**
     * Builds the Vitals Table - The vitals of the encounter
     *
     * @param encounter the encounter item
     * @param vitalMap the map of the vital values
     * @return PdfPTable the itext table to add to the document
     */
    private PdfPTable createVitalsTable(PatientEncounterItem encounter, VitalMultiMap vitalMap) {

        PdfPTable table = getDefaultTable(3);

        table.addCell(getDefaultHeaderCell("Patient Vitals", 3));

        table.addCell(getVitalMapCell("Blood Pressure: ", "bloodPressure", vitalMap));
        table.addCell(getVitalMapCell("Temperature (°F):", "temperature", vitalMap));
        table.addCell(getVitalMapCell("Temperature (°C):", "temperatureCelsius", vitalMap));
//        table.completeRow();

        table.addCell(getVitalMapCell("Glucose:", "glucose", vitalMap));
        table.addCell(getVitalMapCell("Height (Imperial):", "heightImperial", vitalMap));
        table.addCell(getVitalMapCell("Height (Metric):", "heightMetric", vitalMap));
//        table.completeRow();

        table.addCell(getVitalMapCell("Heart Rate: ", "heartRate", vitalMap));
        table.addCell(getVitalMapCell("Weight (lbs):", "weight", vitalMap));
        table.addCell(getVitalMapCell("Weight (kg):", "weightKgs", vitalMap));
//        table.completeRow();

        table.addCell(getVitalMapCell("Respiration Rate:", "respiratoryRate", vitalMap));
        table.addCell(getVitalMapCell("Oxygen Saturation:", "oxygenSaturation", vitalMap));

        //Sam Zanni
        PdfPCell cell = new PdfPCell(table.getDefaultCell());
        cell.setPaddingTop(2);
        table.addCell(getVitalMapCell("Weeks Pregnant:", "weeksPregnant", vitalMap));
//        table.completeRow();

        return table;
    }

    /**
     * Builds the Assessments Table - The assessment fields for the encounter
     *
     * @param tabFieldMultiMap multimap of the encounter's tab fields
     * @param prescriptionItems a list of the encounter's prescriptions
     * @param problemItems a list of the encounter's problems
     * @return PdfPTable the itext table to add to the document
     */
    private PdfPTable getAssessments(TabFieldMultiMap tabFieldMultiMap, List<PrescriptionItem> prescriptionItems , List<ProblemItem> problemItems) {

        PdfPTable table = getDefaultTable(3);   //Set table to span 3 columns to counteract tablesize for dispensed prescriptions
        table.addCell(getDefaultHeaderCell("Assessments", 3));

        // Row 1
        PdfPCell cellMSH = new PdfPCell(table.getDefaultCell());
        TabFieldItem msh = tabFieldMultiMap.getMostRecentOrEmpty("medicalSurgicalHistory", null);
        cellMSH.addElement(getStyledPhrase("Medical Surgical History: ", outputStringOrNA(msh.getValue())));
        cellMSH.setColspan(3);
        table.addCell(cellMSH);

        // Row 2
        PdfPCell cellCM = new PdfPCell(table.getDefaultCell());
        TabFieldItem cm = tabFieldMultiMap.getMostRecentOrEmpty("currentMedication", null);
        cellCM.addElement(getStyledPhrase("Medication: ", outputStringOrNA(cm.getValue())));
        cellCM.setColspan(3);
        table.addCell(cellCM);

        // Row 3
        PdfPCell cellSH = new PdfPCell(table.getDefaultCell());
        TabFieldItem sh = tabFieldMultiMap.getMostRecentOrEmpty("socialHistory", null);
        cellSH.addElement(getStyledPhrase("Social History: ", outputStringOrNA(sh.getValue())));
        cellSH.setColspan(3);
        table.addCell(cellSH);

        // Row 4
        PdfPCell cellAssesment = new PdfPCell(table.getDefaultCell());
        TabFieldItem assessment = tabFieldMultiMap.getMostRecentOrEmpty("assessment", null);
        cellAssesment.addElement(getStyledPhrase("Assessment: ", outputStringOrNA(assessment.getValue())));
        cellAssesment.setColspan(3);
        table.addCell(cellAssesment);

        // Row 5
        PdfPCell cellFH = new PdfPCell(table.getDefaultCell());
        TabFieldItem fh = tabFieldMultiMap.getMostRecentOrEmpty("familyHistory", null);
        cellFH.addElement(getStyledPhrase("Family History: ", outputStringOrNA(fh.getValue())));
        cellFH.setColspan(3);
        table.addCell(cellFH);

        // Row 6
        PdfPCell cellTreatment = new PdfPCell(table.getDefaultCell());
        TabFieldItem treatment = tabFieldMultiMap.getMostRecentOrEmpty("procedure_counseling", null);
        cellTreatment.addElement(getStyledPhrase("Procedure/Counseling: ", outputStringOrNA(treatment.getValue())));
        cellTreatment.setColspan(3);
        table.addCell(cellTreatment);

        // Loop through and add any potential Custom Field Names
        // Row 7+ , set cells to colspan of 2 so they fill the whole page
        for (String customField : tabFieldMultiMap.getCustomFieldNameList()) {

            String value = tabFieldMultiMap.getMostRecentOrEmpty(customField, null).getValue();
            PdfPCell customCell = new PdfPCell(table.getDefaultCell());
            customCell.setColspan(3);
            customCell.addElement(getStyledPhrase(customField + " :", outputStringOrNA(value)));
            table.addCell(customCell);
        }

        // AJ Saclayan Dispensed Table
        PdfPCell prescriptionCell = new PdfPCell(table.getDefaultCell());

        if(!prescriptionItems.isEmpty()) {
            //If there are prescriptions use the default formatting for prescriptions
            PdfPCell individualPrescriptionCell;
            //Create Dispensed Table.

            /*Paragraph originalMedsTitle = new Paragraph("Original", getTitleFont());
            cell = new PdfPCell(originalMedsTitle);

            table.addCell(cell);

            Paragraph replacedMedsTitle = new Paragraph("Replacement", getTitleFont());
            cell = new PdfPCell(replacedMedsTitle);

            table.addCell(cell);

            table.completeRow();*/

            Paragraph prescriptionsTitle = new Paragraph("Dispensed Prescription(s):", getTitleFont());

            prescriptionCell.setPaddingRight(10);
            prescriptionCell.addElement(prescriptionsTitle);
            prescriptionCell.setColspan(3);
            table.addCell(prescriptionCell);

            for (PrescriptionItem prescription : prescriptionItems) {
                String medicationForm = prescription.getMedicationForm();

                if (medicationForm == null || medicationForm.equals("")) {
                    medicationForm = "N/A";
                } else {
                    medicationForm = medicationForm.trim();
                }

                individualPrescriptionCell = new HtmlCell();
                if (prescription.getReplacementMedicationName() != null) {
                    individualPrescriptionCell.addElement(new Paragraph("Prescription #" + prescription.getId() + " - REPLACED", getValueFont()));
                    individualPrescriptionCell.addElement(new Paragraph(prescription.printFullPrescriptionName(), getValueFontStrikethrough()));

                    individualPrescriptionCell.addElement(
                            new Paragraph(
                            "This prescription was replaced by prescription #" + prescription.getReplacementId(),
                            getValueFont())
                    );
                } else {
                    individualPrescriptionCell.addElement(new Paragraph("Prescription #" + prescription.getId(), getValueFont()));
                    individualPrescriptionCell.addElement(new Paragraph(prescription.printFullPrescriptionName()));
                }

                individualPrescriptionCell.setColspan(2);
                individualPrescriptionCell.setPadding(5);
                table.addCell(individualPrescriptionCell);

            }
        } else {
            //If there are no prescriptions, display the title and N/A on the same line
            prescriptionCell.addElement(getStyledPhrase("Dispensed Prescription(s): ", "N/A"));
        }
        //Add Prescriptions cell to the table
        table.addCell(prescriptionCell);
        table.completeRow();

        // Get Problems
        PdfPCell problemsCell = new PdfPCell(table.getDefaultCell());
        if(!problemItems.isEmpty()){

            //If there are problemItems, then use the default title element with other elements
            Paragraph problemsTitle = new Paragraph("Problem(s):", getTitleFont());
            problemsCell.addElement(problemsTitle);

            for (ProblemItem problem : problemItems) {
                Paragraph probText = new Paragraph(" - "+problem.getName(), getValueFont());
                problemsCell.addElement(probText);
            }
        } else {
            //If no problems, display the Problems section title and N/A on the same line as a styled Phrase
            problemsCell.addElement(getStyledPhrase("Problem(s): ", "N/A"));
        }
        //Add the problems section cell to the Assessment section.
        table.addCell(problemsCell);

        table.completeRow();
        return table;
    }

    /**
     * Builds the Chief Complaints Table - The fields unique to each chief complaint, if there is one
     *
     * @param tabFieldMultiMap multimap of the encounter's tab fields
     * @return PdfPTable the itext table to add to the document
     */
    private PdfPTable getHPIAndChiefComplaintTable(TabFieldMultiMap tabFieldMultiMap){

        PdfPTable table = getDefaultTable(2);

        PdfPCell header = getDefaultHeaderCell("HPI & Chief Complaint", 2);
        table.addCell(header);

        if (tabFieldMultiMap.getChiefComplaintList().isEmpty()) {
            // Add treatment fields for null chief complaint
            addHPIAndChiefComplaintSectionToTable(table, null, tabFieldMultiMap);
        }
        else {
            // Add treatment fields for all chief complaints
            for (String chiefComplaint : tabFieldMultiMap.getChiefComplaintList()) {

                addHPIAndChiefComplaintSectionToTable(table, chiefComplaint, tabFieldMultiMap);
            }
        }

        return table;
    }

    /**
     * Adds the fields for the cheif complaint to the passed in table
     *
     * @param table the PdfPTable object to add the rows to
     * @param chiefComplaint the chief complaint as a string or null
     * @param tabFieldMultiMap multimap of the encounter's tab fields
     */
    private void addHPIAndChiefComplaintSectionToTable(PdfPTable table, String chiefComplaint, TabFieldMultiMap tabFieldMultiMap){

        PdfPCell cellCC = new PdfPCell(table.getDefaultCell());
        cellCC.addElement(getStyledPhrase("Chief Complaint: ", outputStringOrNA(chiefComplaint)));
        cellCC.setColspan(2);
        table.addCell(cellCC);

        // Known Field Names
        // Put styled phrase into a cell, then add it to the table
        PdfPCell onsetC = new PdfPCell(table.getDefaultCell());
        onsetC.addElement(getStyledPhrase("Onset: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("onset", chiefComplaint).getValue())));
        table.addCell(onsetC);

        PdfPCell fieldCell = new PdfPCell(table.getDefaultCell());
        fieldCell.setPaddingRight(5);
        fieldCell.addElement(getStyledPhrase("Quality: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("quality", chiefComplaint).getValue())));
        table.addCell(fieldCell);

        fieldCell = new PdfPCell(table.getDefaultCell());
        fieldCell.setPaddingRight(5);
        fieldCell.addElement(getStyledPhrase("Severity: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("severity", chiefComplaint).getValue())));
        table.addCell(fieldCell);

        fieldCell = new PdfPCell(table.getDefaultCell());
        fieldCell.setPaddingRight(5);
        fieldCell.addElement(getStyledPhrase("Provokes: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("provokes", chiefComplaint).getValue())));
        table.addCell(fieldCell);

        fieldCell = new PdfPCell(table.getDefaultCell());
        fieldCell.setPaddingRight(5);
        fieldCell.addElement(getStyledPhrase("Palliates: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("palliates", chiefComplaint).getValue())));
        table.addCell(fieldCell);

        fieldCell = new PdfPCell(table.getDefaultCell());
        fieldCell.setPaddingRight(5);
        fieldCell.addElement(getStyledPhrase("TimeOfDay: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("timeOfDay", chiefComplaint).getValue())));
        table.addCell(fieldCell);

        fieldCell = new PdfPCell(table.getDefaultCell());
        fieldCell.setPaddingRight(5);
        fieldCell.addElement(getStyledPhrase("Radiation: ", outputStringOrNA(tabFieldMultiMap.getMostRecentOrEmpty("radiation", chiefComplaint).getValue())));
        table.addCell(fieldCell);

        // Physical Examination
        PdfPCell cellPE = new PdfPCell(table.getDefaultCell());
        TabFieldItem fieldItem = tabFieldMultiMap.getMostRecentOrEmpty("physicalExamination", chiefComplaint);
        cellPE.addElement(getStyledPhrase("Physical Examination: ", outputStringOrNA(fieldItem.getValue())));
        cellPE.setColspan(2);
        table.addCell(cellPE);

        // Narrative
        PdfPCell cellNarrative = new PdfPCell(table.getDefaultCell());
        fieldItem = tabFieldMultiMap.getMostRecentOrEmpty("narrative", chiefComplaint);
        cellNarrative.addElement(getStyledPhrase("Narrative: ", outputStringOrNA(fieldItem.getValue())));
        cellNarrative.setColspan(2);
        table.addCell(cellNarrative);

        // add an empty row to add spacing between HPI and chief complaints section.
        table.addCell(" ");
        table.completeRow();
    }



    /**
     * Default Styles
     *
     * Trying to remove some code duplication and set some base styles
     *
     * **/

    /**
     * Builds a table with numColumns with the base stylins
     * @param numColumns the number of columns the table will have
     * @return an instantiated PdfPTable object
     */
    private PdfPTable getDefaultTable(int numColumns){
        PdfPTable table = new PdfPTable(numColumns);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10);
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        return table;
    }

    /**
     * Builds the Header Cell used for every section of the document
     *
     * @param title the title for the cell
     * @param colspan the number of columns in the table it will be added to
     * @return a formatted PdfPCell ready to insert into a PdfPTable
     */
    private PdfPCell getDefaultHeaderCell(String title, int colspan){

        PdfPCell cell = new PdfPCell();
        Paragraph titleParagraph = new Paragraph(title, new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));
        cell.addElement(titleParagraph);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(colspan);
        cell.setBorderColorBottom(BaseColor.DARK_GRAY);
        cell.setBorderWidthBottom(1);
        cell.setPaddingBottom(5);

        return cell;
    }

    /**
     * Most values are in the format
     *  title: value
     *
     *  This returns the font used for the title portion
     *
     * @return the Font used for Titles on the pdf
     */
    private Font getTitleFont(){

        return new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
    }

    /**
     * Most values are in the format
     *  title: value
     *
     *  This returns the font used for the value portion
     *
     * @return the Font used for Values on the pdf
     */
    private Font getValueFont(){
        return new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
    }

    private Font getValueFontStrikethrough(){
        return new Font(Font.FontFamily.HELVETICA, 11, Font.STRIKETHRU, BaseColor.BLACK);
    }

    private Phrase getStyledPhrase(String title, String value) {
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(title, getTitleFont()));
        phrase.add(new Chunk(value, getValueFont()));
        return phrase;
    }


    // @TODO - Add the units to each measurement
    /**
     * Builds a cell that lists all values for the given key present in the vital map,
     *  one measurement per line
     *
     * @param titleString The title of the vital map cell
     * @param key The key to get the values in the vital map
     * @param vitalMap the vital map that has all the values
     * @return PdfPCell the table cell formatted with the requested vital elements
     */
    private PdfPCell getVitalMapCell(String titleString, String key, VitalMultiMap vitalMap){

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);

        // Add the title
        Paragraph title = new Paragraph(titleString, getTitleFont());
        cell.addElement(title);

        if(!vitalMap.getDateListChronological().isEmpty()){
            // For each vital value in the map, if there are any add a new Paragraph element
            for (int dateIndex = 1; dateIndex <= vitalMap.getDateListChronological().size(); dateIndex++) {

                String value;
                if( key.equals("bloodPressure") ){

                    value = outputStringOrNA(vitalMap.get("bloodPressureSystolic", vitalMap.getDate(dateIndex - 1)));
                    value += '/' + outputStringOrNA(vitalMap.get("bloodPressureDiastolic", vitalMap.getDate(dateIndex - 1)));
                }
                else if( key.equals("heightImperial")){
                    //vitalMap.get could be null, causing nullpointer exception in all these casts. Check for this.
                    Integer feet;
                    Integer inches;
                    try{
                        feet = Double.valueOf(vitalMap.get("heightFeet", vitalMap.getDate(dateIndex - 1))).intValue();
                        inches = Double.valueOf(vitalMap.get("heightInches", vitalMap.getDate(dateIndex - 1))).intValue();
                    } catch(Exception e){
                        feet = null;
                        inches = null;
                    }
                    value = outputHeightImperialOrNA(feet, inches);

                }
                else if( key.equals(("heightMetric"))){
                    //vitalMap.get could be null, causing nullpointer exception in all these casts. Check for this.
                    Integer meters;
                    Integer cm;
                    try{
                        meters = Double.valueOf(vitalMap.get("heightMeters", vitalMap.getDate(dateIndex - 1))).intValue();
                        cm =  Double.valueOf(vitalMap.get("heightCm", vitalMap.getDate(dateIndex - 1))).intValue();
                    } catch (Exception e){
                        meters = null;
                        cm = null;
                    }

                    value = outputHeightMetricOrNA(meters, cm);
                }
                else {
                    value = outputStringOrNA(vitalMap.get(key, vitalMap.getDate(dateIndex - 1)));
                }
                //Since there could be multiple vital measurements in a day (seen in triage then in medical),
                //multiple numbers will pop up. Tack the time onto them to at least given them a chronological order.
                String date = vitalMap.getDateListChronological().get(dateIndex-1);
                Paragraph p = new Paragraph(outputStringOrNA(value) + " at " + date);
                cell.addElement(p);


            }
        } else {
            cell.addElement(new Paragraph("N/A"));
        }


        return cell;
    }

}
