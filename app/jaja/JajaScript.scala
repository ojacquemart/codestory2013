package jaja

import play.api.libs.json._

case class Path(name: String, startAt: Int, duration: Int, price: Int, nexts: List[Path]) 
case class JajaResult(gain: Int, path: List[String])

object JajaFormats {


	implicit object PathFormat extends Format[Path] {

		def reads(json: JsValue): Path = new Path(
				(json \ "VOL").as[String], (json \ "DEPART").as[Int], (json \ "DUREE").as[Int], (json \ "PRIX").as[Int], List())

		def writes(path: Path) : JsValue = {
			JsObject(
				List("VOL" -> JsString(path.name),
					"DEPART" -> JsNumber(path.startAt),
					"DUREE" -> JsNumber(path.duration),
					"PRIX" -> JsNumber(path.price))
			)
		}
	}

	implicit object JajaResultFormat extends Format[JajaResult] {

		def reads(json: JsValue): JajaResult = new JajaResult((json \ "gain").as[Int], (json \ "path").as[List[String]])

		def writes(jaja: JajaResult) : JsValue = {
			JsObject(
				List("gain" -> JsNumber(jaja.gain), "path" -> JsArray(jaja.path.map(JsString(_))))
			)
		}
	}
}


object JajaScript {
	
	def optimize(planning: String): String = {
		""
	}
}