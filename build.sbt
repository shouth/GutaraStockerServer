name := "GutaraStocker"

version := "0.1"

scalaVersion := "2.13.0"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.23",
  "com.softwaremill.akka-http-session" %% "core" % "0.5.10",
  "com.github.t3hnar" %% "scala-bcrypt" % "4.1",
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.5",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.5",
  "com.h2database" % "h2" % "1.4.199",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)