package femr.data.daos.system;

import com.google.inject.Inject;
import femr.business.helpers.QueryProvider;
import femr.data.daos.core.IBurnRateRepository;
import femr.data.models.core.IBurnRate;

import femr.data.models.mysql.BurnRate;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import org.joda.time.DateTime;
import play.Logger;

import javax.inject.Provider;
import java.util.List;

public class BurnRateRepository implements IBurnRateRepository {



    private final Provider<IBurnRate> burnRateProvider;

    @Inject
    public BurnRateRepository(Provider<IBurnRate> burnRateProvider) {
        this.burnRateProvider = burnRateProvider;
    }




    @Override
    public IBurnRate createBurnRate(int medID, String as, DateTime calculatedDateTime,int tripId) {

        IBurnRate britem = burnRateProvider.get();
        britem.setAs(as);
        britem.setMedId(medID);
        britem.setCalculatedTime(calculatedDateTime);
        britem.setTripId(tripId);
        try {

            Ebean.save(britem);
        } catch (Exception ex) {

            Logger.error("BurnRateRepository-createBurnRate", ex);
            throw ex;
        }

        return britem;
    }

    @Override
    public IBurnRate updateBurnRate(IBurnRate burnRate) {

        try {
            Ebean.update(burnRate);
        } catch (Exception ex) {

            Logger.error("BurnRateRepository-updateBurnRate", ex);
            throw ex;
        }
        return burnRate;
    }
    @Override
    public IBurnRate deleteBurnRate(IBurnRate burnRate) {

        try {
            Ebean.delete(burnRate);
        } catch (Exception ex) {

            Logger.error("BurnRateRepository-deleteBurnRate", ex);
            throw ex;
        }
        return burnRate;
    }



    @Override
    public IBurnRate retrieveBurnRateByMedId(int medid) {
        ExpressionList<BurnRate> burnRateQuery = QueryProvider.getBurnRateQuery().where().eq("med_id", medid);

        IBurnRate burnRate = null;
        try {
            burnRate = burnRateQuery.findOne();
        } catch (Exception ex) {

            Logger.error("BurnRateRepository-retrieveBurnRateByMedId", ex);
            throw ex;
        }

        return burnRate;
    }

    @Override
    public List<? extends IBurnRate> retrieveAllBurnRates() {
        Query<BurnRate> burnRateQuery = QueryProvider.getBurnRateQuery();

        List<? extends IBurnRate> burnRates;
        try{
            burnRates = burnRateQuery.findList();
        }catch(Exception ex){

            Logger.error("BurnRateRepository-retrieveAllBurnRates", ex);
            throw ex;
        }
        return burnRates;
    }

    @Override
    public List<? extends IBurnRate> retrieveAllBurnRatesByTripId(int tripId) {
        ExpressionList<BurnRate> burnRateQuery = QueryProvider.getBurnRateQuery().where().eq("trip_id",tripId);

        List<? extends IBurnRate> burnRates;
        try{
            burnRates = burnRateQuery.findList();
        }catch(Exception ex){

            Logger.error("BurnRateRepository-retrieveAllBurnRatesByTripId", ex);
            throw ex;
        }
        return burnRates;
    }

    @Override
    public IBurnRate retrieveBurnRateByMedIdAndTripId(int medid, int tripId) {
        ExpressionList<BurnRate> burnRateQuery = QueryProvider.getBurnRateQuery().where().eq("med_id", medid).eq("trip_id",tripId);

        IBurnRate burnRate = null;
        try {
            burnRate = burnRateQuery.findOne();
        } catch (Exception ex) {

            Logger.error("BurnRateRepository-retrieveBurnRateByMedId", ex);
            throw ex;
        }

        return burnRate;
    }
}
