package models

import play.api.mvc.Call
import controllers.routes


case class GalleryItem(url: Call, alt: String) {

}


object GalleryItem {
  def apply(name: String, alt: String): GalleryItem = apply(
    routes.Assets.at("images/garden_caffe/gallery/resized-" + name),
    alt
  )
}
