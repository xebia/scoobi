package com.nicta.scoobi.unittest

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map

import com.nicta.scoobi.impl.plan.Smart
import com.nicta.scoobi.WireFormat

object ScalaListWrapper {
   implicit def fromList[T] (list : List[T]) : ListWrapper[T] = new ScalaListWrapper(list);
   implicit def fromListWrapper[T] (wrapper : ListWrapper[T]) : List[T] = wrapper.asInstanceOf[ScalaListWrapper[T]].list;
}

class ScalaListWrapper[T](val list : List[T]) extends ListWrapper[T]{
  
  import ScalaListWrapper.fromList
  
	def flatMap[B : Manifest : WireFormat](f : T => Iterable[B]) : ListWrapper[B] = {
	  list.flatMap(f)
	}
	def map[B : Manifest : WireFormat](f : T => B) : ListWrapper[B] = {
	  list.map(f)
	}
	def groupByKey[K, V](implicit ev:   Smart.DList[T] <:< Smart.DList[(K, V)],
                mK:   Manifest[K],
                wtK:  WireFormat[K],
                ordK: Ordering[K],
                mV:   Manifest[V],
                wtV:  WireFormat[V]) : ListWrapper[(K,Iterable[V])] = {
	  val map : Map[K, List[V]] = new HashMap[K, List[V]]
	  list.foreach {case (k : K,v : V) => if (map.contains(k)) {map.put(k, v :: map(k)) } else {map.put(k, v :: Nil)} }
	  map.toList
	}
	def combine[K, V](f: (V, V) => V)(implicit ev:   Smart.DList[T] <:< Smart.DList[(K,Iterable[V])],
                mK:   Manifest[K],
                wtK:  WireFormat[K],
                ordK: Ordering[K],
                mV:   Manifest[V],
                wtV:  WireFormat[V]) : ListWrapper[(K,V)] = {
	  list.collect{case (k : K, vs: Iterable[V]) => (k, vs.tail.foldLeft(vs.head)(f))}
	}

}
