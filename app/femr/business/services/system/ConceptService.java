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
package femr.business.services.system;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.helpers.QueryProvider;
import femr.business.services.core.IConceptService;
import femr.common.IItemModelMapper;
import femr.common.dtos.ServiceResponse;
import femr.common.models.MedicationItem;
import femr.data.daos.IRepository;
import femr.data.models.core.IMedication;
import femr.data.models.mysql.concepts.ConceptMedication;

import java.util.ArrayList;
import java.util.List;

public class ConceptService implements IConceptService {

    private final IRepository<IMedication> conceptMedicationRepository;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public ConceptService(IRepository<IMedication> conceptMedicationRepository,
                          @Named("identified") IItemModelMapper itemModelMapper) {

        this.conceptMedicationRepository = conceptMedicationRepository;
        this.itemModelMapper = itemModelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> retrieveAllMedicationConcepts() {

        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();
        List<MedicationItem> medicationConcepts = new ArrayList<>();

        ExpressionList<ConceptMedication> conceptMedicationExpressionList = QueryProvider.getConceptMedicationQuery()
                .where()
                .eq("isDeleted", false);

        try {


            List<? extends IMedication> allMedications = conceptMedicationRepository.find(conceptMedicationExpressionList);

            for (IMedication m : allMedications) {
                medicationConcepts.add(itemModelMapper.createMedicationItem(m, null, null, null));
            }

            response.setResponseObject(medicationConcepts);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> retrieveConceptMedication(int conceptMedicationID){


        ServiceResponse<MedicationItem> response = new ServiceResponse<>();
        MedicationItem medicationConcept;

        ExpressionList<ConceptMedication> conceptMedicationExpressionList = QueryProvider.getConceptMedicationQuery()
                .where()
                .eq("isDeleted", false)
                .eq("id", conceptMedicationID);

        try{

            IMedication medication = conceptMedicationRepository.findOne(conceptMedicationExpressionList);

            medicationConcept = itemModelMapper.createMedicationItem(medication, null, null, null);

            response.setResponseObject(medicationConcept);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }
}
