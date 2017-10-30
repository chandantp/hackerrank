package com.desk.search

import java.io.FileNotFoundException

import SearchManager._

object MainApp {

  def main(args: Array[String]): Unit = {
    if (args.size == 0) showUsage()
    else {
      try {
        val (ignoreCase, collection, field, key) = parse(args)
        println(header(collection))
        loadData
        search(ignoreCase, collection, field, key).foreach(println)
      } catch {
        case e: IllegalArgumentException => showUsage(Option(e.getMessage))
        case e: FileNotFoundException => println(e.getMessage)
        case e: Exception => println(e.getMessage)
      }
    }
  }
}
