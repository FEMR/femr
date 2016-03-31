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
package femr.util.startup;

import com.avaje.ebean.Ebean;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.core.*;
import femr.data.models.mysql.*;
import femr.util.calculations.dateUtils;
import femr.util.encryptions.BCryptPasswordEncryptor;
import femr.util.encryptions.IPasswordEncryptor;
import org.apache.commons.codec.binary.StringUtils;
import play.Play;
import java.util.ArrayList;
import java.util.List;

//TODO: stop assigning primary keys
public class DatabaseSeeder {

    private final Repository<ConceptDiagnosis> diagnosisRepository;
    private final Repository<ConceptMedicationUnit> medicationMeasurementUnitRepository;
    private final Repository<ConceptMedicationForm> conceptMedicationFormRepository;
    private final Repository<ConceptPrescriptionAdministration> conceptPrescriptionAdministrationRepository;
    private final IRepository<MissionCountry> missionCountryRepository;
    private final IRepository<MissionCity> missionCityRepository;
    private final IRepository<MissionTeam> missionTeamRepository;
    private final IRepository<User> userRepository;
    private final Repository<Role> roleRepository;
    private final Repository<SystemSetting> systemSettingRepository;
    private final Repository<TabField> tabFieldRepository;
    private final Repository<TabFieldSize> tabFieldSizeRepository;
    private final Repository<TabFieldType> tabFieldTypeRepository;
    private final Repository<Tab> tabRepository;
    private final Repository<PatientAgeClassification> patientAgeClassificationRepository;

    public DatabaseSeeder() {
        diagnosisRepository = new Repository<>();
        medicationMeasurementUnitRepository = new Repository<>();
        conceptMedicationFormRepository = new Repository<>();
        conceptPrescriptionAdministrationRepository = new Repository<>();
        userRepository = new Repository<>();
        roleRepository = new Repository<>();
        systemSettingRepository = new Repository<>();
        tabFieldRepository = new Repository<>();
        tabFieldSizeRepository = new Repository<>();
        tabFieldTypeRepository = new Repository<>();
        tabRepository = new Repository<>();
        patientAgeClassificationRepository = new Repository<>();
        missionCountryRepository = new Repository<>();
        missionTeamRepository = new Repository<>();
        missionCityRepository = new Repository<>();
    }

    public void seed() {

        seedMissionTripInformation();
        seedSystemSettings();
        seedAdminUser();
        seedDefaultTabNames();
        seedDefaultTabFieldSizes();
        seedDefaultTabFieldTypes();
        seedDefaultTabFields();
        seedMedicationUnits();
        seedMedicationForms();
        seedPatientAgeClassification();
        seedDiagnosis();
        seedConceptPrescriptionAdministrations();
    }

