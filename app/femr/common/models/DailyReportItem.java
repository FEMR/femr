package femr.common.models;

/**
 * Daily Report export item - holds all possible fields from EMT-MDS Daily Reporting Form.
 * Fields can be null/missing since not all fields are always provided.
 * 
 * Sections:
 * - Team Information (org, team, contact, dates)
 * - Number of Patients / Bed Count (consultations, new patients, bed capacity, ICU)
 * - Age Categories (male/female/pregnant by age group)
 * - Health Events (injuries, infections, mental health, complications, procedures)
 * - Needs and Risks (supply/staffing issues)
 * - Outcomes (discharge, death, referral)
 */
public class DailyReportItem {
    
    // ===== TEAM INFORMATION =====
    private String organizationName;
    private String teamName;
    private String teamType;  // Type 1, Type 2, Type 3, Specialized
    private String contactPersonName;
    private String phoneNumber;
    private String email;
    private String estimatedDateOfDeparture;
    private String dateOfActivity;
    private String timeOfReporting;
    private String state;
    private String city;
    private String village;
    private String facilityName;
    private String gaoTag;
    
    // ===== NUMBER OF PATIENTS / BED COUNT =====
    private Integer totalNumberOfNewConsultations;
    private Integer totalAdmissions;
    private Integer liveBirth;
    private Integer totalBedCapacity;
    private Integer emptyIntensiveCareUnitBedEcu;
    
    // ===== AGE CATEGORIES (Male and Female breakdowns) =====
    // Age: <1
    private Integer maleLessThan1;
    private Integer femaleLessThan1;
    private Integer femalePregnantLessThan1;
    
    // Age: 1-4
    private Integer male1To4;
    private Integer female1To4;
    private Integer femalePregnant1To4;
    
    // Age: 5-17
    private Integer male5To17;
    private Integer female5To17;
    private Integer femalePregnant5To17;
    
    // Age: 18-64
    private Integer male18To64;
    private Integer female18To64;
    private Integer femalePregnant18To64;
    
    // Age: 65+
    private Integer male65Plus;
    private Integer female65Plus;
    private Integer femalePregnant65Plus;
    
    // ===== HEALTH EVENTS (Cases by condition) =====
    private Integer majorHeadFaceInjury;
    private Integer majorTorsoInjury;
    private Integer majorExtremityInjury;
    private Integer moderateInjury;
    private Integer minorInjury;
    private Integer upperRespiratoryInfection;
    private Integer acuteWateryDiarrhea;
    private Integer acuteBloodyDiarrhea;
    private Integer acuteJaundiceSyndrome;
    private Integer suspectedMeasles;
    private Integer suspectedMeningitis;
    private Integer suspectedTetanus;
    private Integer acuteFlacidParalysis;
    private Integer acuteHaemorrhagicFever;
    private Integer feverOfUnknownOrigin;
    private Integer surgicalEmergency;
    private Integer medicalEmergency;
    private Integer skinDisease;
    private Integer acuteMentalHealthProblem;
    private Integer diabeticComplications;
    private Integer severeAcuteMalnutritionSam;
    private Integer otherDiagnosisNotSpecifiedAbove;
    
    // ===== PROCEDURES (Cases by procedure type) =====
    private Integer majorProcedureExcludingHds31;
    private Integer limbAmputationExcludingDigits;
    private Integer minorSurgicalProcedure;
    private Integer normalVaginalDeliveryNvd;
    private Integer caesareanSection;
    private Integer obstetricsOthers;
    
    // ===== OUTCOMES =====
    private Boolean dischargeWithoutMedicalFollowUp;
    private Boolean dischargeWithMedicalFollowUp;
    private Boolean dischargeAgainstMedicalAdvice;
    private Boolean referral;
    private Boolean deathWithinFacility;
    private Boolean deathAfterDischarge;
    
    // ===== OUTCOMES - OUTCOMES TO EVENT (Indicators) =====
    private Boolean directlyRelatedToEvent;
    private Boolean indirectlyRelatedToEvent;
    private Boolean notRelatedToEvent;
    private Boolean vulnerableChild;
    private Boolean sexualGenderBasedViolenceSgbv;
    private Boolean violenceOwnSgbv;
    
