import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "garden-caffe"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.mongodb" %% "casbah" % "2.6.2",
    "joda-time" % "joda-time" % "2.2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
