import scala.actors.Actor._

object ActorExample extends App {
  val actor1 = actor {
  while(true) {
    receive {
	  case "Stop" => println("Exiting..."); exit()
      case s: String => println("Received msg: " + s)
      case _ => println("Unknown msg")
    }
   }
  }
  
  
  actor1 ! "Hi there!"
  actor1 ! "How are you"
  actor1 ! 100
  actor1 ! "Stop"
}