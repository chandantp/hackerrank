package com.desk.search

//
// Only id,name fields are mandatory
//
case class User(id: Int,
                url: Option[String],
                externalId: Option[String],
                name: String,
                alias: Option[String],
                createdAt: Option[String],
                active: Option[Boolean],
                verified: Option[Boolean],
                shared: Option[Boolean],
                locale: Option[String],
                timezone: Option[String],
                lastLoginAt: Option[String],
                email: Option[String],
                phone: Option[String],
                signature: Option[String],
                organizationId: Option[Int],
                tags: Option[List[String]],
                suspended: Option[Boolean],
                role: Option[String]) {

  override def toString: String = {
    "%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s".format(
      id,
      name,
      alias.getOrElse(""),
      email.getOrElse(""),
      phone.getOrElse(""),
      organizationId.flatMap(Organizations.orgName(_)).getOrElse(""), // Org name or id
      locale.getOrElse(""),
      timezone.getOrElse(""),
      signature.getOrElse(""),
      role.getOrElse(""),
      tags.getOrElse(Nil).mkString(":"),
      active.getOrElse(""),
      verified.getOrElse(""),
      shared.getOrElse(""),
      suspended.getOrElse(""),
      createdAt.getOrElse(""),
      lastLoginAt.getOrElse(""),
      externalId.getOrElse(""),
      url.getOrElse("")
    )
  }
}

object User {
  val Header =
    "ID,Name,Alias,Email,Phone,Organization,Locale,Timezone,Signature,Role,Tags,Active,Verified,Shared,Suspended,Created,LastLogin,ExternalID,URL"
}
