name := "sbt-slickcodegen"

version := "1.0"

organization := "com.doddot"

scalaVersion := "2.10.6"

sbtPlugin := true

libraryDependencies ++= Seq(
   "org.apache.derby" % "derbyclient" % "10.12.1.1",
  "com.h2database" % "h2" % "1.4.188",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "mysql" % "mysql-connector-java" % "5.1.39",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7"
)