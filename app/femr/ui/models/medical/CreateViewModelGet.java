package femr.ui.models.medical;


public class CreateViewModelGet {
    //begin patient
    private int pID;
    private String city;
    private String firstName;
    private String lastName;
    private int age;
    private String sex;
    //begin vitals
    private Integer bloodPressureSystolic;
    private Integer bloodPressureDiastolic;
    private Integer heartRate;
    private Float temperature;
    private Integer respiratoryRate;
    private Float oxygenSaturation;
    private Integer heightFeet;
    private Integer heightInches;
    private Float weight;
    private Float glucose;
    //begin encounter
    private String chiefComplaint;
    private Integer weeksPregnant;
    private Boolean isPregnant;

    //editable information - prescriptions
    private String prescription1;
    private String prescription2;
    private String prescription3;
    private String prescription4;
    private String prescription5;
    //editable information - Treatment_fields
    private String assessment;
    private String problem1;
    private String problem2;
    private String problem3;
    private String problem4;
    private String problem5;
    private String treatment;
    //editable information - HPI_fields
    private String onset;
    private String severity;
    private String radiation;
    private String quality;
    private String provokes;
    private String palliates;
    private String timeOfDay;
    private String physicalExamination;
    private String narrative;
    //editable information - PMH
    private String familyHistory;
    private String medicalSurgicalHistory;
    private String socialHistory;
    private String currentMedication;

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getBloodPressureSystolic() {
        return bloodPressureSystolic;
    }

    public void setBloodPressureSystolic(Integer bloodPressureSystolic) {
        this.bloodPressureSystolic = bloodPressureSystolic;
    }

    public Integer getBloodPressureDiastolic() {
        return bloodPressureDiastolic;
    }

    public void setBloodPressureDiastolic(Integer bloodPressureDiastolic) {
        this.bloodPressureDiastolic = bloodPressureDiastolic;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Float oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public Integer getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Integer respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Integer getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(Integer heightFeet) {
        this.heightFeet = heightFeet;
    }

    public Integer getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(Integer heightInches) {
        this.heightInches = heightInches;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public Boolean getIsPregnant() {
        return isPregnant;
    }

    public Integer getWeeksPregnant() {
        return weeksPregnant;
    }

    public void setWeeksPregnant(Integer weeksPregnant) {
        this.weeksPregnant = weeksPregnant;
    }

    public String getPrescription1() {
        return prescription1;
    }

    public void setPrescription1(String prescription1) {
        this.prescription1 = prescription1;
    }

    public String getPrescription2() {
        return prescription2;
    }

    public void setPrescription2(String prescription2) {
        this.prescription2 = prescription2;
    }

    public String getPrescription3() {
        return prescription3;
    }

    public void setPrescription3(String prescription3) {
        this.prescription3 = prescription3;
    }

    public String getPrescription4() {
        return prescription4;
    }

    public void setPrescription4(String prescription4) {
        this.prescription4 = prescription4;
    }

    public String getPrescription5() {
        return prescription5;
    }

    public void setPrescription5(String prescription5) {
        this.prescription5 = prescription5;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getProblem1() {
        return problem1;
    }

    public void setProblem1(String problem1) {
        this.problem1 = problem1;
    }

    public String getProblem2() {
        return problem2;
    }

    public void setProblem2(String problem2) {
        this.problem2 = problem2;
    }

    public String getProblem3() {
        return problem3;
    }

    public void setProblem3(String problem3) {
        this.problem3 = problem3;
    }

    public String getProblem4() {
        return problem4;
    }

    public void setProblem4(String problem4) {
        this.problem4 = problem4;
    }

    public String getProblem5() {
        return problem5;
    }

    public void setProblem5(String problem5) {
        this.problem5 = problem5;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getOnset() {
        return onset;
    }

    public void setOnset(String onset) {
        this.onset = onset;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getRadiation() {
        return radiation;
    }

    public void setRadiation(String radiation) {
        this.radiation = radiation;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getProvokes() {
        return provokes;
    }

    public void setProvokes(String provokes) {
        this.provokes = provokes;
    }

    public String getPalliates() {
        return palliates;
    }

    public void setPalliates(String palliates) {
        this.palliates = palliates;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(String physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public Float getGlucose() {
        return glucose;
    }

    public void setGlucose(Float glucose) {
        this.glucose = glucose;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
    public String getCurrentMedication() {
        return currentMedication;
    }

    public void setCurrentMedication(String currentMedication) {
        this.currentMedication = currentMedication;
    }

    public String getSocialHistory() {
        return socialHistory;
    }

    public void setSocialHistory(String socialHistory) {
        this.socialHistory = socialHistory;
    }

    public String getMedicalSurgicalHistory() {
        return medicalSurgicalHistory;
    }

    public void setMedicalSurgicalHistory(String medicalSurgicalHistory) {
        this.medicalSurgicalHistory = medicalSurgicalHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

}
