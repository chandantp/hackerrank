object CompressString extends App {
	
	val input = readLine
	val stack = new scala.collection.mutable.Stack[Char]()
	
	var count = 0
	for(c <- input) {
		if (stack.isEmpty) {
			stack.push(c)
			count+=1;
		}
		else {
			if (stack.head == c) count+=1
			else {
				print(stack.pop + (if (count > 1) count else ""))
				stack.push(c)
				count=1
			}
		}
	}
	if (!stack.isEmpty)
		print(stack.pop + (if (count > 1) count else ""))

}
