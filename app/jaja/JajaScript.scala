package jaja

import collection.mutable.ListBuffer

import play.api.libs.json._

case class Path(name: String, startAt: Int, duration: Int, price: Int, var next: Option[Path] = None) {

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
		    allPaths.foreach(p => p.next = findNextPathByBestPrice(p, allPaths))

		    val result = calculateResult(Some(findPath(allPaths)))
			Json.toJson(new JajaResult(result._1, result._2)).toString
		}
	}

	def getJsonPlanning(planning: String) = {
		val jsonPlanning = Json.parse(planning)
		if (jsonPlanning.isInstanceOf[JsObject]) JsArray(List(jsonPlanning))
		else jsonPlanning
	}

	def findNextPathByBestPrice(current: Path, paths: List[Path]): Option[Path] = {

    def iter0(current: Path, paths: List[Path]): List[Path] = {
      paths match {
        case Nil => Nil
        case head :: tail => {
          if (head == current) iter0(current, tail)
          else if (current.startAt + current.duration <= head.startAt) {
            List(head) ++ iter0(current, tail)
          } else iter0(current, tail)
        }
      }
    }

    val result: List[Path] = iter0(current, paths)
    if (result.isEmpty) None
    else Some(result.maxBy(_.price))
  }

	def calculateResult(path: Option[Path]): (Int, List[String]) = {
      var prices = 0
      var names: ListBuffer[String] = ListBuffer()

      def iterNextOptionPath(path: Option[Path]): Unit = {
        path match {
          case None => {}
          case Some(p: Path) => {
            prices += p.price
            names += p.name
            iterNextOptionPath(p.next)
          }
        }
      }
      iterNextOptionPath(path)

      (prices, names.toList)
    }

	def findPath(list: List[Path]): Path = if (list.forall(p => p.next == None)) list.maxBy(_.price) else list.head

	def maxByPrice(list: List[Path]): Option[Path] = if (list.isEmpty) None else Some(list.maxBy(_.price))			

}