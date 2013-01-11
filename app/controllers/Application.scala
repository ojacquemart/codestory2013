package controllers

import play.api.mvc._

object Application extends Controller {

  def index(q: String) = Action {
    q match {
      case "Quelle est ton adresse email" => Ok("o.jacquemart@gmail.com")
      case _ => Ok("")
    }
  }

}