package com.aucfanlab.datalake

import java.util.Properties

import com.databricks.spark.avro._
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by kori on 2016/04/18.
  */
object SparkJDBCExportJob {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkBigQueryExportJob")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val avroFile = "/usr/local/opt/apache-spark/libexec/examples/src/main/resources/users.avro"
    val df = sqlContext.read.avro(avroFile)
    df.show()

    // join array as string
    import sqlContext.implicits._
    val mkString = org.apache.spark.sql.functions.udf((a: Seq[Object]) => a.mkString(","))
    val dfa = df.withColumn("favorite_numbers", mkString($"favorite_numbers"))
    dfa.show()

    val jdbcString = "jdbc:mysql://localhost:3306/hadooptest"
    val jdbcProps = new Properties()
    jdbcProps.setProperty("user", "root")
    jdbcProps.setProperty("useSSL", "false")
    // jdbcProps.setProperty("password", "")
    dfa.write.jdbc(jdbcString, "users", jdbcProps)
  }
}
