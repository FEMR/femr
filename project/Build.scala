import play.ebean.sbt.PlayEbean
import play.sbt.PlayJava
import com.typesafe.sbt.digest.Import._
import com.typesafe.sbt.gzip.Import._
import com.typesafe.sbt.web.Import._
import play.sbt.Play.autoImport._
import sbt.Keys._
import sbt._


object ApplicationBuild extends Build {

  val appName = "fEMR"
  val appVersion = "2.3.0"
  val currentScalaVersion = "2.11.7"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    evolutions,
    "com.google.inject" % "guice" % "4.1.0",
    "mysql" % "mysql-connector-java" % "5.1.40",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "org.apache.commons" % "commons-collections4" % "4.0",
    "com.google.code.gson" % "gson" % "2.3.1",
    "com.itextpdf" % "itextpdf" % "5.5.6",
    "com.itextpdf.tool" % "xmlworker" % "5.5.6"
  )


  val main = Project(appName, file(".")).enablePlugins(PlayJava, PlayEbean).settings(

    javacOptions += "-Xlint:deprecation", //*/   //use when searching for deprecated API usage
    javacOptions += "-Xlint:unchecked", //*/     //use when you want to display java warnings
    version := appVersion,
    scalaVersion := currentScalaVersion,
    libraryDependencies ++= appDependencies,
    pipelineStages := Seq(digest, gzip),
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
