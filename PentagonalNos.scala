
object PentagonalNos {

	val pcache = new Array[Long](100001)

	def main(args: Array[String]) {
		val input = io.Source.stdin.getLines.toList
		val nos = input.drop(1)
		nos.map(_.toInt).map(penta(_)).foreach(println)
	}
	
	def penta(n: Int):Long = {
		if (pcache(n) != 0) pcache(n)
		else if (n >= 1) {
			val pn = (3*n - 2) + penta(n-1)
			pcache(n) = pn
			pcache(n)
		}
		else pcache(0)
	}
}