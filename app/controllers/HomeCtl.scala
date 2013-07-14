package controllers

import play.api._
import play.api.mvc._

import models.Event

object HomeCtl extends Controller {
  
  def index = Action { implicit request =>
    val events = Event.findFuture()
    Ok(views.html.index(events))
  }
  
}