    // ===== NEEDS AND RISKS (Checkboxes) =====
    private Boolean unexpectedDeaths;
    private Boolean insufficientPersonnel;
    private Boolean notAvailableDiseaseProtection;
    private Boolean protectionIssue;
    private Boolean criticalIncidentEmtOrCommunity;
    private Boolean vaccinationIssueRequiringImmediateReporting;
    private Boolean communityOrSuspectedCoverInfectiousDisease;
    private Boolean environmentalRiskOrExposure;
    private Boolean shelterOrFoodItems;
    private Boolean foodInsecurity;
    private Boolean logisticsOrOperationalSupport;
    private Boolean supplies;
    private Boolean humanResources;
    private Boolean security;
    private Boolean finance;
    
    // ===== GENERAL NOTES =====
    private String detailedComment1;
    private String detailedComment2;
    private String detailedComment3;
    private String detailedCommentFinal;
    
    // ===== GETTERS AND SETTERS =====
    
    // Team Information
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    
    public String getTeamType() { return teamType; }
    public void setTeamType(String teamType) { this.teamType = teamType; }
    
    public String getContactPersonName() { return contactPersonName; }
    public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getEstimatedDateOfDeparture() { return estimatedDateOfDeparture; }
    public void setEstimatedDateOfDeparture(String estimatedDateOfDeparture) { this.estimatedDateOfDeparture = estimatedDateOfDeparture; }
    
    public String getDateOfActivity() { return dateOfActivity; }
    public void setDateOfActivity(String dateOfActivity) { this.dateOfActivity = dateOfActivity; }
    
    public String getTimeOfReporting() { return timeOfReporting; }
    public void setTimeOfReporting(String timeOfReporting) { this.timeOfReporting = timeOfReporting; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }
    
    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    
    public String getGaoTag() { return gaoTag; }
    public void setGaoTag(String gaoTag) { this.gaoTag = gaoTag; }
    
    // Patient/Bed Count
    public Integer getTotalNumberOfNewConsultations() { return totalNumberOfNewConsultations; }
    public void setTotalNumberOfNewConsultations(Integer totalNumberOfNewConsultations) { this.totalNumberOfNewConsultations = totalNumberOfNewConsultations; }
    
    public Integer getTotalAdmissions() { return totalAdmissions; }
    public void setTotalAdmissions(Integer totalAdmissions) { this.totalAdmissions = totalAdmissions; }
    
    public Integer getLiveBirth() { return liveBirth; }
    public void setLiveBirth(Integer liveBirth) { this.liveBirth = liveBirth; }
    
    public Integer getTotalBedCapacity() { return totalBedCapacity; }
    public void setTotalBedCapacity(Integer totalBedCapacity) { this.totalBedCapacity = totalBedCapacity; }
    
    public Integer getEmptyIntensiveCareUnitBedEcu() { return emptyIntensiveCareUnitBedEcu; }
    public void setEmptyIntensiveCareUnitBedEcu(Integer emptyIntensiveCareUnitBedEcu) { this.emptyIntensiveCareUnitBedEcu = emptyIntensiveCareUnitBedEcu; }
    
    // Age Categories - <1
    public Integer getMaleLessThan1() { return maleLessThan1; }
    public void setMaleLessThan1(Integer maleLessThan1) { this.maleLessThan1 = maleLessThan1; }
    
    public Integer getFemaleLessThan1() { return femaleLessThan1; }
    public void setFemaleLessThan1(Integer femaleLessThan1) { this.femaleLessThan1 = femaleLessThan1; }
    
    public Integer getFemalePregnantLessThan1() { return femalePregnantLessThan1; }
    public void setFemalePregnantLessThan1(Integer femalePregnantLessThan1) { this.femalePregnantLessThan1 = femalePregnantLessThan1; }
    
