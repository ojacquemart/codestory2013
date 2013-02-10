package diet

import collection.mutable.ListBuffer

import play.api.libs.json._

case class Activity(name: String, value: Int)

case class Diet(activities: List[Activity]) {

  def resolve(): Set[List[String]] = {
    var solutions: ListBuffer[List[String]] = ListBuffer()

    activities.foreach({
      a => {
        val nexts = getNexts(a)
        val sizeNexts = nexts.size
        for (i <- 0 to sizeNexts) {
          val comb = nexts.takeRight(sizeNexts - i)
          if (comb.map(_.value).sum + a.value == 0) {
            val combAndCurrent = comb ++ List(a)
            solutions += combAndCurrent.map(_.name).sorted
          }
        }
      }
    })

    solutions.toSet
  }

  def getNexts(activity: Activity) = activities.filterNot(_.name == activity.name)

  def resolveAsJson() = {
    val result = resolve

    if (result.isEmpty) Json.toJson(List("no solution"))
    else Json.toJson(result.head)
  }

}


object Diet {

  def apply(json: String) = {
    val activities = jsonAsActivites(json)
    new Diet(activities)
  }

  def jsonAsActivites(json: String): List[Activity] = {
    val jsObjects: List[JsObject] = Json.parse(json).as[List[JsObject]]
    jsObjects.map(j => new Activity((j \ "name").as[String], (j \ "value").as[Int]))
  }

}
