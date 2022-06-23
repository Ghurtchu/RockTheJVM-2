package lectures.part4implicits

object ContextBounds extends scala.App {

  trait Serializer[A]:
    def serialize(a: A): String

  case class Data(x: Int, y: Int)

  implicit object DataSerializer extends Serializer[Data]:
    override def serialize(a: Data): String = s"Data(x=${a.x}, y=${a.y})"

  implicit class SerializerSyntax[T](t: T):
    def ~~>(implicit serializer: Serializer[T]): String = serializer.serialize(t)

  println(Data(1, 2).~~>)

  // context bounds

  def serializeBoilerplate[T](t: T)(implicit serializer: Serializer[T]): String =
    s"<html> ${serializer.serialize(t)} </html>"

  def serializeSugar[T : Serializer](content: T): String =
    implicitly[Serializer[T]].serialize(content)

  println(serializeSugar(Data(10, 20)))

}
