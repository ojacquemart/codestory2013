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

	"respond to /?q=1+1" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=1+1"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("2")
	}

	"respond to /?q=(1+2)*2" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=(1+2)*2"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("6")
	}

	"respond to /?q=(1+2)/2" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=(1+2)/2"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("1,5")
	}

	"respond to /?q=1,5*4" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=1,5*4"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("6")
	}

	"respond to /?((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*553344300034334349999000" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=((1,1+2)+3,14+4+(5+6+7)+(8+9+10)*4267387833344334647677634)/2*5533443000343343499990004"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("318780189038289007235256033541132189372519974699008")
	}

	"respond to anything else" in {
	  	val Some(result) = routeAndCall(FakeRequest(GET, "/?q=happy?"))
		status(result) must equalTo(OK)
	  	contentAsString(result) must equalTo("OUI")
	}	

}