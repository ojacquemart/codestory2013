package controllers

import play._
import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    Logger.info("Query = "   q)

    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case "Es tu abonne a la mailing list(OUI/NON)" => Ok("o.jacquemart@gmail.com")
      case "Est ce que tu reponds toujours oui(OUI/NON)" => Ok("NON")
      case "Est ce que tu reponds toujours oui(OUI/NON)" => Ok("NON")
      case "As tu bien recu le premier enonce(OUI/NON)" => Ok("OUI")
      case _ => Ok("OUI")
    }
  }

   def enonce(index: Int) = Action { request =>
    println("Request = " + request)
    println("Headers = " + request.headers)
    println("Method = " + request.method)
    println("QueryString = " + request.queryString)
    println("ContentType = " + request.contentType)
    println("BodyAsFormUrlEncoded = " + request.body.asFormUrlEncoded)
    println("BodyAsText = " + request.body.asText)
    println("Enonce = " + request.body)
    Created
  }

}