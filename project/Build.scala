import com.typesafe.sbt.digest.Import._
import com.typesafe.sbt.gzip.Import._
import com.typesafe.sbt.jse.JsTaskImport.JsTaskKeys
import com.typesafe.sbt.uglify.Import._
import com.typesafe.sbt.rjs.Import._
import com.typesafe.sbt.web.Import._
import com.typesafe.sbt.web.SbtWeb
import play.Play.autoImport._
import sbt.Keys._
import sbt._
import scala.concurrent.duration._
import JsTaskKeys._


object ApplicationBuild extends Build {

  val appName = "fEMR"
  val appVersion = "2.1.4"
  val currentScalaVersion = "2.11.2"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "com.google.inject" % "guice" % "4.0",
    "mysql" % "mysql-connector-java" % "5.1.36",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "org.apache.commons" % "commons-collections4" % "4.0",
    "com.google.code.gson" % "gson" % "2.3.1",
    "com.itextpdf" % "itextpdf" % "5.5.6",
    "com.itextpdf.tool" % "xmlworker" % "5.5.6"
  )


  val main = Project(appName, file(".")).enablePlugins(play.PlayJava, SbtWeb).settings(
    javacOptions += "-Xlint:deprecation", //*/   //use when searching for deprecated API usage
    javacOptions += "-Xlint:unchecked", //*/     //use when you want to display java warnings
    version := appVersion,
    scalaVersion := currentScalaVersion,
    libraryDependencies ++= appDependencies,
    timeoutPerSource := 10.minutes,
    pipelineStages := Seq(rjs, uglify, digest, gzip),
    includeFilter in uglify := GlobFilter("*.js"),
    excludeFilter  in uglify := GlobFilter("*min.js"),
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
