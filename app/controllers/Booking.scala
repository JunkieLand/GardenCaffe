package controllers

import play.api.mvc._


object Booking extends Controller{

  def page() = Action { implicit request =>
    Ok(views.html.booking())
  }

}
