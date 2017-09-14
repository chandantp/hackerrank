
sealed trait btree[+A]
case object Empty extends btree[A]
case class Node[+A](elem: A, left: btree[A], right:btree[A]) extends btree[A]

def insert2tree(x : Int, t : btree[Int]) : btree[Int] = {
    (x, t) match {
        case (x, Empty) => Node(x, Empty, Empty)
        case (x, Node(v, left, right)) => {
            if(v > x) Node(v, insert2tree(x, left), right)
            else if(v < x) Node(v, left, insert2tree(x,right))
            else throw new Exception("Element exists!")
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
