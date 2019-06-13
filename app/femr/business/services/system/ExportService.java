package femr.business.services.system;

import femr.business.helpers.QueryProvider;
import femr.business.services.core.IExportService;
import femr.common.dtos.ServiceResponse;
import femr.common.models.ResearchExportItem;
import femr.data.daos.IRepository;
import femr.data.daos.core.IResearchRepository;
import femr.data.models.core.*;
import femr.data.models.mysql.PatientEncounterTabField;
import femr.util.calculations.dateUtils;
import femr.util.export.CsvFileBuilder;
import io.ebean.ExpressionList;

import javax.inject.Inject;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ExportService implements IExportService {

    private final IResearchRepository researchEncounterRepository;
    private final IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository;

    @Inject
    public ExportService(IResearchRepository researchEncounterRepository, IRepository<IPatientEncounterTabField> patientEncounterTabFieldRepository) {
        this.researchEncounterRepository = researchEncounterRepository;
        this.patientEncounterTabFieldRepository = patientEncounterTabFieldRepository;
    }

    @Override
    public ServiceResponse<File> exportAllEncounters(Collection<Integer> tripIds){

        ServiceResponse<File> response = new ServiceResponse<>();

        // As new patients are encountered, generate a UUID to represent them in the export file
        Map<Integer, UUID> patientIdMap = new HashMap<>();
        List<ResearchExportItem> exportItems = this.researchEncounterRepository
                .findAllEncountersForTripIds(tripIds)
                .stream()
                .peek(encounter -> patientIdMap.putIfAbsent(encounter.getPatient().getId(), UUID.randomUUID()))
                .map(encounter -> {

                    ResearchExportItem item = new ResearchExportItem();
                    item.setPatientId(patientIdMap.get(encounter.getPatient().getId()));
                    item.setPatientCity(encounter.getPatient().getCity());
                    item.setGender(encounter.getPatient().getSex());
                    item.setBirthDate(encounter.getPatient().getAge());
                    item.setDayOfVisit(dateUtils.convertTimeToString(encounter.getDateOfTriageVisit()));

                    // We should be able to assume a Mission Trip exists here since we are querying by tripIds
                    item.setTripId(encounter.getMissionTrip().getId());
                    item.setTripTeam(encounter.getMissionTrip().getMissionTeam().getName());
                    item.setTripCountry(encounter.getMissionTrip().getMissionCity().getMissionCountry().getName());

                    Integer age = (int) Math.floor(dateUtils.getAgeAsOfDateFloat(encounter.getPatient().getAge(), encounter.getDateOfTriageVisit()));
                    item.setAge(age);

                    encounter.getChiefComplaints()
                        .forEach(c -> item.getChiefComplaints().add(c.getValue()));

                    // TODO - need to take into account replacements, should we skip undispensed medications?
                    encounter.getPatientPrescriptions()
                        .stream()
                        .filter(p -> p.getDateDispensed() != null)
                        .forEach(p -> item.getDispensedMedications().add(p.getMedication().getName()));

                    // TODO - move this to a repository or include via ebean
                    ExpressionList<PatientEncounterTabField> patientEncounterTabFieldExpressionList = QueryProvider.getPatientEncounterTabFieldQuery()
                            .where()
                            .eq("patient_encounter_id", encounter.getId());
                    patientEncounterTabFieldRepository.find(patientEncounterTabFieldExpressionList)
                        .forEach(tf -> item.getTabFieldMap().put(tf.getTabField().getName(), tf.getTabFieldValue()));

                    encounter.getEncounterVitals()
                        .forEach(v -> {
                            item.getVitalMap().put(v.getVital().getName(), v.getVitalValue());
                        });

                    return item;

                })
                .collect(Collectors.toList());

        // TODO
        // make sure multiple problem fields make it into the export
        // only used dispensed medications for now - prescribed and not dispensed is too much

        File exportedCsv = CsvFileBuilder.createCsvFile(exportItems);
        response.setResponseObject(exportedCsv);

        return response;
    }

}
