package femr.data.models.mysql;
import femr.data.models.core.IRankedPatientMatch;
import io.ebean.annotation.Sql;
import javax.persistence.*;

@Entity
@Sql
public class RankedPatientMatch implements IRankedPatientMatch {

    @OneToOne
    Patient patient;
    Integer rank;

    public RankedPatientMatch(Patient patient, int rank) {
        this.patient = patient;
        this.rank = rank;
    }

    @Override
    public Integer getRank() {
        return rank;
    }

    @Override
    public Patient getPatient() {
        return patient;
    }

    @Override
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }
}
