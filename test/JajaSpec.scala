import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import play.api.libs.json._

import jaja._
import jaja.JajaFormats._

class JajaSpec extends Specification {

  "The JajaSpec" should {

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

    "handle empty input" in {
      JajaScript.optimize(None) must equalTo("""{"gain":0,"path":[]}""")
    }

    "handle enonce case" in {
    	val planning = Some("""[{ "VOL": "MONAD42", "DEPART": 0, "DUREE": 5, "PRIX": 10 },
                             { "VOL": "META18", "DEPART": 3, "DUREE": 7, "PRIX": 14 },
                             { "VOL": "LEGACY01", "DEPART": 5, "DUREE": 9, "PRIX": 8 },
                             { "VOL": "YAGNI17", "DEPART": 5, "DUREE": 9, "PRIX": 7 }]""")
      JajaScript.optimize(planning) must equalTo("""{"gain":18,"path":["MONAD42","LEGACY01"]}""")
    }

    "handle one way" in {
      val planning = Some("""{"VOL": "AF514", "DEPART":0, "DUREE":5, "PRIX": 10}""")
      JajaScript.optimize(planning) mustEqual("""{"gain":10,"path":["AF514"]}""")
    }

    "handle one way as array" in {
      val planning = Some("""[{"VOL": "AF514", "DEPART":0, "DUREE":5, "PRIX": 10}]""")
      JajaScript.optimize(planning) mustEqual("""{"gain":10,"path":["AF514"]}""")
    }

    "handle best prices with same startAt" in {
      val planning = Some("""[{"VOL": "AF1", "DEPART":0, "DUREE":1, "PRIX": 5},
                            {"VOL": "AF2", "DEPART":0, "DUREE":1, "PRIX": 6}]""")
      JajaScript.optimize(planning) mustEqual("""{"gain":6,"path":["AF2"]}""")
    }

   "handle best prices with same start" in {
      val planning = Some("""[{"VOL": "AF1", "DEPART":0, "DUREE":1, "PRIX": 2},
                              {"VOL": "AF2", "DEPART":4, "DUREE":1, "PRIX": 4},
                              {"VOL": "AF3", "DEPART":2, "DUREE":1, "PRIX": 6} ]""")
      JajaScript.optimize(planning) mustEqual("""{"gain":12,"path":["AF1","AF3","AF2"]}""")
    }

  }

}