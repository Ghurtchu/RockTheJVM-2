package lectures.part3concurrency
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FuturesAndPromises extends scala.App {

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000) // sleep for two seconds

    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates meaning of life on ANOTHER thread
  }

  println(aFuture.value) // Option[Try[Int]]

  println("Waiting on the future")

  aFuture.onComplete {
    case scala.util.Success(value) => println(s"succeeded with value $value")
    case _                         => println("failed")
  } // will be called by SOME thread or the same thread where the future was executed
    // we have no control over that
  
  Thread sleep 3000

}
