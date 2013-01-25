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

	def optimize(input: Option[String]): String = input match {
		case None 	=> Json.toJson(new JajaResult(0, List())).toString
		case Some(planning: String) => {
			val jsonPlanning: JsValue = Json.parse(planning)
			val allPaths = jsonPlanning.as[List[JsObject]].map(_.as[Path]).sortBy(_.duration)

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

		    val result: List[(Path, List[Path])] = allPaths.flatMap(p => Map(p -> findPaths(p, allPaths)))
		    val max = result.filter(!_._2.isEmpty).maxBy(p => p._2.maxBy(_.price))
		    val pathStart = max._1
		    val pathMax = max._2.head

			Json.toJson(new JajaResult(pathStart.price + pathMax.price, List(pathStart.name, pathMax.name))).toString
		}
	}

}