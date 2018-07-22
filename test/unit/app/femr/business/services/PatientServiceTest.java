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
package unit.app.femr.business.services;

import femr.business.services.core.IPatientService;
import femr.business.services.system.PatientService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.PatientItem;
import mock.femr.data.MockDataModelMapper;
import mock.femr.data.daos.MockPatientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;
import mock.femr.common.MockItemModelMapper;

public class PatientServiceTest {

    private IPatientService patientService;
    private MockPatientRepository mockPatientRepository;
    private MockDataModelMapper mockDataModelMapper;
    private MockItemModelMapper mockItemModelMapper;

    @Before
    public void setUp() throws Exception {


        mockPatientRepository = new MockPatientRepository();
        mockDataModelMapper =  new MockDataModelMapper();
        mockItemModelMapper = new MockItemModelMapper();

        patientService = new PatientService(mockPatientRepository, mockDataModelMapper, mockItemModelMapper);
    }

    @After
    public void tearDown() throws Exception {

        //party \(-__-)\
        //      /(-__-)/
        //      \(-__-)/
    }

    @Test
    public void updatePatientAddress_invalidPatientIdProvided_errorProduced() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientAddress(0, "123 not a real address");

        //assert
        assertFalse(mockPatientRepository.savePatientWasCalled);
        assertFalse(mockItemModelMapper.createPatientItemWasCalled);
        assertNull(response.getResponseObject());
        assertTrue(response.hasErrors());
    }

    @Test
    public void updatePatientAddress_ageProvided_agePersisted() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientAddress(1, "123 not a real address");

        //assert
        assertTrue(mockPatientRepository.savePatientWasCalled);
        assertTrue(mockItemModelMapper.createPatientItemWasCalled);
        assertNotNull(response.getResponseObject());
        assertEquals(response.getResponseObject().getAddress(), "123 not a real address");
    }

    @Test
    public void updatePatientAge_invalidPatientIdProvided_errorProduced() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientAge(0, new Date());

        //assert
        assertFalse(mockPatientRepository.savePatientWasCalled);
        assertFalse(mockItemModelMapper.createPatientItemWasCalled);
        assertNull(response.getResponseObject());
        assertTrue(response.hasErrors());
    }

    @Test
    public void updatePatientAge_ageProvided_agePersisted() throws Exception {

        Date testDate = Calendar.getInstance().getTime();

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientAge(1, testDate);

        //assert
        assertTrue(mockPatientRepository.savePatientWasCalled);
        assertTrue(mockItemModelMapper.createPatientItemWasCalled);
        assertNotNull(response.getResponseObject());
        assertEquals(response.getResponseObject().getBirth(), testDate);
    }

    @Test
    public void updatePatientPhoneNumber_invalidPatientIdProvided_errorProduced() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientPhoneNumber(0, "male");

        //assert
        assertFalse(mockPatientRepository.savePatientWasCalled);
        assertFalse(mockItemModelMapper.createPatientItemWasCalled);
        assertNull(response.getResponseObject());
        assertTrue(response.hasErrors());
    }

    @Test
    public void updatePatientPhoneNumber_numberProvided_numberPersisted() throws Exception {

        Random rand = new Random();
        String randomNumber = String.valueOf(rand.nextInt(13));

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientPhoneNumber(1, randomNumber);

        //assert
        assertTrue(mockPatientRepository.savePatientWasCalled);
        assertTrue(mockItemModelMapper.createPatientItemWasCalled);
        assertNotNull(response.getResponseObject());
        assertEquals(response.getResponseObject().getPhoneNumber(), randomNumber);
    }

    @Test
    public void updatePatientSex_invalidPatientIdProvided_errorProduced() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientSex(0, "male");

        //assert
        assertFalse(mockPatientRepository.savePatientWasCalled);
        assertFalse(mockItemModelMapper.createPatientItemWasCalled);
        assertNull(response.getResponseObject());
        assertTrue(response.hasErrors());
    }

    @Test
    public void updatePatientSex_sexProvided_sexPersisted() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.updatePatientSex(1, "male");

        //assert
        assertTrue(mockPatientRepository.savePatientWasCalled);
        assertTrue(mockItemModelMapper.createPatientItemWasCalled);
        assertNotNull(response.getResponseObject());
        assertEquals(response.getResponseObject().getSex(), "male");

        //want to run again with female, but not in a separate unit test.
        //this will make sure that we aren't getting a false positive based
        //on what is stored in the MockPatient by default
        mockPatientRepository.savePatientWasCalled = false;
        mockItemModelMapper.createPatientItemWasCalled = false;

        //act
        response = patientService.updatePatientSex(1, "female");

        //assert
        assertTrue(mockPatientRepository.savePatientWasCalled);
        assertTrue(mockItemModelMapper.createPatientItemWasCalled);
        assertNotNull(response.getResponseObject());
        assertEquals(response.getResponseObject().getSex(), "female");
    }

    @Test
    public void deletePatient_nullReasonProvided_errorProduced() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.deletePatient(1, 2, null);

        //assert
        assertFalse(mockPatientRepository.retrievePatientByIdWasCalled);
        assertFalse(mockPatientRepository.savePatientWasCalled);
        assertTrue(response.hasErrors());
    }

    @Test
    public void deletePatient_emptyReasonProvided_errorProduced() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.deletePatient(1, 2, "");

        //assert
        assertFalse(mockPatientRepository.retrievePatientByIdWasCalled);
        assertFalse(mockPatientRepository.savePatientWasCalled);
        assertTrue(response.hasErrors());
    }

    @Test
    public void deletePatient_parametersProvided_parametersSaved() throws Exception {

        //arrange
        ServiceResponse<PatientItem> response;

        //act
        response = patientService.deletePatient(1, 2, "fake reason");

        //assert
        assertTrue(mockPatientRepository.retrievePatientByIdWasCalled);
        assertTrue(mockPatientRepository.savePatientWasCalled);
            //make sure patient repository was called with proper reason
        assertEquals(mockPatientRepository.mockPatient.getReasonDeleted(), "fake reason");
            //make sure proper user ID is logged
        assertEquals(mockPatientRepository.mockPatient.getDeletedByUserId(), (Integer)2);
        //assertEquals(mockPatientRepository.mockPatient.getId(), 1);

        assertFalse(response.hasErrors());
    }

}