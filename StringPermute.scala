object StringPermute extends App {
   
   val input = io.Source.stdin.getLines.toList
   println("input = " + input)

   val n = input(0).toInt
   println("n = " + n)
   
   val strs = input.drop(1)   
   println("strs = " + strs)
   
   strs.foreach(swapAndPrint(_))
   
   def swapAndPrint(s: String) = {
	   val buf = new StringBuilder(s)
	   for(i <- 0 until buf.length by 2) {
		  val tmp = buf(i)
		  buf(i) = buf(i+1)
		  buf(i+1) = tmp
	   }
	   println(buf)
   }

}