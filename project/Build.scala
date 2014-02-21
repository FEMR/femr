import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "fEMR"
  val appVersion      = "0.0.1"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "com.google.inject" % "guice" % "3.0",
    "mysql" % "mysql-connector-java" % "5.1.18",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "commons-collections" % "commons-collections" % "3.2.1"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    testOptions in Test ~= { args =>
      for {
        arg <- args
        ta: Tests.Argument = arg.asInstanceOf[Tests.Argument]
        newArg = if(ta.framework == Some(TestFrameworks.JUnit)) ta.copy(args = List.empty[String]) else ta
      } yield newArg
    },
    sbt.Keys.fork in Test := false
  )
}