package lectures.part3concurrency

object ConcurrencyProblems extends scala.App {

  object problem1 {
    def runInParallel = {

      var x = 0

      val t1 = new Thread(() => {
        x = 1
      })

      val t2 = new Thread(() => {
        x = 2
      })

      t1.start()
      t2.start()

      println(x)
    }

    // race condition problem
    // two threads are trying to manipulate the same memory at the same time

    for _ <- 1 to 100 do runInParallel
  }

  object problem2 {

    class BankAccount(var amount: Int):
      override def toString: String = "" + amount

    def buy(account: BankAccount, thing: String, price: Int): Unit = {
      account.amount -= price
      println("I've bought " + thing)
      println("my account is now " + account)
    }

    // synchronized - no two threads can access the synchronized block
    def buySafe(account: BankAccount, thing: String, price: Int): Unit = account.synchronized {
      account.amount -= price
      println("I've bought " + thing + " safely")
      println("my account is now " + account + " safely")
    }

    // we could also use @volatile for variables that are going to be changed

  }

  import problem2._

  for _ <- 1 to 1000 do {
    val account = new BankAccount(50000)
    val t1 = new Thread(() => buy(account, "Guitar", 3000))
    val t2 = new Thread(() => buy(account, "Iphone12", 4000))

    t1.start()
    t2.start()
    Thread.sleep(1000)
  }

}
