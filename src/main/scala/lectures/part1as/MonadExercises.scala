package lectures.part1as

object MonadExercises extends scala.App {

  class Lazy[+A](value: => A):
    def use: A = value
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(value)

  object Lazy:
    def apply[A](value: => A): Lazy[A] = new Lazy[A](value)

  val lazyInstance: Lazy[Int] = Lazy {
    println("Today I don't feel like doing anything")

    42
  }

  println(lazyInstance.use)

  val flatMappedInstance = lazyInstance.flatMap { inner =>
    Lazy {
      inner * 10
    }
  }

  println(flatMappedInstance.use)

}
