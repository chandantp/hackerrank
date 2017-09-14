/*
  The code here demonstrates how one can build there own control
  structures in Scala.

  The key features which makes this possible are:
    1. Everything is an expression
    2. Pass by name


 */

import scala.annotation.tailrec

object Main {

  def main(args: Array[String]): Unit = {
    loopN(10) {
      println("Hello world!")
    }

    var i = 3
    whilst(i > 0) {
      println("This is my while loop, i = %d".format(i))
      i = i - 1
    }
  }

  @tailrec
  def loopN(n: Int)(codeBlock: => Unit): Unit = {
    if (n > 0) {
      codeBlock
      loopN(n - 1)(codeBlock)
    }
  }

  /* Borrowed from
     https://alvinalexander.com/scala/how-to-create-control-structures-dsls-in-scala-cookbook
   */
  @tailrec
  def whilst(testCondition: => Boolean)(codeBlock: => Unit) {
    if (testCondition) {
      codeBlock
      whilst(testCondition)(codeBlock)
    }
  }
}
