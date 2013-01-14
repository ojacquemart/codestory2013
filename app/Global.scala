import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {
	
	override def onRouteRequest(request: RequestHeader) = {
		logRequest(request)
    	super.onRouteRequest(request)
  	}  

   override def onBadRequest(request: RequestHeader, error: String) = {
   		logRequest(request)
    	super.onBadRequest(request, error)
  }  

  def logRequest(implicit request: RequestHeader) = {
  		Logger.info("Request: " + request)
  }
}
