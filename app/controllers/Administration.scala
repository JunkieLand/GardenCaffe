package controllers

import play.api.mvc.{Action, Controller}
import play.api.data._
import play.api.data.Forms._
import models._
import models.Event._
import utils.Auth


object Administration extends Controller with Auth {

  def news(page: Int) = Authenticated() { implicit request =>
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

  def createNews() = Authenticated() { implicit request =>
    newsForm.bindFromRequest().fold(
      formWithErrors => BadRequest,
      simpleNews => {
        simpleNews.save()
        Redirect(routes.Administration.news())
      }
    )
  }

  val loginForm = Form(
    tuple(
      "login" -> text,
      "pwd" -> text
    ) verifying("Identifiants invalides", { _ match {
      case (e: String, p: String) => { e == "admin" && p == "adminozor" }
    }})
  )

  def getLogin() = Action { implicit request =>
    Ok(views.html.admin.index())
  }

  def postLogin() = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.admin.index(true)),
      values => Redirect(routes.Administration.news()).withSession(createSession)
    )
  }

  def logout() = Authenticated() { implicit request =>
    Redirect(routes.Administration.getLogin()).withNewSession
  }
}
