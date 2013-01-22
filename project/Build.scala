import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "scala-codestory"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	"org.codehaus.groovy" % "groovy-all" % "2.0.6"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
		resolvers += (
    		"Repository codehaus" at "http://repository.codehaus.org"
		)
    )

}
