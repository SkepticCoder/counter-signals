package assessment.signal

import org.apache.spark.sql.{functions, DataFrame, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{first, last}

object CalculatorStatistic {

  def calculate(signals: DataFrame)(implicit spark: SparkSession): DataFrame = {
    import spark.implicits._

    val entityWindow = Window.partitionBy("entity_id")
      .orderBy($"month_id", $"item_id")

    val entityUnboundedWindow = entityWindow.rowsBetween(Window.unboundedPreceding, Window.unboundedFollowing).orderBy($"month_id")

    signals.withColumn("row_number", functions.row_number().over(entityWindow))
      .withColumn("oldest_item_id", first($"item_id").over(entityUnboundedWindow))
      .withColumn("newest_item_id", last($"item_id").over(entityUnboundedWindow
        .orderBy($"month_id", $"item_id".desc)))
      .withColumn("total_signals", functions.sum($"signal_count").over(entityUnboundedWindow))
      .where($"row_number" === 1)
      .drop($"row_number")
      .select($"entity_id", $"oldest_item_id", $"newest_item_id", $"total_signals")
  }

}
