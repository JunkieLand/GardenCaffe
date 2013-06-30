package controllers

import play.api.mvc._


object Contact extends Controller{

  def page() = Action { implicit request =>
    Ok(views.html.contact())
  }

}
