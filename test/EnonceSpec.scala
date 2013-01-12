import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class HelloWorldSpec extends Specification {

"respond to the enonce/1 Action" in {
  val expected = """ {"name": "New Group", "collabs": ["foo", "asdf"]} """
  val Some(result) = routeAndCall(FakeRequest(POST, "/enonce/1", FakeHeaders(), expected))
  
  status(result) must equalTo(OK)
  contentType(result) must beSome("text/plain")
  charset(result) must beSome("utf-8")
  contentAsString(result) must contain(expected)
}

  "The 'Hello world' string" should {
    "contain 11 characters" in {
      "Hello world" must have size(11)
    }
    "start with 'Hello'" in {
      "Hello world" must startWith("Hello")
    }
    "end with 'world'" in {
      "Hello world" must endWith("world")
    }
  }
}