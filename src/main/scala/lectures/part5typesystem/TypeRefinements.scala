package lectures.part5typesystem

object TypeRefinements extends scala.App {

  type JavaCloseable = java.io.Closeable

  // enrich with new abstract method
  type AdvancedCloseable = JavaCloseable {
    def closeSilently(): Unit
  }

  class AdvancedJavaCloseable extends JavaCloseable {
    override def close(): Unit = println("Java resource closing")
    def closeSilently(): Unit = println("Java silent resource closing")
  }

  // type checking => duck typing

  type SoundMaker = {
    def makeSound(): Unit
  }

  class Dog {
    def makeSound(): Unit = println("bark!")
  }

  class Car {
    def makeSound(): Unit = println("vroooom!")
  }

  val dog: SoundMaker = new Dog
  val car: SoundMaker = new Dog


}
