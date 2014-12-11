package unit.app.femr.business.services;

import com.google.inject.Provider;
import femr.business.helpers.DomainMapper;
import femr.business.services.IResearchService;
import femr.business.services.ResearchService;
import femr.common.dto.ServiceResponse;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.*;
import mock.femr.data.daos.MockRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class ResearchServiceTest {

    private IResearchService researchService;

    private Repository<IPatient> mockPatientIRepository;
    private Repository<IPatientEncounter> mockPatientEncounterIRepository;
    private Repository<IPatientEncounterVital> mockPatientEncounterVitalIRepository;

    private Repository<IChiefComplaint> mockChiefComplaintIRepository;
    private Repository<IUser> mockUserIRepository;
    private Repository<IVital> mockVitalIRepository;
    private Repository<IPatientPrescription> mockPrescriptionIRepository;
    private Repository<IMedication> mockMedicationIRepository;

    private Provider<IPatientEncounterVital> mockPatientEncounterVitalProvider;
    private DomainMapper domainMapper;

    private Provider<IChiefComplaint> chiefComplaintProvider;
    private Provider<IMedication> medicationProvider;
    private Provider<IMedicationActiveDrugName> medicationActiveDrugNameProvider;
    private Provider<IMedicationActiveDrug> medicationActiveDrugProvider;
    private Provider<IMedicationMeasurementUnit> medicationMeasurementUnitProvider;
    private Provider<IMedicationForm> medicationFormProvider;
    private Provider<IPatient> patientProvider;
    private Provider<IPatientAgeClassification> patientAgeClassificationProvider;
    private Provider<IPatientEncounterPhoto> patientEncounterPhotoProvider;
    private Provider<IPatientEncounter> patientEncounterProvider;
    private Provider<IPatientEncounterTabField> patientEncounterTabFieldProvider;
    private Provider<IPatientEncounterVital> patientEncounterVitalProvider;
    private Provider<IPatientPrescription> patientPrescriptionProvider;

    private Provider<IPhoto> photoProvider;
    private Provider<IRole> roleProvider;
    private Provider<ITabField> tabFieldProvider;
    private Provider<ITabFieldSize> tabFieldSizeProvider;
    private Provider<ITabFieldType> tabFieldTypeProvider;
    private Provider<ITab> tabProvider;
    private Provider<IUser> userProvider;
    private Provider<IVital> vitalProvider;


    @Before
    public void setUp() {

        mockPatientIRepository = new Repository<>();
        mockPatientEncounterIRepository = new Repository<>();
        mockChiefComplaintIRepository = new Repository<>();
        mockUserIRepository = new Repository<>();
        mockVitalIRepository = new Repository<>();
        mockPrescriptionIRepository = new Repository<>();
        mockMedicationIRepository = new Repository<>();
        mockPatientEncounterVitalIRepository = new Repository<>();

        domainMapper = new DomainMapper(chiefComplaintProvider,
                            medicationProvider,
                            medicationActiveDrugNameProvider,
                            medicationFormProvider,
                            medicationActiveDrugProvider,
                            medicationMeasurementUnitProvider,
                            patientProvider,
                            patientAgeClassificationProvider,
                            patientEncounterPhotoProvider,
                            patientEncounterProvider,
                            patientEncounterTabFieldProvider,
                            patientEncounterVitalProvider,
                            patientPrescriptionProvider,
                            photoProvider,
                            roleProvider,
                            tabFieldProvider,
                            tabFieldSizeProvider,
                            tabFieldTypeProvider,
                            tabProvider,
                            userProvider,
                            vitalProvider);

        researchService = new ResearchService(mockChiefComplaintIRepository,
                                                mockPatientIRepository,
                                                mockPatientEncounterIRepository,
                                                mockPatientEncounterVitalIRepository,
                                                mockUserIRepository,
                                                mockVitalIRepository,
                                                mockPrescriptionIRepository,
                                                mockMedicationIRepository,
                                                mockPatientEncounterVitalProvider,
                                                domainMapper);

    }

    @Test
    public void getAllMedicationsCheck(){

        //ServiceResponse<Map<Integer, String>> medResponse = researchService.getAllMedications();
        //assertThat(medResponse.getResponseObject().size() > 1);
    }

}
