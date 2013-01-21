package controllers

import play._
import play.api.mvc._

import play.api.libs.json._

import scalaskel._

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
    println("Body asJson " + request.body.asJson)
    println("Headers = " + request.headers)
    println("Method = " + request.method)
    Created
  }

  def scalaskel(money: Int) = Action { request =>
    val result = Scalaskel.changeAsJson(money)
    Logger.info("[money=%d, response=%s".format(money, result))
    Ok(result).as("application/json")
  }

}