package assessment.signal.util

import assessment.signal.Main.spark
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkEnvironment {

  protected lazy val config = ConfigFactory.load()

  protected lazy val sparkConf = new SparkConf(true)
  config.entrySet().forEach(entry => {
    sparkConf.set(entry.getKey, entry.getValue.unwrapped().toString)
  })

  protected implicit lazy val spark: SparkSession = {
    val session = SparkSession.builder()
      .config(sparkConf)
      .getOrCreate()

    sys.addShutdownHook(session.close)

    session
  }

}