    // Age Categories - 1-4
    public Integer getMale1To4() { return male1To4; }
    public void setMale1To4(Integer male1To4) { this.male1To4 = male1To4; }
    
    public Integer getFemale1To4() { return female1To4; }
    public void setFemale1To4(Integer female1To4) { this.female1To4 = female1To4; }
    
    public Integer getFemalePregnant1To4() { return femalePregnant1To4; }
    public void setFemalePregnant1To4(Integer femalePregnant1To4) { this.femalePregnant1To4 = femalePregnant1To4; }
    
    // Age Categories - 5-17
    public Integer getMale5To17() { return male5To17; }
    public void setMale5To17(Integer male5To17) { this.male5To17 = male5To17; }
    
    public Integer getFemale5To17() { return female5To17; }
    public void setFemale5To17(Integer female5To17) { this.female5To17 = female5To17; }
    
    public Integer getFemalePregnant5To17() { return femalePregnant5To17; }
    public void setFemalePregnant5To17(Integer femalePregnant5To17) { this.femalePregnant5To17 = femalePregnant5To17; }
    
    // Age Categories - 18-64
    public Integer getMale18To64() { return male18To64; }
    public void setMale18To64(Integer male18To64) { this.male18To64 = male18To64; }
    
    public Integer getFemale18To64() { return female18To64; }
    public void setFemale18To64(Integer female18To64) { this.female18To64 = female18To64; }
    
    public Integer getFemalePregnant18To64() { return femalePregnant18To64; }
    public void setFemalePregnant18To64(Integer femalePregnant18To64) { this.femalePregnant18To64 = femalePregnant18To64; }
    
    // Age Categories - 65+
    public Integer getMale65Plus() { return male65Plus; }
    public void setMale65Plus(Integer male65Plus) { this.male65Plus = male65Plus; }
    
    public Integer getFemale65Plus() { return female65Plus; }
    public void setFemale65Plus(Integer female65Plus) { this.female65Plus = female65Plus; }
    
    public Integer getFemalePregnant65Plus() { return femalePregnant65Plus; }
    public void setFemalePregnant65Plus(Integer femalePregnant65Plus) { this.femalePregnant65Plus = femalePregnant65Plus; }
    
    // Health Events
    public Integer getMajorHeadFaceInjury() { return majorHeadFaceInjury; }
    public void setMajorHeadFaceInjury(Integer majorHeadFaceInjury) { this.majorHeadFaceInjury = majorHeadFaceInjury; }
    
    public Integer getMajorTorsoInjury() { return majorTorsoInjury; }
    public void setMajorTorsoInjury(Integer majorTorsoInjury) { this.majorTorsoInjury = majorTorsoInjury; }
    
    public Integer getMajorExtremityInjury() { return majorExtremityInjury; }
    public void setMajorExtremityInjury(Integer majorExtremityInjury) { this.majorExtremityInjury = majorExtremityInjury; }
    
    public Integer getModerateInjury() { return moderateInjury; }
    public void setModerateInjury(Integer moderateInjury) { this.moderateInjury = moderateInjury; }
    
    public Integer getMinorInjury() { return minorInjury; }
    public void setMinorInjury(Integer minorInjury) { this.minorInjury = minorInjury; }
    
    public Integer getUpperRespiratoryInfection() { return upperRespiratoryInfection; }
    public void setUpperRespiratoryInfection(Integer upperRespiratoryInfection) { this.upperRespiratoryInfection = upperRespiratoryInfection; }
    
    public Integer getAcuteWateryDiarrhea() { return acuteWateryDiarrhea; }
    public void setAcuteWateryDiarrhea(Integer acuteWateryDiarrhea) { this.acuteWateryDiarrhea = acuteWateryDiarrhea; }
    
    public Integer getAcuteBloodyDiarrhea() { return acuteBloodyDiarrhea; }
    public void setAcuteBloodyDiarrhea(Integer acuteBloodyDiarrhea) { this.acuteBloodyDiarrhea = acuteBloodyDiarrhea; }
    
