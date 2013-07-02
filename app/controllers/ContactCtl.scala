package controllers

import play.api.mvc._


object ContactCtl extends Controller{

  def page() = Action { implicit request =>
    Ok(views.html.contact())
  }

}
