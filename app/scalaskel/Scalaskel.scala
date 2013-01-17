package scalaskel

import play.api.libs.json._

object Scalaskel {

  case class Cent(value: Int, currency: String)

  val foo = Cent(1, "foo")
  val bar = Cent(7, "bar")
  val qix = Cent(11, "qix")
  val baz = Cent(21, "baz")
  val barQixBaz = List(bar, qix, baz)

  case class Change(cent: Cent, nb: Int) {
    def value() = cent.value * nb

    // Maybe see: http://www.playframework.org/documentation/api/2.0/scala/index.html#play.api.libs.json.package
    // But the current class doesn't really match the json waited by the codestory bot.
    override def toString = "'%s': %d".format(cent.currency, nb)
  }

  def getFoos(nb: Int) = Change(foo, nb)

  def changeAsJson(money: Int) = change(money).map(l => l.mkString("{",",", "}")).mkString("[", ",", "]")

  def change(money: Int) = {
    def foosUntilMoney() = List(List(getFoos(money)))
    def barQixBazEqualsToMoney() = barQixBaz.filter(_.value == money).map(p => List(Change(p, 1)))
    def barQixBazWithFoosEqualsToMoney() = barQixBaz.filter(money - _.value > 0).map(p => List(Change(p, 1), getFoos(money - p.value)))

    (foosUntilMoney() ++ barQixBazEqualsToMoney() ++ 
    	barQixBazWithFoosEqualsToMoney() ++ decomposeFooBarQixBaz(money)).toSet
  }

  def decomposeFooBarQixBaz(money: Int): List[List[Change]] = {
    def possibleChangesForCentByMoney(cent: Cent, money: Int): List[Change] = {
      for (i <- (1 to money / cent.value).toList) yield Change(cent, i)
    }
    val possiblesChangeForBar = possibleChangesForCentByMoney(bar, money)
    val possiblesChangeForQixBaz = List(possibleChangesForCentByMoney(qix, money), possibleChangesForCentByMoney(baz, money))
    val possiblesChangeForFooQixBaz = List(possiblesChangeForBar) ++ possiblesChangeForQixBaz
    def possiblesChanges(change: Change) = {
      possiblesChangeForFooQixBaz.map(l => l.filter(c => c.cent.value != change.cent.value))
    }

    def generate(money: Int, change: Change): List[List[Change]] = {
      if (change.value() > money) List()
      else if (change.value == money) List(List(change))
      else barQixBazAndRestOfFoo(money, change, possiblesChanges(change))
    }

    def barQixBazAndRestOfFoo(money: Int, change: Change, otherChanges: List[List[Change]]): List[List[Change]] = {

      def combinationsBarQixBaz(rest: Int, otherChanges: List[List[Change]]): List[Change] = {

        def maxFromChanges(changes: List[Change]) = if (changes.isEmpty) List() else List(changes.maxBy(_.nb))

        if (otherChanges.isEmpty) List()
        else maxFromChanges(otherChanges.head.filter(p => p.value <= rest)) ++ combinationsBarQixBaz(rest, otherChanges.tail)
      }

      val rest = money - change.value
      if (rest > 0) {
        val otherCombinations = combinationsBarQixBaz(rest, otherChanges)
        if (otherCombinations.isEmpty) {
          // No other possible changes, complete with foos.
          List(List(change, getFoos(rest)))
        } else {
          // Map each possible bar with qix, baz and foos
          otherCombinations.map(p => {
            val restAfterNewChange = rest - p.value
            if (restAfterNewChange > 0) List(change, p, getFoos(restAfterNewChange))
            else List(change, p)
          })
        }
      }
      else List()
    }

    possiblesChangeForFooQixBaz.flatMap(l => l.map(x => generate(money, x))).flatten
  }
}