import org.specs2.mutable._

import diet._

class DietSpec extends Specification {

  "The DietSpec" should {

    "read json" in {
      val json =
        """[
          { "name" : "croissant", "value" : 180 },
          { "name" : "au-travail-a-velo", "value" : -113 },
          { "name" : "coca-light", "value" : 1 },
          { "name" : "guitar-hero", "value" : -181 }
        ]"""

      Diet.jsonAsActivites(json) must equalTo(List(
        new Activity("croissant", 180),
        new Activity("au-travail-a-velo", -113),
        new Activity("coca-light", 1),
        new Activity("guitar-hero", -181)))
    }

    "resolve diet with first question" in {
      val json =
        """[
          { "name" : "croissant", "value" : 180 },
          { "name" : "au-travail-a-velo", "value" : -113 },
          { "name" : "coca-light", "value" : 1 },
          { "name" : "guitar-hero", "value" : -181 }
        ]"""

     val result = Diet(json).resolveAsJson.toString
      result must be equalTo("""["coca-light","croissant","guitar-hero"]""")
    }

    "resolve diet with no answer" in {
      val json =
        """[
          { "name" : "croissant", "value" : 180 }
        ]"""

     val result = Diet(json).resolveAsJson.toString
      result must be equalTo("""["no solution"]""")
    }

  }


}