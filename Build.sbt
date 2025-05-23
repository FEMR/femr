import play.ebean.sbt.PlayEbean
import play.sbt.PlayJava
import com.typesafe.sbt.digest.Import._
import com.typesafe.sbt.gzip.Import._
import com.typesafe.sbt.web.Import._


val appName = "femr"
val appVersion = "3.1.1"
val currentScalaVersion = "2.11.7"

val appDependencies = Seq(
  // Add your project dependencies here,
  javaCore,
  javaJdbc,
  evolutions,
  jodaForms,
  guice,
  "org.json" % "json" % "20240303",
  "mysql" % "mysql-connector-java" % "5.1.49",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.apache.commons" % "commons-collections4" % "4.0",
  "org.apache.commons" % "commons-text" % "1.3",
  "org.mockito" % "mockito-inline" % "3.5.13",
  "com.google.code.gson" % "gson" % "2.3.1",
  "com.itextpdf" % "itextpdf" % "5.5.6",
  "com.itextpdf.tool" % "xmlworker" % "5.5.6",
  "com.h2database" % "h2" % "1.4.193",
  "com.jcraft" % "jsch" % "0.1.54",
  "ca.uhn.hapi.fhir" % "hapi-fhir-base" % "5.7.0",
  "ca.uhn.hapi.fhir" % "hapi-fhir-structures-r5" % "5.7.0"
)


val main = (project in file(".")).enablePlugins(PlayJava, PlayEbean).settings(

  javacOptions += "-Xlint:deprecation", //*/   //use when searching for deprecated API usage
  javacOptions += "-Xlint:unchecked", //*/     //use when you want to display java warnings
  version := appVersion,
  name := appName,
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
  doc in Compile := target.map(_ / "none").value
)

