package dao

import dao.utils.MongoUtils
import models.{SimpleNews, News}
import models.News._
import com.mongodb.casbah.commons.conversions.scala._
import com.mongodb.casbah.Imports._
import org.joda.time.DateTime


object NewsDao extends MongoUtils {

  val newsColl = db("news")
  RegisterJodaTimeConversionHelpers()


  def create(news: SimpleNews): News = {
    val query = §(
      TITLE -> news.title,
      EVENT_DATE -> news.eventDate,
      POST_DATE -> news.postDate,
      MSG -> news.msg
    )
    newsColl.insert(query)
    newsFromDBO(query)
  }

  def findAll(skip: Int, limit: Int): Seq[News] = {
    newsColl.find()
      .sort(§(POST_DATE -> -1))
      .skip(skip)
      .limit(limit)
      .map(newsFromDBO(_))
      .toList
  }

  def totalNews(): Int = newsColl.find().count


  def newsFromDBO(dbo: MongoDBObject) = News(
    dbo.as[ObjectId](ID).toString,
    dbo.as[String](TITLE),
    dbo.as[DateTime](EVENT_DATE),
    dbo.as[DateTime](POST_DATE),
    dbo.as[String](MSG)
  )
}
