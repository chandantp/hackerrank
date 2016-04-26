object TreeOfLife {

	def main(args: Array[String]) {
	
		val rule = collection.immutable.BitSet.fromBitMask(Array(readInt))
		val tree = readLine.filter(_ != ' ').toUpperCase
		val numQueries = readInt		
		var queries = List[String]()		
		for(i <- 1 to numQueries) queries :+= readLine		
	
		var current = 0
		var currTree = deserialize(tree)
		val trees = collection.mutable.ListBuffer[String]()
		trees += serialize(currTree)
		
		queries.foreach(query => {
			val tmp = query.split(" ")
			val iterations = tmp(0).toInt
			val path = tmp(1)
			//println("Query: iterations = %d, path = %s".format(iterations, path))		
			
			if (iterations == 0) {
				println(getCellData(currTree, path))
			}
			else if (iterations < 0) {
				current += iterations
				currTree = deserialize(trees(current))
				println(getCellData(currTree, path))				
			}
			if (iterations > 0) {
				if (current+iterations < trees.size) {
					current += iterations
					currTree = deserialize(trees(current))
					println(getCellData(currTree, path))
				}
				else {
					currTree = deserialize(trees(trees.size-1))
					for(i <- 1 to iterations) {
						current += 1
						if (current >= trees.size) {							
							evolve(currTree, rule)
							trees += serialize(currTree)
						}
					}				
					println(getCellData(currTree, path))
				}
			}			
		})
	}
	
	sealed trait BTree
	case object Empty extends BTree
	case class Node(var data: Boolean, var left: BTree, var right:BTree, var parent:BTree) extends BTree {
		var ndata = false
		
		override def toString:String = {
			val buf = new StringBuffer("Node(")
			buf.append(data).append(",")
			buf.append(left match {
				case Empty => Empty
				case x:Node => x
			}).append(",").append(right match {
				case Empty => Empty
				case x:Node => x
			})
			buf.append(")")
			buf.toString
		}
	}

	def deserialize(tree: String) : BTree = {
		val stack = collection.mutable.Stack[Any]()
		for(token <- tree) {		
			token match {
				case '(' => stack.push('(')
				case '.' => stack.push(Node(false, Empty, Empty, Empty))
				case 'X' => stack.push(Node(true, Empty, Empty, Empty))
				case ')' => {
							val right = stack.pop.asInstanceOf[Node]
							val parent = stack.pop.asInstanceOf[Node]
							val left = stack.pop.asInstanceOf[Node]
							val tmp = stack.pop
							if (tmp.asInstanceOf[Char] != '(') throw new Exception("Invalid Tree!")
							parent.left = left
							parent.right = right
							left.parent = parent
							right.parent = parent
							stack.push(parent)
						}					
			}			
		}
		if (stack.size != 1) throw new Exception("Invalid Tree!!")
		stack.pop.asInstanceOf[Node]				
	}
	
	def serialize(btree: BTree): String = {
		btree match {
			case x: Node => {
				val ldata = serialize(x.left)				
				val rdata = serialize(x.right)
				val buf = new StringBuffer("")
				buf.append(if (ldata.isEmpty) ldata else "(" + ldata)				   
				   .append(if (x.data) "X" else ".")				   
				   .append(if (rdata.isEmpty) rdata else rdata + ")")
				buf.toString
			}
			case Empty => ""
		}
	}
	
	def cellNeighbourhoodValue(node: BTree): Int = {
		def cellValue(cell: BTree): Int = {
			cell match {
				case Empty => 0
				case x:Node => if (x.data) 1 else 0
			}
		}
		
		node match {
			case cell:Node => {
					val cellCurr = cellValue(cell)
					val cellParent = cellValue(cell.parent)
					val cellLeft = cellValue(cell.left)
					val cellRight = cellValue(cell.right)
					Integer.parseInt("%d%d%d%d".format(cellParent, cellLeft, cellCurr, cellRight), 2)
				}
			case Empty => 0
		}
	}

	def getNode(node: BTree, path: String) = {
		var curr = node.asInstanceOf[Node]
		path.foreach(x => x match {
			case '<' => curr = curr.left.asInstanceOf[Node]
			case '>' => curr = curr.right.asInstanceOf[Node]
			case _ => curr
		})
		curr		 
	}
	
	def getCellData(node: BTree, path: String) = {
		val tmp = getNode(node, path)
		if (tmp.data) "X" else "."		 
	}
	
	def nextCellValue(node: BTree, rule: collection.immutable.BitSet): Boolean = {
		val currValue = cellNeighbourhoodValue(node)
		rule(currValue)
	}
	
	def evolve(tree: BTree, rule: collection.immutable.BitSet) {
		def evolveNode(tree: BTree) {
			tree match {
				case x: Node => {
					x.ndata = nextCellValue(x, rule)
					evolveNode(x.left)
					evolveNode(x.right)
				}
				case Empty => ()
			}
		}
		
		def commit(tree: BTree) {
			tree match {
				case x: Node => {
					x.data = x.ndata
					commit(x.left)
					commit(x.right)
				}
				case Empty => ()
			}
		}
		
		evolveNode(tree)
		commit(tree)
	}	
}