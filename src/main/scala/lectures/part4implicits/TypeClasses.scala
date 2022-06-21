package lectures.part4implicits

object TypeClasses extends scala.App {


  trait HTMLWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String =
      s"""
         |<div>
         |  <p> name = $name </p>
         |  <p> age = $age </p>
         |  <p> email = $email </p>
         |</div>
         |""".stripMargin
  }

  println(User("John", 32, "john@rockthejvm.com").toHtml)

  // better alternative

  trait HTMLSerializer[T]:
    def serialize(t: T): String

  object UserSerializer extends HTMLSerializer[User]:
    override def serialize(user: User): String = s"""
                                                     |<div>
                                                     |  <p> name = ${user.name} </p>
                                                     |  <p> age = ${user.age} </p>
                                                     |  <p> email = ${user.email} </p>
                                                     |</div>
                                                     |""".stripMargin

  // we can define serializers for other types
  import java.util.Date

  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(t: Date): String =
      s"""
         | <p>date = $t <.p>
         |""".stripMargin
  }

  trait TypeClassTemplate[T]:
    def action(value: T): String


  // eXercise

  trait Equal[T]:
    def ==(a: T, b: T): Boolean

  object NameEquality extends Equal[User]:
    override def ==(a: User, b: User): Boolean = a.name == b.name

  object EmailEquality extends Equal[User]:
    override def ==(a: User, b: User): Boolean = a.email == b.email

}
