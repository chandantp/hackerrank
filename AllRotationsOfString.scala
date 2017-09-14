object AllRotationsOfString extends App {

  val input = io.Source.stdin.getLines.toList
  val istrs = input.drop(1)
  istrs.foreach(printRotations(_))

  def printRotations(istr: String) {
    for (spos <- 1 to istr.length) {
      for (delta <- 0 until istr.length) {
        val pos = (spos + delta) % istr.length
        print(istr(pos))
      }
      print(' ')
    }
    println
  }
}
