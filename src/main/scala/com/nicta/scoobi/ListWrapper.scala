package com.nicta.scoobi

import com.nicta.scoobi.impl.plan.Smart

trait ListWrapper[T] {
	def flatMap[B : Manifest : WireFormat](f : T => Iterable[B]) : ListWrapper[B]
	def map[B : Manifest : WireFormat](f : T => B) : ListWrapper[B]
	def groupByKey[K, V](implicit ev:   Smart.DList[T] <:< Smart.DList[(K, V)],
                mK:   Manifest[K],
                wtK:  WireFormat[K],
                ordK: Ordering[K],
                mV:   Manifest[V],
                wtV:  WireFormat[V]) : ListWrapper[(K,Iterable[V])]
	def combine[K, V](f: (V, V) => V)(implicit ev:   Smart.DList[T] <:< Smart.DList[(K,Iterable[V])],
                mK:   Manifest[K],
                wtK:  WireFormat[K],
                ordK: Ordering[K],
                mV:   Manifest[V],
                wtV:  WireFormat[V]) : ListWrapper[(K,V)]
}