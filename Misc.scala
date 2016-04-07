
sealed trait btree[+A]
case object Empty extends btree[A]
case class Node[+A](elem: A, left: btree[A], right:btree[A]) extends btree[A]

def insert2tree(x : Int, t : btree[Int]) : btree[Int] = {
    (x, t) match {
        case (x, Empty) => Node(x, Empty, Empty)
        case (x, Node(v, left, right)) => {
            if(v > x) Node(v, insert2tree(x, left), right)
            else if(v < x) Node(v, left, insert2tree(x,right))
            else throw new Exception("Element exsist!")
        }
    }
}

def traverse(x: btree[Int]) : Unit = {
  x match {
      case Node(v, left, right) => {
        traverse(left); print(" -> " + v); traverse(right)
      }
      case _ => print("")
  }
}

sealed trait MyList[+A]
case object End extends MyList[Nothing]
case class Node[+A](head: A, tail: MyList[A]) extends MyList[A]

def insert2list(x: Int, start: MyList[Int]) : MyList[Int] = {
   start match {
      case End => Node(x, End)
      case _ => {
        val n = Node(x, start)
        n
	  }
	}
}

object StrMing {    

    def main(args: Array[String]) {	
		val n = readLine.toInt
		val y = readLine		
		for(i <- 0 until x.length) print(x(i)+""+y(i))	
	}
	
	def pascals() = { 		
		val a = Array.ofDim[Int](k,k)
		
		for(r <- 0 until k) {
			for(c <- 0 to r) {
				if (c == 0) a(r)(c) = 1				
					else if(r == c)	a(r)(c) = 1				
						else a(r)(c) = a(r-1)(c) + a(r-1)(c-1)									
				print(a(r)(c) + " ")
			}
			println
		}		
	}	
}