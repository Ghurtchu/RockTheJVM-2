package lectures.part4implicits

object EqualityTypeClassPattern {

  trait Equal[T]:
    def <=>(t1: T, t2: T): Boolean

  object Equal:
    def equals[T](v1: T, v2: T)(implicit equal: Equal[T]): Boolean = equal.<=>(v1, v2)
    def apply[T](implicit equal: Equal[T]): Equal[T] = equal

  case class Point(x: Int, y: Int)

  implicit object PointEqualizer extends Equal[Point]:
    override def <=>(t1: Point, t2: Point): Boolean = t1.x == t2.x && t1.y == t2.y

  implicit class PointEqualSyntax(p1: Point):
    def ===(p2: Point)(implicit equal: Equal[Point]): Boolean = equal.<=>(p1, p2)

  val pEq = Equal[Point]

  println(pEq.<=>(Point(1, 1), Point(1, 2))) // false

  println(Point(1, 1) === Point(1, 1))

  // AD-HOC polymorphism - inner type polymorphism


}
