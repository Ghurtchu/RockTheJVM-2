package lectures.part5typesystem

object HigherKindedTypes extends scala.App {

  trait Type[F[_]] // higher kinded type

  trait MyList[T] {
    def flatMap[B](f: T => MyList[B]): MyList[B]
  }

  trait MyOption[A] {
    def map[B](f: A => B): MyOption[B]
  }

  trait MyFuture[C] {
    def flatMap[G](f: C => MyFuture[G]): G
  }

  // combine/multiply List(1, 2) x List("a", "b") => List("1a", "1b", "2a", "2b")
  def multiplyList[A, B](listA: List[A], listB: List[B]): List[(A, B)] = for {
    a <- listA
    b <- listB
  } yield (a, b)

  // Do you smell code duplication?
  def multiplyOption[A, B](optionA: Option[A], optionB: Option[B]): Option[(A, B)] = for {
    a <- optionA
    b <- optionB
  } yield (a, b)

  trait Monad[F[_], A] {
    def flatMap[B](f: A => F[B]): F[B]
    def map[B](f: A => B): F[B]
  }

  class MonadList(list: List[Int]) extends Monad[List, Int] {
    override def flatMap[B](f: Int => List[B]): List[B] = list.flatMap(f)
    override def map[B](f: Int => B): List[B] = list.map(f)
    override def toString: String = list.toString()
  }

  println {
    MonadList {
      List (1, 2, 3, 4, 5).map { int =>
        int + 1
      }
    }
  }



}
