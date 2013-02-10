package diet

import play.api.libs.json._

case class Activity(name: String, value: Int) {
  override def toString() = "[name=%s,value=%d]".format(name, value)
}

case class Diet(activities: List[Activity]) {

  def partitionByValue() = {

  }

  def resolve(): String = {
    val solution = findSolution()
    if (solution.isEmpty) """["no solution"]"""
    else Json.toJson(solution.map(_.name).sorted).toString
  }

  def findSolution(): List[Activity] = {
    val sortedActivities = activities.sortBy(_.value)
    val (negatives, positives) = sortedActivities.partition(_.value < 0)

    for (negative <- negatives) {
      val comb = findCombinationToValue(negative, positives)
      if (!comb.isEmpty) return comb :+ negative
    }

    List()
  }

  def findCombinationToValue(negative: Activity, positives: List[Activity]): List[Activity] = {
    val absValue: Int = negative.value.abs
    val lessOrEqualThanNegative = positives.filter(_.value <= absValue)
    for (a <- lessOrEqualThanNegative) {
      val others = lessOrEqualThanNegative.filterNot(_.name == a.name)
      val othersSize = others.size
      for (i <- 0 to othersSize) {
        val comb = others.takeRight(othersSize - i) :+ a
        val combSum = comb.map(_.value).sum
        if (combSum == absValue) {
          return comb
        }
      }
    }

    List()
  }

}


object Diet {

  def apply(json: String) = {
    val activities = jsonAsActivites(json)
    new Diet(activities)
  }

  def jsonAsActivites(json: String): List[Activity] = {
    val jsObjects: List[JsObject] = Json.parse(json).as[List[JsObject]]
    val map: List[Activity] = jsObjects.map(j => new Activity((j \ "name").as[String], (j \ "value").as[Int]))
    println(map.mkString("|"))
    map
  }

}