    public Integer getAcuteJaundiceSyndrome() { return acuteJaundiceSyndrome; }
    public void setAcuteJaundiceSyndrome(Integer acuteJaundiceSyndrome) { this.acuteJaundiceSyndrome = acuteJaundiceSyndrome; }
    
    public Integer getSuspectedMeasles() { return suspectedMeasles; }
    public void setSuspectedMeasles(Integer suspectedMeasles) { this.suspectedMeasles = suspectedMeasles; }
    
    public Integer getSuspectedMeningitis() { return suspectedMeningitis; }
    public void setSuspectedMeningitis(Integer suspectedMeningitis) { this.suspectedMeningitis = suspectedMeningitis; }
    
    public Integer getSuspectedTetanus() { return suspectedTetanus; }
    public void setSuspectedTetanus(Integer suspectedTetanus) { this.suspectedTetanus = suspectedTetanus; }
    
    public Integer getAcuteFlacidParalysis() { return acuteFlacidParalysis; }
    public void setAcuteFlacidParalysis(Integer acuteFlacidParalysis) { this.acuteFlacidParalysis = acuteFlacidParalysis; }
    
    public Integer getAcuteHaemorrhagicFever() { return acuteHaemorrhagicFever; }
    public void setAcuteHaemorrhagicFever(Integer acuteHaemorrhagicFever) { this.acuteHaemorrhagicFever = acuteHaemorrhagicFever; }
    
    public Integer getFeverOfUnknownOrigin() { return feverOfUnknownOrigin; }
    public void setFeverOfUnknownOrigin(Integer feverOfUnknownOrigin) { this.feverOfUnknownOrigin = feverOfUnknownOrigin; }
    
    public Integer getSurgicalEmergency() { return surgicalEmergency; }
    public void setSurgicalEmergency(Integer surgicalEmergency) { this.surgicalEmergency = surgicalEmergency; }
    
    public Integer getMedicalEmergency() { return medicalEmergency; }
    public void setMedicalEmergency(Integer medicalEmergency) { this.medicalEmergency = medicalEmergency; }
    
    public Integer getSkinDisease() { return skinDisease; }
    public void setSkinDisease(Integer skinDisease) { this.skinDisease = skinDisease; }
    
    public Integer getAcuteMentalHealthProblem() { return acuteMentalHealthProblem; }
    public void setAcuteMentalHealthProblem(Integer acuteMentalHealthProblem) { this.acuteMentalHealthProblem = acuteMentalHealthProblem; }
    
    public Integer getDiabeticComplications() { return diabeticComplications; }
    public void setDiabeticComplications(Integer diabeticComplications) { this.diabeticComplications = diabeticComplications; }
    
    public Integer getSevereAcuteMalnutritionSam() { return severeAcuteMalnutritionSam; }
    public void setSevereAcuteMalnutritionSam(Integer severeAcuteMalnutritionSam) { this.severeAcuteMalnutritionSam = severeAcuteMalnutritionSam; }
    
    public Integer getOtherDiagnosisNotSpecifiedAbove() { return otherDiagnosisNotSpecifiedAbove; }
    public void setOtherDiagnosisNotSpecifiedAbove(Integer otherDiagnosisNotSpecifiedAbove) { this.otherDiagnosisNotSpecifiedAbove = otherDiagnosisNotSpecifiedAbove; }
    
    // Procedures
    public Integer getMajorProcedureExcludingHds31() { return majorProcedureExcludingHds31; }
    public void setMajorProcedureExcludingHds31(Integer majorProcedureExcludingHds31) { this.majorProcedureExcludingHds31 = majorProcedureExcludingHds31; }
    
    public Integer getLimbAmputationExcludingDigits() { return limbAmputationExcludingDigits; }
    public void setLimbAmputationExcludingDigits(Integer limbAmputationExcludingDigits) { this.limbAmputationExcludingDigits = limbAmputationExcludingDigits; }
    
    public Integer getMinorSurgicalProcedure() { return minorSurgicalProcedure; }
    public void setMinorSurgicalProcedure(Integer minorSurgicalProcedure) { this.minorSurgicalProcedure = minorSurgicalProcedure; }
    
