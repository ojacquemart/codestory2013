package controllers

import play._
import play.api.mvc._

import play.api.libs.json._

import com.codahale.jerkson.Json._

import query._
import scalaskel._
import jaja._
import jaja.JajaFormats._


object Application extends Controller {

  def index(q: String) = Action { request =>
    Ok(QueryHandler.handle(request.rawQueryString.replace("q=", "")))
  }

   def enonce(id: Int) = Action { request =>
    println("Request " + request)
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

  def jajascript() = Action { request =>
    println("Body " + request.body)
    println("Body as Text " + request.body.asText)
    println("Request " + request)
    println("Request queryString " + request.queryString)
    println("Body asJson " + request.body.asJson)
    println("Headers = " + request.headers)
    Created(JajaScript.optimize(request.body.asText)).as("application/json")
  }

}