package lectures.part4implicits

object EqualityEnrichment extends scala.App {

  trait Equal[T]:
    def equals(t1: T, t2: T): Boolean

  case class ProgrammingLanguage(name: String, paradigm: String)

  implicit object ProgrammingLanguageEqualizer extends Equal[ProgrammingLanguage]:
    override def equals(t1: ProgrammingLanguage, t2: ProgrammingLanguage): Boolean =
      t1.name == t2.name && t1.paradigm == t2.paradigm

  implicit class EqualSyntax[T](t1: T):
    def ===(t2: T)(implicit equal: Equal[T]): Boolean = equal.equals(t1, t2)
    def !==(t2: T)(implicit equal: Equal[T]): Boolean = ! ===(t2)

  val pl1 = ProgrammingLanguage("Scala", "Hybrid")
  val pl2 = ProgrammingLanguage("Scala", "Hybrid")
  val pl3 = ProgrammingLanguage("Clojure", "Functional")

  assert(pl1 === pl2)
  assert(pl1 !== pl3)
  assert(pl2 !== pl3)

  println("reachable")

  // it's type safe :)


}
