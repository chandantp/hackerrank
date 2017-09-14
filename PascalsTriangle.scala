//
// Problem: https://www.hackerrank.com/challenges/pascals-triangle
//

object PascalsTriangle {

  def main(args: Array[String]) {
    if (args.length != 1) {
      println("USAGE: scala PascalsTriangle <n>")
    } else {
      pascals(args(0).toInt)
    }
  }

  def pascals(k: Int) {
    val a = Array.ofDim[Int](k, k)

    for (r <- 0 until k) {
      for (c <- 0 to r) {
        if (c == 0) a(r)(c) = 1
        else if (r == c) a(r)(c) = 1
        else a(r)(c) = a(r - 1)(c) + a(r - 1)(c - 1)
        print(a(r)(c) + " ")
      }
      println
    }
  }
}
