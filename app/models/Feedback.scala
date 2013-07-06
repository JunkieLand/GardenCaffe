package models

import org.joda.time.DateTime
import dao.FeedbackDao
import org.joda.time.format.DateTimeFormat
import services.AsyncSendgridMailer


trait FeedbackData {
  val title: String
  val date: DateTime
  val author: String
  val msg: String
}


case class SimpleFeedback(title: String, date: DateTime, author: String, msg: String) extends FeedbackData {
  def save(): Feedback = {
    val feedback = FeedbackDao.create(this)
    // Send mail to admin
    val mailer = AsyncSendgridMailer()
    mailer.send(
      subject = "Garden Caffé - Avis d'un client",
      from = "avis@garden-caffe.com",
      to = mailer.ADMIN_EMAIL,
      htmlBody = views.html.email.feedbackToAdmin(feedback).toString
    )
    feedback
  }
}

object SimpleFeedback {
  def apply(title: String, author: String, msg: String): SimpleFeedback = apply(
    title, new DateTime(), author, msg
  )
  def unapplyNodate(s: SimpleFeedback) = Some(s.title, s.author, s.msg)
}


case class Feedback(id: Feedback.Id, title: String, date: DateTime, author: String, msg: String) extends FeedbackData {
  def formatedDate: String = {
    val formatter = DateTimeFormat.forPattern("EEE d MMMM yyyy")
    formatter.print(date)
  }
}


object Feedback {
  val PAGE_SIZE = 10
  type Id = String

  val TITLE = "title"
  val DATE = "date"
  val AUTHOR = "author"
  val MSG = "msg"


  def find(page: Int): Seq[Feedback] = {
    FeedbackDao.find((page - 1) * PAGE_SIZE, PAGE_SIZE)
  }

  def totalPageNb(): Int = FeedbackDao.totalFeedbacks / PAGE_SIZE + 1

}