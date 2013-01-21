package scalaskel

import collection.mutable.ListBuffer

import play.api.libs.json._

trait Cent {
  val currency: String
  val nb: Int

  override def toString = if (nb == 0) "" else "'%s' : %d".format(currency, nb)
}

case class Foo(nb: Int) extends Cent { val currency = "foo" }
case class Bar(nb: Int) extends Cent { val currency = "bar" }
case class Qix(nb: Int) extends Cent { val currency = "qix" }
case class Baz(nb: Int) extends Cent { val currency = "baz" }

class Coin(foo: Foo, bar: Bar, qix: Qix, baz: Baz) {
  def this(nbFoo: Int, nbBar: Int, nbQix: Int, nbBaz: Int) = this(Foo(nbFoo), Bar(nbBar), Qix(nbQix), Baz(nbBaz))

  override def toString = {
    List(foo, bar, qix, baz).map(_.toString).filter(!_.isEmpty).mkString("{", ",", "}")
  }
}

object Pieces extends Enumeration {
  val Foo = Value(1)
  val Bar = Value(7)
  val Qix = Value(11)
  val Baz = Value(21)
}

object Scalaskel {
  def changeAsJson(money: Int) = change(money).map(_.toString).mkString("[", ",", "]")

  def change(money: Int) = {
    val buffer: ListBuffer[Coin] = ListBuffer()
    for (baz <- 0 to money / Pieces.Baz.id) {
      val moneyMinusBaz = money - baz * Pieces.Baz.id
      for (qix <- 0 to moneyMinusBaz / Pieces.Qix.id) {
        val moneyMinusQixBaz = moneyMinusBaz - qix * Pieces.Qix.id
        for (bar <- 0 to moneyMinusQixBaz / Pieces.Bar.id) {
          val foo = moneyMinusQixBaz - bar * Pieces.Bar.id
          buffer += new Coin(foo, bar, qix, baz)
        }
      }
    }
    buffer.toList
  }
}