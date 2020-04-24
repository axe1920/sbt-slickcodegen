name := "sbt-slickcodegen"

version := "1.0.1"

organization := "com.doddot"

scalaVersion := "2.12.6"

sbtPlugin := true

libraryDependencies ++= Seq(
   "org.apache.derby" % "derbyclient" % "10.12.1.1",
  "com.h2database" % "h2" % "1.4.188",
  "org.postgresql" % "postgresql" % "42.2.8",
  "mysql" % "mysql-connector-java" % "5.1.39",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7"
)