import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import play.api.libs.json._

import scalaskel._

class ScalaskelSpec extends Specification {

  "The ScalaskelSpec" should {

    "be for 1" in {
        Scalaskel.changeAsJson(1) must equalTo("""[{'foo' : 1}]""")
    }

    "be for 7" in {
        Scalaskel.changeAsJson(7) must equalTo("""[{'foo' : 7},{'bar' : 1}]""")
    }

    "be for 11" in {
        Scalaskel.changeAsJson(11) must equalTo("""[{'foo' : 11},{'foo' : 4,'bar' : 1},{'qix' : 1}]""")
    }
  }

}