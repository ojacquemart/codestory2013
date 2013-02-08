package minesweeper

import scala.collection.mutable.ListBuffer

case class Minesweeper(mines: List[List[String]]) {

  val MineSymbol = "*"

  // TODO: see tu use array or some other type, instead of list.

  def solve(): List[String] = {
    var result: ListBuffer[String] = ListBuffer()
    var row = ""

    mines.indices.foreach({
      x => mines(x).indices.foreach({
        y => row += (mines(x)(y) match {
          case MineSymbol => MineSymbol
          case _ => nbMinesAround(x, y).toString
        })
      })
      result += row
      row = ""
    })

    result.toList
  }

  def isAMine(value: String) = MineSymbol == value

  def nbMinesAround(x: Int, y: Int): Int = {
    nbMinesAroundTop(x, y) + nbMinesAroundBottom(x, y) + nbMinesAroundLeftRight(x, y)
  }

  def nbMinesAroundTop(x: Int, y: Int): Int = {
    nbMinesInOneCell(x - 1, y - 1) + nbMinesInOneCell(x - 1, y) + nbMinesInOneCell(x - 1, y + 1)
  }

  def nbMinesAroundBottom(x: Int, y: Int): Int = {
    nbMinesInOneCell(x + 1, y - 1) + nbMinesInOneCell(x + 1, y) + nbMinesInOneCell(x + 1, y + 1)
  }

  def nbMinesAroundLeftRight(x: Int, y: Int): Int = {
    nbMinesInOneCell(x, y - 1) + nbMinesInOneCell(x, y + 1)
  }

  def nbMinesInOneCell(x: Int, y: Int) = getOrElse(x, mines) match {
    case None => 0
    case Some(l: List[String]) => {
      getOrElse(y, l) match {
        case None => 0
        case Some(s: String) => if (isAMine(s)) 1 else 0
      }
    }
  }

  // TODO: migrate to play 2.1 and use List#getOrElse.
  def getOrElse[T](x: Int, list: Seq[T]): Option[T] = {
    if (x < 0 || x >= mines.size) None
    else Some(list(x))
  }

}
