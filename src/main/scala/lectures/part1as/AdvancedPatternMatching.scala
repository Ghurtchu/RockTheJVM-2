package lectures.part1as

object AdvancedPatternMatching extends scala.App {

  val numbers = List(1)

  val description = numbers match {
    case head :: Nil  => println(s"Single element list: $head")
    case head :: tail => println("more than one element")
  }

  /*
  * constants
  * wildcards
  * case classes
  * tuples
  * some special magic like above
  * */

  class Person(val name: String, val age: Int)

  object Person {
    // custom unapply
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None
      else Some((person.name, person.age))


    def unapply(age: Int): Option[String] = Some(if (age < 21) "minor" else "major")
  }

  val bob = new Person("Bob", 22)

  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a years old."
  }

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  object even {
    def unapply(arg: Int): Option[Boolean] =
      if (arg % 2 == 0) Some(true)
      else None
  }

  object singleDigit {
    def unapply(arg: Int): Option[Boolean] =
      if (arg > -10 && arg < 10) Some(true)
      else None
  }

  object one {
    def unapply(arg: Int): Boolean = arg == 1
  }

  val res = 100 match {
    case singleDigit(_) => "one number"
    case even(_)        => "even"
    case one()          => "found one"
    case _              => "other"
  }

  println(res)

  // infix patterns
  case class Or[A, B](a: A, b: B) // scala stdlib => Either[A, B]
  val either           = Or(2, "two")
  val humanDescription = either match {
    case Or(number, string) => s"$number is written as $string"
  }

  // decomposing sequences

  val varARg = numbers match {
    case List(1, _*) => s"List starts with 1"
  }

  abstract class MyList[+A] {
    def head:        A  = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing] {
    override def head: Nothing = throw new NoSuchElementException("Nope!")
    override def tail: MyList[Nothing] = this
  }

  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  val myList: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))

  val decomposed = myList match {
    case MyList(1, 2, _*) => "starting with 1, 2"
    case _                => "something else"
  }

  println(decomposed)

  // custom return types for unapply
  // return types must have isEmpty: Boolean, get: A

  abstract class Wrapper[A] {
    def isEmpty: Boolean
    def get    : A
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false
      override def get: String = s"${person.name} is ${person.age} years old"
    }
  }

  val p = Person("Nika", 22)

  println(p match {
    case PersonWrapper(n) => s"The result = $n"
    case _                => "I don't care about the result"
  })
}
