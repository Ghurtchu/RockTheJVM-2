package lectures.part3concurrency

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object BankingAppExample extends scala.App {

  case class User(name: String)

  case class Transaction(sender: String, receiver: String, amount: Double, status: String)


  val name = "Rock the JVM banking"

  def fetchUser(name: String): Future[User] = Future {
    // simulate fetching from the DB
    Thread.sleep(500)

    User(name)
  }

  def createTransaction(from: User, to: String, amount: Double): Future[Transaction] = Future {
    // simulate some processes
    Thread.sleep(1000)

    Transaction(from.name, to, amount, "SUCCESS")
  }

  def purchase(userName: String, item: String, merchantName: String, cost: Double): String = {
    // fetch the user from the DB
    // create a transaction
    // wait for the transaction to finish in order to return proper status

    val transactionStatusFuture = for {
      user <- fetchUser(userName)
      transaction <- createTransaction(user, merchantName, cost)
    } yield transaction.status

    Await.result(transactionStatusFuture, 60.seconds)
    // implicit conversions -> pimp my library
  }

  println(purchase("Daniel", "Iphone 12", "rock the jvm store", 3000))

}
