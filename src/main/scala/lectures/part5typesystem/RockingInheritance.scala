package lectures.part5typesystem

object RockingInheritance extends scala.App {

  // convenience

  trait Writer[T]:
    def write(value: T): Unit

  trait Closeable:
    def close(status: Int): Unit

  trait GenericStream[T]:
    // some methods here
    def forEachRunF(f: T => Unit): Unit

  // warm up
  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.forEachRunF(println)
    stream.write(1.asInstanceOf[T])
    stream.close(0)
  }

  // diamond problem

  trait Animal:
    def name: String

  trait Lion   extends Animal:
    override def name: String = "lion"

  trait Tiger  extends Animal:
    override def name: String = "tiger"

  // overrides both of them
  class Mutant extends Lion with Tiger
  // override def name: String = "Liger?!.. I don't know"

  val m = new Mutant
  println(m.name)

  // the super problem + type linearization

  trait Cold:
    def print(): Unit = println("cold")

  trait Green extends Cold:
    override def print(): Unit =
      println("green")
      super.print() // should also print "cold"

  trait Blue extends Cold:
    override def print(): Unit =
      println("blue")
      super.print() // should also print "cold"

  trait Red:
    def print(): Unit = println("red")

  class White extends Red with Green with Blue:
    override def print(): Unit =
      println("white")
      super.print()

  val color = new White
  color.print()

}
