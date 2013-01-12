package controllers

import play._
import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    Logger.info("Query = " + q)

    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case "Es tu abonne a la mailing list(OUI/NON)" => Ok("OUI")
      case "Es tu heureux de participer(OUI/NON)" => Ok("OUI")
      case "Es tu pret a recevoir une enonce au format markdown par http post(OUI/NON)" => Ok("OUI")
      case "Est ce que tu reponds toujours oui(OUI/NON)" => Ok("NON")
      case _ => Ok("")
    }
  }

}