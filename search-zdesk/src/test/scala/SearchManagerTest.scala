package com.desk.search

import org.scalatest.{BeforeAndAfter, FunSuite}
import SearchManager._

class SearchManagerTest extends FunSuite with BeforeAndAfter {

  before {
    Users.init("src/test/resources/users.json")
    Tickets.init("src/test/resources/tickets.json")
    Organizations.init("src/test/resources/organizations.json")
  }

  test("parsing arguments of invalid length = 1,2 & 5 fails") {
    val input =
      List(Array("1"), Array("1", "2"), Array("1", "2", "3", "4", "5"))

    input.foreach {args =>
      val thrown = intercept[IllegalArgumentException] { parse(args) }
      assert(thrown.getMessage === "Invalid argument size '%d' detected !!".format(args.size))
    }
  }

  test("parsing arguments of valid length '3' succeeds") {
    val result = parse(Array("users", "name", "Alice"))
    assert(result === (false, "users", "name", "Alice"))
  }

  test("parsing arguments of valid length '4' but invalid first argument fails") {
    val thrown = intercept[IllegalArgumentException] {
      parse(Array("1", "users", "name", "Alice"))
    }
    assert(thrown.getMessage === "Invalid argument '1' found !!")
  }

  test("parsing arguments of valid length '4' with valid first argument '-i' succeeds") {
    val result = parse(Array("-i", "users", "name", "Alice"))
    assert(result === (true, "users", "name", "Alice"))
  }

  test("header() throws exception when an invalid collection name is passed") {
    val thrown = intercept[IllegalArgumentException] {
      header("abc")
    }
    assert(thrown.getMessage === "Invalid collection 'abc' detected !!")
  }

  test("header() succeeds when valid collection name (organizations,users,tickets) is passed") {
    assert(!header("organizations").isEmpty)
    assert(!header("users").isEmpty)
    assert(!header("tickets").isEmpty)
  }

  test("search() throws exception when an invalid collection name is passed") {
    val thrown = intercept[IllegalArgumentException] {
      search(false, "blah", "name", "Alice")
    }
    assert(thrown.getMessage === "Invalid collection 'blah' detected !!")
  }

  test("isMatching('#null#',1,true) returns false") {
    assert(isMatching(EmptySearchKey, 1, true) == false)
  }


}
