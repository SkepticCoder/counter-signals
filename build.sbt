name := "counter-signals"
version := "1.0.0"
scalaVersion := "2.12.17"
val sparkVersion = "3.3.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "compile",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "compile",
  "com.typesafe" % "config" % "1.4.2",
  // testing
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test",

  // logging
  "org.apache.logging.log4j" % "log4j-api" % "2.4.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.4.1"
)

scalacOptions ++= List("-feature", "-deprecation", "-unchecked", "-Xlint")

//lazy val spark_run = taskKey[Unit]("Builds the assembly and ships it to the Spark Cluster")
//spark_run := {
//  ("/full/path/to/bin/spark_submit " + assemblyOption.value) !
//}

