package com.desk.search

//
// Only id,name fields are mandatory
//
case class Organization(id: Int,
                        url: Option[String],
                        externalId: Option[String],
                        name: String,
                        domainNames: Option[List[String]],
                        createdAt: Option[String],
                        details: Option[String],
                        sharedTickets: Option[Boolean],
                        tags: Option[List[String]]) {

  override def toString: String = {
    "%d,%s,%s,%s,%s,%s,%s,%s,%s"
      .format(
        id,
        name,
        details.getOrElse(""),
        domainNames.getOrElse(Nil).mkString(":"),
        tags.getOrElse(Nil).mkString(":"),
        sharedTickets.getOrElse(""),
        createdAt.getOrElse(""),
        externalId.getOrElse(""),
        url.getOrElse("")
      )
  }
}

object Organization {
  val Header =
    "ID,Name,Details,Domains,Tags,SharedTickets,Created,ExternalID,URL"
}
