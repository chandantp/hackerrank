import collection.mutable._

object Crossword {
    def main(args: Array[String]) {
		
		val matrix = Array.ofDim[Char](10,10)
		for(i <- 0 to 9) {
			val s = readLine
			for(j <- 0 to 9) matrix(i)(j) = s(j)
		}
		//println("Crossword = " + matrix.deep.mkString("\n"))
		
		val words = readLine.split(";").toBuffer
		//println("Words = %s".format(words.mkString(",")))
		
		val hpos = ListBuffer[(Int,Int,Int)]()
		val vpos = ListBuffer[(Int,Int,Int)]()
		
		for(i <- 0 to 9) {
			for(j <- 0 to 8) {
				if ((j == 0 || matrix(i)(j-1) == '+') && matrix(i)(j) == '-' && matrix(i)(j+1) == '-') {
					var c = j
					while(c <= 9 && matrix(i)(c) == '-') c += 1
					hpos.append((i,j,c-j))					
				} 
			}
		}
		//println("HPOS = " + hpos)
		
		for(j <- 0 to 9) {
			for(i <- 0 to 8) {
				if ((i == 0 || matrix(i-1)(j) == '+') && matrix(i)(j) == '-' && matrix(i+1)(j) == '-') {
					var r = i
					while(r <= 9 && matrix(r)(j) == '-') r += 1
					vpos.append((i,j,r-i))					
				} 
			}
		}		
		//println("VPOS = " + vpos)
		
		solveCrossword(matrix, words, hpos, vpos)
    }
	
	def printCrossword(matrix: Array[Array[Char]]) {
		for(i <- 0 to 9) {
			for(j <- 0 to 9) {
				print(matrix(i)(j))
			}
			println
		}
	}
	
	def solveCrossword(matrix: Array[Array[Char]], words: Buffer[String], hpos: ListBuffer[(Int,Int,Int)], vpos: ListBuffer[(Int,Int,Int)]) {
		if (words.isEmpty && hpos.isEmpty && vpos.isEmpty) {
			printCrossword(matrix)
			System.exit(0)
		}
		
		for(wpos <- 0 until words.length) {
			val word = words(wpos)
			
			// Check if word fits horizontally			
			for(pos <- 0 until hpos.length) {
				val (r,c,len) = hpos(pos)
				
				if (word.length == len) {
					var fits = true
					for(j <- 0 until len) if (matrix(r)(c+j) != '-' && word(j) != matrix(r)(c+j)) fits = false
					if (fits) {
						// Save original word
						val ostr = new StringBuffer
						for(j <- 0 until len) ostr.append(matrix(r)(c+j))
						
						// Write word
						for(j <- 0 until len) matrix(r)(c+j) = word(j)
						hpos.remove(pos)
						words.remove(wpos)
						solveCrossword(matrix, words, hpos, vpos)
						
						// Restore
						for(j <- 0 until len) matrix(r)(c+j) = ostr.charAt(j)
						hpos.insert(pos, (r,c,len))
						words.insert(wpos, word)
						
					}					
				}				
			}
			
			// Check if word fits vertically			
			for(pos <- 0 until vpos.length) {
				val (r,c,len) = vpos(pos)
				if (word.length == len) {
					var fits=true
					for(i <- 0 until len) if (matrix(r+i)(c) != '-' && word(i) != matrix(r+i)(c)) fits = false
					if (fits) {
						// Save original word
						val ostr = new StringBuffer
						for(i <- 0 until len) ostr.append(matrix(r+i)(c))
						
						// Write word
						for(i <- 0 until len) matrix(r+i)(c) = word(i)
						vpos.remove(pos)
						words.remove(wpos)
						solveCrossword(matrix, words, hpos, vpos)
						
						// Restore
						for(i <- 0 until len) matrix(r+i)(c) = ostr.charAt(i)
						vpos.insert(pos, (r,c,len))
						words.insert(wpos, word)
					}					
				}				
			}
		}		
	}
}



