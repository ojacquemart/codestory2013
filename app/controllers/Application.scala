package controllers

import play.api.mvc._

import query._
import scalaskel._
import jaja._
import minesweeper._
import diet._
import play.api.libs.json.JsValue

object Application extends Controller {

  def index(q: String) = Action {
    request =>
      Ok(QueryHandler.handle(request.rawQueryString.replace("q=", "")))
  }

  def enonce(id: Int) = Action {
    request =>
      println("Body " + request.body)
      Created
  }

  def scalaskel(money: Int) = Action {
    request =>
      val result = Scalaskel.changeAsJson(money)
      println("[money=%d, response=%s".format(money, result))
      Ok(result).as("application/json")
  }

  def jajascript() = Action(parse.tolerantText) {
    request =>
      println("Body " + request.body)
      println("Headers = " + request.headers)

      val result = JajaScript.optimize(Some(request.body))
      println("Jaja = %s".format(result))
      Created(result).as("application/json")
  }

  def minesweeper() = Action(parse.text) {
    request =>
      println("Flat playload: begin: " + request.body.split("\n").mkString("|") + "end")

      val result = Minesweeper(request.body).solve
      println("Minesweeper = %s".format(result.mkString("|")))
      Ok(result.mkString("\n")).as("application/text")
  }

  def diet() = Action(parse.tolerantText) {
    request =>
      val result = Diet(request.body).resolve
      println("Diet = %s".format(result))
      Created(result).as("application/text")
  }

}