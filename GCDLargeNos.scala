object GCDLargeNos {

    def main(args: Array[String]) {
        val n = readLine.toInt
        val alst = readLine.split(' ')		
        val m = readLine.toInt
        val blst = readLine.split(' ')
		println("n = %d, m = %d".format(n,m))
		println("alst = " + alst)
		println("blst = " + blst)
        
        var a:BigInt = 1
        var b:BigInt = 1
        
        alst.foreach(a *= _.toInt)
        blst.foreach(b *= _.toInt) 
		println("a = " + a + ", b = " + b)	
        println(gcd(a,b) % BigInt(1000000007))
           
        def gcd(a: BigInt, b: BigInt): BigInt = {
            if (a == b) a
            else if (a > b)
				if (a%b == 0) gcd(b,b) else gcd(a%b,b)
            else 
				if (b%a == 0) gcd(a,a) else gcd(a,b%a)
        }  
            
    }
}