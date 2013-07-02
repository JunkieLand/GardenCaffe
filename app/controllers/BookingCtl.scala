package controllers

import play.api.mvc._
import dao.BookingDao
import models.{AccommodationType, SimpleBooking}
import models.Booking._
import org.joda.time.DateTime
import play.api.data._
import play.api.data.Forms._


object BookingCtl extends Controller {

  def page() = Action { implicit request =>
    val booking = new SimpleBooking(
      "Toto",
      "toto@toto.com",
      "222222",
      new DateTime().plusDays(10),
      new DateTime().plusDays(20),
      3,
      AccommodationType.Bungalow,
      "Avec une bière bien fraiche !",
      new DateTime()
    )
    BookingDao.create(booking)
    Ok(views.html.booking())
  }


  val bookingForm = Form(
    mapping(
      NAME -> text,
      EMAIL -> email,
      PHONE -> text,
      IN_DATE -> text,
      OUT_DATE -> text,
      PEOPLE_NB -> number,
      ACCOMMODATION_TYPE -> text,
      MSG -> text
    )(SimpleBooking.apply)(SimpleBooking.unapplyNoDate)
  )

  def create() = Action { implicit request =>
    bookingForm.bindFromRequest().fold(
      formWithError => BadRequest,
      simpleBooking => {
        simpleBooking.save()
        Ok
      }
    )
  }

}
