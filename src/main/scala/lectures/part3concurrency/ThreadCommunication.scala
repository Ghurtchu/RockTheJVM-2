package lectures.part3concurrency

import scala.util.Random

object ThreadCommunication extends scala.App {

  /**
   * the producer-consumer problem
   *
   * producer -> [ produces data ] -> consumer { consumes data }
   *
   */

  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0

    def set(newValue: Int): Unit = {
      value = newValue
    }

    def get: Int =  {
      val result = value
      value = 0

      result
    }
  }

  def naiveProducerConsumer(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      while container.isEmpty do // example of busy waiting
        println("[consumer] actively waiting...")
      println("[consumer] I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing...")
      Thread.sleep(500)
      val value = 42
      println(s"[producer] I have produced, after long work, the value $value")
      container set value
    })

    consumer.start()
    producer.start()

  }

  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      container.synchronized {
        container.wait()
      }

      // container must have the value
      println(s"[consumer] I have consumed ${container.get}")
    })

    val producer = new Thread(() => {
      println("[producer] Hard at work...")
      Thread sleep 2000
      val value = 42

      container.synchronized {
        println(s"[producer] I'm producing the $value")
        container.set(value)
        container.notify()
      }
    })

    consumer.start()
    producer.start()
  }

  smartProdCons()

  // wait and notify - better tools

  /*
  * producer -> [ ? ? ? ] -> consumer
  * both prod and cons may block each other = deadlock
  * */

  def prodConsLargeBuffer(): Unit = {
    import scala.collection._
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]()
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()
      while true do {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffer empty, waiting...")
            buffer.wait()
          }

          // there must be at least ONE value in the buffer
          val x = buffer.dequeue() // extract value out of buffer
          println(s"[consumer] I consumed $x")

          buffer.notify()
        }

        Thread.sleep(random.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0

      while true do {
        buffer.synchronized {
          // if buffer is full
          if (buffer.size == capacity) {
            println("[producer] buffer is full, waiting...")
            buffer.wait() // wait for consumer to wake me up
          }

          // there must be at least one empty space in the buffer for me to produce the value
          println(s"[producer] producing $i")
          buffer.enqueue(i) // put number

          buffer.notify()

          i += 1 // and increment it

          Thread.sleep(random.nextInt(500))

        }
      }
    })

  }

}
