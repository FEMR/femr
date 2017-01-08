package femr.util;

import play.http.HttpErrorHandler;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ErrorHandler implements HttpErrorHandler {
    @Override
    public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {

        //When a resource is not found or a bad request is requested
        if (statusCode == Http.Status.NOT_FOUND || statusCode == Http.Status.BAD_REQUEST){

            return CompletableFuture.completedFuture(
                    Results.internalServerError(
                            femr.ui.views.html.errors.global.render()
                    )
            );
        }

        return null;
    }

    //called when throwing a runtime exception
    @Override
    public CompletionStage<Result> onServerError(Http.RequestHeader request, Throwable exception) {
        return CompletableFuture.completedFuture(
                Results.internalServerError(
                        femr.ui.views.html.errors.global.render()
                )
        );
    }
}
