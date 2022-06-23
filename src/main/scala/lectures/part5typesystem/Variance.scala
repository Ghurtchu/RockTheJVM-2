package lectures.part5typesystem

object Variance extends scala.App {

  trait Animal

  class Dog       extends Animal
  class Cat       extends Animal
  class Crocodile extends Animal
  class Kitty     extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  class Cage[T]
  // yes - covariance

  class CCage[+T] // covariant

  // replacing general cage of animals
  // with the specific cage of cats
  val ccage: CCage[Animal] = new CCage[Cat] // Cat extends Animal

  // no - invariance
  class ICage[T]

  val icage: ICage[Animal] = new ICage[Animal]

  // hell no - contravariance

  class XCage[-T]

  // replacing specific cage of cats
  // with a general cage of animals
  val xCage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](val animal: T) // invariant

  // covariant positions
  class CovariantCage[+T](val animal: T) // we say that the generic type of fields are in the COVARIANT position

  //  class ContravariantCage[-T](val animal: T)
  // if it compiled then we'd be able to do stupid stuff like this:
  /**
   * val catCage: ContravariantCage[Cat] = new ContraVariantCage[Animal](new Crocodile) // makes no sense right?
   *
   *
   * */

  // also does not compile because we can re-assign it to other variables wich would make no sense
  // types of vars are in CONTRAVARIANT position
  //  class CovariantVariableCage[+T](var animal: T)
  /**
   * val ccage: CCage[Animal] = new CCage[Cat](new Cat) // fine, but since it's `var`
   * we can do: ccage.animal = new Crocodile // types don't match here.. so it's a problem
   *
   * */

  //  class ContravariantVariableCate[-T](var animal: T) // also does not compile

  class InvariantVariableCage[T](var animal: T) // this is ok

  class MyList[+A]:
    // type widening
    def add[B >: A](element: B): MyList[B] = new MyList[B]

  val emptyList       = new MyList[Kitty] // type turns into => MyList[Kitty]
  val animals         = emptyList.add(new Kitty) // type turns into => MyList[Kitty]
  val moreAnimals     = animals.add(new Cat) // type turns into => MyList[Cat]
  val evenMoreAnimals = moreAnimals.add(new Dog) // type turns into => MyList[Animal]

  // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION.

  // what about method return types?

  class PetShop[-T]:
//    def get(isItAPuppy: Boolean): T // METHOD RETRN TYPES ARE IN COVARIANT POSITION
    def get[B <: T](isItAPuppy: Boolean)(default: B): B = default

}
