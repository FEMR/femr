package femr.data.daos.core;

import femr.data.models.core.IBurnRate;
import org.joda.time.DateTime;

import java.util.List;


/**
 * Created by Parham on 11/20/16.
 */
public interface IBurnRateRepository {

    IBurnRate createBurnRate (int medID, float burnRate, DateTime calculatedDateTime);

    IBurnRate updateBurnRate (IBurnRate burnRate);

    IBurnRate retrieveBurnRateByMedId(int medid);

    List<? extends IBurnRate> retrieveAllBurnRates();

}
