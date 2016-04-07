object RecursiveYTrees extends App {
    val icount = readInt
	val matrix = Array.ofDim[Char](63,100)
	for(r <- 0 until 63; c <- 0 until 100) matrix(r)(c) = '_'
   
	drawY(62, 49, 16, icount)
	printMatrix
	
	def printMatrix {
		for(r <- 0 until 63) {
			for(c <- 0 until 100) print(matrix(r)(c))
			println
		}
	}		
   
	def drawY(row:Int, col:Int, size:Int, icount:Int) {
		if (icount > 0 && size > 0) {
			for(i <- 0 until size) matrix(row-i)(col) = '1'
			for(i <- 1 to size) {
				matrix(row-size-i+1)(col-i) = '1'
				matrix(row-size-i+1)(col+i) = '1'
			}
			drawY(row-2*size, col-size, size/2, icount-1)
			drawY(row-2*size, col+size, size/2, icount-1)
		}   
	}     
}