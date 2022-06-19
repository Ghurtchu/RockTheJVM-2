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

}
