package com.desk.search

import net.liftweb.json.{DefaultFormats, JField, parse}

import scala.collection.mutable

import SearchManager.isMatching

object Organizations {

  private val Id = "id"
  private val Name = "name"
  private val Details = "details"
  private val Domains = "domains"
  private val Tags = "tags"
  private val SharedTickets = "sharedTickets"
  private val CreatedAt = "createdAt"
  private val ExternalId = "externalId"
  private val Url = "url"

  private val DefaultOrganizationsFile = "src/main/resources/organizations.json"

  private implicit val formats = DefaultFormats

  private var organizations: List[Organization] = _

  val id2org = new mutable.HashMap[Int, Organization]()

  def init(file: String = DefaultOrganizationsFile) = {
    // load data
    organizations = parse(io.Source.fromFile(file).mkString)
      .transformField {
        case JField("_id", x)            => JField("id", x)
        case JField("external_id", x)    => JField("externalId", x)
        case JField("created_at", x)     => JField("createdAt", x)
        case JField("domain_names", x)   => JField("domainNames", x)
        case JField("shared_tickets", x) => JField("sharedTickets", x)
      }
      .extract[List[Organization]]

    // build indexes
    for (org <- organizations) {
      id2org.put(org.id, org)
    }
  }

  def orgName(orgId: Int): Option[String] = id2org.get(orgId).map(_.name)

  def search(field: String, key: String, ignoreCase: Boolean): List[Organization] = field match {
    case Id => organizations.filter(org => isMatching(key, org.id, ignoreCase))
    case Name => organizations.filter(org => isMatching(key, org.name, ignoreCase))
    case Details => organizations.filter(org => isMatching(key, org.details, ignoreCase))
    case Domains => organizations.filter(org => isMatching(key, org.domainNames, ignoreCase))
    case Tags => organizations.filter(org => isMatching(key, org.tags, ignoreCase))
    case SharedTickets => organizations.filter(org => isMatching(key, org.sharedTickets, ignoreCase))
    case CreatedAt => organizations.filter(org => isMatching(key, org.createdAt, ignoreCase))
    case ExternalId => organizations.filter(org => isMatching(key, org.externalId, ignoreCase))
    case Url => organizations.filter(org => isMatching(key, org.url, ignoreCase))
    case _ => throw new IllegalArgumentException("Invalid field '%s' detected !!".format(field))
  }

}
