package lectures.part5typesystem

object PathDependentTypes extends scala.App {

  class Outer {
    class Inner
    object InnerObj
    type InnerType
  }

  def aMethod: Int = {
    class HelperClass
    type HelperType = String

    2
  }

  // per-instnace
  val outer = new Outer
  val inner = new outer.Inner // cherez outer ;)))

}
