package models

import org.joda.time.DateTime
import dao.EventsDao
import org.joda.time.format.DateTimeFormat


trait EventData {
  val title: String
  val eventDate: DateTime
  val postDate: DateTime
  val msg: String
}


case class SimpleEvent(title: String, eventDate: DateTime, postDate: DateTime, msg: String) extends EventData {
  def save(): Event = EventsDao.create(this)
}


object SimpleEvent {
  val formatter = DateTimeFormat.forPattern("dd-MM-yyyy")

  def apply(title: String, eventDate: String, msg: String): SimpleEvent = {
    def parseDate(date: String) = formatter.parseDateTime(date)
    apply(title, parseDate(eventDate), new DateTime(), msg)
  }
  def unapplyNoPostDate(news: SimpleEvent) = Some(news.title, formatter.print(news.eventDate), news.msg)
}


case class Event(id: Event.Id, title: String, eventDate: DateTime, postDate: DateTime, msg: String) extends EventData {
  private val formatter = DateTimeFormat.forPattern("dd-MM-yyyy")

  def eventDateDDMMYYY: String = {
    formatter.print(eventDate)
  }

  def postDateDDMMYYY: String = {
    formatter.print(postDate)
  }
}


object Event {
  type Id = String
  val PAGE_SIZE = 10

  val TITLE = "title"
  val EVENT_DATE = "eventDate"
  val POST_DATE = "postDate"
  val MSG = "msg"

  def findAll(page: Int): Seq[Event] = {
    EventsDao.findAll((page - 1) * PAGE_SIZE, PAGE_SIZE)
  }

  def findFuture(): Seq[Event] = EventsDao.findFuture()

  def totalPageNb() = EventsDao.totalEvents() / PAGE_SIZE + 1

}