    private void seedDiagnosis() {
        List<? extends IConceptDiagnosis> diagnosis_but_plural = diagnosisRepository.findAll(ConceptDiagnosis.class);
        List<String> availableDiagnosis = new ArrayList<>();

        availableDiagnosis.add("Acne Vulgaris");
        availableDiagnosis.add("Allergies");
        availableDiagnosis.add("Amenorrhea");
        availableDiagnosis.add("Anemia, Iron Deficiency");
        availableDiagnosis.add("Anemia, Sickle Cell");
        availableDiagnosis.add("Anxiety Disorder");
        availableDiagnosis.add("Arthritis, Osteo");
        availableDiagnosis.add("Arthritis, Rheumatoid");
        availableDiagnosis.add("Ascites");
        availableDiagnosis.add("Asthma");
        availableDiagnosis.add("Bacterial Infection, Abscess");
        availableDiagnosis.add("Bacterial Infection, Skin");
        availableDiagnosis.add("Bacterial Vaginosis");
        availableDiagnosis.add("Trichomonas Vaginalis");
        availableDiagnosis.add("Benign Prostatic Hyperplasia");
        availableDiagnosis.add("Bronchitis");
        availableDiagnosis.add("Bursitis");
        availableDiagnosis.add("Cataracts");
        availableDiagnosis.add("Cerebral Palsy");
        availableDiagnosis.add("Chlamydia");
        availableDiagnosis.add("Chronic Obstructive Pulmonary Disease");
        availableDiagnosis.add("Constipation");
        availableDiagnosis.add("Contact Dermatitis");
        availableDiagnosis.add("Cough");
        availableDiagnosis.add("Conjuctivitis");
        availableDiagnosis.add("Cystitis");
        availableDiagnosis.add("Dehydration");
        availableDiagnosis.add("Dental, Abscess");
        availableDiagnosis.add("Dental, Chipped Tooth");
        availableDiagnosis.add("Dental, Gingivitis");
        availableDiagnosis.add("Dental, Infection");
        availableDiagnosis.add("Dental, Pain");
        availableDiagnosis.add("Depression");
        availableDiagnosis.add("Diabetes Mellitus, Type 1");
        availableDiagnosis.add("Diabetes mellitus, Type 2");
        availableDiagnosis.add("Diarrhea");
        availableDiagnosis.add("Dizziness");
        availableDiagnosis.add("Dry Eyes");
        availableDiagnosis.add("Dry Skin");
        availableDiagnosis.add("Dysmenorrhea");
        availableDiagnosis.add("Dysuria");
        availableDiagnosis.add("Ear Infection (not Otitis Media)");
        availableDiagnosis.add("Eczema");
        availableDiagnosis.add("Fever");
        availableDiagnosis.add("Folliculitis");
        availableDiagnosis.add("Gastric Ulcer");
        availableDiagnosis.add("Gastritis");
        availableDiagnosis.add("Gastroenteritis");
        availableDiagnosis.add("Gastroesophageal Reflux Disease");
        availableDiagnosis.add("Headache");
        availableDiagnosis.add("Herpangina");
        availableDiagnosis.add("Herpes Simplex Virus, Oral");
        availableDiagnosis.add("Herpes Simplex Virus, Genital");
        availableDiagnosis.add("High Blood Pressure");
        availableDiagnosis.add("Hypertension");
        availableDiagnosis.add("Irregular Menstruation");
        availableDiagnosis.add("Laceration");
        availableDiagnosis.add("Lipoma");
        availableDiagnosis.add("Lymphadenitis");
        availableDiagnosis.add("Malaria");
        availableDiagnosis.add("Malnutrition");
        availableDiagnosis.add("Melena");
        availableDiagnosis.add("Nausea");
        availableDiagnosis.add("Neuropathy");
        availableDiagnosis.add("Orthostatic Hypotension");
        availableDiagnosis.add("Otitis Media");
        availableDiagnosis.add("Pain, Abdominal");
        availableDiagnosis.add("Pain, Arm");
        availableDiagnosis.add("Pain, Bone");
        availableDiagnosis.add("Pain, Breast");
        availableDiagnosis.add("Pain, Costrochondral");
        availableDiagnosis.add("Pain, Ear");
        availableDiagnosis.add("Pain, Eye");
        availableDiagnosis.add("Pain, Foot");
        availableDiagnosis.add("Pain, Generalized");
        availableDiagnosis.add("Pain, Joint");
        availableDiagnosis.add("Pain, Knee");
        availableDiagnosis.add("Pain, Leg");
        availableDiagnosis.add("Pain, Muscle");
        availableDiagnosis.add("Parasitic Infection");
        availableDiagnosis.add("Pelvic Inflammatory Disease");
        availableDiagnosis.add("Pharyngitis, Bacterial");
        availableDiagnosis.add("Pharyngitis, Viral");
        availableDiagnosis.add("Phimosis");
        availableDiagnosis.add("Pneumonia");
        availableDiagnosis.add("Pregnancy");
        availableDiagnosis.add("Prostitis");
        availableDiagnosis.add("Psoriasis");
        availableDiagnosis.add("Pylonephritis");
        availableDiagnosis.add("Scabies");
        availableDiagnosis.add("Sebhorragic Dermatitis");
        availableDiagnosis.add("Seizure Disorder");
        availableDiagnosis.add("Shortness of Breath");
        availableDiagnosis.add("Angina Pectoris, Stable");
        availableDiagnosis.add("Angina Pectoris, Unstable");
        availableDiagnosis.add("Sinusitis");
        availableDiagnosis.add("Sty");
        availableDiagnosis.add("Syphilis");
        availableDiagnosis.add("Thelarche");
        availableDiagnosis.add("Tinea Capitis");
        availableDiagnosis.add("Tinea Corporis");
        availableDiagnosis.add("Tinea Cruris");
        availableDiagnosis.add("Tinea Legionella");
        availableDiagnosis.add("Tinea Versicolor");
        availableDiagnosis.add("Urinary Tract Infection");
        availableDiagnosis.add("Uterine Fibroids");
        availableDiagnosis.add("Uterine Prolapse");
        availableDiagnosis.add("Candiasis, Vaginal");
        availableDiagnosis.add("Candiasis, Oral");
        availableDiagnosis.add("Candiasis, Cutaneous");

        List<ConceptDiagnosis> newDiagnosis = new ArrayList<>();
        ConceptDiagnosis diagnosis;
        for (String diag : availableDiagnosis)
            if (diagnosis_but_plural != null && !containDiagnosis(diagnosis_but_plural, diag)) {
                diagnosis = new ConceptDiagnosis();
                diagnosis.setName(diag);
                newDiagnosis.add(diagnosis);
            }
        diagnosisRepository.createAll(newDiagnosis);
    }

