package scalaskel

import play.api.libs.json._

object Scalaskel {

  trait TCent {
    def currency: String
    def value: Int
    def nb: Int

    override def toString = if (nb == 0) "" else "'%s' : %d".format(currency, nb)
  }

  case class Foo(nb: Int) extends TCent {
    def currency = "foo"
    def value = 1
  }

  case class Bar(nb: Int) extends TCent {
    def currency = "bar"
    def value = 7
  }

  case class Qix(nb: Int) extends TCent {
    def currency = "qix"
    def value = 11
  }

  case class Baz(nb: Int) extends TCent {
    def currency = "baz"
    def value = 21
  }

  case class Coin(foo: Foo, bar: Bar, qix: Qix, baz: Baz) {

    override def toString = {
      List(foo, bar, qix, baz).map(_.toString).filter(s => !s.isEmpty).mkString("{", ",", "}")
    }
  }

  def listChangeToCoin(l: List[Change]): Coin = {
    Coin(
      Foo(l.find(_.cent.value == foo.value).getOrElse(Change(foo, 0)).nb),
      Bar(l.find(_.cent.value == bar.value).getOrElse(Change(bar, 0)).nb),
      Qix(l.find(_.cent.value == qix.value).getOrElse(Change(qix, 0)).nb),
      Baz(l.find(_.cent.value == baz.value).getOrElse(Change(baz, 0)).nb)
    )
  }

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

  def changeAsJson(money: Int) = change(money).map(listChangeToCoin(_)).mkString("[", ",", "]")

  def change(money: Int) = {
    def foosUntilMoney() = List(List(getFoos(money)))
    def barQixBazEqualsToMoney() = barQixBaz.filter(_.value == money).map(p => List(Change(p, 1)))
    def barQixBazWithFoosEqualsToMoney() = barQixBaz.filter(money - _.value > 0).map(p => List(Change(p, 1), getFoos(money - p.value)))

    (foosUntilMoney() ++ barQixBazEqualsToMoney() ++ 
    	barQixBazWithFoosEqualsToMoney() ++ decomposeFooBarQixBaz(money)).toSet
  }

  implicit def changeToInt(c: Change): Int = c.cent.value * c.nb

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
        else otherChanges.head.filter(p => p.value <= rest) ++ combinationsBarQixBaz(rest, otherChanges.tail)
      }

        val rest = money - change.value
        val otherCombinations = combinationsBarQixBaz(rest, otherChanges)
        val list = List(List(change, getFoos(rest))) ++ otherCombinations.map(p => {
          val restAfterNewChange = rest - p.value
          if (restAfterNewChange > 0) List(change, p, getFoos(restAfterNewChange))
          else List(change, p)
        })
        list
    }

    possiblesChangeForFooQixBaz.flatMap(l => l.map(x => generate(money, x))).flatten
  }
}