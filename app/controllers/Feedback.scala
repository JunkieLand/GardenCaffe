package controllers

import play.api.mvc._


object Feedback extends Controller{

  def page() = Action { implicit request =>
    Ok(views.html.feedback())
  }

}