    private void seedMissionTripInformation() {
        //mission countries
        List<? extends IMissionCountry> missionCountries = missionCountryRepository.findAll(MissionCountry.class);
        List<String> availableCountries = new ArrayList<>();

        availableCountries.add("Afghanistan");
        availableCountries.add("Albania");
        availableCountries.add("Algeria");
        availableCountries.add("Andorra");
        availableCountries.add("Angola");
        availableCountries.add("Antigua & Deps");
        availableCountries.add("Argentina");
        availableCountries.add("Armenia");
        availableCountries.add("Australia");
        availableCountries.add("Austria");
        availableCountries.add("Azerbaijan");
        availableCountries.add("Bahamas");
        availableCountries.add("Bahrain");
        availableCountries.add("Bangladesh");
        availableCountries.add("Barbados");
        availableCountries.add("Belarus");
        availableCountries.add("Belgium");
        availableCountries.add("Belize");
        availableCountries.add("Benin");
        availableCountries.add("Bhutan");
        availableCountries.add("Bolivia");
        availableCountries.add("Bosnia Herzegovina");
        availableCountries.add("Botswana");
        availableCountries.add("Brazil");
        availableCountries.add("Brunei");
        availableCountries.add("Bulgaria");
        availableCountries.add("Burkina");
        availableCountries.add("Burundi");
        availableCountries.add("Cambodia");
        availableCountries.add("Cameroon");
        availableCountries.add("Canada");
        availableCountries.add("Cape Verde");
        availableCountries.add("Central African Rep");
        availableCountries.add("Chad");
        availableCountries.add("Chile");
        availableCountries.add("China");
        availableCountries.add("Colombia");
        availableCountries.add("Comoros");
        availableCountries.add("Congo");
        availableCountries.add("Congo {Democratic Rep}");
        availableCountries.add("Costa Rica");
        availableCountries.add("Croatia");
        availableCountries.add("Cuba");
        availableCountries.add("Cyprus");
        availableCountries.add("Czech Republic");
        availableCountries.add("Denmark");
        availableCountries.add("Djibouti");
        availableCountries.add("Dominica");
        availableCountries.add("Dominican Republic");
        availableCountries.add("East Timor");
        availableCountries.add("Ecuador");
        availableCountries.add("Egypt");
        availableCountries.add("El Salvador");
        availableCountries.add("Equatorial Guinea");
        availableCountries.add("Eritrea");
        availableCountries.add("Estonia");
        availableCountries.add("Ethiopia");
        availableCountries.add("Fiji");
        availableCountries.add("Finland");
        availableCountries.add("France");
        availableCountries.add("Gabon");
        availableCountries.add("Gambia");
        availableCountries.add("Georgia");
        availableCountries.add("Germany");
        availableCountries.add("Ghana");
        availableCountries.add("Greece");
        availableCountries.add("Grenada");
        availableCountries.add("Guatemala");
        availableCountries.add("Guinea");
        availableCountries.add("Guinea-Bissau");
        availableCountries.add("Guyana");
        availableCountries.add("Haiti");
        availableCountries.add("Honduras");
        availableCountries.add("Hungary");
        availableCountries.add("Iceland");
        availableCountries.add("India");
        availableCountries.add("Indonesia");
        availableCountries.add("Iran");
        availableCountries.add("Iraq");
        availableCountries.add("Ireland {Republic}");
        availableCountries.add("Israel");
        availableCountries.add("Italy");
        availableCountries.add("Ivory Coast");
        availableCountries.add("Jamaica");
        availableCountries.add("Japan");
        availableCountries.add("Jordan");
        availableCountries.add("Kazakhstan");
        availableCountries.add("Kenya");
        availableCountries.add("Kiribati");
        availableCountries.add("Korea North");
        availableCountries.add("Korea South");
        availableCountries.add("Kosovo");
        availableCountries.add("Kuwait");
        availableCountries.add("Kyrgyzstan");
        availableCountries.add("Laos");
        availableCountries.add("Latvia");
        availableCountries.add("Lebanon");
        availableCountries.add("Lesotho");
        availableCountries.add("Liberia");
        availableCountries.add("Libya");
        availableCountries.add("Liechtenstein");
        availableCountries.add("Lithuania");
        availableCountries.add("Luxembourg");
        availableCountries.add("Macedonia");
        availableCountries.add("Madagascar");
        availableCountries.add("Malawi");
        availableCountries.add("Malaysia");
        availableCountries.add("Maldives");
        availableCountries.add("Mali");
        availableCountries.add("Malta");
        availableCountries.add("Marshall Islands");
        availableCountries.add("Mauritania");
        availableCountries.add("Mauritius");
        availableCountries.add("Mexico");
        availableCountries.add("Micronesia");
        availableCountries.add("Moldova");
        availableCountries.add("Monaco");
        availableCountries.add("Mongolia");
        availableCountries.add("Montenegro");
        availableCountries.add("Morocco");
        availableCountries.add("Mozambique");
        availableCountries.add("Myanmar, {Burma}");
        availableCountries.add("Namibia");
        availableCountries.add("Nauru");
        availableCountries.add("Nepal");
        availableCountries.add("Netherlands");
        availableCountries.add("New Zealand");
        availableCountries.add("Nicaragua");
        availableCountries.add("Niger");
        availableCountries.add("Nigeria");
        availableCountries.add("Norway");
        availableCountries.add("Oman");
        availableCountries.add("Pakistan");
        availableCountries.add("Palau");
        availableCountries.add("Panama");
        availableCountries.add("Papua New Guinea");
        availableCountries.add("Paraguay");
        availableCountries.add("Peru");
        availableCountries.add("Philippines");
        availableCountries.add("Poland");
        availableCountries.add("Portugal");
        availableCountries.add("Qatar");
        availableCountries.add("Romania");
        availableCountries.add("Russian Federation");
        availableCountries.add("Rwanda");
        availableCountries.add("St Kitts & Nevis");
        availableCountries.add("St Lucia");
        availableCountries.add("Saint Vincent & the Grenadines");
        availableCountries.add("Samoa");
        availableCountries.add("San Marino");
        availableCountries.add("Sao Tome & Principe");
        availableCountries.add("Saudi Arabia");
        availableCountries.add("Senegal");
        availableCountries.add("Serbia");
        availableCountries.add("Seychelles");
        availableCountries.add("Sierra Leone");
        availableCountries.add("Singapore");
        availableCountries.add("Slovakia");
        availableCountries.add("Slovenia");
        availableCountries.add("Solomon Islands");
        availableCountries.add("Somalia");
        availableCountries.add("South Africa");
        availableCountries.add("South Sudan");
        availableCountries.add("Spain");
        availableCountries.add("Sri Lanka");
        availableCountries.add("Sudan");
        availableCountries.add("Suriname");
        availableCountries.add("Swaziland");
        availableCountries.add("Sweden");
        availableCountries.add("Switzerland");
        availableCountries.add("Syria");
        availableCountries.add("Taiwan");
        availableCountries.add("Tajikistan");
        availableCountries.add("Tanzania");
        availableCountries.add("Thailand");
        availableCountries.add("Togo");
        availableCountries.add("Tonga");
        availableCountries.add("Trinidad & Tobago");
        availableCountries.add("Tunisia");
        availableCountries.add("Turkey");
        availableCountries.add("Turkmenistan");
        availableCountries.add("Tuvalu");
        availableCountries.add("Uganda");
        availableCountries.add("Ukraine");
        availableCountries.add("United Arab Emirates");
        availableCountries.add("United Kingdom");
        availableCountries.add("USA");
        availableCountries.add("Uruguay");
        availableCountries.add("Uzbekistan");
        availableCountries.add("Vanuatu");
        availableCountries.add("Vatican City");
        availableCountries.add("Venezuela");
        availableCountries.add("Vietnam");
        availableCountries.add("Yemen");
        availableCountries.add("Zambia");
        availableCountries.add("Zimbabwe");

        List<MissionCountry> newMissionCountries = new ArrayList<>();
        MissionCountry missionCountry;

        for (String country : availableCountries) {
            if (missionCountries != null && !containMissionCountry(missionCountries, country)) {
                missionCountry = new MissionCountry();
                missionCountry.setName(country);
                newMissionCountries.add(missionCountry);
            }
        }

        missionCountryRepository.createAll(newMissionCountries);
        missionCountries = missionCountryRepository.findAll(MissionCountry.class);

        //mission teams
        List<? extends IMissionTeam> missionTeams = missionTeamRepository.findAll(MissionTeam.class);
        List<MissionTeam> newMissionTeams = new ArrayList<>();
        MissionTeam missionTeam;
        if (missionTeams != null && !containMissionTeam(missionTeams, "Aid for Haiti")) {
            missionTeam = new MissionTeam();
            missionTeam.setName("Aid for Haiti");
            missionTeam.setLocation("Tennessee");
            missionTeam.setDescription("Dr. Sutherland's group");
            newMissionTeams.add(missionTeam);
        }
        if (missionTeams != null && !containMissionTeam(missionTeams, "WSU-WHSO")) {
            missionTeam = new MissionTeam();
            missionTeam.setName("WSU-WHSO");
            missionTeam.setLocation("Detroit");
            missionTeam.setDescription("Wayne State medical students");
            newMissionTeams.add(missionTeam);
        }
        if (missionTeams != null && !containMissionTeam(missionTeams, "ApParent Project")) {
            missionTeam = new MissionTeam();
            missionTeam.setName("ApParent Project");
            missionTeam.setLocation("New York");
            missionTeam.setDescription("Dr. Parson's group - primarily operates in Port Au Prince");
            newMissionTeams.add(missionTeam);
        }
        missionTeamRepository.createAll(newMissionTeams);

        //countries
        List<? extends IMissionCity> missionCities = missionCityRepository.findAll(MissionCity.class);
        List<MissionCity> newMissionCities = new ArrayList<>();

        MissionCity missionCity;
        if (missionCities != null && !containMissionCity(missionCities, "Morne De L' Hopital", "Haiti")) {
            missionCity = new MissionCity();
            missionCity.setName("Morne De L' Hopital");
            missionCity.setMissionCountry(getMissionCountry(missionCountries, "Haiti"));
            newMissionCities.add(missionCity);
        }
        if (missionCities != null && !containMissionCity(missionCities, "Port-au-Prince", "Haiti")) {
            missionCity = new MissionCity();
            missionCity.setName("Port-au-Prince");
            missionCity.setMissionCountry(getMissionCountry(missionCountries, "Haiti"));
            newMissionCities.add(missionCity);
        }
        missionCityRepository.createAll(newMissionCities);

    }

