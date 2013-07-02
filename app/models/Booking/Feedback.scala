package models

import org.joda.time.DateTime
import dao.FeedbackDao
import org.joda.time.format.DateTimeFormat


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
    FeedbackDao.find(page * PAGE_SIZE, PAGE_SIZE)
  }

  def totalPageNb(): Int = FeedbackDao.totalFeedbacks / PAGE_SIZE + 1

}