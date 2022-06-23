package lectures.part5typesystem

object TypeMembers extends scala.App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType // abstract type member
    type BoundedAnimal <: Animal // upper bounded with animal
    type SuperBoundedAnimal >: Dog <: Animal // lower bounded with dog but upper bounded with animal
    type AnimalC = Cat
  }

  val ac = new AnimalCollection
  val pup: ac.SuperBoundedAnimal = new Dog

  type CatAlias = Cat
  val c: CatAlias = new Cat

  trait MyList {
    type T
    def add(element: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    override type T = Int
    override def add(element: T): MyList = this
  }

  // .type
  type CatsType = c.type

  // LOCKED
  trait MList {
    type A
    def head: A
    def tail: MList
  }

  trait ApplicableToNumbers {
    type A <: Number
  }

  // should not be ok because it must only work with numbers
//  class CustomList(hd: String, tl: CustomList) extends MList with ApplicableToNumbers {
//    override type A = String
//    override def head: String = hd
//    override def tail: MList = tl
//  }

  class CustomListInt(hd: Int, tl: CustomListInt) extends MList {
    override type A = Int
    override def head: Int = hd
    override def tail: MList = tl
  }

  // we need Number type


}
