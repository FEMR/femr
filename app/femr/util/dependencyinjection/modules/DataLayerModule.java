package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import femr.common.models.*;
import femr.data.daos.IRepository;
import femr.data.daos.Repository;
import femr.data.models.*;
import femr.util.dependencyinjection.providers.*;

public class DataLayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUser.class).toProvider(UserProvider.class);
        bind(IPatient.class).toProvider(PatientProvider.class);
        bind(IPatientEncounter.class).toProvider(PatientEncounterProvider.class);
        bind(IPatientEncounterVital.class).toProvider(PatientEncounterVitalProvider.class);
        bind(IVital.class).toProvider(VitalProvider.class);
        bind(IPatientPrescription.class).to(PatientPrescription.class);
        bind(IHpiField.class).to(HpiField.class);
        bind(ITreatmentField.class).to(TreatmentField.class);
        bind(IPatientEncounterHpiField.class).to(PatientEncounterHpiField.class);
        bind(IPatientEncounterTreatmentField.class).to(PatientEncounterTreatmentField.class);

        bind(new TypeLiteral<IRepository<IRole>>() {
        }).to(new TypeLiteral<Repository<IRole>>() {});
        bind(new TypeLiteral<IRepository<IUser>>() {}).to(new TypeLiteral<Repository<IUser>>() {});
        bind(new TypeLiteral<IRepository<IPatient>>() {}).to(new TypeLiteral<Repository<IPatient>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounter>>() {}).to(new TypeLiteral<Repository<IPatientEncounter>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterVital>>() {}).to(new TypeLiteral<Repository<IPatientEncounterVital>>() {});
        bind(new TypeLiteral<IRepository<IVital>>() {}).to(new TypeLiteral<Repository<IVital>>() {});
        bind(new TypeLiteral<IRepository<IHpiField>>() {}).to(new TypeLiteral<Repository<IHpiField>>() {});
        bind(new TypeLiteral<IRepository<ITreatmentField>>() {}).to(new TypeLiteral<Repository<ITreatmentField>>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterHpiField>>() {}).to(new TypeLiteral<Repository<IPatientEncounterHpiField >>() {});
        bind(new TypeLiteral<IRepository<IPatientEncounterTreatmentField>>() {}).to(new TypeLiteral<Repository<IPatientEncounterTreatmentField >>() {});

    }
}
