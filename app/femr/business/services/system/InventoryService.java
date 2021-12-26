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

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import femr.business.services.core.IInventoryService;
import femr.common.IItemModelMapper;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.InventoryExportItem;
import femr.common.models.MedicationItem;
import femr.common.models.ShoppingListExportItem;
import femr.data.IDataModelMapper;
import femr.data.daos.core.IBurnRateRepository;
import femr.data.daos.core.IMedicationRepository;
import femr.data.daos.core.IPrescriptionRepository;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.BurnRate;
import femr.util.calculations.dateUtils;
import femr.util.stringhelpers.CSVWriterGson;
import femr.util.stringhelpers.GsonFlattener;
import org.ejml.simple.SimpleMatrix;
import org.joda.time.DateTime;
import play.Logger;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class InventoryService implements IInventoryService {

    private final IMedicationRepository medicationRepository;
    private final IUserRepository userRepository;
    private IDataModelMapper dataModelMapper;
    private final IItemModelMapper itemModelMapper;
    private final IBurnRateRepository burnRateRepository;
    private final IPrescriptionRepository prescriptionRepository;

    @Inject
    public InventoryService(IMedicationRepository medicationRepository,
                            IUserRepository userRepository,
                            IDataModelMapper dataModelMapper,
                            @Named("identified") IItemModelMapper itemModelMapper,
                            IBurnRateRepository burnRateRepository,
                            IPrescriptionRepository prescriptionRepository
    ) {

        this.burnRateRepository = burnRateRepository;
        this.medicationRepository = medicationRepository;
        this.userRepository = userRepository;
        this.dataModelMapper = dataModelMapper;
        this.itemModelMapper = itemModelMapper;
        this.prescriptionRepository = prescriptionRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<MedicationItem>> retrieveMedicationInventorysByTripId(int tripId) {
        ServiceResponse<List<MedicationItem>> response = new ServiceResponse<>();

        List<? extends IMedicationInventory> medicationsInventory;
        try {
            medicationsInventory = medicationRepository.retrieveMedicationInventoriesByTripId(tripId, true);
        } catch (Exception ex) {
            response.addError("exception", ex.getMessage());
            return response;
        }

        List<MedicationItem> medicationItems = new ArrayList<>();

        for (IMedicationInventory m : medicationsInventory) {

            String name;
            if (m.getCreatedBy() == null) {
                name = "";
            } else {
                IUser user = userRepository.retrieveUserById(m.getCreatedBy());
                name = user.getLastName() + ", " + user.getFirstName();
            }

            String timeStamp;
            if (m.getTimeAdded() == null) {
                timeStamp = "";
            } else {
                timeStamp = dateUtils.convertTimeToString(m.getTimeAdded());
            }
            medicationItems.add(itemModelMapper.createMedicationItem(m.getMedication(), m.getQuantityCurrent(),
                    m.getQuantityInitial(), m.getIsDeleted(), timeStamp, name));
        }
        response.setResponseObject(medicationItems);

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> retrieveMedicationInventoryByMedicationIdAndTripId(int medicationId, int tripId) {
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        try {

            IMedicationInventory medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            MedicationItem medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);
        } catch (Exception ex) {

            Logger.error("Attempted and failed to execute retrieveMedicationInventoryByMedicationIdAndTripId(" + medicationId + "," + tripId + ") in InventoryService. Stack trace to follow.");
            ex.printStackTrace();
            response.addError("exception", ex.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> setQuantityTotal(int medicationId, int tripId, int quantityTotal) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {

            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            medicationInventory.setQuantityInitial(quantityTotal);
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityInitial(), medicationInventory.getQuantityCurrent(), null, null, null);

            response.setResponseObject(medicationItem);
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }

        return response;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> existsInventoryMedicationInTrip(int medicationId, int tripId) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();
        IMedicationInventory medicationInventory;

        boolean existsInTrip = false;
        try {
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            if (medicationInventory != null) {
                response.setResponseObject(new Boolean(true));
            } else {
                response.setResponseObject(new Boolean(false));
            }
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }


    /**
     * {@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> setQuantityCurrent(int medicationId, int tripId, int newQuantity) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            medicationInventory.setQuantityCurrent(newQuantity);
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), null, null, null);

            response.setResponseObject(medicationItem);
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> createOrUpdateMedicationInventory(int medicationId, int tripId, int quantityCurrent, DateTime timeAdded, String createdBy) {
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);


            if (medicationInventory == null) {
                //If the medication is not in the inventory, then create it.
                //Assume new inventory medications have 0 current quantity and 0 total quantity.

                medicationInventory = dataModelMapper.createMedicationInventory(quantityCurrent, quantityCurrent, medicationId, tripId);

            } else {

                medicationInventory.setQuantityCurrent(medicationInventory.getQuantityCurrent() + quantityCurrent);
                medicationInventory.setTimeAdded(timeAdded);
            }
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityInitial(), medicationInventory.getQuantityCurrent(), null, timeAdded == null ? null : timeAdded.toString(), createdBy);
            response.setResponseObject(medicationItem);
        } catch (Exception ex) {


            response.addError("", ex.getMessage());
        }

        return response;
    }


    /**
     * {@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> reAddInventoryMedication(int medicationId, int tripId) {
        return setDeletionStateOfInventoryMedication(medicationId, tripId, false);
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public ServiceResponse<MedicationItem> deleteInventoryMedication(int medicationId, int tripId) {
        return setDeletionStateOfInventoryMedication(medicationId, tripId, true);
    }

    private ServiceResponse<MedicationItem> setDeletionStateOfInventoryMedication(int medicationId, int tripId, boolean stateToSetTo) {
        ServiceResponse<MedicationItem> response = new ServiceResponse<>();

        IMedicationInventory medicationInventory;
        MedicationItem medicationItem;
        try {
            //This should exist already, so no need to query for unique.
            medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);
            //Soft-delete of the medication from the formulary, then update the backend to reflect the change.
            if (stateToSetTo == true) {
                medicationInventory.setIsDeleted(DateTime.now());
            } else {
                medicationInventory.setIsDeleted(null);
            }
            medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
            medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), medicationInventory.getQuantityCurrent(), medicationInventory.getQuantityInitial(), medicationInventory.getIsDeleted(), null, null);
            response.setResponseObject(medicationItem);

        } catch (Exception ex) {

            Logger.error("InventoryService-setDeletionStateOfInventoryMedication error while delete state of inventory medication", ex.getStackTrace(), ex);
            response.addError("", ex.getMessage());
        }

        return response;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<MedicationItem> subtractFromQuantityCurrent(int medicationId, int tripId, int quantityToSubtract) {

        ServiceResponse<MedicationItem> response = new ServiceResponse<>();
        MedicationItem medicationItem = null;

        try {

            IMedicationInventory medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(medicationId, tripId);

            if (medicationInventory != null && medicationInventory.getMedication() != null) {

                int currentQuantity = 0;
                int totalQuantity = 0;

                medicationInventory.setQuantityCurrent(medicationInventory.getQuantityCurrent() - quantityToSubtract);
                medicationInventory = medicationRepository.saveMedicationInventory(medicationInventory);
                currentQuantity = medicationInventory.getQuantityCurrent();
                totalQuantity = medicationInventory.getQuantityInitial();

                medicationItem = itemModelMapper.createMedicationItem(medicationInventory.getMedication(), currentQuantity, totalQuantity, null, null, null);
            } else {
                Logger.error("InventoryService-subtractFromQuantityCurrent", "tried to search for medication inventory but got null", "medicationId: " + medicationId + "tripId: " + tripId);
            }
        } catch (Exception ex) {

            response.addError("", ex.getMessage());
        }
        response.setResponseObject(medicationItem);

        return response;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public ServiceResponse<String> exportCSV(int tripId) {
        List<? extends IMedicationInventory> medicationInventory = medicationRepository.retrieveMedicationInventoriesByTripId(tripId, false);
        // Convert result of query to a list to export
        List<InventoryExportItem> inventoryExport = new ArrayList<>();
        for (IMedicationInventory med : medicationInventory) {

            String name;
            if (med.getCreatedBy() == null) {
                name = "";
            } else {
                IUser user = userRepository.retrieveUserById(med.getCreatedBy());
                name = user.getLastName() + ", " + user.getFirstName();
            }

            String timeStamp;
            if (med.getTimeAdded() == null) {
                timeStamp = "";
            } else {
                timeStamp = dateUtils.convertTimeToString(med.getTimeAdded());
            }
            inventoryExport.add(new InventoryExportItem(itemModelMapper.createMedicationItem(
                    med.getMedication(), med.getQuantityCurrent(), med.getQuantityInitial(), med.getIsDeleted(),
                    timeStamp, name)));
        }

        // Convert export list to json
        Gson gson = new Gson();
        GsonFlattener parser = new GsonFlattener();
        List<Map<String, String>> flatJson = parser.parse(gson.toJsonTree(inventoryExport).getAsJsonArray());

        // Convert json to CSV
        CSVWriterGson writer = new CSVWriterGson();
        String csvString = writer.getAsCSV(flatJson, InventoryExportItem.getFieldOrder());

        ServiceResponse<String> response = new ServiceResponse<>();
        response.setResponseObject(csvString.toString());

        return response;
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public ServiceResponse<String> importCSV(int tripId, Object file, CurrentUser currentUser) {

        ServiceResponse<String> response = new ServiceResponse<>();
        List<InventoryExportItem> newMedicationInventory = new ArrayList<>();
        List<? extends IMedicationInventory> existentMedicationInventory = medicationRepository.retrieveMedicationInventoriesByTripId(tripId, false);
        try {
            File myObj = (File) file;
            Scanner myReader = new Scanner(myObj);
            Pattern pattern = Pattern.compile(",");
            if (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] items = pattern.split(data);
                if (!items[0].equals("Medication") || !items[1].equals("Quantity")) {
                    response.addError("", "CSV file header is not valid ...");
                    return response;
                }
            }
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] items = pattern.split(data);
                String name;
                name = currentUser.getLastName() + ", " + currentUser.getFirstName();
                boolean medExists = false;
                IMedicationInventory newMedication = null;
                for (IMedicationInventory med : existentMedicationInventory) {
                    MedicationItem medHelper = itemModelMapper.createMedicationItem(med.getMedication(), 0, 0, null, null, null);
                    if (medHelper.getFullName().contains(items[0])) {
                        medExists = true;
                        newMedication = med;
                        break;
                    } else {
                        medExists = false;
                    }
                }
                InventoryExportItem finalMed;


                if (medExists == true) {
                    //update the drug
                    ServiceResponse<MedicationItem> medicationItemServiceResponse;
                    medicationItemServiceResponse = createOrUpdateMedicationInventory(
                            newMedication.getMedication().getId(),
                            tripId,
                            Integer.parseInt(items[1]),
                            Integer.parseInt(items[1]) == 0 ?
                                    newMedication.getTimeAdded() :
                                    DateTime.now(), name);
                    if (!medicationItemServiceResponse.hasErrors()) {
                        finalMed = new InventoryExportItem(itemModelMapper.createMedicationItem(
                                newMedication.getMedication(), newMedication.getQuantityCurrent(), newMedication.getQuantityInitial(), newMedication.getIsDeleted(),
                                Integer.parseInt(items[1]) == 0 ?
                                        newMedication.getTimeAdded().toString() :
                                        DateTime.now().toString(), name));
                    } else {
                        response.addError("", String.valueOf((medicationItemServiceResponse.getErrors().values())));
                        return response;
                    }
                } else {
                    //add the drug
                    IMedication medication = medicationRepository.createNewMedication(items[0]);
                    ServiceResponse<MedicationItem> medicationItemServiceResponse;
                    medicationItemServiceResponse = createOrUpdateMedicationInventory(
                            medication.getId(),
                            tripId,
                            Integer.parseInt(items[1]),
                            DateTime.now(), name);
                    if (true) {
                        finalMed = new InventoryExportItem(itemModelMapper.createMedicationItem(
                                medication, Integer.parseInt(items[1]), Integer.parseInt(items[1]), null,
                                DateTime.now().toString(), name));
                    } else {
                        response.addError("", String.valueOf(medicationItemServiceResponse.getErrors().values()));
                        return response;
                    }
                }

                newMedicationInventory.add(finalMed);


            }
            myReader.close();
            List<InventoryExportItem> newMedicationInventoryHelper = new ArrayList<>();
            for (IMedicationInventory med : existentMedicationInventory) {
                for (InventoryExportItem newMed : newMedicationInventory) {
                    MedicationItem medHelper = itemModelMapper.createMedicationItem(med.getMedication(), 0, 0, null, null, null);
                    if (!medHelper.getFullName().equals(newMed.getName())) {
                        String name;
                        name = currentUser.getLastName() + ", " + currentUser.getFirstName();
                        newMedicationInventoryHelper.add(
                                new InventoryExportItem(itemModelMapper.createMedicationItem(
                                        med.getMedication(), med.getQuantityCurrent(), med.getQuantityInitial(), med.getIsDeleted(),
                                        med.getTimeAdded().toString(), name))
                        );

                    }
                }
            }

            for (InventoryExportItem med : newMedicationInventoryHelper) {
                newMedicationInventory.add(med);
            }
            response.setResponseObject(newMedicationInventory.toString());
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }

        return response;

    }

    public int sum(int[] arr){
        return Arrays.stream(arr).sum();
    }

    public Long sumL(Long[] arr){
        Long all = 0L;
        for (int i = 0; i<arr.length;i++){
            Long.sum(all,arr[i]);
        }
        return all;
    }

    public Long[] piL(int[] x, Long[] y){
        Long[] pi = new Long[x.length];
        for (int i = 0; i < x.length; i++) {
            pi[i] = x[i] * y[i];
        }
        return pi;
    }

    public Long[] power(int[] arr, int deg){
        Long[] po = new Long[arr.length];
        for (int i = 0; i<arr.length;i++){
            po[i] = (long) Math.pow(arr[i],deg + i);
        }
        return po;
    }

    @Override
    public IBurnRate callPredictor(int medId, int tripId) {

        // Initiate burn-rate
        BurnRate burnRate = (BurnRate) burnRateRepository.retrieveBurnRateByMedIdAndTripId(medId, tripId);
        if (burnRate == null)
            burnRate = (BurnRate) burnRateRepository.createBurnRate(medId, null, DateTime.now(), tripId);

        // Check Time slot passed
        DateTime currentTime = DateTime.now();
        DateTime startDT = new DateTime(currentTime.getMillis() - (7 * 3 * 24 * 3600000L));
        Long timeSlot = 24 * 3600000L;
        Long diffTime = currentTime.getMillis() - burnRate.getCalculatedTime().getMillis();
        Long countTS = diffTime / timeSlot; // Count of Time slots passed

        // If we are in new time slot
        if (countTS >= 1L) {

            int[] arrPP = new int[countTS.intValue()]; // X
            Long[] arrTS = new Long[countTS.intValue()]; // Y

            for (int c = 0; c < countTS; c++) {

                List<? extends IPatientPrescription> listPP = prescriptionRepository.retrieveAllPrescriptionsByMedicationId(
                        medId, new DateTime(Long.sum(startDT.getMillis(), c * timeSlot))
                        , new DateTime(Long.sum(startDT.getMillis(), (c + 1) * timeSlot)));

                int quantity = 0; // Accumulated quantity
                for (IPatientPrescription pp : listPP) {
                    quantity += pp.getAmount();
                }

                arrPP[c] = quantity;
                arrTS[c] = Long.sum(startDT.getMillis(), Long.sum(c * 10L, 5L) / 10L * timeSlot);

            }

            Long avgTime = Long.sum(currentTime.getMillis(), startDT.getMillis()) / 2; // XBar (count of time slices)
            int avgPrescriptions = sum(arrPP) / arrPP.length; // YBar

            // initiate matrix
            int dim = 4;
            double[][] mX = new double[dim][dim];
            double[][] mY = new double[dim][1];
            for (int i=0; i<dim; i++){
                for (int j=0; j<dim;i++){
                    mX[i][j] = sumL(power(arrPP,i)); // Sigma X^ deg+i
                }
                mY[i][0] = sumL(piL(power(arrPP,0),arrTS)); // Sigma X^i * Y
            }

            SimpleMatrix smX = new SimpleMatrix(mX);
            smX = smX.invert();
            SimpleMatrix smY = new SimpleMatrix(mY);
            SimpleMatrix result = smX.mult(smY);

            String results ="";
            for (int k=0;k<result.numRows();k++){
                results.concat(String.valueOf(result.get(k,0)));
                if(k!=result.numRows()-1)
                    results.concat("-");
            }
            // Updating burn-rate
            burnRate.setAs(results);
            DateTime firstOfCurrentDt = new DateTime(Long.sum(startDT.getMillis(), countTS * timeSlot));
            burnRate.setCalculatedTime(firstOfCurrentDt);
            burnRateRepository.updateBurnRate(burnRate);
        }

        return burnRate;
    }

    @Override
    public ServiceResponse<String> exportShoppingListCSV(int tripId, int desiredWeeksOnHand) {

        List<ShoppingListExportItem> shoppingListExportItems = createShoppingList(tripId, desiredWeeksOnHand);


        // Convert export shopping list to json
        Gson gson = new Gson();
        GsonFlattener parser = new GsonFlattener();
        List<Map<String, String>> flatJson = parser.parse(gson.toJsonTree(shoppingListExportItems).getAsJsonArray());

        // Convert json to CSV
        CSVWriterGson writer = new CSVWriterGson();
        String csvString = writer.getAsCSV(flatJson, ShoppingListExportItem.getFieldOrder());

        ServiceResponse<String> response = new ServiceResponse<>();
        response.setResponseObject(csvString.toString());

        return response;
    }

    @Override
    public int getRequiredQuantity(int medId, int weeksCount) {
        BurnRate burnRate = (BurnRate) burnRateRepository.retrieveBurnRateByMedId(medId);
        if (burnRate == null){
            return -1; //no prediction found
        }
        String as = burnRate.getAs();
        String[] asArray = as.split("-");
        Long yS = 0L;
        Long yE = 0L;
        Long xS = DateTime.now().getMillis();
        Long xE = xS + (weeksCount * 7 * 24 * 3600000l);
        for (int i = 0; i < asArray.length; i++) {
            Double asI = Double.parseDouble(asArray[i]);
            int ci = (int) Math.ceil(asI / (i + 1) * 1000);
            yS = Long.sum(yS, (long) (ci * (Math.pow(xS, i + 1))));
            yE = Long.sum(yE, (long) (ci * (Math.pow(xE, i + 1))));
        }
        int requiredQ=(int) (Long.sum(yE, -yS));
        if (requiredQ >0){
            return (int) (Long.sum(yE, -yS));
        }else {
            return (int) (Long.sum(yE, 0));
        }
    }

    @Override
    public List<ShoppingListExportItem> createShoppingList(int tripId, int desiredWeeksOnHand) {
        int desiredDaysOnHand = desiredWeeksOnHand * 7;

        List<? extends IBurnRate> medicationBurnRates = burnRateRepository.retrieveAllBurnRatesByTripId(tripId);


        List<ShoppingListExportItem> shoppingListExportItems = new ArrayList<>();
        int quantity;
        for (IBurnRate burnRate : medicationBurnRates) {
            IMedicationInventory medicationInventory = medicationRepository.retrieveMedicationInventoryByMedicationIdAndTripId(burnRate.getMedId(), tripId);

            if (medicationInventory != null && burnRate.getAs() != null) {
                quantity = getRequiredQuantity(burnRate.getMedId(), desiredWeeksOnHand);
                if (quantity > medicationInventory.getQuantityCurrent()) {
                    shoppingListExportItems.add(new ShoppingListExportItem(itemModelMapper.createMedicationItem(
                            medicationInventory.getMedication(),
                            null,
                            null,
                            null,
                            null,
                            null
                    ), quantity - medicationInventory.getQuantityCurrent()));
                }
            }
        }
        return shoppingListExportItems;
    }
}
