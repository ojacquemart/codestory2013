package jaja

import collection.mutable.ListBuffer

import play.api.libs.json._

case class JajaResult(gain: Int, path: List[String])

case class Path(name: String, startAt: Int, duration: Int, price: Int, var nexts: List[Path] = List(), var next: Option[Path] = None) {

	def nextPrice(): Int = next match {
		case None => 0
		case (Some(p: Path)) => p.price
	}

	def before(other: Path) = startAt + duration <= other.startAt
	
	def totalGain(): Int = price + nexts.map(_.price).sum
  	

	def asJajaResult() = {
	    var prices = 0
	    var names: ListBuffer[String] = ListBuffer()

	    def iterNextOptionPath(path: Option[Path]): Unit = {
	      path match {
	        case None => {}
	        case Some(p: Path) => {
	          prices += p.price
	          names += p.name
	          if (!p.nexts.isEmpty) {
	            iterNextOptionPath(Some(p.nexts.maxBy(_.totalGain)))
	          }
	        }
	      }
	    }
	    iterNextOptionPath(Some(this))

	    new JajaResult(prices, names.toList)
  	}

	override def equals(any: Any) = any match {
		case other: Path => (name == other.name && startAt == other.startAt)
		case _ => false
	}

	override def hashCode() = name.hashCode + startAt.hashCode
}

object Orders {
  implicit def PathOrdering: Ordering[Path] = Ordering.by(_.price)
}


// Implicit JsonFormats
object JajaFormats {

	implicit object PathFormat extends Format[Path] {

		def reads(json: JsValue): Path =
			new Path((json \ "VOL").as[String], (json \ "DEPART").as[Int],
						(json \ "DUREE").as[Int], (json \ "PRIX").as[Int])

		def writes(path: Path) : JsValue = {
			JsObject(
				List("VOL" -> JsString(path.name), "DEPART" -> JsNumber(path.startAt),
						"DUREE" -> JsNumber(path.duration), "PRIX" -> JsNumber(path.price))
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
	
	import jaja.JajaFormats._
	import jaja.Orders._

	val ClazzJsObject = classOf[JsObject]

	def getDefaultJaja() = Json.toJson(new JajaResult(0, List())).toString

	def optimize(input: Option[String]): String = input match {
		case None => getDefaultJaja()
		case Some(planning: String) => {
			val allPaths = getJsonPlanning(planning).as[List[JsObject]].map(_.as[Path]).sortBy(_.startAt)
		    allPaths.foreach(p => p.nexts = findPaths(p, allPaths))

			Json.toJson(allPaths.map(_.asJajaResult).maxBy(_.gain)).toString
		}
	}

	def getJsonPlanning(planning: String) = {
		val jsonPlanning = Json.parse(planning)
		if (jsonPlanning.isInstanceOf[JsObject]) JsArray(List(jsonPlanning))
		else jsonPlanning
	}

  	def findPaths(current: Path, paths: List[Path]): List[Path] = paths.filterNot(_ == current).filter(current.before(_))

}