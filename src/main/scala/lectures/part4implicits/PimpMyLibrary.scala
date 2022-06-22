package lectures.part4implicits

import scala.annotation.tailrec

object PimpMyLibrary extends scala.App {

  // 2.isPrime

  implicit class RichInt(val value: Int):
    def isEven:     Boolean = value % 2 == 0
    def squareRoot: Double  = Math.sqrt(value)
    def times(f: () => Unit): Unit  = {
      @tailrec
      def timesAux(n: Int): Unit = {
        if (n <= 0) ()
        else {
          f()
          timesAux(n - 1)
        }
      }

      timesAux(value)
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] = {
        if (n <= 0) List()
        else concatenate(n - 1) ++ list
      }

      concatenate(value)
    }

  implicit class RicherInt(richInt: RichInt):
    def isOdd: Boolean = richInt.value % 2 != 0

  // can't to double conversion
  // 42.isOdd

  println(5.isEven)
  println(5.squareRoot)

  implicit def stringToRichInt(str: String): RichInt = RichInt(str.toInt)

  println("2".isEven)

  import scala.concurrent.duration._

  3.seconds // method from duration DurationConversions

  implicit class RichString(str: String):
    def asInt: Int = str.toInt
    def encrypt(dist: Int): String = str.map(c => (c + dist).asInstanceOf[Char])

  3.times(() => println("Scala rocks"))

  println(4 * List(1, 2, 3, 4, 5))

  // "3" / 4 with implicit conversions

  implicit def stringToInt(str: String): Int = Integer.valueOf(str)

  println(("6" / 2))

  class RichAltInt(value: Int)

  implicit def enrich(value: Int): RichAltInt = new RichAltInt(value)


  /**
   * if (n) do something
   * else do something else
   *
   */

  implicit def intToBoolean(n: Int): Boolean = n > 0

  val aConditionValue = if (3) "OK" else "Something wrong"
  println(aConditionValue)



}
