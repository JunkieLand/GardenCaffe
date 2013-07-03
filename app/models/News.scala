package models

import org.joda.time.DateTime
import dao.NewsDao
import org.joda.time.format.DateTimeFormat


trait NewsData {
  val title: String
  val eventDate: DateTime
  val postDate: DateTime
  val msg: String
}


case class SimpleNews(title: String, eventDate: DateTime, postDate: DateTime, msg: String) extends NewsData


case class News(id: News.Id, title: String, eventDate: DateTime, postDate: DateTime, msg: String) extends NewsData {
  private val formatter = DateTimeFormat.forPattern("dd-MM-yyyy")

  def eventDateDDMMYYY: String = {
    formatter.print(eventDate)
  }

  def postDateDDMMYYY: String = {
    formatter.print(postDate)
  }
}


object News {
  type Id = String
  val PAGE_SIZE = 10

  val TITLE = "title"
  val EVENT_DATE = "eventDate"
  val POST_DATE = "postDate"
  val MSG = "msg"

  def findAll(page: Int): Seq[News] = {
    NewsDao.findAll((page - 1) * PAGE_SIZE, PAGE_SIZE)
  }

  def totalPageNb() = NewsDao.totalNews() / PAGE_SIZE + 1

}