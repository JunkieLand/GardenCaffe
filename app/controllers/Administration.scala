package controllers

import play.api.mvc.{Action, Controller}
import play.api.data._
import play.api.data.Forms._
import models._
import models.Event._


object Administration extends Controller{

  def news(page: Int) = Action { implicit request =>
    val news = Event.findAll(page)
    val totalPageNb = Event.totalPageNb()
    Ok(views.html.admin.newsAdmin(news, totalPageNb, page))
  }


  val newsForm = Form(
    mapping(
      TITLE -> text,
      EVENT_DATE -> text,
      MSG -> text
    )(SimpleEvent.apply)(SimpleEvent.unapplyNoPostDate)
  )

  def createNews() = Action { implicit request =>
    newsForm.bindFromRequest().fold(
      formWithErrors => BadRequest,
      simpleNews => {
        simpleNews.save()
        Redirect(routes.Administration.news())
      }
    )
  }
}
