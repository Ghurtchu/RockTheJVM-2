package lectures.part4implicits

import java.util.Date

object JsonSerialization extends scala.App {
  /**
  * Users, post, feeds
  * Serialize to JSON
  * */

  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: Date)
  case class Feed(user: User, posts: List[Post])

  /**
   * 1 - intermediate data types: Int, String, List, Date
   * 2 - type classes for conversion from normal case classes to intermediate types
   * 3 - serialize to JSON
   */

  // intermediate type
  sealed trait JsonValue:
    def stringify: String

  final case class JsonObject(values: Map[String, JsonValue]) extends JsonValue {
    override def stringify: String = values.map {
      case (key, value) => "\"" + key + "\":" + value.stringify
    }.mkString("{", ",", "}")
  }

  final case class JsonString(value: String) extends JsonValue {
    override def stringify: String = "\"" + value + "\""
  }

  final case class JsonNumber(value: Int) extends JsonValue {
    override def stringify: String = value.toString
  }

  final case class JsonArray(values: List[JsonValue]) extends JsonValue {
    override def stringify: String = values.map(_.stringify).mkString("[", ",", "]")
  }

  val data = JsonObject(Map(
    "user"  -> JsonString("Daniel"),
    "posts" -> JsonArray(List(
      JsonString("Scala rocks"),
      JsonNumber(345)
    ))
  ))

  println(data.stringify)

  // type class
  // 1 - type class
  // 2 - type class instances (implicits)
  // 3 - "pimp my library" to use type class instances

  // 1
  trait JsonConverter[T]:
    def toJson(value: T): JsonValue

  // 2
  // for existing data types
  implicit object StringConverter extends JsonConverter[String]:
    override def toJson(value: String): JsonValue = JsonString(value)

  implicit object NumberConverter extends JsonConverter[Int]:
    override def toJson(value: Int): JsonValue = JsonNumber(value)

  // for custom data types we must write implicit converters

  implicit object UserConverter extends JsonConverter[User]:
    override def toJson(user: User): JsonValue = JsonObject(
      Map(
        "name"  -> JsonString(user.name),
        "age"   -> JsonNumber(user.age),
        "email" -> JsonString(user.email)
      )
    )

  implicit object PostConverter extends JsonConverter[Post]:
    override def toJson(post: Post): JsonValue = JsonObject(
      Map(
        "content" -> JsonString(post.content),
        "createdAt" -> JsonString(post.createdAt.toString)
      )
    )

  implicit object FeedConverter extends JsonConverter[Feed]:
    override def toJson(feed: Feed): JsonValue = JsonObject(
      Map(
        "user"  -> feed.user.jsonify,
        "posts" -> JsonArray(feed.posts.map(_.jsonify))
      )
    )

  implicit class JsonOps[T](value: T):
    def jsonify(implicit jsonConverter: JsonConverter[T]): JsonValue = jsonConverter.toJson(value)

  // call stringify on result

  val now  = new Date(System.currentTimeMillis())
  val john = User("John", 34, "john@rockthejvm.com")
  val feed = Feed(john, List(
    Post("hello", now),
    Post("look at this cute puppy", now)
  ))

  println {
    feed.jsonify.stringify
  }

}
