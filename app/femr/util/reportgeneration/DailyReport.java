package femr.util.reportgeneration;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

//@Retention(RetentionPolicy.RUNTIME);

@Target(ElementType.FIELD)
@interface CSVFieldHeader {
    String value() default "UNKNOWN";
}

// based on 2019WHO MDS eData version
public class DailyReport {

    // TEAM INFORMATION
    // These will be static or configured once per EMT
    @CSVFieldHeader("a")
    public String organizationName;
    @CSVFieldHeader("b")
    public String teamName;
    @CSVFieldHeader("c1")
    public boolean isType1Mobile;
    @CSVFieldHeader("c2")
    public boolean isType1Fixed;
    @CSVFieldHeader("c3")
    public boolean isType2;
    @CSVFieldHeader("c4")
    public boolean isType3;
    @CSVFieldHeader("c5")
    public boolean isType4;
    @CSVFieldHeader("d")
    public String contactPersonName;
    @CSVFieldHeader("e")
    public int phoneNumber;
    @CSVFieldHeader("f")
    public String email;
    @CSVFieldHeader("n")
    public LocalDate estimatedDateOfDeparture;
    @CSVFieldHeader("h")
    public LocalDate dateOfActivity;
    @CSVFieldHeader("g")
    public LocalDateTime dateOfDeparture;
        /*has to be in 24 hour format (yyyy/mm/dd/hh:mm)
        use with
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH:mm");
            LocalDateTime dt = LocalDateTime.parse("2025/11/06/14:30", formatter);
        */
    // LOCATION
    @CSVFieldHeader("i")
    public String locationState;
    @CSVFieldHeader("j")
    public String locationCity;
    @CSVFieldHeader("k")
    public String locationVillage;
    @CSVFieldHeader("l")
    public String locationFacilityName;
    @CSVFieldHeader("m")
    public String locationGeoTag;
        //do we want to keep it as a string or tuple of doubles


    // DAILY SUMMARY
    @CSVFieldHeader("o")
    public int patientsTotalNumberOfNewConsultations;
        //can be a static which is incremented every time a new patient is added
    @CSVFieldHeader("p")
    public int patientsNewAdmission;
    @CSVFieldHeader("q")
    public int patientsLiveBirth;

    @CSVFieldHeader("r")
    public int bedTotalBedCapacity; //r
    @CSVFieldHeader("s")
    public int bedEmptyInpatientBed; //s
    @CSVFieldHeader("t")
    public int bedEmptyIntensiveCareUnitBed; //t
    @CSVFieldHeader("u")
    public int reserveBlank; //u


    //DEMOGRAPHIC
    @CSVFieldHeader("MDS1:0")
    public int maleDemographics_under1;
    @CSVFieldHeader("MDS1:1-4")
    public int maleDemographics_range1to4;
    @CSVFieldHeader("MDS1:5-17")
    public int maleDemographics_range5to17;
    @CSVFieldHeader("MDS1:18-64")
    public int maleDemographics_range18to64;
    @CSVFieldHeader("MDS1:65-")
    public int maleDemographics_over65;
    @CSVFieldHeader("Total1")
    public int maleDemographics_total;

    @CSVFieldHeader("MDS2:0")
    public int femaleNonPregnantDemographics_under1;
    @CSVFieldHeader("MDS2:1-4")
    public int femaleNonPregnantDemographics_range1to4;
    @CSVFieldHeader("MDS2:5-17")
    public int femaleNonPregnantDemographics_range5to17;
    @CSVFieldHeader("MDS2:18-64")
    public int femaleNonPregnantDemographics_range18to64;
    @CSVFieldHeader("MDS2:65-")
    public int femaleNonPregnantDemographics_over65;
    @CSVFieldHeader("Total2")
    public int femaleNonPregnantDemographics_total;

    @CSVFieldHeader("MDS3:0")
    public int femalePregnantDemographics_under1;
    @CSVFieldHeader("MDS3:1-4")
    public int femalePregnantDemographics_range1to4;
    @CSVFieldHeader("MDS3:5-17")
    public int femalePregnantDemographics_range5to17;
    @CSVFieldHeader("MDS3:18-64")
    public int femalePregnantDemographics_range18to64;
    @CSVFieldHeader("MDS3:65-")
    public int femalePregnantDemographics_over65;
    @CSVFieldHeader("Total3")
    public int femalePregnantDemographics_total;

