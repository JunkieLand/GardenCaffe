package controllers

import play.api.mvc.{Action, Controller}
import models.News


object Administration extends Controller{

  def news(page: Int) = Action { implicit request =>
    val news = News.findAll(page)
    val totalPageNb = News.totalPageNb()
    Ok(views.html.admin.newsAdmin(news, totalPageNb, page))
  }
}
