package lectures.part5typesystem

object SelfTypes extends scala.App {

  // Self Types is a way of requiring a type to be mixed in

  trait Instrumentalist:
    def play(): Unit

  trait Singer { self: Instrumentalist => // whoever implements singer must implement Instrumentalist as well
    def sing(): Unit
  }

  // follows the constraint imposed by the self type on `Singer` trait
  class LeadSinger extends Singer with Instrumentalist {
    override def sing(): Unit = ???

    override def play(): Unit = ???
  }

  // illegal. It must also extend Instrumentalist
  //  class Vocalist extends Singer {
  //
  //  }

  val jamesHetfield = new Singer with Instrumentalist:
    override def sing(): Unit = "mur mur"

    override def play(): Unit = "solo"


  class Guitarist extends Instrumentalist:
    override def play(): Unit = "prrrrrrrrrrrrrrrrrrrrl"

  val ericClapton = new Guitarist with Singer {
    override def sing(): Unit = println("clapton sings")
    override def play(): Unit = println("clapton plays")
  }

  class A
  class B extends A

  trait T
  trait S { self: T => // S requires a T, T must be mixed in, it must be a part of equation

  } // CAKE PATTERN, composition, "dependency injection"

  class Component {
    // API
  }

  class ComponentA extends Component
  class ComponentB extends Component

  // depends on Component
  // may receive ComponentA or ComponentB
  class DependentComponent(component: Component)

  // CAKE PATTERN
  trait ScalaComponent {
    // API
    def `do`(): Unit
  }

  class ScalaComponentA extends ScalaComponent {
    override def `do`(): Unit = println("comp a")
  }
  class ScalaComponentB extends ScalaComponent {
    override def `do`(): Unit = println("comp b")
  }

  // must use ScalaComponent
  trait ScalaDependentComponent { self: ScalaComponent =>
    def act(x: Int): String = {
      self.`do`()

      "str"
    }
  }

  // layer 1
  trait Picture extends ScalaComponent
  trait Stats   extends ScalaComponent

  // layer 2
  trait Profile   extends ScalaDependentComponent with Picture
  trait Analytics extends ScalaDependentComponent with Stats
  
}