    private void seedPatientAgeClassification() {
        //sort order auto increments
        List<? extends IPatientAgeClassification> patientAgeClassifications = patientAgeClassificationRepository.findAll(PatientAgeClassification.class);

        List<PatientAgeClassification> newPatientAgeClassifications = new ArrayList<>();
        PatientAgeClassification patientAgeClassification;
        if (patientAgeClassifications != null && !containClassification(patientAgeClassifications, "infant")) {
            patientAgeClassification = new PatientAgeClassification();
            patientAgeClassification.setName("infant");
            patientAgeClassification.setDescription("0-1");
            patientAgeClassification.setIsDeleted(false);
            patientAgeClassification.setSortOrder(1);
            newPatientAgeClassifications.add(patientAgeClassification);
        }
        if (patientAgeClassifications != null && !containClassification(patientAgeClassifications, "child")) {
            patientAgeClassification = new PatientAgeClassification();
            patientAgeClassification.setName("child");
            patientAgeClassification.setDescription("2-12");
            patientAgeClassification.setIsDeleted(false);
            patientAgeClassification.setSortOrder(2);
            newPatientAgeClassifications.add(patientAgeClassification);
        }
        if (patientAgeClassifications != null && !containClassification(patientAgeClassifications, "teen")) {
            patientAgeClassification = new PatientAgeClassification();
            patientAgeClassification.setName("teen");
            patientAgeClassification.setDescription("13-17");
            patientAgeClassification.setIsDeleted(false);
            patientAgeClassification.setSortOrder(3);
            newPatientAgeClassifications.add(patientAgeClassification);
        }
        if (patientAgeClassifications != null && !containClassification(patientAgeClassifications, "adult")) {
            patientAgeClassification = new PatientAgeClassification();
            patientAgeClassification.setName("adult");
            patientAgeClassification.setDescription("18-64");
            patientAgeClassification.setIsDeleted(false);
            patientAgeClassification.setSortOrder(4);
            newPatientAgeClassifications.add(patientAgeClassification);
        }
        if (patientAgeClassifications != null && !containClassification(patientAgeClassifications, "elder")) {
            patientAgeClassification = new PatientAgeClassification();
            patientAgeClassification.setName("elder");
            patientAgeClassification.setDescription("65+");
            patientAgeClassification.setIsDeleted(false);
            patientAgeClassification.setSortOrder(5);
            newPatientAgeClassifications.add(patientAgeClassification);
        }
        patientAgeClassificationRepository.createAll(newPatientAgeClassifications);
    }

