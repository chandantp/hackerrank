//
// Problem: https://www.hackerrank.com/challenges/functions-and-fractals-sierpinski-triangles
//

object SierpinskiTriangles extends App {
  val icount = readInt
  val mrows = 32
  val mcols = 63
  val matrix = Array.ofDim[Char](mrows, mcols)
  for (r <- 0 until mrows; c <- 0 until mcols) matrix(r)(c) = '_'

  drawTriangle(0,
               31,
               32 / (math.pow(2, icount - 1)).toInt,
               63 / (math.pow(2, icount - 1)).toInt,
               true)
  printMatrix

  def printMatrix {
    for (r <- 0 until mrows) {
      for (c <- 0 until mcols) print(matrix(r)(c))
      println
    }
    println
  }

  def drawTriangle(row: Int, col: Int, height: Int, width: Int, draw: Boolean) {
    if (draw && row + height <= mrows) {
      for (r <- 0 until height) {
        for (c <- 0 until r * 2 + 1) {
          matrix(row + r)(col - r + c) = '1'
        }
        //printMatrix				
      }
      drawTriangle(row + height, col - width / 2 - 1, height, width, draw)
      drawTriangle(row + height, col + width / 2 + 1, height, width, draw)
    }
  }
}
