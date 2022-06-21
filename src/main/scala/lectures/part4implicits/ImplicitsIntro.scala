package lectures.part4implicits

object ImplicitsIntro extends scala.App {

  // how does that compile?
  val pair = "daniel" -> "555"

  case class Person(name: String):
    def greet = s"Hi, my name is $name"

  implicit def fromStringToPerson(str: String): Person = Person(str)

  // String is converted to Person instance so we can use methods defined for Person type
  println("swift".greet)

  case class Box(value: Int):
    def box: Int = value

  implicit def intToBox(int: Int): Box = Box(int)

  println(1.box)

  // implicit params

  def increment(x: Int)(implicit amount: Int): Int = x + amount

  implicit val defaultAmount: Int = 10

  println(increment(2))


}
