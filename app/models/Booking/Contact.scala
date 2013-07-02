package models

import org.joda.time.DateTime
import dao.ContactDao


trait ContactData {
  val name: String
  val email: String
  val phone: String
  val msg: String
  val date: DateTime
}


case class SimpleContact(name: String, email: String, phone: String, msg: String, date: DateTime = new DateTime())
  extends ContactData {
  def save(): Contact = {
    ContactDao.create(this)
    // TODO Send email to amdin
  }
}

object SimpleContact {
  def apply(name: String, email: String, phone: String, msg: String): SimpleContact = apply(
    name, email, phone, msg, new DateTime()
  )
  def unapplyNoDate(contact: SimpleContact) = Some(contact.name, contact.email, contact.phone, contact.msg)
}


case class Contact(id: Contact.Id, name: String, email: String, phone: String, msg: String, date: DateTime)
  extends ContactData

object Contact {
  type Id = String

  val NAME = "name"
  val EMAIL = "email"
  val PHONE = "phone"
  val MSG = "msg"
  val DATE = "date"
}