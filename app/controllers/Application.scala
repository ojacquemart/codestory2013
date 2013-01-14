package controllers

import play._
import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case "Est ce que tu reponds toujours oui(OUI/NON)" => Ok("NON")
      case _ => Ok("OUI")
    }
  }

   def enonce(id: Int) = Action { request =>
    println("Body " + request.body)
    println("Headers = " + request.headers)
    println("Method = " + request.method)
    Created
  }

}