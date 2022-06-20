package lectures.part3concurrency

object Intro extends scala.App {

  // JVM threads
  val aThread: Thread = new Thread(() => println("goo"))

  // create a JVM thread which maps to OS thread one to one
  aThread.start() // gives the signal to JVM to start thread
  // .start() executes .run() of runnable instance which has been passed into the thread instance

  aThread.join() //  main thread is blocked until aThread finishes running

  val t1 = new Thread(() => (1 to 5).foreach(_ => println("t1")))
  val t2 = new Thread(() => (1 to 5).foreach(_ => println("t2")))

  // different runs produce different results
  t1.start()
  t2.start()

  // executors

  val pool = java.util.concurrent.Executors.newFixedThreadPool(10)

  pool.execute(() => println("boom"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 secs")
  })

  pool.shutdown() // no more actions can be submitted
}
