package models

import org.joda.time.DateTime
import dao.ContactDao
import services.AsyncSendgridMailer
import play.api.mvc.{AnyContent, Request}

trait ContactData {
  val name: String
  val email: String
  val phone: String
  val msg: String
  val date: DateTime
}


case class SimpleContact(name: String, email: String, phone: String, msg: String, date: DateTime = new DateTime())
  extends ContactData {
  def save()(implicit request: Request[AnyContent]): Contact = {
    val contact = ContactDao.create(this)
    val mailer = AsyncSendgridMailer()
    mailer.send(
      subject = "Une personne a contacté le Garden Caffé",
      from = "contact@garden-caffe.com",
      to = mailer.ADMIN_EMAIL,
      htmlBody = views.html.email.contact(contact).toString
    )
    contact
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