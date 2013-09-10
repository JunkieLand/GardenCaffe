package controllers.utils

import play.api.mvc._
import play.api.mvc.Results._

import controllers.routes


trait Auth {

  val SESSION_KEY = "i"
  val SESSION_VALUE = "admin"

  def createSession(): Session = Session(Map(SESSION_KEY -> SESSION_VALUE))

  def checkAuthentication(request: RequestHeader): Option[Boolean] = {
    request.session.get(SESSION_KEY).map(_ == SESSION_VALUE)
  }

  def onUnauthorized(request: RequestHeader) = Redirect(routes.Administration.getLogin())

  def Authenticated[A](bodyParser: BodyParser[A] = BodyParsers.parse.anyContent)(f: => Request[A] => Result) = {
    Security.Authenticated(checkAuthentication, onUnauthorized) { _ =>
      Action[A](bodyParser)( request => {
//        Logger("user-connection").info(s"${user.email} - ${request.uri}")
        f(request)
      })
    }
  }

}
