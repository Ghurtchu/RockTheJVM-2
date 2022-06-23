package lectures.part5typesystem

object FBoundedPolymorphism extends scala.App {

//  trait Animal {
//    def breed: List[Animal]
//  }
//
//  class Cat extends Animal {
//    override def breed: List[Animal] = ??? // we want List[Cat]
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Animal] = ??? // we want List[Dog]
//  }

  // solution
//  trait Animal[A <: Animal[A]] { // F-Bounded polymorphism
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Animal[Cat]] = ???
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Animal[Dog]] = ???
//  }

//  trait Entity[E <: Entity[E]] // ORM

  class Person extends Comparable[Person]:
    override def compareTo(t: Person): Int = ???


}