    //HEALTH EVENTS - TRAUMA
    @CSVFieldHeader("MDS4:0")
    public int traumaMajorHeadOrSpineInjury_none;
    @CSVFieldHeader("MDS4:5-")
    public int traumaMajorHeadOrSpineInjury_over5;

    @CSVFieldHeader("MDS5:0")
    public int traumaMajorTorsoInjury_none;
    @CSVFieldHeader("MDS5:5-")
    public int traumaMajorTorsoInjury_over5;

    @CSVFieldHeader("MDS6:0")
    public int traumaMajorExtremityInjury_none;
    @CSVFieldHeader("MDS6:5-")
    public int traumaMajorExtremityInjury_over5;

    @CSVFieldHeader("MDS7:0")
    public int traumaModerateInjury_none;
    @CSVFieldHeader("MDS7:5-")
    public int traumaModerateInjury_over5;

    @CSVFieldHeader("MDS8:0")
    public int traumaMinorInjury_none;
    @CSVFieldHeader("MDS8:5-")
    public int traumaMinorInjury_over5;

    // HEALTH EVENTS - INFECTIOUS DISEASE
    @CSVFieldHeader("MDS9:0")
    public int infectiousAcuteRespiratoryInfection_none;
    @CSVFieldHeader("MDS9:5-")
    public int infectiousAcuteRespiratoryInfection_over5;

    @CSVFieldHeader("MDS10:0")
    public int infectiousAcuteWateryDiarrhea_none;
    @CSVFieldHeader("MDS10:5-")
    public int infectiousAcuteWateryDiarrhea_over5;

    @CSVFieldHeader("MDS11:0")
    public int infectiousAcuteBloodyDiarrhea_none;
    @CSVFieldHeader("MDS11:5-")
    public int infectiousAcuteBloodyDiarrhea_over5;

    @CSVFieldHeader("MDS12:0")
    public int infectiousAcuteJaundiceSyndrome_none;
    @CSVFieldHeader("MDS12:5-")
    public int infectiousAcuteJaundiceSyndrome_over5;

    @CSVFieldHeader("MDS13:0")
    public int infectiousSuspectedMeasles_none;
    @CSVFieldHeader("MDS13:5-")
    public int infectiousSuspectedMeasles_over5;

    @CSVFieldHeader("MDS14:0")
    public int infectiousSuspectedMeningitis_none;
    @CSVFieldHeader("MDS14:5-")
    public int infectiousSuspectedMeningitis_over5;

    @CSVFieldHeader("MDS15:0")
    public int infectiousSuspectedTetanus_none;
    @CSVFieldHeader("MDS15:5-")
    public int infectiousSuspectedTetanus_over5;

    @CSVFieldHeader("MDS16:0")
    public int infectiousAcuteFlaccidParalysis_none;
    @CSVFieldHeader("MDS16:5-")
    public int infectiousAcuteFlaccidParalysis_over5;

    @CSVFieldHeader("MDS17:0")
    public int infectiousAcuteHaemorrhagicFever_none;
    @CSVFieldHeader("MDS17:5-")
    public int infectiousAcuteHaemorrhagicFever_over5;

    @CSVFieldHeader("MDS18:0")
    public int infectiousFeverOfUnknownOrigin_none;
    @CSVFieldHeader("MDS18:5-")
    public int infectiousFeverOfUnknownOrigin_over5;


    // HEALTH EVENTS - ADDITIONAL
    @CSVFieldHeader("MDS19")
    public int additionalAddedItem1;
    @CSVFieldHeader("MDS20")
    public int additionalAddedItem2;
    @CSVFieldHeader("MDS21")
    public int additionalAddedItem3;
    @CSVFieldHeader("MDS22")
    public int additionalAddedItem4;


    // HEALTH EVENTS - EMERGENCIES
    @CSVFieldHeader("MDS23")
    public int emgSurgicalEmergencyNonTrauma;
    @CSVFieldHeader("MDS24")
    public int emgMedicalEmergencyNonInfectious;

    @CSVFieldHeader("MDS25")
    public int otherKeyDiseasesSkinDisease;
    @CSVFieldHeader("MDS26")
    public int otherKeyDiseasesAcuteMentalHealthProblem;
    @CSVFieldHeader("MDS27")
    public int otherKeyDiseasesObstetricComplications;


