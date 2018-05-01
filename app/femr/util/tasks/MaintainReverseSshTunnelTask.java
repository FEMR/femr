package femr.util.tasks;

import javax.inject.Inject;

import akka.actor.ActorSystem;
import femr.util.InternetConnnection.InternetConnectionUtil;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Check to see if we have an interenet connection, every x seconds, as set in application.conf
 */
public class MaintainReverseSshTunnelTask {

    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;

    @Inject
    public MaintainReverseSshTunnelTask(ActorSystem actorSystem, ExecutionContext executionContext) {
        this.actorSystem = actorSystem;
        this.executionContext = executionContext;

        this.initialize();
    }

    private void initialize() {
        this.actorSystem.scheduler().schedule(
                Duration.create(0, TimeUnit.SECONDS), // initialDelay
                Duration.create(InternetConnectionUtil.getSshTimeoutInMilliseconds()/2, TimeUnit.MILLISECONDS), // interval
                () -> {
                    System.out.println("Maintaining rssh");
                    System.out.println(InternetConnectionUtil.getSshTimeoutInMilliseconds()/2);
                    InternetConnectionUtil.maintainRsshSession();
                },
                this.executionContext
        );
    }

}