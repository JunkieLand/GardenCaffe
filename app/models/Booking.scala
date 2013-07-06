package models

import org.joda.time.DateTime
import dao.BookingDao
import org.joda.time.format.DateTimeFormat
import services.AsyncSendgridMailer


trait BookingData {
  val name: String
  val email: String
  val phone: String
  val inDate: DateTime
  val outDate: DateTime
  val peopleNb: Int
  val accommodationType: AccommodationType.Value
  val msg: String
  val creationDate: DateTime

  private val formatter = DateTimeFormat.forPattern("dd-MM-yyyy")

  def inDateDDMMYYYY = formatter.print(inDate)
  def outDateDDMMYYYY = formatter.print(outDate)
  val accommodationTypeAsString = AccommodationType.toString(accommodationType)
}

case class SimpleBooking(name: String,
                         email: String,
                         phone: String,
                         inDate: DateTime,
                         outDate: DateTime,
                         peopleNb: Int,
                         accommodationType: AccommodationType.Value,
                         msg: String,
                         creationDate: DateTime) extends BookingData {

  def save(): Booking = {
    val booking = BookingDao.create(this)
    val mailer = AsyncSendgridMailer()
    // Send confirmation mail
    mailer.send(
      subject = "Garden Caffé - Confirmation de réservation",
      from = "reservation@garden-caffe.com",
      to = email,
      htmlBody = views.html.email.bookingToCustomer(booking).toString
    )
    // Send mail to admin
    mailer.send(
      subject = "Garden Caffé - Réservation en ligne",
      from = "reservation@garden-caffe.com",
      to = mailer.ADMIN_EMAIL,
      htmlBody = views.html.email.bookingToAdmin(booking).toString
    )

    booking
  }
}

object SimpleBooking {
  val formatter = DateTimeFormat.forPattern("dd-MM-yyyy")

  def apply(name: String,
            email: String,
            phone: String,
            inDate: String,
            outDate: String,
            peopleNb: Int,
            accommodationType: AccommodationType.Value,
            msg: String): SimpleBooking = {
    def parseDate(date: String) = formatter.parseDateTime(date)
    apply(name, email, phone, parseDate(inDate), parseDate(outDate), peopleNb, accommodationType, msg, new DateTime())
  }

  def unapplyNoDate(b: SimpleBooking) = {
    Some(
      b.name, b.email, b.phone, formatter.print(b.inDate),
      formatter.print(b.outDate), b.peopleNb, b.accommodationType, b.msg
    )
  }
}


case class Booking(id: Booking.Id,
                   name: String,
                   email: String,
                   phone: String,
                   inDate: DateTime,
                   outDate: DateTime,
                   peopleNb: Int,
                   accommodationType: AccommodationType.Value,
                   msg: String,
                   creationDate: DateTime) extends BookingData


object Booking {
  type Id = String

  val NAME = "name"
  val EMAIL = "email"
  val PHONE = "phone"
  val IN_DATE = "inDate"
  val OUT_DATE = "outDate"
  val PEOPLE_NB = "peopleNb"
  val ACCOMMODATION_TYPE = "accommodationType"
  val MSG = "msg"
  val CREATION_DATE = "creationDate"

}


object AccommodationType {
  type Value = String
  val Bungalow: Value = "Bungalow"
  val ClassicRoom: Value = "ClassicRoom"

  def toString(value: Value) = if(value == "Bungalow") {
    "Bungalow"
  } else {
    "Chambre classique"
  }
}