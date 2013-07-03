package dao

import dao.utils.MongoUtils
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers
import models.{Feedback, SimpleFeedback}
import models.Feedback._
import com.mongodb.casbah.Imports._
import org.joda.time.DateTime


object FeedbackDao extends MongoUtils {

  private val feedbackColl = db("feedbacks")
  RegisterJodaTimeConversionHelpers()


  def create(feedback: SimpleFeedback): Feedback = {
    val query = §(
      TITLE -> feedback.title,
      DATE -> feedback.date,
      AUTHOR -> feedback.author,
      MSG -> feedback.msg
    )
    feedbackColl.insert(query)
    feedbackFromDBO(query)
  }

  def find(skip: Int = 0, limit: Int = 10): Seq[Feedback] = {
    feedbackColl.find()
      .sort(§(DATE -> -1))
      .skip(skip)
      .limit(limit)
      .map(feedbackFromDBO(_))
      .toList
  }

  def totalFeedbacks: Int = feedbackColl.find().count


  def feedbackFromDBO(dbo: MongoDBObject) = Feedback(
    dbo.as[ObjectId](ID).toString,
    dbo.as[String](TITLE),
    dbo.as[DateTime](DATE),
    dbo.as[String](AUTHOR),
    dbo.as[String](MSG)
  )
}
