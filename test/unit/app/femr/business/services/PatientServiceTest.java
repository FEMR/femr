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