package lectures.part2afp

object StreamExample extends scala.App {

  abstract class MyStream[+A]:

    def isEmpty: Boolean

    def head: A

    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] // prepend operator

    def ++[B >: A](another: MyStream[B]): MyStream[B] // concatenate operator

    def foreach(f: A => Unit): Unit

    def map[B](f: A => B): MyStream[B]

    def flatMap[B](f: A => MyStream[B]): MyStream[B]

    def filter(p: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A] // takes the first n elements out of this stream

    def takeAsList(n: Int): List[A]

  object MyStream:
    // MyStream.from(1)(_ + 1) = stream of natural numbers (potentially infinite)
    def from[A](start: A)(generator: A => A): MyStream[A] = ???

  case object EmptyStream extends MyStream[Nothing] {
    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException

    override def tail: MyStream[Nothing] = throw new NoSuchElementException

    override def #::[B >: Nothing](element: B): MyStream[B] = new Cons(element, this)

    override def ++[B >: Nothing](another: MyStream[B]): MyStream[B] = another

    override def foreach(f: Nothing => Unit): Unit = ()

    override def map[B](f: Nothing => B): MyStream[B] = this

    override def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this

    override def filter(p: Nothing => Boolean): MyStream[Nothing] = this

    override def take(n: Int): MyStream[Nothing] = this

    override def takeAsList(n: Int): List[Nothing] = Nil
  }

  class Cons[+A](hd: A, tl: => MyStream[A]) extends MyStream[A] {
    override def isEmpty: Boolean = false

    override val head: A = hd

    override lazy val tail: MyStream[A] = tl // call by need

    override def #::[B >: A](element: B): MyStream[B] = new Cons(element, this)

    override def ++[B >: A](another: MyStream[B]): MyStream[B] = new Cons(head, tail ++ another)

    override def foreach(f: A => Unit): Unit = ???

    override def map[B](f: A => B): MyStream[B] = ???

    override def flatMap[B](f: A => MyStream[B]): MyStream[B] = ???

    override def filter(p: A => Boolean): MyStream[A] = ???

    override def take(n: Int): MyStream[A] = ???

    override def takeAsList(n: Int): List[A] = ???
  }
}
