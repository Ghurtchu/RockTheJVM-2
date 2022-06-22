package lectures.part4implicits

object ImplicitsWithTypeClasses extends scala.App {

  trait HTMLSerializer[T]:
    def serialize(t: T): String

  case class Person(name: String):
    override def toString: String = s"Person(name=$name)"

  object PersonSerializer extends HTMLSerializer[Person]:
    override def serialize(person: Person): String =
      s"""
         |<p> Person = ${person} </p>
         |""".stripMargin

  implicit class PersonSerializerSyntax[T](t: T):
    def toHtml(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(t)


  object HTMLSerializer:
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]): HTMLSerializer[T] = serializer

  val p = Person("Niko")
  println(PersonSerializer.serialize(p))

  // due to the apply[T] we can use this
  HTMLSerializer[Person].serialize(p)

  implicit val personSerializer: HTMLSerializer[Person] = PersonSerializer
  println(p.toHtml)

}
