package dao

import dao.utils.MongoUtils
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import models.{Contact, SimpleContact}
import models.Contact._
import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import org.joda.time.DateTime


object ContactDao extends MongoUtils {

  val contactColl = db("contacts")
  RegisterJodaTimeConversionHelpers()

  def create(contact: SimpleContact): Contact = {
    val query = §(
      NAME -> contact.name,
      EMAIL -> contact.email,
      PHONE -> contact.phone,
      MSG -> contact.msg,
      DATE -> contact.date
    )
    contactColl.insert(query)
    contactFromDBO(query)
  }


  def contactFromDBO(dbo: MongoDBObject) = Contact(
    dbo.as[ObjectId](ID).toString,
    dbo.as[String](NAME),
    dbo.as[String](EMAIL),
    dbo.as[String](PHONE),
    dbo.as[String](MSG),
    dbo.as[DateTime](DATE)
  )
}
