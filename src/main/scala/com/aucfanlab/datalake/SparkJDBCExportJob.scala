package com.aucfanlab.datalake

import java.util.Properties

import com.databricks.spark.avro._
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * get Avro file and insert to RDB through JDBC connector.
  * Usage:
  * SparkJDBCExportJob.jar avroFile jdbcString jdbcTableName -Dcom.aucfalab.datalake.arraycolumns="columntojoin,columntojoin2"
  */
object SparkJDBCExportJob {
  def main(args: Array[String]) {
    val file = args(0)
    val jdbcString = args(1)
    val jdbcTableName = args(2)

    val arrayColumns = Option(System.getProperty("com.aucfanlab.datalake.arraycolumns"))

    val conf = new SparkConf().setAppName(s"SparkJDBCExportJob_$jdbcTableName")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    // val avroFile = "/usr/local/opt/apache-spark/libexec/examples/src/main/resources/users.avro"
    var df = sqlContext.read.avro(file)
    // var df = sqlContext.read.json(file)

    // join array as string
    val mkString = org.apache.spark.sql.functions.udf((a: Seq[Object]) => a.mkString(","))
    if (arrayColumns.isDefined) {
      for (c <- arrayColumns.getOrElse("").split(",")) {
        if (!c.isEmpty) df = df.withColumn(c, mkString(df(c)))
      }
    }

    // val jdbcString = "jdbc:mysql://localhost:3306/hadooptest?user=root&useSSL=false"
    val jdbcProps = new Properties()
    // jdbcProps.setProperty("user", "root")
    // jdbcProps.setProperty("useSSL", "false")
    // jdbcProps.setProperty("password", "")
    df.write.mode("append").jdbc(jdbcString, jdbcTableName, jdbcProps)
  }
}
