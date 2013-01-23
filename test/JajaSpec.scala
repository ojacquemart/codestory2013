import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import play.api.libs.json._

import jaja._
import jaja.JajaFormats._

class JajaSpec extends Specification {

  "The JajaSpec" should {

    "receive POST data" in {
      running(FakeApplication()) {
        val json = """{"name": "new name", "description": "new description"}"""
        val Some(result) = routeAndCall(
          FakeRequest(
            POST, 
            "/jajascript/optimize",
            FakeHeaders(Map("Content-Type" -> Seq("application/x-www-form-urlencoded"))), 
            json
          ) )

        status(result) must equalTo(OK)
        contentType(result) must beSome("application/json")
      }
    }     

  	"json writes single path" in {
  		val result = new Path("MONAD42", 0, 5, 10)
  		Json.toJson(result).toString must equalTo("""{"VOL":"MONAD42","DEPART":0,"DUREE":5,"PRIX":10}""")
  	}

	 "json reads single path" in {
  		val json = """{ "VOL": "MONAD42", "DEPART": 0, "DUREE": 5, "PRIX": 10 }"""
  		Json.parse(json).as[Path] must equalTo(new Path("MONAD42", 0, 5, 10))
  	}

    "json reads enonce with listte" in {
      val json = """[{ "VOL": "MONAD42", "DEPART": 0, "DUREE": 5, "PRIX": 10 }, { "VOL": "META18", "DEPART": 3, "DUREE": 7, "PRIX": 14 }]"""
      Json.parse(json).as[List[JsObject]].map(_.as[Path]) must equalTo(List(new Path("MONAD42", 0, 5, 10), new Path("META18", 3, 5, 14)))
    }    

  	"json writes result" in {
  		val result = new JajaResult(18, List("MONAD42", "LEGACY01"))
  		Json.toJson(result).toString must equalTo("""{"gain":18,"path":["MONAD42","LEGACY01"]}""")
  	}

  	"json reads result" in {
  		val json = """{"gain":18,"path":["MONAD42","LEGACY01"]}"""
  		Json.parse(json).as[JajaResult] must equalTo(new JajaResult(18, List("MONAD42", "LEGACY01")))
  	}

    "be ok for default case" in {
    	val jsonPlanning = Json.parse("""[{ "VOL": "MONAD42", "DEPART": 0, "DUREE": 5, "PRIX": 10 },
                         { "VOL": "META18", "DEPART": 3, "DUREE": 7, "PRIX": 14 },
                         { "VOL": "LEGACY01", "DEPART": 5, "DUREE": 9, "PRIX": 8 },
                        { "VOL": "YAGNI17", "DEPART": 5, "DUREE": 9, "PRIX": 7 }]""")
      JajaScript.optimize(jsonPlanning) must equalTo("""{"gain":18,"path":["MONAD42","LEGACY01"]}""")
    }

  }

}