    private void seedMedicationForms() {

        List<? extends IConceptMedicationForm> conceptMedicationForms = conceptMedicationFormRepository.findAll(ConceptMedicationForm.class);

        List<ConceptMedicationForm> newMedicationForms = new ArrayList<>();
        ConceptMedicationForm conceptMedicationForm;
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "B/S")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("B/S");
            conceptMedicationForm.setDescription("bite and swallow");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "caps")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("caps");
            conceptMedicationForm.setDescription("capsules");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "crm")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("crm");
            conceptMedicationForm.setDescription("cream");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "elix")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("elix");
            conceptMedicationForm.setDescription("elixir");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "gtts")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("gtts");
            conceptMedicationForm.setDescription("drops");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "MDI")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("MDI");
            conceptMedicationForm.setDescription("metered dose inhaler");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "nebs")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("nebs");
            conceptMedicationForm.setDescription("solution for nebulization");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "NPO")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("NPO");
            conceptMedicationForm.setDescription("nothing by mouth");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "PO")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("PO");
            conceptMedicationForm.setDescription("by mouth, orally , or swallowed");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "PR")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("PR");
            conceptMedicationForm.setDescription("suppository");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "SL")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("SL");
            conceptMedicationForm.setDescription("sublingual form");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "soln")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("soln");
            conceptMedicationForm.setDescription("solution");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "supp")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("supp");
            conceptMedicationForm.setDescription("suppository");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "susp")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("susp");
            conceptMedicationForm.setDescription("suspension");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "syr")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("syr");
            conceptMedicationForm.setDescription("syrup");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "tabs")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("tabs");
            conceptMedicationForm.setDescription("tablets");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        if (conceptMedicationForms != null && !containForm(conceptMedicationForms, "ung")) {
            conceptMedicationForm = new ConceptMedicationForm();
            conceptMedicationForm.setName("ung");
            conceptMedicationForm.setDescription("ointment");
            conceptMedicationForm.setIsDeleted(false);
            newMedicationForms.add(conceptMedicationForm);
        }
        conceptMedicationFormRepository.createAll(newMedicationForms);
    }

    private void seedMedicationUnits() {
        List<? extends IConceptMedicationUnit> medicationUnits = medicationMeasurementUnitRepository.findAll(ConceptMedicationUnit.class);

        List<ConceptMedicationUnit> newMedicationMeasurementUnits = new ArrayList<>();
        ConceptMedicationUnit medicationMeasurementUnit;
        if (medicationUnits != null && !containUnit(medicationUnits, "%")) {
            medicationMeasurementUnit = new ConceptMedicationUnit();
            medicationMeasurementUnit.setName("%");
            medicationMeasurementUnit.setDescription("g/dL");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "g")) {
            medicationMeasurementUnit = new ConceptMedicationUnit();
            medicationMeasurementUnit.setName("g");
            medicationMeasurementUnit.setDescription("gram");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "gr")) {
            medicationMeasurementUnit = new ConceptMedicationUnit();
            medicationMeasurementUnit.setName("gr");
            medicationMeasurementUnit.setDescription("grain");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "IU")) {
            medicationMeasurementUnit = new ConceptMedicationUnit();
            medicationMeasurementUnit.setName("IU");
            medicationMeasurementUnit.setDescription("international units");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "mg")) {
            medicationMeasurementUnit = new ConceptMedicationUnit();
            medicationMeasurementUnit.setName("mg");
            medicationMeasurementUnit.setDescription("milligram");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        if (medicationUnits != null && !containUnit(medicationUnits, "U")) {
            medicationMeasurementUnit = new ConceptMedicationUnit();
            medicationMeasurementUnit.setName("U");
            medicationMeasurementUnit.setDescription("unit");
            medicationMeasurementUnit.setIsDeleted(false);
            newMedicationMeasurementUnits.add(medicationMeasurementUnit);
        }
        medicationMeasurementUnitRepository.createAll(newMedicationMeasurementUnits);
    }


    /**
     * Seed available system settings
     */
    private void seedSystemSettings() {
        List<? extends ISystemSetting> systemSettings = systemSettingRepository.findAll(SystemSetting.class);

        SystemSetting systemSetting;
        if (systemSettings != null && !containSetting(systemSettings, "Multiple chief complaints")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Multiple chief complaints");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }
        if (systemSettings != null && !containSetting(systemSettings, "Medical PMH Tab")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical PMH Tab");
            systemSetting.setActive(true);
            systemSettingRepository.create(systemSetting);
        }
        if (systemSettings != null && !containSetting(systemSettings, "Medical Photo Tab")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical Photo Tab");
            systemSetting.setActive(true);
            systemSettingRepository.create(systemSetting);
        }
        if (systemSettings != null && !containSetting(systemSettings, "Medical HPI Consolidate")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Medical HPI Consolidate");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }

        if (systemSettings != null && !containSetting(systemSettings, "Metric System Option")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Metric System Option");
            systemSetting.setActive(true);
            systemSettingRepository.create(systemSetting);
        }

        //Filters the patient search based on which country the team is currently in
        if (systemSettings != null && !containSetting(systemSettings, "Country Filter")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Country Filter");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }

        if (systemSettings != null && !containSetting(systemSettings, "Research Only")) {
            systemSetting = new SystemSetting();
            systemSetting.setName("Research Only");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }
        //Asks a physician in medical if they screened the patient for diabetes based on
        //criteria: (Age >= 18) AND (Systolic bp >= 140 OR Diastolic bp >= 90)
        if (systemSettings != null && !containSetting(systemSettings, "Diabetes Prompt")){
            systemSetting = new SystemSetting();
            systemSetting.setName("Diabetes Prompt");
            systemSetting.setActive(false);
            systemSettingRepository.create(systemSetting);
        }

    }

    /**
     * Uses references to HPI, PMH, and Treatment Tabs
     * Uses references to both number and text TabFieldTypes
     */
    private void seedDefaultTabFields() {
        List<? extends ITabField> tabFields = tabFieldRepository.findAll(TabField.class);

        //get the id references for tabs
        List<? extends ITab> tabs = tabRepository.findAll(Tab.class);
        int hpiId = -1;
        int pmhId = -1;
        int treatmentId = -1;
        for (ITab t : tabs) {
            switch (t.getName()) {
                case "HPI":
                    hpiId = t.getId();
                    break;
                case "PMH":
                    pmhId = t.getId();
                    break;
                case "Treatment":
                    treatmentId = t.getId();
                    break;
            }
        }


        //get the id references for tab field types
        List<? extends ITabFieldType> tabFieldTypes = tabFieldTypeRepository.findAll(TabFieldType.class);
        int numberId = -1;
        int textId = -1;
        for (ITabFieldType tft : tabFieldTypes) {
            switch (tft.getName()) {
                case "number":
                    numberId = tft.getId();
                    break;
                case "text":
                    textId = tft.getId();
                    break;
            }
        }


        List<TabField> tabFieldsToAdd = new ArrayList<>();

        if (tabFields != null) {

            TabField tabField;
            if (!containTabField(tabFields, "onset")) {

                tabField = new TabField();
                tabField.setName("onset");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "severity")) {

                tabField = new TabField();
                tabField.setName("severity");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, numberId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "radiation")) {

                tabField = new TabField();
                tabField.setName("radiation");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "quality")) {

                tabField = new TabField();
                tabField.setName("quality");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "provokes")) {

                tabField = new TabField();
                tabField.setName("provokes");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "palliates")) {

                tabField = new TabField();
                tabField.setName("palliates");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "timeOfDay")) {

                tabField = new TabField();
                tabField.setName("timeOfDay");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "physicalExamination")) {

                tabField = new TabField();
                tabField.setName("physicalExamination");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "narrative")) {

                tabField = new TabField();
                tabField.setName("narrative");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, hpiId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "assessment")) {

                tabField = new TabField();
                tabField.setName("assessment");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, treatmentId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "problem")) {

                tabField = new TabField();
                tabField.setName("problem");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, treatmentId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "treatment")) {

                tabField = new TabField();
                tabField.setName("treatment");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, treatmentId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "medicalSurgicalHistory")) {

                tabField = new TabField();
                tabField.setName("medicalSurgicalHistory");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, pmhId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "socialHistory")) {

                tabField = new TabField();
                tabField.setName("socialHistory");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, pmhId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "currentMedication")) {

                tabField = new TabField();
                tabField.setName("currentMedication");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, pmhId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
            if (!containTabField(tabFields, "familyHistory")) {

                tabField = new TabField();
                tabField.setName("familyHistory");
                tabField.setIsDeleted(false);
                tabField.setTab(Ebean.getReference(Tab.class, pmhId));
                tabField.setTabFieldType(Ebean.getReference(TabFieldType.class, textId));
                tabFieldsToAdd.add(tabField);
            }
        }

        tabFieldRepository.createAll(tabFieldsToAdd);
    }

    private void seedDefaultTabNames() {

        List<? extends ITab> tabs = tabRepository.findAll(Tab.class);
        List<Tab> tabsToAdd = new ArrayList<>();

        if (tabs != null) {

            Tab tab;
            if (!containTab(tabs, "HPI")) {

                tab = new Tab();
                tab.setName("HPI");
                tab.setIsDeleted(false);
                tab.setDateCreated(dateUtils.getCurrentDateTime());
                tab.setUserId(null);
                tab.setLeftColumnSize(2);
                tab.setRightColumnSize(2);
                tab.setIsCustom(false);
                tabsToAdd.add(tab);
            }
            if (!containTab(tabs, "PMH")) {

                tab = new Tab();
                tab.setName("PMH");
                tab.setIsDeleted(false);
                tab.setDateCreated(dateUtils.getCurrentDateTime());
                tab.setUserId(null);
                tab.setLeftColumnSize(0);
                tab.setRightColumnSize(0);
                tab.setIsCustom(false);
                tabsToAdd.add(tab);
            }
            if (!containTab(tabs, "Treatment")) {

                tab = new Tab();
                tab.setName("Treatment");
                tab.setIsDeleted(false);
                tab.setDateCreated(dateUtils.getCurrentDateTime());
                tab.setUserId(null);
                tab.setLeftColumnSize(0);
                tab.setRightColumnSize(0);
                tab.setIsCustom(false);
                tabsToAdd.add(tab);
            }
            if (!containTab(tabs, "Photos")) {

                tab = new Tab();
                tab.setName("Photos");
                tab.setIsDeleted(false);
                tab.setDateCreated(dateUtils.getCurrentDateTime());
                tab.setUserId(null);
                tab.setLeftColumnSize(0);
                tab.setRightColumnSize(0);
                tab.setIsCustom(false);
                tabsToAdd.add(tab);
            }
        }

        tabRepository.createAll(tabsToAdd);
    }

    private void seedDefaultTabFieldTypes() {

        List<? extends ITabFieldType> tabFieldTypes = tabFieldTypeRepository.findAll(TabFieldType.class);
        List<TabFieldType> tabFieldTypesToAdd = new ArrayList<>();

        if (tabFieldTypes != null) {

            TabFieldType tabFieldType;
            if (!containTabFieldType(tabFieldTypes, "text")) {

                tabFieldType = new TabFieldType();
                tabFieldType.setName("text");
                tabFieldTypesToAdd.add(tabFieldType);
            }
            if (!containTabFieldType(tabFieldTypes, "number")) {

                tabFieldType = new TabFieldType();
                tabFieldType.setName("number");
                tabFieldTypesToAdd.add(tabFieldType);
            }

            tabFieldTypeRepository.createAll(tabFieldTypesToAdd);
        }
    }

    private void seedDefaultTabFieldSizes() {

        List<? extends ITabFieldSize> tabFieldSizes = tabFieldSizeRepository.findAll(TabFieldSize.class);
        List<TabFieldSize> tabFieldSizesToAdd = new ArrayList<>();


        if (tabFieldSizes != null) {

            TabFieldSize tabFieldSize;
            if (!containTabFieldSize(tabFieldSizes, "medium")) {

                tabFieldSize = new TabFieldSize();
                tabFieldSize.setName("medium");
                tabFieldSizesToAdd.add(tabFieldSize);
            }
            if (!containTabFieldSize(tabFieldSizes, "large")) {

                tabFieldSize = new TabFieldSize();
                tabFieldSize.setName("large");
                tabFieldSizesToAdd.add(tabFieldSize);
            }
        }

        tabFieldSizeRepository.createAll(tabFieldSizesToAdd);
    }

    private void seedConceptPrescriptionAdministrations() {
        List<? extends IConceptPrescriptionAdministration> administrations = conceptPrescriptionAdministrationRepository.findAll(ConceptPrescriptionAdministration.class);
        List<ConceptPrescriptionAdministration> conceptPrescriptionAdministrationsToAdd = new ArrayList<>();

        if (administrations != null) {
            ConceptPrescriptionAdministration conceptPrescriptionAdministration;

            /* Daily modifier is used as helper in the calculation when prescribing a medication.
             * It is how many times per day it should be taken. This modifier is then multiplied by
             * the amount of days the prescriber sets. The total can be adjusted */

            if (!containConceptPrescriptionAdministration(administrations, "alt")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("alt");
                conceptPrescriptionAdministration.setDailyModifier(0.5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "BID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("BID");
                conceptPrescriptionAdministration.setDailyModifier(2f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "BIW")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("BIW");
                conceptPrescriptionAdministration.setDailyModifier(0.2857f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "CID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("CID");
                conceptPrescriptionAdministration.setDailyModifier(5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "HS")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("HS");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q12h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q12h");
                conceptPrescriptionAdministration.setDailyModifier(2f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q24h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q24h");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q4-6h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q4-6h");
                conceptPrescriptionAdministration.setDailyModifier(5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q4h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q4h");
                conceptPrescriptionAdministration.setDailyModifier(6f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q6h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q6h");
                conceptPrescriptionAdministration.setDailyModifier(4f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q8h")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q8h");
                conceptPrescriptionAdministration.setDailyModifier(3f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qAM")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qAM");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qd")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qd");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qHS")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qHS");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "QID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("QID");
                conceptPrescriptionAdministration.setDailyModifier(4f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q5min")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q5min");
                conceptPrescriptionAdministration.setDailyModifier(288f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qOd")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qOd");
                conceptPrescriptionAdministration.setDailyModifier(0.5f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "qPM")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("qPM");
                conceptPrescriptionAdministration.setDailyModifier(1f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "q week")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("q week");
                conceptPrescriptionAdministration.setDailyModifier(0.142857f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "TID")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("TID");
                conceptPrescriptionAdministration.setDailyModifier(3f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }
            if (!containConceptPrescriptionAdministration(administrations, "TIW")) {
                conceptPrescriptionAdministration = new ConceptPrescriptionAdministration();
                conceptPrescriptionAdministration.setName("TIW");
                conceptPrescriptionAdministration.setDailyModifier(0.42857f);
                conceptPrescriptionAdministrationsToAdd.add(conceptPrescriptionAdministration);
            }


            //a whole bunch of if statements to fix the problem with the daily modifier turning to 0 when navigating through evolutions
            for (IConceptPrescriptionAdministration existingConceptPrescriptionAdministrations : administrations) {

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "alt") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "BID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(2f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "BIW") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.2857f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "CID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "HS") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q12h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(2f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q24h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q4-6h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q4h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(6f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q6h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(4f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q8h") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(3f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qAM") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qd") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qHS") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "QID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(4f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q5min") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(288f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qOd") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.5f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "qPM") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(1f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "q week") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.142857f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "TID") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(3f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                if (StringUtils.equals(existingConceptPrescriptionAdministrations.getName(), "TIW") && existingConceptPrescriptionAdministrations.getDailyModifier() == 0.00) {

                    existingConceptPrescriptionAdministrations.setDailyModifier(0.42857f);
                    conceptPrescriptionAdministrationRepository.update((ConceptPrescriptionAdministration) existingConceptPrescriptionAdministrations);
                }

                }


            conceptPrescriptionAdministrationRepository.createAll(conceptPrescriptionAdministrationsToAdd);
        }
    }

    private static IMissionCountry getMissionCountry(List<? extends IMissionCountry> missionCountries, String countryName) {
        for (IMissionCountry mc : missionCountries) {
            if (mc.getName().toLowerCase().equals(countryName.toLowerCase())) {
                return mc;
            }
        }
        return null;
    }

    private static boolean containMissionCity(List<? extends IMissionCity> missionCities, String cityName, String countryName) {
        for (IMissionCity mc : missionCities) {
            if (mc.getName().toLowerCase().equals(cityName.toLowerCase()) && mc.getMissionCountry().getName().toLowerCase().equals(countryName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static boolean containMissionTeam(List<? extends IMissionTeam> missionTeams, String name) {
        for (IMissionTeam mt : missionTeams) {
            if (mt.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containMissionCountry(List<? extends IMissionCountry> missionCountries, String name) {
        for (IMissionCountry mc : missionCountries) {
            if (mc.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containDiagnosis(List<? extends IConceptDiagnosis> diagnosises, String name) {
        for (IConceptDiagnosis d : diagnosises) {
            if (d.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containClassification(List<? extends IPatientAgeClassification> ageClassifications, String name) {
        for (IPatientAgeClassification pac : ageClassifications) {
            if (pac.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containForm(List<? extends IConceptMedicationForm> medicationForms, String form) {
        for (IConceptMedicationForm mf : medicationForms) {
            if (mf.getName().equals(form)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containUnit(List<? extends IConceptMedicationUnit> conceptMedicationUnits, String unit) {
        for (IConceptMedicationUnit mmu : conceptMedicationUnits) {
            if (mmu.getName().equals(unit)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containSetting(List<? extends ISystemSetting> systemSettings, String setting) {
        for (ISystemSetting ss : systemSettings) {
            if (ss.getName().equals(setting)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containTabField(List<? extends ITabField> tabFields, String tabField) {
        for (ITabField tf : tabFields) {
            if (tf.getName().equals(tabField)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containTab(List<? extends ITab> tabs, String tab) {
        for (ITab t : tabs) {
            if (t.getName().equals(tab)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containTabFieldType(List<? extends ITabFieldType> tabFieldTypes, String tabFieldType) {
        for (ITabFieldType tft : tabFieldTypes) {
            if (tft.getName().equals(tabFieldType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containTabFieldSize(List<? extends ITabFieldSize> tabFieldSizes, String tabFieldSize) {
        for (ITabFieldSize tfs : tabFieldSizes) {
            if (tfs.getName().equals(tabFieldSize)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containConceptPrescriptionAdministration(List<? extends IConceptPrescriptionAdministration> administrations, String administration) {
        for(IConceptPrescriptionAdministration a : administrations) {
            if (a.getName().equals(administration)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Seed the admin user from the configuration file
     * and the super user information.
     */
    private void seedAdminUser() {
        int userCount = userRepository.count(User.class);

        if (userCount == 0) {
            String defaultAdminUsername = Play.application().configuration().getString("default.admin.username");
            String defaultAdminPassword = Play.application().configuration().getString("default.admin.password");
            String defaultSuperuserUsername = Play.application().configuration().getString("default.superuser.username");
            String defaultSuperuserPassword = Play.application().configuration().getString("default.superuser.password");

            IPasswordEncryptor encryptor = new BCryptPasswordEncryptor();

            //create the Admin user
            //Admin is used for managing users, creating users, managing inventory, etc
            //Admin information is given to the manager/group leader/whoever is in charge
            User adminUser = new User();
            String encryptedAdminPassword = encryptor.encryptPassword(defaultAdminPassword);
            adminUser.setFirstName("Administrator");
            adminUser.setLastName("");
            adminUser.setEmail(defaultAdminUsername);
            adminUser.setPassword(encryptedAdminPassword);
            adminUser.setLastLogin(dateUtils.getCurrentDateTime());
            adminUser.setDateCreated( dateUtils.getCurrentDateTime() );
            adminUser.setDeleted(false);
            Role role = roleRepository.findOne(Ebean.find(Role.class).where().eq("name", "Administrator"));
            adminUser.addRole(role);
            adminUser.setPasswordReset(false);
            adminUser.setPasswordCreatedDate( dateUtils.getCurrentDateTime() );
            userRepository.create(adminUser);

            //SuperUser is currently only used for managing dynamic tabs on the medical page
            //SuperUser is an account that gives access to important configuration
            //settings
            User superUser = new User();
            String encryptedSuperuserPassword = encryptor.encryptPassword(defaultSuperuserPassword);
            superUser.setFirstName("SuperUser");
            superUser.setLastName("");
            superUser.setEmail(defaultSuperuserUsername);
            superUser.setPassword(encryptedSuperuserPassword);
            superUser.setLastLogin(dateUtils.getCurrentDateTime());
            superUser.setDateCreated( dateUtils.getCurrentDateTime() );
            superUser.setDeleted(false);
            Role role1 = roleRepository.findOne(Ebean.find(Role.class).where().eq("name", "SuperUser"));
            superUser.addRole(role1);
            superUser.setPasswordReset(false);
            superUser.setPasswordCreatedDate( dateUtils.getCurrentDateTime() );
            userRepository.create(superUser);
        }
    }
}
