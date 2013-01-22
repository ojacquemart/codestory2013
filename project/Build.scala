import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "scala-codestory"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	"de.congrace" % "exp4j" % "0.3.8",
    	"org.codehaus.groovy" % "groovy-all" % "1.8.6"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
		resolvers += (
    		"Repository codehaus" at "http://repository.codehaus.org"
		)
    )

}
