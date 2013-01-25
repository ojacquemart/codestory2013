import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "scala-codestory"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	"org.codehaus.groovy" % "groovy-all" % "2.0.6",
        "com.codahale" % "jerkson_2.9.1" % "0.5.0",
        "commons-io" % "commons-io" % "2.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
		resolvers += ("Repository codehaus" at "http://repository.codehaus.org"),
        resolvers += ("repo.codahale.com" at "http://repo.codahale.com")
    )

}
