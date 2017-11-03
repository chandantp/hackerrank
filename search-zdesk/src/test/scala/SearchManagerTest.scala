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

  test("empty search key == empty field or empty List") {
    assert(isMatching(EmptySearchKey, None, false))
    assert(isMatching(EmptySearchKey, Nil, false))
  }

  test("empty search key != non-empty Int/Option(Int) field") {
    assert(!isMatching(EmptySearchKey, 1, false))
    assert(!isMatching(EmptySearchKey, Some(1), false))
  }

  test("empty search key != non-empty Boolean/Option(Boolean) field") {
    assert(!isMatching(EmptySearchKey, true, false))
    assert(!isMatching(EmptySearchKey, Some(true), false))
  }

  test("empty search key != non-empty String/Option(String) field") {
    assert(!isMatching(EmptySearchKey, "Alice", false))
    assert(!isMatching(EmptySearchKey, Some("Alice"), false))
  }

  test("empty search key != non-empty List[String]/Option(List[String]) field") {
    assert(!isMatching(EmptySearchKey, List("Alice"), false))
    assert(!isMatching(EmptySearchKey, Some(List("Alice")), false))
  }

  test("search key matches Int/Option(Int) field value") {
    assert(isMatching("1", 1, false))
    assert(isMatching("1", Some(1), false))
  }

  test("search key matches Boolean/Option(Boolean) field value") {
    assert(isMatching("true", true, false))
    assert(isMatching("true", Some(true), false))
  }

  test("case-sensitive match with String/Option(String) field fails") {
    assert(!isMatching("alice", "Alice", false))
    assert(!isMatching("alice", Some("Alice"), false))
  }

  test("case-insensitive match with String/Option(String) field succeeds") {
    assert(isMatching("alice", "Alice", true))
    assert(isMatching("alice", Some("Alice"), true))
  }

  test("case-sensitive match with List[String]/Option(List[String]) field fails") {
    assert(!isMatching("alice", List("Alice"), false))
    assert(!isMatching("alice", Some(List("Alice")), false))
  }

  test("case-insensitive match with List[String]/Option(List[String]) field succeeds") {
    assert(isMatching("alice", List("Alice"), true))
    assert(isMatching("alice", Some(List("Alice")), true))
  }

}
