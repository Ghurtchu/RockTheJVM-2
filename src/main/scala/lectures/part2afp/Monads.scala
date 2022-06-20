package lectures.part2afp

object Monads extends scala.App {

  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }

  case class Success[A](value: A) extends Attempt[A] {
    override def flatMap[B](f: A => Attempt[B]): Attempt[B] = f(value)
  }

  case class Failure(e: Throwable) extends Attempt[Nothing] {
    override def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  object Attempt {
    def apply[A](a: => A): Attempt[A] =
      try Success(a)
      catch case e: Exception => Failure(e)

  }

  /*
  * left identity
  * unit.flatMap(f) = f(x)
  * */

  val increment: Int => Success[Int] = a => Success(a + 1)

  assert(Success(5).flatMap(increment) == increment(5))
  


}
