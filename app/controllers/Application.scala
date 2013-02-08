package controllers

import play._
import play.api.mvc._

import play.api.libs.json._

import query._
import scalaskel._
import jaja._
import minesweeper.Minisweeper

object Application extends Controller {

  def index(q: String) = Action {
    request =>
      Ok(QueryHandler.handle(request.rawQueryString.replace("q=", "")))
  }

  def enonce(id: Int) = Action {
    request =>
      println("Request " + request)
      println("Body " + request.body)
      println("Body asJson " + request.body.asJson)
      println("Headers = " + request.headers)
      println("Method = " + request.method)
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
      println("Body " + request.body)
      println("??" + request.body.split("\n").mkString("|"))

      val result = Minisweeper(request.body).solve
      println("Minisweeper = %s".format(result))
      Ok(result).as("application/text")
  }

}