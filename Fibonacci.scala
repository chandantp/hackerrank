object Fibonacci {

	val fcache = new Array[BigInt](10000+1)	
	for(i <- 0 to 10000) fcache(i) = -1

	def main(args: Array[String]) {
		val input = io.Source.stdin.getLines.toList
		val nos = input.drop(1)
		nos.map(_.toInt).map(calc(_)).foreach(println)
	}
	
	def calc(n: Int) = {
		fibo(n) % BigInt(100000007)
	}
	
	def fibo(n: Int):BigInt = {
		if (fcache(n) != -1) {
			fcache(n)
		}
		else if (n == 0 || n == 1) {
			fcache(n) = n
			fcache(n)
		}
		else {
			fcache(n) = fibo(n-1) + fibo(n-2)
			fcache(n)
		}
	}
}