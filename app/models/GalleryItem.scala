package models

import play.api.mvc.Call
import controllers.routes


case class GalleryItem(url: Call) {

}


object GalleryItem {
  def apply(name: String): GalleryItem = apply(routes.Assets.at("images/garden_caffe/gallery/resized-" + name))
}
