/**
  * Copyright 2011 National ICT Australia Limited
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.nicta.scoobi

import java.io._
import org.apache.hadoop.io._
import annotation.implicitNotFound


/** Type-class for sending types across the Hadoop wire. */
@implicitNotFound(msg = "Cannot find Grouping type class for ${A}")
trait Grouping[A] extends Serializable {
  def partition(key: A, num: Int): Int
  def sortCompare(x: A, y: A): Int
  def groupCompare(x: A, y: A): Int
}

/** Implicit definitions of Grouping instances for common types. */
object Grouping {

  implicit def IntGrouping = new Grouping[Int] {
    def partition(key: Int, num: Int) = (key & Int.MaxValue) % num
    def sortCompare(x: Int, y: Int): Int = implicitly[Ordering[Int]].compare(x, y)
    def groupCompare(x: Int, y: Int): Int = sortCompare(x, y)
  }

//implicit def IntegerGrouping = new Grouping[java.lang.Integer] {
//}

  implicit def LongGrouping = new Grouping[Long] {
    def partition(key: Long, num: Int) = (key & Int.MaxValue).toInt % num
    def sortCompare(x: Long, y: Long): Int = implicitly[Ordering[Long]].compare(x, y)
    def groupCompare(x: Long, y: Long): Int = sortCompare(x, y)
  }

//implicit def DoubleGrouping = new Grouping[Double] {
//}

//implicit def CharGrouping = new Grouping[Char] {
//}

//implicit def ByteGrouping = new Grouping[Byte] {
//}

//implicit def StringGrouping = new Grouping[String] {
//}

//implicit def AnyRefGrouping[T <: AnyRef] = new Grouping[T] {
//  def partition(key: T, num: Int) = (key.hashCode & Int.MaxValue) % num
//  def sortCompare(x: T, y: T): Int = (x.hashCode - y.hashCode)
//  def groupCompare(x: T, y: T): Int = sortCompare(x, y)
//}

  implicit def OrderingGrouping[T : Ordering] = new Grouping[T] {
    def partition(key: T, num: Int) = (key.hashCode & Int.MaxValue) % num
    def sortCompare(x: T, y: T): Int = implicitly[Ordering[T]].compare(x, y)
    def groupCompare(x: T, y: T): Int = sortCompare(x, y)
  }
}
