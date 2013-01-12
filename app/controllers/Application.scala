package controllers

import play._
import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    Logger.info("Query = " + q)

    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case "Est ce que tu reponds toujours oui(OUI/NON)" => Ok("NON")
      case "As tu bien recu le premier enonce(OUI/NON)" => Ok("NON")
      case _ => Ok("OUI")
    }
  }

   def enonce(index: Int) = Action { request =>
    println("Enonce = " + request.body)
    Ok
  }

}