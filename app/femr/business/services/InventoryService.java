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
package femr.business.services;

import com.avaje.ebean.ExpressionList;
import com.google.inject.Inject;
import femr.business.helpers.DomainMapper;
import femr.business.helpers.QueryProvider;
import femr.common.dto.ServiceResponse;
import femr.data.models.IMedication;
import femr.data.daos.IRepository;
import femr.data.models.Medication;
import femr.common.models.MedicationItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryService implements IInventoryService {
    private IRepository<IMedication> medicationRepository;
    private DomainMapper domainMapper;

    @Inject
    public InventoryService(IRepository<IMedication> medicationRepository,
                            DomainMapper domainMapper) {
        this.medicationRepository = medicationRepository;
        this.domainMapper = domainMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> getMedicationInventory() {
        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();

        ExpressionList<Medication> query = QueryProvider.getMedicationQuery()
                .where()
                .eq("isDeleted", false);

        List<? extends IMedication> medications;
        try {
            medications = medicationRepository.find(query);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        List<MedicationItem> medicationItems = new ArrayList<>();
        for (IMedication m : medications) {
            medicationItems.add(DomainMapper.createMedicationItem(m));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    public ServiceResponse<MedicationItem> createMedication(MedicationItem medicationItem) {
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        try {
            IMedication medication = domainMapper.createMedication(medicationItem);
            medication = medicationRepository.create(medication);
            MedicationItem newMedicationItem = DomainMapper.createMedicationItem(medication);
            response.setResponseObject(newMedicationItem);
        } catch (Exception ex) {
            response.addError("", "error creating medication");
        }

        return response;
    }

}
