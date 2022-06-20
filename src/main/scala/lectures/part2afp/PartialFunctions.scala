package lectures.part2afp

object PartialFunctions extends scala.App {

  val aFunction = (x: Int) => x + 1 // Function1[Int, Int] === Int => Int
  val aFuncBeautiful: Int => Int = _ + 1

  val aFussyFunction = (x: Int) => {
    if      (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException
  }

  class FunctionNotApplicableException extends RuntimeException {
    override def getMessage: String = "boom"
  }

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
    case _ => throw new FunctionNotApplicableException
  }

  // {1, 2, 5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  } // partial function value

  println(aPartialFunction(2)) // 56
//  println(aPartialFunction(1254)) // not defined here, throws MatchError

  // PF utilities
  println(aPartialFunction.isDefinedAt(67)) // returns boolean value

  // lift
  val lifted = aPartialFunction.lift // PF to TF (Int => Option[Int])

  println(lifted(2)) // Some(56)
  println(lifted(100)) // None

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // PF extends normal functions

  val aTotalFunc: Int => Int = {
    case 1 => 99
  }

  // HOF-s accept partial functions as well

  val mappedList = List(
    1, 2, 3
  ).map {
    case 1 | 2 => 5
    case 3 => 100000
//    case 10 => ??? this would blow up
  }

  println {
    mappedList
  }

  /*
  * construct a PF instance yourself
  * */

  val pf: PartialFunction[Int, String] = {
    case 1 => "1"
    case 2 => "2"
  }

  val partF = new PartialFunction[Int, Int]:

    override def apply(v1: Int): Int = v1 match {
      case 1 => 42
      case 2 => 65
      case 5 => 1000
    }

    override def isDefinedAt(x: Int): Boolean = x == 1 || x == 2 || x == 5

  val chatbot: PartialFunction[String, String] = {
    case "hello"    => "hello there"
    case "goodbye"  => "bye bye honey"
    case "call mom" => "no :P"
  }

  scala.io.Source.stdin.getLines().foreach { line =>
    println(chatbot(line))
  }

}
