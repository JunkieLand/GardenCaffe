package models

import org.joda.time.DateTime
import dao.FeedbackDao


trait FeedbackData {
  val title: String
  val date: DateTime
  val author: String
  val msg: String
}


case class SimpleFeedback(title: String, date: DateTime, author: String, msg: String) extends FeedbackData {
  def save(): Feedback = {
    FeedbackDao.create(this)
    // TODO Send mail to admin
  }
}


case class Feedback(id: Feedback.Id, title: String, date: DateTime, author: String, msg: String) extends FeedbackData


object Feedback {
  type Id = String

  val TITLE = "title"
  val DATE = "date"
  val AUTHOR = "author"
  val MSG = "msg"
}