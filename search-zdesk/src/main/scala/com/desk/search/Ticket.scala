package com.desk.search

//
// Only id,subject fields are mandatory
//
case class Ticket(id: String,
                  url: Option[String],
                  externalId: Option[String],
                  createdAt: Option[String],
                  ticketType: Option[String],
                  subject: String,
                  description: Option[String],
                  priority: Option[String],
                  status: Option[String],
                  submitterId: Option[Int],
                  assigneeId: Option[Int],
                  organizationId: Option[Int],
                  tags: Option[List[String]],
                  hasIncidents: Option[Boolean],
                  dueAt: Option[String],
                  via: Option[String]) {

  override def toString: String = {
    "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s".format(
      id,
      subject,
      ticketType.getOrElse(""),
      priority.getOrElse(""),
      status.getOrElse(""),
      submitterId.flatMap(Users.userName(_)).getOrElse(""), // Submitter name or id
      assigneeId.flatMap(Users.userName(_)).getOrElse(""), // Assignee name or id
      organizationId.flatMap(Organizations.orgName(_)).getOrElse(""), // Org name or id
      hasIncidents.getOrElse(""),
      via.getOrElse(""),
      tags.getOrElse(Nil).mkString(":"),
      createdAt.getOrElse(""),
      dueAt.getOrElse(""),
      externalId.getOrElse(""),
      url.getOrElse(""),
      description.getOrElse("")
    )
  }
}

object Ticket {
  val Header =
    "ID,Subject,Type,Priority,Status,Submitter,Assignee,Organization,HasIncidents,Via,Tags,Created,Due,ExternalID,URL,Description"
}
