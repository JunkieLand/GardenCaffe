package dao

import dao.utils.MongoUtils
import models.{SimpleEvent, Event}
import models.Event._
import com.mongodb.casbah.commons.conversions.scala._
import com.mongodb.casbah.Imports._
import org.joda.time.DateTime


object EventsDao extends MongoUtils {

  val eventsColl = db("events")
  RegisterJodaTimeConversionHelpers()


  def create(news: SimpleEvent): Event = {
    val query = §(
      TITLE -> news.title,
      EVENT_DATE -> news.eventDate,
      POST_DATE -> news.postDate,
      MSG -> news.msg
    )
    eventsColl.insert(query)
    eventsFromDBO(query)
  }

  def findAll(skip: Int, limit: Int): Seq[Event] = {
    eventsColl.find()
      .sort(§(POST_DATE -> -1))
      .skip(skip)
      .limit(limit)
      .map(eventsFromDBO(_))
      .toList
  }

  def findFuture(): Seq[Event] = {
    val query = §(EVENT_DATE -> §("$gte" -> new DateTime()))
    eventsColl.find(query)
      .sort(§(EVENT_DATE -> 1))
      .map(eventsFromDBO(_))
      .toList
  }

  def totalEvents(): Int = eventsColl.find().count


  def eventsFromDBO(dbo: MongoDBObject) = Event(
    dbo.as[ObjectId](ID).toString,
    dbo.as[String](TITLE),
    dbo.as[DateTime](EVENT_DATE),
    dbo.as[DateTime](POST_DATE),
    dbo.as[String](MSG)
  )
}
