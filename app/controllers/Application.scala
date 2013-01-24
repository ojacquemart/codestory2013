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

  def jajascript(optmize: String) = Action { request =>
    println("Body " + request.body)
    println("Request " + request)
    println("Request queryString " + request.queryString)
    println("Body asJson " + request.body.asJson)
    println("Headers = " + request.headers)
    println("Method = " + request.method)
    //println("As objects = " + request.body.as[List[JsObject]].map(_.as[Path]))
    val jsonPlanning = Json.parse("""[{ "VOL": "MONAD42", "DEPART": 0, "DUREE": 5, "PRIX": 10 },
                         { "VOL": "META18", "DEPART": 3, "DUREE": 7, "PRIX": 14 },
                         { "VOL": "LEGACY01", "DEPART": 5, "DUREE": 9, "PRIX": 8 },
                        { "VOL": "YAGNI17", "DEPART": 5, "DUREE": 9, "PRIX": 7 }]""")
    val result = JajaScript.optimize(jsonPlanning)
    Created(result).as("application/json")
  }

}