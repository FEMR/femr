// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += "jBCrypt Repository" at "https://repo1.maven.org/maven2/org/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.7.0")

// Ebean ORM
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "5.0.1")

//versioning and compression
addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-gzip" % "1.0.2")

