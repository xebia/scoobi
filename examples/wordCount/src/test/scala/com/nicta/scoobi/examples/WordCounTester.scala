package com.nicta.scoobi.examples

import com.nicta.scoobi._

object WordCounTester {

  def main(args: Array[String]): Unit = {
    val result = WordCount.algorithme(new ScalaListWrapper("Kris is gek" :: "Maarten is ook gek" :: "Age is ook niet gek" :: Nil)).asInstanceOf[ScalaListWrapper[(String, Int)]].list
    println(result);
  }

}