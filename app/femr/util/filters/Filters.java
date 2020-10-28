package femr.util.filters;

import com.google.inject.Inject;
import play.http.DefaultHttpFilters;

public class Filters extends DefaultHttpFilters {

    @Inject
    public Filters(ResearchFilter researchFilter){

        super(researchFilter);
    }
}
