

import org.specs2.mutable._
import minesweeper._

class MinesweeperSpec extends Specification {

  val mines = List(
    List("*", ".", ".", "."),
    List(".", ".", ".", "."),
    List(".", "*", ".", "."),
    List(".", ".", ".", ".")
  )
  val mineSweeper = new Minesweeper(mines)

  "The MinesweeperSpec" should {

    "identify a mine" in {
      mineSweeper.isAMine("*") must be equalTo (true)
    }

    "getOrElse from outOfBounds and correct indexes" in {
      mineSweeper.getOrElse(-1, mines) must be equalTo (None)
      mineSweeper.getOrElse(mines.size, mines) must be equalTo (None)
      mineSweeper.getOrElse(mines.size + 1, mines) must be equalTo (None)
      mineSweeper.getOrElse(1, mines) must be equalTo (Some(mines(1)))
    }

    "count nb mines for a (x,y)" in {
      mineSweeper.nbMinesInOneCell(1, 1) must be equalTo (0)
    }

    "count nb mines at left/right of a (x,y)" in {
      mineSweeper.nbMinesAroundLeftRight(1, 1) must be equalTo (0)
    }
    "count nb mines at top of a (x,y)" in {
      mineSweeper.nbMinesAroundTop(1, 1) must be equalTo (1)
    }

    "count nb mines at bottom of a (x,y)" in {
      mineSweeper.nbMinesAroundBottom(1, 1) must be equalTo (1)
    }

    "count nb mines around a (x,y)" in {
      mineSweeper.nbMinesAround(1, 1) must be equalTo (2)
      mineSweeper.nbMinesAround(1, 0) must be equalTo (2)
    }

    "solve minesweeper" in {
      val result = mineSweeper.solve()
      result(0) must be equalTo ("*100")
      result(1) must be equalTo ("2210")
      result(2) must be equalTo ("1*10")
      result(3) must be equalTo ("1110")
    }

    "convert payload to List[List[String]] and resolve" in {
      val payload = "4 4\n*...\n....\n.*..\n...."

      val result = Minisweeper(payload).solve
      result(0) must be equalTo ("*100")
      result(1) must be equalTo ("2210")
      result(2) must be equalTo ("1*10")
      result(3) must be equalTo ("1110")
    }
  }


}