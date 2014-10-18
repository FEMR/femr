import sbt._
import Keys._
import play.Play.autoImport._

object ApplicationBuild extends Build {

  val appName = "fEMR"
  val appVersion = "2.1.0"
  val currentScalaVersion = "2.11.2"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "com.google.inject" % "guice" % "3.0",
    "mysql" % "mysql-connector-java" % "5.1.18",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "commons-collections" % "commons-collections" % "3.2.1",
    "com.google.code.gson" % "gson" % "2.2.4"
  )


  val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
    /*javacOptions += "-Xlint:deprecation",*/   //use when searching for deprecated API usage
    /*javacOptions += "-Xlint:unchecked",*/     //use when you want to display java warnings
    version := appVersion,
    scalaVersion := currentScalaVersion,
    libraryDependencies ++= appDependencies,
    // Add your own project settings here
    testOptions in Test ~= {
      args =>
        for {
          arg <- args
          ta: Tests.Argument = arg.asInstanceOf[Tests.Argument]
          newArg = if (ta.framework == Some(TestFrameworks.JUnit)) ta.copy(args = List.empty[String]) else ta
        } yield newArg
    },
    sbt.Keys.fork in Test := false,
    doc in Compile <<= target.map(_ / "none")
  )
}
