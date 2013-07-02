package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Feedback._
import models.SimpleFeedback


object FeedbackCtl extends Controller{

  def page() = Action { implicit request =>
    Ok(views.html.feedback())
  }


  val feedbackForm = Form(
    mapping(
      TITLE -> text,
      DATE -> jodaDate,
      AUTHOR -> text,
      MSG -> text
    )(SimpleFeedback.apply)(SimpleFeedback.unapply)
  )

  def create() = Action { implicit request =>
    feedbackForm.bindFromRequest().fold(
      formWithError => BadRequest,
      simpleFeedback => {
        simpleFeedback.save()
        Redirect(routes.FeedbackCtl.page())
      }
    )
  }
}
