name := "SparkJDBCExportJob"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.6.0",
  "com.databricks" % "spark-avro_2.10" % "2.0.1",
  // "com.databricks" % "spark-csv_2.10" % "1.4.0",
  "mysql" % "mysql-connector-java" % "6.0.2", // MySQL
  "com.microsoft.sqlserver" % "sqljdbc42" % "4.2" // SQL Server (not in central repo)
  // "net.starschema.clouddb.bqjdbc" % "bqjdbc" % "1.4" // @TODO BigQuery JDBC (not in central repo) @see https://code.google.com/archive/p/starschema-bigquery-jdbc/wikis/JDBCURL.wiki
)