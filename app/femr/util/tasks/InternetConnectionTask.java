package femr.util.tasks;

import javax.inject.Named;
import javax.inject.Inject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import femr.util.InternetConnnection.InternetConnectionUtil;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
//import femr.util.InternetConnnection.InternetConnectionUtil;

public class InternetConnectionTask {

    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;

    @Inject
    public InternetConnectionTask(ActorSystem actorSystem, ExecutionContext executionContext) {
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
                    if(InternetConnectionUtil.getExistsConnection()){
                        InternetConnectionUtil.sendLocationInformation();
                    }
                },
                this.executionContext
        );
    }

}