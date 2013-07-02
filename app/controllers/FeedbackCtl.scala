package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Feedback._
import models.{Feedback, SimpleFeedback}


object FeedbackCtl extends Controller{

  def page(page: Int) = Action { implicit request =>
    val feedbacks = Feedback.find(page)
    val totalPageNb = Feedback.totalPageNb()
    Ok(views.html.feedback(feedbacks, page, totalPageNb))
  }


  val feedbackForm = Form(
    mapping(
      TITLE -> text,
      AUTHOR -> text,
      MSG -> text
    )(SimpleFeedback.apply)(SimpleFeedback.unapplyNodate)
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
