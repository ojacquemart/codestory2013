package controllers

import play._
import play.api.mvc._

import play.api.libs.json._

import com.codahale.jerkson.Json._

import javax.servlet.http.HttpServletRequest

import query._
import scalaskel._
import jaja._
import jaja.JajaFormats._

import org.apache.commons.io._


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

  def jajascript() = Action(parse.tolerantText) { request =>
    println(request.body)
    println(request)
    println("Body " + request.body)
    /*println("Request uri " + request.uri )
    println("Request accept " + request.accept )
    println("Request queryString " + request.queryString )
    println("Body as Text " + request.body.asText)
    println("Request " + request)
    println("Request asFormUrlEncoded " + request.body.asFormUrlEncoded)
    println("Request queryString " + request.queryString)
    println("Body asJson " + request.body.asJson)
    println("Headers = " + request.headers)*/
    Created("Oh shit").as("application/json")
    //Created(JajaScript.optimize(request.body.asText)).as("application/json")
  }

}