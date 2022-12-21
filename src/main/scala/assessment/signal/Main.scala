package assessment.signal

import assessment.signal.model.Signal
import assessment.signal.util.SparkEnvironment
import org.apache.spark.sql.{functions, SaveMode}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{first, last, sum}


object Main extends SparkEnvironment {

  import spark.implicits._

  def main(args: Array[String]): Unit = {
    val signals = spark.read.parquet(this.getClass.getResource("/signals").getPath)
    val ds = signals.as[Signal]

    val statistic_signals = CalculatorStatistic.calculate(signals)

    statistic_signals.coalesce(1).write.mode(SaveMode.Overwrite).parquet("./result.parquet")

    sys.addShutdownHook(spark.close)
  }

}
