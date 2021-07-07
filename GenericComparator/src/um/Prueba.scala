package um

import scala.reflect.runtime.universe._
import java.time.LocalDate

class Person(private var name:String, private var id:String, private var age:Int, private var birthdate:LocalDate) {
  override def toString = s"{name: ${name}, id: $id, age: $age, birthdate: $birthdate}"
}

object Prueba {
  def main(args:Array[String]): Unit = {
    val user1 = new Person("Yusef", "13790473G", 20, LocalDate.of(2000, 5, 24))
    val user2 = new Person("Omar", "49597919F", 23, LocalDate.of(1997, 10, 16))
    val user3 = new Person("Miriam", "39346792H", 29, LocalDate.of(1992, 2, 5))
    val users = List(user1, user2, user3)	 
    implicit val criterio = new GenericComparator[Person]("age", Order.DESCENDING)
   
    users.foreach(println)
    println
    println
		users.sorted.foreach(println)
  }
}