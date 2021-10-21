//import org.apache.hadoop.fs.Path
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.apache.spark.sql.functions.{col, current_date, date_format, desc, lit, round, when}
import org.apache.spark.sql.types.{DateType, DoubleType, IntegerType, StringType, StructField, StructType}

import java.io.File


object SparkTraining {

  val schema = StructType(
    List(
      StructField("OrderLineId", IntegerType, false),
      StructField("OrderId", IntegerType, false),
      StructField("ProductId", IntegerType, false),
      StructField("ShipDate", DateType, false),
      StructField("BillDate", DateType, false),
      StructField("UnitPrice", DoubleType, false),
      StructField("NumUnits", IntegerType, false),
      StructField("TotalPrice", DoubleType, false)
    )
  )

  val schemaParquet = StructType(
    List(
      StructField("Destination", StringType, false),
      StructField("Origin", StringType, false),
      StructField("Count", IntegerType, false)
    )
  )

  def main(args: Array[String]): Unit = {

    val hiveDir = new File("D:system/hive")

    val ss = SparkSession.builder()
      .appName(name = "Mon application Spark")
      .master(master = "local[*]")
    //  .enableHiveSupport()
    //  .config("spark.sql.crossJoin.enabled", "true")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.shuffle.compress", "true")
      .config("spark.sql.warehouse.dir", hiveDir.getAbsolutePath())
      .getOrCreate()


    val sc = ss.sparkContext.parallelize(Seq("j","bob", "vins", "stan", "taylor"))
    sc.foreach(e => println(e))

    var rdd2 = ss.sparkContext.parallelize(List(34, 56, 89, 289, 30))
    rdd2.map(e => e*2).foreach(i => println(i))

  /*  var rdd3 = ss.sparkContext.textFile(path = "C:\\test")
      .flatMap(t => t.split(" ") )*/

    //rdd3.foreach(i => println(i))
    //rdd3.take(num = 3)

    import ss.implicits._
   /* val df1 = rdd3.toDF()
    df1.show(10)*/

    val df_orders = ss.read
      .format(source = "com.databricks.spark.csv")
      .schema(schema)
      .option("header", "true")
      .option("delimiter", "\t")
      .option("inferSchema", "true")
      .load("C:\\test\\orderline.txt")
   /* df_orders.show(20)


    df_orders.printSchema()

    df_orders.columns.foreach(e => println(e))

    val df_prix = df_orders.select(
      col("UnitPrice"),
      col("Num".concat("Units")),
      col("TotalPrice"),
      col("OrderLineId").cast(StringType)
      //col("OrderLine").as("Order Line Id").cast(StringType)
    )

    val df_prix2 = df_orders.select(
      $"Unitprice".as("Unit Price"),
      $"NumUnits",
      $"TotalPrice",
      $"OrderLineId"
    )

    val listeCols =df_prix.columns

    val df_prix3 = df_prix.selectExpr(listeCols:_*)
    df_prix3.printSchema()

    var df_orderTaxes = df_orders
      .withColumn("Shipdate", date_format(col("Shipdate"), "yyyy-mm-dd"))
      .withColumn("taxes", col("numunits")*col("totalprice")*lit(0.20))
      .withColumn("processingDate", when(col("Shipdate").isNull, current_date()).otherwise("shipdate"))
      .withColumn("promo", when(col("totalprice")<100, 0).otherwise(when(col("totalprice")<=600, 0.05).otherwise(0.06)))
      .withColumn("promoAmount", round(col("promo")*col("totalprice"), 2))
      .withColumn("totalBill", round(col("totalprice")-col("promoAmount")-col("taxes"), 2))

    df_orderTaxes.sort(desc("TotalPrice")).show(200)*/

   /* val df_orderfiltre = df_orderTaxes
      .filter(col("numUnits") === lit(2))
    /µval df_join = df_orders.join(df_orderTaxes, Seq("orderLine"), "inner")
    //.join(df_orderTaxes)
    .filter(col("numunits") > lit(2))
*/
  /*  val df_OrdersDict = ss.read
      .format(source = "com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", "\t")
      .option("inferSchema", "true")
      .load("C:\\test\\orders.txt")

    df_OrdersDict.printSchema()

    val df_ordersJoin = df_orders.join(df_OrdersDict, Seq("OrderId"), "inner")

   // df_ordersJoin.show(200)

    //val df_Union = df_orders.union(df_orderTaxes)
    val df_kpi = df_orders.groupBy(col("productId")).count//("totalprice")

    df_kpi.show()

  //  val df_hive = ss.sql("SELECT * From hivetable_Client where City = 'Paris'")
*/
   /* df_orders.createOrReplaceTempView("orderline")
    df_OrdersDict.createOrReplaceTempView("orders")

    val df_sql= ss.sql("select zipcode, sum(orderline.totalprice) from orders inner join orderline on orders.orderid = orderline.orderid group by orders.zipcode")
    df_sql.show(100)*/

    val df_mysql = ss.read
      .format("jdbc")
      .option("url", "jdbc:mysql://127.0.0.1:3306")
      .option("user", "consultant")
      .option("password", "pwd#86")
      .option("dbtable", "(select state, city, sum(round(numunits * totalprice)) as commandes_totales from jea_db.orders group by state, city) requete")
      .load()

    df_mysql.show()

   df_mysql
     .repartition(col("City"))
     .write
    // .partitionBy("City")
      .format("com.databricks.spark.csv")
      .option("header", "true")
     .mode(SaveMode.Overwrite)
      //.option("")
      .csv("C:\\test\\outputmysql")

    df_orders
      .repartition(1)
      .write
     // .partitionBy("ProductId")
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", ";")
      .mode(SaveMode.Overwrite)
      .csv("C:\\test\\orderlines_semicolumn2")

    df_orders.write
      .format("ors")
      .option("header", "true")
      .mode(SaveMode.Append)
      .csv("C:\\test\\orderlines_ors")

    df_orders.write
      .format("parquet")
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("C:\\test\\orderlines_parquet")
  }

}