    public Integer getNormalVaginalDeliveryNvd() { return normalVaginalDeliveryNvd; }
    public void setNormalVaginalDeliveryNvd(Integer normalVaginalDeliveryNvd) { this.normalVaginalDeliveryNvd = normalVaginalDeliveryNvd; }
    
    public Integer getCaesareanSection() { return caesareanSection; }
    public void setCaesareanSection(Integer caesareanSection) { this.caesareanSection = caesareanSection; }
    
    public Integer getObstetricsOthers() { return obstetricsOthers; }
    public void setObstetricsOthers(Integer obstetricsOthers) { this.obstetricsOthers = obstetricsOthers; }
    
    // Outcomes
    public Boolean getDischargeWithoutMedicalFollowUp() { return dischargeWithoutMedicalFollowUp; }
    public void setDischargeWithoutMedicalFollowUp(Boolean dischargeWithoutMedicalFollowUp) { this.dischargeWithoutMedicalFollowUp = dischargeWithoutMedicalFollowUp; }
    
    public Boolean getDischargeWithMedicalFollowUp() { return dischargeWithMedicalFollowUp; }
    public void setDischargeWithMedicalFollowUp(Boolean dischargeWithMedicalFollowUp) { this.dischargeWithMedicalFollowUp = dischargeWithMedicalFollowUp; }
    
    public Boolean getDischargeAgainstMedicalAdvice() { return dischargeAgainstMedicalAdvice; }
    public void setDischargeAgainstMedicalAdvice(Boolean dischargeAgainstMedicalAdvice) { this.dischargeAgainstMedicalAdvice = dischargeAgainstMedicalAdvice; }
    
    public Boolean getReferral() { return referral; }
    public void setReferral(Boolean referral) { this.referral = referral; }
    
    public Boolean getDeathWithinFacility() { return deathWithinFacility; }
    public void setDeathWithinFacility(Boolean deathWithinFacility) { this.deathWithinFacility = deathWithinFacility; }
    
    public Boolean getDeathAfterDischarge() { return deathAfterDischarge; }
    public void setDeathAfterDischarge(Boolean deathAfterDischarge) { this.deathAfterDischarge = deathAfterDischarge; }
    
    // Outcomes Indicators
    public Boolean getDirectlyRelatedToEvent() { return directlyRelatedToEvent; }
    public void setDirectlyRelatedToEvent(Boolean directlyRelatedToEvent) { this.directlyRelatedToEvent = directlyRelatedToEvent; }
    
    public Boolean getIndirectlyRelatedToEvent() { return indirectlyRelatedToEvent; }
    public void setIndirectlyRelatedToEvent(Boolean indirectlyRelatedToEvent) { this.indirectlyRelatedToEvent = indirectlyRelatedToEvent; }
    
    public Boolean getNotRelatedToEvent() { return notRelatedToEvent; }
    public void setNotRelatedToEvent(Boolean notRelatedToEvent) { this.notRelatedToEvent = notRelatedToEvent; }
    
    public Boolean getVulnerableChild() { return vulnerableChild; }
    public void setVulnerableChild(Boolean vulnerableChild) { this.vulnerableChild = vulnerableChild; }
    
    public Boolean getSexualGenderBasedViolenceSgbv() { return sexualGenderBasedViolenceSgbv; }
    public void setSexualGenderBasedViolenceSgbv(Boolean sexualGenderBasedViolenceSgbv) { this.sexualGenderBasedViolenceSgbv = sexualGenderBasedViolenceSgbv; }
    
    public Boolean getViolenceOwnSgbv() { return violenceOwnSgbv; }
    public void setViolenceOwnSgbv(Boolean violenceOwnSgbv) { this.violenceOwnSgbv = violenceOwnSgbv; }
    
    // Needs and Risks
    public Boolean getUnexpectedDeaths() { return unexpectedDeaths; }
    public void setUnexpectedDeaths(Boolean unexpectedDeaths) { this.unexpectedDeaths = unexpectedDeaths; }
    
