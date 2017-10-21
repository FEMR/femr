package mock.femr.data.daos;

import femr.data.daos.core.IPatientRepository;
import femr.data.models.core.IPatient;
import femr.data.models.core.IPatientAgeClassification;
import femr.util.stringhelpers.StringUtils;
import mock.femr.data.models.MockPatient;

import java.util.ArrayList;
import java.util.List;

public class MockPatientRepository implements IPatientRepository{

    public boolean createPatientAgeClassificationWasCalled = false;
    public boolean retrieveAllPatientAgeClassificationsWasCalled = false;
    public boolean retrieveAllPatientsWasCalled = false;
    public boolean retrievePatientsInCountryWasCalled = false;
    public boolean retrievePatientByIdWasCalled = false;
    public boolean retrievePatientsByNameWasCalled = false;
    public boolean retrievePatientsByPhoneNumberWasCalled = false;
    public boolean savePatientWasCalled = false;

    public IPatient mockPatient;
    private List<? extends IPatient> mockPatients;

    public MockPatientRepository(){

        this.mockPatient = new MockPatient();

        List<MockPatient> tempList = new ArrayList<>();
        tempList.add(new MockPatient());
        tempList.add(new MockPatient());

        this.mockPatients = tempList;
    }

    @Override
    public IPatientAgeClassification createPatientAgeClassification(String name, String description, int sortOrder) {

        createPatientAgeClassificationWasCalled = true;
        return null;
    }

    @Override
    public List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications() {

        retrieveAllPatientAgeClassificationsWasCalled = true;
        return null;
    }

    @Override
    public List<? extends IPatientAgeClassification> retrieveAllPatientAgeClassifications(boolean isDeleted) {

        retrieveAllPatientAgeClassificationsWasCalled = true;
        return null;
    }

    @Override
    public List<? extends IPatient> retrieveAllPatients() {

        retrieveAllPatientsWasCalled = true;
        return mockPatients;
    }

    @Override
    public List<? extends IPatient> retrievePatientsInCountry(String country) {

        retrievePatientsInCountryWasCalled = true;
        return mockPatients;
    }

    @Override
    public IPatient retrievePatientById(Integer id) {

        retrievePatientByIdWasCalled = true;
        if (id == 0)
            mockPatient = null;
        return mockPatient;
    }

    @Override
    public List<? extends IPatient> retrievePatientsByName(String firstName, String lastName) {

        retrievePatientsByNameWasCalled = true;
        return mockPatients;
    }

    @Override
    public List<? extends IPatient> retrievePatientsByPhoneNumber(String phoneNumber) {

        retrievePatientsByPhoneNumberWasCalled = true;
        return mockPatients;
    }

    @Override
    public IPatient savePatient(IPatient patient) {
        
        savePatientWasCalled = true;

        return patient;
    }
}
