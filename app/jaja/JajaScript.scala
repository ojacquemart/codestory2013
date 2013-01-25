package jaja

import play.api.libs.json._

case class Path(name: String, startAt: Int, duration: Int, price: Int) {

	override def equals(any: Any) = any match {
		case other: Path => (name == other.name && startAt == other.startAt)
		case _ => false
	}

	override def hashCode() = name.hashCode + startAt.hashCode
}

object Orders {
  implicit def PathOrdering: Ordering[Path] = Ordering.by(_.price)
}

case class JajaResult(gain: Int, path: List[String])

// Implicit JsonFormats
object JajaFormats {

	implicit object PathFormat extends Format[Path] {

		def reads(json: JsValue): Path =
			new Path((json \ "VOL").as[String],
						(json \ "DEPART").as[Int],
							(json \ "DUREE").as[Int],
								(json \ "PRIX").as[Int])

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
	
	import jaja.JajaFormats._
	import jaja.Orders._

	val ClazzJsObject = classOf[JsObject]

	def getDefaultJaja() = Json.toJson(new JajaResult(0, List())).toString

	def optimize(input: Option[String]): String = input match {
		case None => getDefaultJaja()
		case Some(planning: String) => {
			val allPaths = getJsonPlanning(planning).as[List[JsObject]].map(_.as[Path]).sortBy(_.duration)
		    val mapPaths: List[(Path, List[Path])] = allPaths.flatMap(p => Map(p -> findPaths(p, allPaths)))

			Json.toJson(getJajaResult(mapPaths)).toString
		}
	}

	def getJsonPlanning(planning: String) = {
		val jsonPlanning = Json.parse(planning)
		if (jsonPlanning.isInstanceOf[JsObject]) JsArray(List(jsonPlanning))
		else jsonPlanning
	}

	def findPaths(current: Path, paths: List[Path]): List[Path] = {
		if (paths.isEmpty) Nil
	  	else if (paths.head == current) Nil ++ findPaths(current, paths.tail)
	  	else {
	        val head = paths.head
	        if (current.startAt + current.duration <= head.startAt) {
	          List(head) ++ findPaths(current, paths.tail)
	        } else Nil ++ findPaths(current, paths.tail)
	  	}
	}

	def getJajaResult(mapPaths: List[(Path, List[Path])]): JajaResult = {
		println(mapPaths)
		val head = mapPaths.head._1
		mapPaths.filter(!_._2.isEmpty) match {
			case Nil =>  {
				val maxByPrice: Path = mapPaths.maxBy(p => p._1.price)._1
				new JajaResult(maxByPrice.price, List(maxByPrice.name))
			}
			case list => {
			val pathMaxPrice = list.maxBy(p => p._2.maxBy(_.price))._2.head
			new JajaResult(head.price + pathMaxPrice.price, List(head.name, pathMaxPrice.name))
			}
		}
	}

}