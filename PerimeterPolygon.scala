//
// Problem: https://www.hackerrank.com/challenges/lambda-march-compute-the-perimeter-of-a-polygon
//

object PerimeterPolygon {

  def main(args: Array[String]) {
    val n = readInt
    var points = List[String]()
    for (i <- 1 to n) points :+= readLine

    var perimeter: Double = 0
    for (i <- 0 until points.size) {
      val tmp1 = points(i).split(" ")
      val x1 = tmp1(0).toInt
      val y1 = tmp1(1).toInt
      val tmp2 = points((i + 1) % n).split(" ")
      val x2 = tmp2(0).toInt
      val y2 = tmp2(1).toInt

      val res = math.sqrt(math.pow(x2 - x1, 2) + math.pow(y2 - y1, 2))
      perimeter += res
    }
    println("%.1f".format(perimeter))
  }
}
