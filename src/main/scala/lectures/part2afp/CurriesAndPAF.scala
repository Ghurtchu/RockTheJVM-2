package lectures.part2afp

object CurriesAndPAF extends scala.App {

  // curried functions

  val superAdder: Int => Int => Int = a => b => a + b

  println(superAdder(1)(2))

  val addNumTo5 = superAdder(5)

  assert(addNumTo5(2) == 7)

  // method
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  // method converted to function values, other name = eta expansion
  val add4: Int => Int => Int = curriedAdder // must write type annotation otherwise won't compile

  // functions and methods are not the same
  // functions are values of Function[-A, +B] type whereas methods are instance definitions to perform an action

  def inc(x: Int) = x + 1
  List(1, 2, 3).map(inc) // ETA-expansions
  // rewritten as below
  List(1, 2, 3).map { each =>
    inc(each)
  }

  val addF = (x: Int, y: Int) => x + y

  def addM(x: Int, y: Int) = x + y

  def curried(x: Int)(y: Int) = x + y

  val add7v1: Int => Int = a => addF(7, a)
  val add7v2: Int => Int = a => addM(7, a)
  val add7v3: Int => Int = a => curried(7)(a)
  val add7v4: Int => Int = addF.curried(7)

  def concatenate(a: String, b: String, c: String): String = a concat b concat c

  val insertName: String => String = concatenate("Hello, I am", _ , " how are you?")

  println(insertName {
    "Nika"
  })

  val insert2and3: (String, String) => String = concatenate("Hey", _, _)

  println(insert2and3("Oi", "Ui"))

  def formatter(arg: String)(format: String) = ???

  def byName(n: => Int) = n + 1

  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

}
