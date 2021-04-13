package mock.femr.data.daos;

import femr.data.models.core.IRankedPatientMatch;
import femr.data.models.mysql.Patient;
import femr.data.models.mysql.RankedPatientMatch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RankedPatientMatchTest {
    Patient mockPatient;
    IRankedPatientMatch rankedPatientMatch;
    Integer rank = -1;

    @Before
    public void setUp(){
        mockPatient = mock(Patient.class);
        rankedPatientMatch = new RankedPatientMatch(mockPatient, rank);
    }

    @Test
    public void test1() {
        Assert.assertEquals(rankedPatientMatch.getRank().intValue(), -1);
    }

    @Test
    public void test2() {
        rankedPatientMatch.setRank(2);
        Assert.assertEquals(rankedPatientMatch.getRank().intValue(), 2);
    }
}
