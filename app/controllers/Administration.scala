package controllers

import play.api.mvc.{Action, Controller}
import play.api.data._
import play.api.data.Forms._
import models.{SimpleNews, News}
import models.News._


object Administration extends Controller{

  def news(page: Int) = Action { implicit request =>
    val news = News.findAll(page)
    val totalPageNb = News.totalPageNb()
    Ok(views.html.admin.newsAdmin(news, totalPageNb, page))
  }


  val newsForm = Form(
    mapping(
      TITLE -> text,
      EVENT_DATE -> text,
      MSG -> text
    )(SimpleNews.apply)(SimpleNews.unapplyNoPostDate)
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
