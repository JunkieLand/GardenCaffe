package services

import java.util.Properties
import javax.mail.{PasswordAuthentication, Authenticator, Message, Session}
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMultipart, MimeMessage}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import utils.Log


trait Mailer {
  val ADMIN_EMAIL = "jacquessevestre@yahoo.fr"
  def send(subject: String, htmlBody: String, from: String, to: String)
}

case class AsyncSendgridMailer(host: String = "smtp.sendgrid.net", smtpPort: String = "587") extends Mailer with Log {
  val props = new Properties();
  props.put("mail.transport.protocol", "smtp")
  props.put("mail.smtp.host", host)
  props.put("mail.smtp.port", smtpPort)
  props.put("mail.smtp.auth", "true")

  val auth = new SMTPAuthenticator("GARDEN_CAFFE", "yop777//garden_caffe")

  val session = Session.getInstance(props, auth)

  def syncSend(subject: String, htmlBody: String, from: String, to: String) {
    log.debug(s"AsyncSendgridMailer sending to $to : $subject")
    log.trace(htmlBody)

    val transport = session.getTransport()

    val message = new MimeMessage(session)

    val multipart = new MimeMultipart("alternative")
    val part1 = new MimeBodyPart()
    part1.setContent(htmlBody, "text/html")

    multipart.addBodyPart(part1)

    message.setSubject(subject)
    message.setContent(multipart)
    message.setFrom(new InternetAddress("Garden Caffé<" + from + ">"))
    message.setRecipients(Message.RecipientType.TO, to)

    transport.connect()
    transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO))
    transport.close()
  }

  def send(subject: String, htmlBody: String, from: String, to: String) {
    future {
      try {
        syncSend(subject, htmlBody, from, to)
      } catch {
        case e:Throwable => log.error(e.getLocalizedMessage(), e)
      }
    }
  }

  class SMTPAuthenticator(username: String, password: String) extends Authenticator {
    override def getPasswordAuthentication: PasswordAuthentication = {
      return new PasswordAuthentication(username, password);
    }
  }
}

object FakeMailer extends Mailer with Log {
  def send(subject: String, htmlBody: String, from: String, to: String) {
    log.info(s"Fakemailer sending to $to : $subject")
  }
}