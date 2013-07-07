package dao.utils

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoClient


trait MongoUtils {

  val db = MongoClient("94.23.51.112", 27017)("gardencaffe")


  val § = MongoDBObject
  val ID = "_id"
}
