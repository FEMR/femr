package femr.util.tasks;

import javax.inject.Inject;
import akka.actor.ActorSystem;
import femr.util.InternetConnnection.InternetConnectionUtil;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;

public class SendLocationDataTask {

    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;

    @Inject
    public SendLocationDataTask(ActorSystem actorSystem, ExecutionContext executionContext) {
        this.actorSystem = actorSystem;
        this.executionContext = executionContext;

        this.initialize();
    }

    private void initialize() {
        this.actorSystem.scheduler().schedule(
                Duration.create(0, TimeUnit.SECONDS), // initialDelay
                Duration.create(InternetConnectionUtil.getSendLocationDataInvervalInSeconds(), TimeUnit.SECONDS), // interval
                () -> {
                    InternetConnectionUtil.updateExistsConnection();
                    if(InternetConnectionUtil.getExistsConnection() == true){
                        InternetConnectionUtil.sendLocationInformation();
                    }
                },
                this.executionContext
        );
    }

}