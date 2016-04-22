name := "SparkJDBCExportJob"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.6.0",
  "com.databricks" % "spark-avro_2.10" % "2.0.1",
  "mysql" % "mysql-connector-java" % "6.0.2"
)