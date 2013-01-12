package controllers

import play._
import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    Logger.info("Query = " + q)

    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case "Es tu abonne a la mailing list(OUI/NON)" => Ok("OUI")
      case _ => Ok("")
    }
  }

}