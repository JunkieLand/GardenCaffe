package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Contact._
import models.SimpleContact


object ContactCtl extends Controller{

  def page() = Action { implicit request =>
    Ok(views.html.contact())
  }

  val contactForm = Form(
    mapping(
      NAME -> text,
      EMAIL -> email,
      PHONE -> text,
      MSG -> text
    )(SimpleContact.apply)(SimpleContact.unapplyNoDate)
  )

  def create() = Action { implicit request =>
    contactForm.bindFromRequest().fold(
      formWithErrors => BadRequest,
      simpleContact => {
        simpleContact.save()
        Ok
      }
    )
  }
}
