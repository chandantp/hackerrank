
class Animal {
   override def toString = "Animal"
}
class Mammal extends Animal {
   override def toString = super.toString + ":Mammal"
}
class Tiger extends Mammal {
   override def toString = super.toString + ":Tiger"
}
class Zebra extends Mammal {
   override def toString = super.toString + ":Zebra"
}

class Vet[-A] {
  def treat(a: A) = this + " treats " + a
}  

class Box[+A]

object MyApp extends App {
  def treatsMammals(vet: Vet[Mammal]) {
     println(vet + " treats mammals")
  }
  
  def isLargeEnough(box: Box[Mammal]) {
     println(box + " is large enough")
  }
  
  treatsMammals(new Vet[Mammal]) // Invariant
  treatsMammals(new Vet[Animal]) // Contra-variant

  isLargeEnough(new Box[Mammal]) // Invariant
  isLargeEnough(new Box[Zebra]) // Co-variant
}

