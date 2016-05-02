object DecentNumber {
	def main(args: Array[String]) {
		val n = readInt
		println(decentNumber(n))
	}
	
	def decentNumber(n: Int): String = {
		
        def recurse(n: Int, pres: String): String = {
			def rec2(n: Int, pres: String): String = {
				val x = recurse(n-3, pres + "555")
				val y = recurse(n-5, pres + "33333")
				if (x > y) x else y
			}
			
			if (n == 0) pres
			else if (n < 3) "-1"
			else {
				val mul:Int = n/15
				if (mul > 1) {
					rec2(15 + (n % 15), "5" * (15 * (mul-1)))
				}
				else {
					rec2(n, pres)
				}	
			}
		}
		
		recurse(n, "")
    }
}