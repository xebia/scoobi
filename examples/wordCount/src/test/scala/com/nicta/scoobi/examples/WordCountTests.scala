package com.nicta.scoobi.examples

import com.nicta.scoobi._
import com.nicta.scoobi.unittest.ScalaListWrapper.fromList
import com.nicta.scoobi.unittest.ScalaListWrapper.fromListWrapper
import org.scalatest.FunSuite

class WordCountSuite extends FunSuite {

  test("should count single line with same word") {
    val result: List[(String, Int)] = WordCount.algorithme("word word word" :: Nil)
    assert(result === ("word", 3) :: Nil)
  }

  test("should count multiple times same line") {
    val result: List[(String, Int)] = WordCount.algorithme("a word is a word" :: "a word is a word" :: Nil)
    assert(result.toMap === Map("a" -> 4, "word" -> 4, "is" -> 2))
  }

  test("should count some lines") {
    val result: List[(String, Int)] = WordCount.algorithme("this is a line" :: "and another line" :: Nil)
    assert(result.toMap === Map("this" -> 1, "is" -> 1, "a" -> 1, "line" -> 2, "and" -> 1, "another" -> 1))
  }

}