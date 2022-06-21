package lectures.part3concurrency

import scala.util.{ Try, Success, Failure }
import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object PromiseEx extends scala.App {

  val promise = Promise[Int]() // "controller" over a future
  val future = promise.future

  // thread = 1 - "consumer" knows how to handle future completion

  future.onComplete {
    case Success(value) => println(s"[consumer] I've received $value")
  }

  // thread = 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(1000)
    // "fulfilling" the promise
    promise.success(42)
    println("[producer] done")
  })

  Thread.sleep(2000)

  producer.start()

}