    // PROCEDURES & OUTCOME
    @CSVFieldHeader("MDS28")
    public int procedureSevereAcuteMalnutritionSAM;
    @CSVFieldHeader("MDS29")
    public int procedureOtherDiagnosisNotSpecified;
    @CSVFieldHeader("MDS30")
    public int procedureMajorProcedureExcludingMDS31;
    @CSVFieldHeader("MDS31")
    public int procedureLimbAmputationExcludingDigits;
    @CSVFieldHeader("MDS32")
    public int procedureMinorSurgicalProcedure;
    @CSVFieldHeader("MDS33")
    public int procedureNormalVaginalDeliveryNVD;
    @CSVFieldHeader("MDS34")
    public int procedureCaesareanSection;
    @CSVFieldHeader("MDS35")
    public int procedureObstetricsOthers;

    @CSVFieldHeader("MDS36")
    public int outcomeDischargeWithoutMedicalFollowUp;
    @CSVFieldHeader("MDS37")
    public int outcomeDischargeWithMedicalFollowUp;
    @CSVFieldHeader("MDS38")
    public int outcomeDischargeAgainstMedicalAdvice;
    @CSVFieldHeader("MDS39")
    public int outcomeReferral;
    @CSVFieldHeader("MDS40")
    public int outcomeAdmission;
    @CSVFieldHeader("MDS41")
    public int outcomeDeadOnArrival;
    @CSVFieldHeader("MDS42")
    public int outcomeDeathWithinFacility;
    @CSVFieldHeader("MDS43")
    public int outcomeRequiringLongTermRehabilitation;


    // CONTEXT
    @CSVFieldHeader("MDS44")
    public int contextDirectlyRelatedToEvent;
    @CSVFieldHeader("MDS45")
    public int contextIndirectlyRelatedToEvent;
    @CSVFieldHeader("MDS46")
    public int contextNotRelatedToEvent;

    @CSVFieldHeader("MDS47")
    public int contextVulnerableChild;
    @CSVFieldHeader("MDS48")
    public int contextVulnerableAdult;
    @CSVFieldHeader("MDS49")
    public int contextSexualGenderBasedViolenceSGBV;
    @CSVFieldHeader("MDS50")
    public int contextViolenceNonSGBV;


    // NEEDS AND RISKS
    @CSVFieldHeader("MDS51")
    public int needsUnexpectedDeath;
    @CSVFieldHeader("MDS52")
    public int needsNotifiableDisease;
    @CSVFieldHeader("MDS53")
    public int needsProtectionIssues;
    @CSVFieldHeader("MDS54")
    public int needsCriticalIncidentToEMTAndCommunity;
    @CSVFieldHeader("MDS55")
    public int needsOtherIssueRequiringImmediateReporting;

    @CSVFieldHeader("MDS56")
    public int communityWASH;
    @CSVFieldHeader("MDS57")
    public int communitySuspectedOrInfectiousDisease;
    @CSVFieldHeader("MDS58")
    public int communityEnvironmentalRiskExposure;
    @CSVFieldHeader("MDS59")
    public int communityShelterNonFoodItems;
    @CSVFieldHeader("MDS60")
    public int communityFoodInsecurity;

    @CSVFieldHeader("MDS61")
    public int operationalLogisticsOperationalSupport;
    @CSVFieldHeader("MDS62")
    public int operationalSupply;
    @CSVFieldHeader("MDS63")
    public int operationalHumanResources;
    @CSVFieldHeader("MDS64")
    public int operationalFinance;
    @CSVFieldHeader("MDS65")
    public int operationalOthers;


    // DETAILS COMMENTS
    @CSVFieldHeader("NR1-No")
    public int detailedComment1Count;
    @CSVFieldHeader("NR1-DC")
    public String detailedComment1Text;

    @CSVFieldHeader("NR2-No")
    public int detailedComment2Count;
    @CSVFieldHeader("NR2-DC")
    public String detailedComment2Text;

    @CSVFieldHeader("NR3-No")
    public int detailedComment3Count;
    @CSVFieldHeader("NR3-DC")
    public String detailedComment3Text;

    @CSVFieldHeader("NR4-No")
    public int detailedComment4Count;
    @CSVFieldHeader("NR4-DC")
    public String detailedComment4Text;

    // OTHER
    @CSVFieldHeader("ToolVer")
    public String csvFileCreationToolNameAndVersion = "fEMR ?v3.1.2?";

    @CSVFieldHeader("Ver")
    public String emtMDSDailyReportingFormVersion = "2019WHO";

    // ====================METHODS=======================
    public DailyReport() {}
}
