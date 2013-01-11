package controllers

import play._
import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    Logger.info("Query = " + q)

    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case _ => Ok("")
    }
  }

}