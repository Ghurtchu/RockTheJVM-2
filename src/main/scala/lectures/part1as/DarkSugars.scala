package lectures.part1as

object DarkSugars extends scala.App {

  // syntax sugar #1: methods with single param

  def singleArgMethod(arg: Int): String = s"$arg little ducks..."

  val description = singleArgMethod {
    println("code block")

    5
  }

  val aTryInstance = scala.util.Try {
    throw new RuntimeException()
  }

  List(1, 2, 3).map { number =>
    println("adding one...")
    number + 1
  }

  // syntax sugar $2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val action: Action = new Action:
    override def act(x: Int): Int = x + x

  // SAB
  val anInstance: Action = _ + 1
  val anInstance2: Action = x => x + 1

  // example: Runnables
  val aThread: Thread = new Thread (
    new Runnable {
      override def run(): Unit = println("let's go")
    }
  )

  val sweeterThread = new Thread(() => println("yeah.."))

  abstract class AnAbstractType {
    def implemented: Int = 134
    def f(a: Int): Unit
  }

  val anAbstractTypeInstance: AnAbstractType = (a: Int) => println("sweet")

  // syntax sugar #3 special methods

  val prependedList = 2 :: List(3, 4)
  // same as
  List(3, 4).::(2) // associativity

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // could be actual impl here
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar #4: multi-word method naming
  class TeenGirl(name: String) {
    def `and then said`(gossip: String): Unit = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  // syntax sugar #5: infix types

  class Composite[A, B]

  val composite: Composite[Int, String] = ???
  val compositeInfix: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // syntax sugar #6: update() is very special, much like apply()

  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7) => anArray(index, newValue)
  // used in mutable collections

  // syntax sugar #7: setters for mutable containers

  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member: Int = internalMember // getter
    def member_=(value: Int): Unit = {
      internalMember = value // setter
    }
  }

  val mutCont = new Mutable

  mutCont.member = 42 // calls mutCont.member_=

}
