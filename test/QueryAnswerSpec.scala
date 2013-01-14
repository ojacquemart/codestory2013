import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class QueryAnswersSpec extends Specification {

	"respond to /?q=Quelle+est+ton+adresse+email" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=Quelle+est+ton+adresse+email"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("o.jacquemart@gmail.com")
	}

	"respond to /?q=Es+tu+abonne+a+la+mailing+list(OUI/NON)" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=Es+tu+abonne+a+la+mailing+list(OUI/NON)"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("OUI")
	}

	"respond to /?q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("NON")
	}

	"respond to anything else" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=happy?"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("OUI")
	}	

}