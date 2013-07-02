package dao

import dao.utils.MongoUtils
import models.{AccommodationType, Booking, SimpleBooking}
import models.Booking._
import com.mongodb.casbah.commons.conversions.scala._
import com.mongodb.casbah.Imports._
import org.bson.types.ObjectId
import org.joda.time.DateTime


object BookingDao extends MongoUtils {

  private val bookingsColl = db("bookings")
  RegisterJodaTimeConversionHelpers()


  def create(booking: SimpleBooking) = {
    val query: MongoDBObject = §(
      NAME -> booking.name,
      EMAIL -> booking.email,
      PHONE -> booking.phone,
      IN_DATE -> booking.inDate,
      OUT_DATE -> booking.outDate,
      PEOPLE_NB -> booking.peopleNb,
      ACCOMMODATION_TYPE -> booking.accommodationType,
      MSG -> booking.msg
    )
    bookingsColl.insert(query)
    bookingFromDBO(query)
  }

  def bookingFromDBO(dbo: MongoDBObject) = Booking(
    dbo.as[ObjectId](ID).toString,
    dbo.as[String](NAME),
    dbo.as[String](EMAIL),
    dbo.as[String](PHONE),
    dbo.as[DateTime](IN_DATE),
    dbo.as[DateTime](OUT_DATE),
    dbo.as[Int](PEOPLE_NB),
    dbo.as[AccommodationType.Value](ACCOMMODATION_TYPE),
    dbo.as[String](MSG)
  )
}
