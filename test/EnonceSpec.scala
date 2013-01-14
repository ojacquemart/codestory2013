import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class EnonceSpec extends Specification {

	"respond to enonce/1" in {
		val expected = """ {"foo": "bar"} """

	  	val Some(result) = routeAndCall(
	  		FakeRequest(POST,
			"/enonce/1",
			FakeHeaders(), 
			expected))

		status(result) must equalTo(201)
	}

}