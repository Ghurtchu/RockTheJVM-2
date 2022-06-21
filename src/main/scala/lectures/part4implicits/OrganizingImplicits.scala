package lectures.part4implicits

object OrganizingImplicits extends scala.App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  println(List(4, 2, 3, 5, 1).sorted) // implicit param is being looked in scala.Predef

  /*
  * Implicits (used as implicit parameters):
  * - val/var
  * - object
  * - accessor methods = defs with no parens
  * */

  case class Person(name: String, age: Int)

  implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan((p1, p2) => {
    p1.name.compareTo(p2.name) < 0
  })

  println(List(
    Person("Nika", 12),
    Person("Adda", 55),
    Person("Scalper", 123)
  ).sorted)

  /*
  * Implicit scope
  * - normal scope = LOCAL scope
  * - imported scope
  * - companions of all types involved in the method signature
  * */

  /*
  * Exercise.
  sort by:
  - totalPrice = 50%
  - unit = 25%
  - price = 25%
  * */

  case class Purchase(units: Int, price: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((p1, p2) => {
      p1.price * p1.units < p2.price * p2.units
    })
  }

  object UnitCountOrdering {
    implicit val v: Ordering[Purchase] = Ordering.fromLessThan(_.units < _.units)
  }

  import UnitCountOrdering._

  println(List(
    Purchase(50, 5.3),
    Purchase(1, 1.0)
  ).sorted)

}
