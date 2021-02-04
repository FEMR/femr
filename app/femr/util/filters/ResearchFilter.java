package femr.util.filters;



import akka.stream.Materializer;
import com.google.inject.Inject;
import femr.ui.controllers.routes;
import com.typesafe.config.Config;
import play.mvc.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class ResearchFilter extends Filter {

    private Config configuration;

    @Inject
    public ResearchFilter(Config configuration, Materializer mat){

        super(mat);
        this.configuration = configuration;
    }


    @Override
    public CompletionStage<Result> apply(Function<Http.RequestHeader, CompletionStage<Result>> next, Http.RequestHeader rh) {

        //get the research only setting from config file
        String researchOnlySetting_String = configuration.getString("settings.researchOnly");

        //lets assume it's 0 unless told otherwise
        Integer researchOnlySetting_Integer = 0;

        try {

            researchOnlySetting_Integer = Integer.parseInt(researchOnlySetting_String);
        } catch (Exception ex) {

            //it's okay, just leave it at 0
            researchOnlySetting_Integer = 0;
        }

        //only allow a user to enter the research module. If the user does not have a researcher role,
        //they will be stuck on the homepage. These are the actions the user is ALLOWED to take
        if (researchOnlySetting_Integer == 1 &&
                !rh.path().contains("/research") &&
                !rh.path().contains("/assets") &&
                !rh.path().contains("/admin") &&
                !rh.path().contains("/login") &&
                !rh.path().contains("/logout") &&
                !rh.path().equals("/"))

            return CompletableFuture.completedFuture(
                    Results.redirect(routes.ResearchController.indexGet()
                    )
            );

        else {

            return next.apply(rh);
        }
    }
}
