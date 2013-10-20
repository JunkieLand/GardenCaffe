package controllers

import play.api.mvc.{Action, Controller}
import models.GalleryItem


object GalleryCtl extends Controller {

  def page() = Action { implicit request =>

    val imgs = Seq(
      GalleryItem("DSC_2477.JPG"),
      GalleryItem("DSC_2478.JPG"),
      GalleryItem("DSC_2481.JPG"),
      GalleryItem("DSC_2483.JPG"),
      GalleryItem("DSC_2485.JPG"),
      GalleryItem("DSC_2498.JPG")
    )

    Ok(views.html.gallery(imgs))
  }
}