    public Boolean getInsufficientPersonnel() { return insufficientPersonnel; }
    public void setInsufficientPersonnel(Boolean insufficientPersonnel) { this.insufficientPersonnel = insufficientPersonnel; }
    
    public Boolean getNotAvailableDiseaseProtection() { return notAvailableDiseaseProtection; }
    public void setNotAvailableDiseaseProtection(Boolean notAvailableDiseaseProtection) { this.notAvailableDiseaseProtection = notAvailableDiseaseProtection; }
    
    public Boolean getProtectionIssue() { return protectionIssue; }
    public void setProtectionIssue(Boolean protectionIssue) { this.protectionIssue = protectionIssue; }
    
    public Boolean getCriticalIncidentEmtOrCommunity() { return criticalIncidentEmtOrCommunity; }
    public void setCriticalIncidentEmtOrCommunity(Boolean criticalIncidentEmtOrCommunity) { this.criticalIncidentEmtOrCommunity = criticalIncidentEmtOrCommunity; }
    
    public Boolean getVaccinationIssueRequiringImmediateReporting() { return vaccinationIssueRequiringImmediateReporting; }
    public void setVaccinationIssueRequiringImmediateReporting(Boolean vaccinationIssueRequiringImmediateReporting) { this.vaccinationIssueRequiringImmediateReporting = vaccinationIssueRequiringImmediateReporting; }
    
    public Boolean getCommunityOrSuspectedCoverInfectiousDisease() { return communityOrSuspectedCoverInfectiousDisease; }
    public void setCommunityOrSuspectedCoverInfectiousDisease(Boolean communityOrSuspectedCoverInfectiousDisease) { this.communityOrSuspectedCoverInfectiousDisease = communityOrSuspectedCoverInfectiousDisease; }
    
    public Boolean getEnvironmentalRiskOrExposure() { return environmentalRiskOrExposure; }
    public void setEnvironmentalRiskOrExposure(Boolean environmentalRiskOrExposure) { this.environmentalRiskOrExposure = environmentalRiskOrExposure; }
    
    public Boolean getShelterOrFoodItems() { return shelterOrFoodItems; }
    public void setShelterOrFoodItems(Boolean shelterOrFoodItems) { this.shelterOrFoodItems = shelterOrFoodItems; }
    
    public Boolean getFoodInsecurity() { return foodInsecurity; }
    public void setFoodInsecurity(Boolean foodInsecurity) { this.foodInsecurity = foodInsecurity; }
    
    public Boolean getLogisticsOrOperationalSupport() { return logisticsOrOperationalSupport; }
    public void setLogisticsOrOperationalSupport(Boolean logisticsOrOperationalSupport) { this.logisticsOrOperationalSupport = logisticsOrOperationalSupport; }
    
    public Boolean getSupplies() { return supplies; }
    public void setSupplies(Boolean supplies) { this.supplies = supplies; }
    
    public Boolean getHumanResources() { return humanResources; }
    public void setHumanResources(Boolean humanResources) { this.humanResources = humanResources; }
    
    public Boolean getSecurity() { return security; }
    public void setSecurity(Boolean security) { this.security = security; }
    
    public Boolean getFinance() { return finance; }
    public void setFinance(Boolean finance) { this.finance = finance; }
    
    // Comments
    public String getDetailedComment1() { return detailedComment1; }
    public void setDetailedComment1(String detailedComment1) { this.detailedComment1 = detailedComment1; }
    
    public String getDetailedComment2() { return detailedComment2; }
    public void setDetailedComment2(String detailedComment2) { this.detailedComment2 = detailedComment2; }
    
    public String getDetailedComment3() { return detailedComment3; }
    public void setDetailedComment3(String detailedComment3) { this.detailedComment3 = detailedComment3; }
    
    public String getDetailedCommentFinal() { return detailedCommentFinal; }
    public void setDetailedCommentFinal(String detailedCommentFinal) { this.detailedCommentFinal = detailedCommentFinal; }
}
