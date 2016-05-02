object SwapArray {

	def main(args: Array[String]) {
		val x = List(31,14,15,7,2).toArray
		swap_array(x)
	}

	def swap_array(arr: Array[Int]) = {
	
		def countOnes(x: Int) = {
			var count = 0
			var num = x
			while(num > 0) {
				count += num & 1
				num >>= 1
			}
			count
		}
		
		val num2ones = collection.mutable.Map[Int, Int]()
		val bitCounts2Ints = collection.mutable.Map[Int, collection.mutable.ArrayBuffer[Int]]()
		
		for(i <- 0 until arr.size) {
			val num = arr(i)			
			val ones = countOnes(num)			
			num2ones(num) = ones
			
			if (!bitCounts2Ints.contains(ones)) {
				bitCounts2Ints(ones) = new collection.mutable.ArrayBuffer[Int]()
			}
			
			bitCounts2Ints(ones) += num		
		}
		
		def sortFunc(n1: Int, n2: Int) = {
			if (num2ones(n1) == num2ones(n2)) n1 > n2 else num2ones(n1) > num2ones(n2)
		}
		
		for(i <- (0 to 31).reverse) {
			if (bitCounts2Ints.contains(i)) {
				val x = bitCounts2Ints(i).toArray
				val sortedx = x.sortWith(sortFunc)
				sortedx.foreach(println)				
			}
		}
    }
}