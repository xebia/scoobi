package com.nicta.scoobi.unittest;

import com.nicta.scoobi.impl.plan.Smart
import com.nicta.scoobi.DList
import com.nicta.scoobi.WireFormat

object DListWrapper {
  implicit def fromDList[T](dlist: DList[T]): ListWrapper[T] = new DListWrapper(dlist);
  implicit def fromListWrapper[T](wrapper: ListWrapper[T]): DList[T] = wrapper.asInstanceOf[DListWrapper[T]].dlist;
}

class DListWrapper[T](val dlist: DList[T]) extends ListWrapper[T] {

  import DListWrapper.fromDList

  def flatMap[B: Manifest: WireFormat](f: T => Iterable[B]): ListWrapper[B] = {
    dlist.flatMap(f)
  }
  def map[B: Manifest: WireFormat](f: T => B): ListWrapper[B] = {
    dlist.map(f)
  }
  def groupByKey[K, V](implicit ev: Smart.DList[T] <:< Smart.DList[(K, V)],
    mK: Manifest[K],
    wtK: WireFormat[K],
    ordK: Ordering[K],
    mV: Manifest[V],
    wtV: WireFormat[V]): ListWrapper[(K, Iterable[V])] = {
    new DListWrapper(dlist.groupByKey)
  }
  def combine[K, V](f: (V, V) => V)(implicit ev: Smart.DList[T] <:< Smart.DList[(K, Iterable[V])],
    mK: Manifest[K],
    wtK: WireFormat[K],
    ordK: Ordering[K],
    mV: Manifest[V],
    wtV: WireFormat[V]): ListWrapper[(K, V)] = {
    new DListWrapper(dlist.combine(f))
  }
}