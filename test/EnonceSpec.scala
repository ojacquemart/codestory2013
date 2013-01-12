import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class HelloWorldSpec extends Specification {

"respond to the enonce/1 Action" in {
  val expected = """ {"name": "New Group", "collabs": ["foo", "asdf"]} """
  val Some(result) = routeAndCall(FakeRequest(POST, "/enonce/1", FakeHeaders(), expected))
  
  status(result) must equalTo(OK)
}
  
}