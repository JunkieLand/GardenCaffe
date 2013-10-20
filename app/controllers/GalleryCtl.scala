package controllers

import play.api.mvc.{Action, Controller}
import models.GalleryItem


object GalleryCtl extends Controller {

  def page() = Action { implicit request =>

    val imgs = Seq(
      GalleryItem("DSC_2477.JPG"),
      GalleryItem("DSC_2485.JPG"),
      GalleryItem("DSC_2491.JPG"),
      GalleryItem("DSC_2495.JPG"),
      GalleryItem("DSC_2503.JPG"),
      GalleryItem("DSC_2504.JPG"),
      GalleryItem("DSC_2511.JPG"),
      GalleryItem("DSC_2513.JPG"),
      GalleryItem("DSC_2516.JPG"),
      GalleryItem("DSC_2517.JPG"),
      GalleryItem("DSC_2522.JPG"),
      GalleryItem("DSC_2528.JPG"),
      GalleryItem("DSC_2530.JPG"),
      GalleryItem("DSC_2533.JPG"),
      GalleryItem("DSC_2541.JPG")
    )

    Ok(views.html.gallery(imgs))
  }
}
