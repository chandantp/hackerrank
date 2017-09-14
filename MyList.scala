
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
