import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import play.api.libs.json._

import jaja._
import jaja.JajaFormats._

class JajaSpec extends Specification {

  "The JajaSpec" should {

  	"json writes single path" in {
  		val result = new Path("MONAD42", 0, 5, 10, List())
  		Json.toJson(result).toString must equalTo("""{"VOL":"MONAD42","DEPART":0,"DUREE":5,"PRIX":10}""")
  	}

	"json reads single path" in {
  		val json = """{ "VOL": "MONAD42", "DEPART": 0, "DUREE": 5, "PRIX": 10 }"""
  		Json.parse(json).as[Path] must equalTo(new Path("MONAD42", 0, 5, 10, List()))
  	}

  	"json writes result" in {
  		val result = new JajaResult(18, List("MONAD42", "LEGACY01"))
  		Json.toJson(result).toString must equalTo("""{"gain":18,"path":["MONAD42","LEGACY01"]}""")
  	}

  	"json reads result" in {
  		val json = """{"gain":18,"path":["MONAD42","LEGACY01"]}"""
  		Json.parse(json).as[JajaResult] must equalTo(new JajaResult(18, List("MONAD42", "LEGACY01")))
  	}

    "be for default case" in {
    	val planning = ""
        JajaScript.optimize(planning) must equalTo("""{"gain" : 18, "path" : ["MONAD42","LEGACY01"] }""")
    }

  }

}