package com.desk.search

object SearchManager {

  val EmptySearchKey = "#null#"

  def showUsage(message: Option[String] = None) {
    if (message.isDefined) {
      println("\n" + message.get + "\n\nRefer to the usage below for more information...\n")
    }

    println("""scala ./search.jar [-i] <collection> <field> <key>
      |            -i : Ignore case. By default, search is case-sensitive (Optional)
      |                    Applicable only in case of string fields
      |  <collection> : collection to be searched (Mandatory)
      |                    collection = organizations | users | tickets
      |       <field> : field to be searched which should be one of organization, user or ticket fields (Mandatory)
      |                    Org fields = id,name,details,domains,tags,sharedTickets,createdAt,externalId,url
      |                   User fields = id,name,alias,email,phone,organizationId,locale,timezone,signature,role
      |                                 tags,active,verified,shared,suspended,createdAt,lastLoginAt,externalId,url
      |                 Ticket fields = id,subject,type,priority,status,submitterId,assigneeId,organizationId
      |                                 hasIncidents,via,tags,createdAt,dueAt,externalId,url,description
      |         <key> : search key (Mandatory)
      |                    key = #null# is used to match empty fields"""
      .stripMargin + "\n")
  }

  def parse(args: Array[String]) = args.size match {
    case 3 => (false, args(0), args(1), args(2))
    case 4 => args(0) match {
      case "-i" => (true, args(1), args(2), args(3))
      case _ => throw new IllegalArgumentException("Invalid argument '%s' found !!".format(args(0)))
    }
    case _ => throw new IllegalArgumentException("Invalid argument size '%d' detected !!".format(args.size))
  }

  def loadData = {
    Organizations.init()
    Users.init()
    Tickets.init()
  }

  def search(ignoreCase: Boolean, collection: String, field: String, key: String): List[AnyRef] = collection match {
    case "organizations" => Organizations.search(field, key, ignoreCase)
    case "users" => Users.search(field, key, ignoreCase)
    case "tickets" => Tickets.search(field, key, ignoreCase)
    case _ => throw new IllegalArgumentException("Invalid collection '%s' detected !!".format(collection))
  }

  def header(collection: String): String = collection match {
    case "organizations" => Organization.Header
    case "users" => User.Header
    case "tickets" => Ticket.Header
    case _ => throw new IllegalArgumentException("Invalid collection '%s' detected !!".format(collection))
  }

  // Match key with optional field value (field type is one of Int,Boolean,String,List[String])
  def isMatching[T](key: String, fieldValueOpt: Option[T], ignoreCase: Boolean): Boolean = fieldValueOpt match {
    case None => key.equalsIgnoreCase(EmptySearchKey)
    case Some(fieldValue) => fieldValue match {
      case i: Int        => isMatching(key, i, ignoreCase)
      case b: Boolean    => isMatching(key, b, ignoreCase)
      case str: String   => isMatching(key, str, ignoreCase)
      case list: List[_] => isMatching(key, list, ignoreCase)
    }
  }

  // Match key with mandatory field value (field type is one of Int,Boolean,String,List[String])
  def isMatching[T](key: String, fieldValue: T, ignoreCase: Boolean): Boolean = key match {
    case EmptySearchKey => fieldValue match {
      case list: List[_] => list.isEmpty // Match depends of whether list is empty or not as search is for empty field
      case _ => false                    // No match as field exists but search is for empty field
    }
    case _ => fieldValue match {
      case i: Int => key.equals(i.toString)
      case b: Boolean => key.equalsIgnoreCase(b.toString)
      case str: String => if (ignoreCase) key.equalsIgnoreCase(str) else key.equals(str)
      case list: List[_] => {
        if (list.isEmpty) false          // No match as list is empty and search key is not empty
        else list.exists(x => isMatching(key, x, ignoreCase))
      }
    }
  }

}
