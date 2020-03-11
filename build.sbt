name := "SlickMySQLTest"

version := "0.1"

scalaVersion := "2.13.1"


libraryDependencies++= Seq(

  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.slf4j" % "slf4j-api" % "1.7.26",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "mysql" % "mysql-connector-java" % "8.0.16"

)
