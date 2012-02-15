package com.nicta.scoobi

import com.nicta.scoobi.impl.plan.Smart

class DListWrapper[T] (val dlist : DList[T]) extends ListWrapper[T] {
	def flatMap[B : Manifest : WireFormat](f : T => Iterable[B]) : ListWrapper[B] = {
	  new DListWrapper(dlist.flatMap(f))
	}
	def map[B : Manifest : WireFormat](f : T => B) : ListWrapper[B] = {
	  new DListWrapper(dlist.map(f))
	}
	def groupByKey[K,V]
			(implicit ev:   Smart.DList[T] <:< Smart.DList[(K, V)],
                mK:   Manifest[K],
                wtK:  WireFormat[K],
                ordK: Ordering[K],
                mV:   Manifest[V],
                wtV:  WireFormat[V]) : ListWrapper[(K,Iterable[V])] = {
	  new DListWrapper(dlist.groupByKey)
	}
	def combine[K, V](f: (V, V) => V)
			(implicit ev:   Smart.DList[T] <:< Smart.DList[(K,Iterable[V])],
                mK:   Manifest[K],
                wtK:  WireFormat[K],
                ordK: Ordering[K],
                mV:   Manifest[V],
                wtV:  WireFormat[V]): ListWrapper[(K,V)] = {
	  new DListWrapper(dlist.combine(f))
	}
}