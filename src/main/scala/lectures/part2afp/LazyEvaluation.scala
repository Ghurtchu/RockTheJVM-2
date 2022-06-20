package lectures.part2afp

object LazyEvaluation extends scala.App {

//  val x: Int = throw new RuntimeException // eager evaluation -> evaluated wherever it is defined

  // evaluated on by need basis
  lazy val lazyX: Int = {
    println("Hello")

    42
  } // evaluation is delayed until it's used elsewhere

  println(lazyX)
  println(lazyX)

  // examples of implications:
  def sideEffectCondition: Boolean = {
    println("boo")

    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition

  // short circuiting example
  // simpleCondition is already false so `lazyCondition` is never evaluated
  println(if (simpleCondition && lazyCondition) "yes" else "no")

  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = n + n + n + 1
  def retrieveMagicValue: Int = {
    // side effect or long computation
    println("computing...")
    Thread sleep 1000

    42
  }

  println(byNameMethod {
    retrieveMagicValue
  })

  // cal by need
  def byNameBetter(n: => Int): Int = {
    lazy val t = n
    t + t + t + 1
  }

  println {
    byNameBetter {
      retrieveMagicValue
    }
  }

  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")

    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")

    i > 20
  }

  val numbers = List(
    1, 25, 40, 5, 23
  )

  val lt30 = numbers.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)

  println(gt20)

  println("lazy --- ")

  val lt30lazy = numbers.withFilter(lessThan30)

  // for-comprehensions use withFilter with guards

  for {
    a <- List(1, 2, 3) if a % 2 == 0
  } yield a + 1

  // translates to
  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1)

}
