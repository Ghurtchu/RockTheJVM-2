package lectures.part1as

import scala.annotation.tailrec

object Recap extends scala.App {

  val aCondition: Boolean = false
  val aConditionedVal = if aCondition then 42 else 65
  // instructions vs expressions

  // does not have a type but compiler infers it
  val aCodeBlock = {
    if (aCondition) 52 // redundant

    56 // return value
  }

  // Unit = void
  // value repr of unit = {} or ()
  val theUnit = println("Hello, Scala!")

  def aFunction(x: Int): Int = x + 1

  // recursion: stack and tail
  // rewritten in while loop
  @tailrec def fact(n: Int, acc: Int): Int = {
    if n <= 0 then acc
    else fact(n - 1, acc * n)
  }

  // oop

  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog // subtyping polymorphism

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch crunch")
  }

  // method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  1 + 2
  1.+(2)

  // anonymous classes

  val anonCarn = new Carnivore:
    override def eat(a: Animal): Unit = println(s"anonymous carnivore eating $a")

  // generics
  abstract class MyList[+A] // variance and variance problems

  // singletons and companions
  object MyList {
    // smart constructors here
  }

  // case classes
  case class Person(name: String, age: Int)

  // exceptions and try/catch/finally

  val throwsException: Nothing = throw new RuntimeException("booooooom")

  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e: Exception => "I caught an exception"
  } finally {
    println("logs..")
  }

  // packaging and imports

  // functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x: Int) => x + 1

  List(1, 2, 3).map(anonymousIncrementer)

  // map, flatMap, filter

  // for comprehension

  val pairs = for {
    num  <- List(1, 2, 3)
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  // collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples

  val aMap = Map (
    "Daiel" -> 123,
    "Nika"  -> 555
  )

  // collections: Options, Try (monads)
  val anOption = Some(5)

  // pattern matching
  val x = 2

  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x + "th"
  }

  val bob = Person("Bob", 22)

  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
    case _            => "I dont know..."
  }

}
