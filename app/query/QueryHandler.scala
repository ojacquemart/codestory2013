package query

import play.api._

import groovy.lang._

object QueryHandler {
	
	def handle(question: String): String = {
		Logger.info("question=[%s]".format(question))

		val eqPattern = """[0-9+]+([+|*|-|/])+""".r
	    eqPattern findFirstIn question match {
	      case None => Question.answer(question)
	      case _ 	=> Equation.resolve(question).toString
	    }
  	}	

}

object Equation {

	def resolve(exp: String): String = {
		val calc: String = new GroovyShell().evaluate(prepareIn(exp)).toString
   		prepareOut(calc)
  	}

  	def prepareIn(value: String) = value.replace(",", ".")
  	def prepareOut(value: String) = {
  		value.replaceAll("""(\d+)(\.0+)""", "$1").replace(".", ",")
  	}
}

object Question {
	
	 def answer(question: String): String = question match {
		case "Quelle+est+ton+adresse+email" 				=> "o.jacquemart@gmail.com"
		case "Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" 	=> "NON"
		case _ 												=> "OUI"
	}

}

