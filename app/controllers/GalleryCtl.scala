package controllers

import play.api.mvc.{Action, Controller}
import models.GalleryItem


object GalleryCtl extends Controller {

  def page() = Action { implicit request =>

    val imgs = Seq(
      GalleryItem("DSC_2477.JPG", "Garden Caffé - Intérieur du bar et du restaurant avec vue sur la cage du perroquet."),
      GalleryItem("DSC_2485.JPG", "Garden Caffé - Vue intérieure sur les tables du restaurant avec le bar en fond."),
      GalleryItem("DSC_2491.JPG", "Garden Caffé - Vue intéreiure sur le bar annexe avec les machines à sous en fond."),
      GalleryItem("DSC_2495.JPG", "Garden Caffé - Vue sur les jeux d'arcades et baby-foot."),
      GalleryItem("DSC_2498.JPG", "Garden Caffé - Vue sur la salle de jeux, avec les billards."),
      GalleryItem("DSC_2503.JPG", "Garden Caffé - Vue extérieure sur l'entrée du Garden Caffé depuis la rue."),
      GalleryItem("DSC_2504.JPG", "Garden Caffé - Vue extérieure depuis la rue sur le jardin et le mini-golf."),
      GalleryItem("DSC_2511.JPG", "Garden Caffé - Vue extérieure sur un bungalow extérieur du restaurant."),
      GalleryItem("DSC_2513.JPG", "Garden Caffé - Vue extérieure sur le jardin et le mini-golf 1."),
      GalleryItem("DSC_2516.JPG", "Garden Caffé - Vue extérieure sur les bungalows de l'hôtel et le mini-golf."),
      GalleryItem("DSC_2517.JPG", "Garden Caffé - Vue extérieure sur les bungalows de l'hôtel et le jardin."),
      GalleryItem("DSC_2522.JPG", "Garden Caffé - Vue extérieure sur les bungalows de l'hôtel, le jardin et un petit pont.."),
      GalleryItem("DSC_2528.JPG", "Garden Caffé - Vue extérieure sur les bungalows de l'hôtel, le jardin et la balançoire pour enfants."),
      GalleryItem("DSC_2530.JPG", "Garden Caffé - Vue extérieure sur l'entrée du Garden Caffé depuis le jardin, et sur le mini-golf. "),
      GalleryItem("DSC_2533.JPG", "Garden Caffé - Vue extérieure sur les terrains de pétanque."),
      GalleryItem("DSC_2541.JPG", "Garden Caffé - Vue sur l'intérieur d'une chambre de l'hôtel.")
    )

    Ok(views.html.gallery(imgs))
  }
}
