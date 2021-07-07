package um

import scala.reflect.runtime.universe._
import scala.runtime.RichInt
import scala.reflect.ClassTag

class GenericComparator[T:ClassTag](
    val field:String, 
    val order:Order.Value) extends Ordering[T] {
  
    def this(field:String) = this(field, Order.ASCENDING)
 
    
    def compare(o1:T, o2:T) = compare_(o1,o2) * getOrder
  
    private def compare_[T:ClassTag](o1:T, o2:T) = {
      val getterO1 = getMethod(field, o1)
      val fieldO1 = getterO1()
      val getterO2 = getMethod(field, o2)
      val fieldO2 = getterO2()
      val compare = getMethod("compareTo", fieldO1) 
      compare(fieldO2).asInstanceOf[Int]
    }
    
    private def getOrder = if (order == Order.DESCENDING) -1 else 1
    
    private def getMethod[T:ClassTag](name:String, obj:T) = createMethod(name, createInstanceMirror(obj))
    
    private def createMethod(name:String, instanceMirror:InstanceMirror) = instanceMirror.reflectMethod(instanceMirror.symbol.info.member(TermName(name)).asMethod)   // Otra forma: instanceMirror.reflectMethod(instanceMirror.symbol.toType.decl(TermName(name)).asMethod)
    
    private def createInstanceMirror[T:ClassTag](obj:T) = {
      val mirror = runtimeMirror(obj.getClass.getClassLoader)
      mirror.reflect(obj)
    }
}

