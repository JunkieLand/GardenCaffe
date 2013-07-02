package models

import org.joda.time.DateTime
import dao.BookingDao


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
    BookingDao.create(this)
    // TODO Send confirmation mail
    // TODO Send mail to admin
  }
}

object SimpleBooking {
  def apply(name: String,
            email: String,
            phone: String,
            inDate: DateTime,
            outDate: DateTime,
            peopleNb: Int,
            accommodationType: AccommodationType.Value,
            msg: String): SimpleBooking =
    apply(name, email, phone, inDate, outDate, peopleNb, accommodationType, msg, new DateTime())

  def unapplyNoDate(b: SimpleBooking) =
    Some(b.name, b.email, b.phone, b.inDate, b.outDate, b.peopleNb, b.accommodationType, b.msg)
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
}