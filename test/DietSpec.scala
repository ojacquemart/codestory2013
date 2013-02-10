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

      val result = Diet(json).resolve.toString
      result must be equalTo ("""["coca-light","croissant","guitar-hero"]""")

    }

    "resolve diet with no answer" in {
      val json =
        """[
                      { "name" : "croissant", "value" : 180 }
                    ]"""

      val result = Diet(json).resolve.toString
      result must be equalTo ("""["no solution"]""")


    }

    "resolve diet with bigger payload" in {
      val json =
        """[
                      {"name":"coca","value":138},
                      {"name":"the-froid","value":126},
                      {"name":"vin","value":35},
                      {"name":"biere","value":125},
                      {"name":"coca-light","value":1},
                      {"name":"cafe","value":16},
                      {"name":"jus-de-fruit","value":50},
                      {"name":"limonade","value":134},
                      {"name":"au-travail-a-velo","value":-113},
                      {"name":"au-travail-en-marchant","value":-212},
                      {"name":"trente-minutes-de-jogging","value":-518},
                      {"name":"an-travail-en-voiture","value":-20},
                      {"name":"seance-de-gym","value":-204},
                      {"name":"roller","value":-129},
                      {"name":"badminton","value":-83},
                      {"name":"squash","value":-660},
                      {"name":"partie-de-foot","value":-360},
                      {"name":"partie-de-foot-gardien","value":-55},
                      {"name":"ping-pong","value":-98},
                      {"name":"mario-kart","value":-120},
                      {"name":"whisky","value":48}
                      ]"""

      val result = Diet(json).resolve.toString
      result must be equalTo ("""["jus-de-fruit","ping-pong","whisky"]""")

    }

    "resolve diet with test payload" in {
      val json =
        """[
                      {"name":"jus-de-fruit","value":50},
                      {"name":"jus-de-fruit","value":16},
                      {"name":"jus-de-fruit","value":32},
                      {"name":"ping-pong","value":-98},
                      {"name":"ping-pong","value":-1},
                      {"name":"ping-pong","value":-1000},
                      {"name":"whisky","value":48},
                      {"name":"whisky","value":48},
                      {"name":"whisky","value":120},
                      {"name":"whisky","value":55},
                      {"name":"whisky","value":111}
                      ]"""

      val result = Diet(json).resolve.toString
      result must be equalTo ("""["jus-de-fruit","ping-pong","whisky"]""")

    }


  }


}