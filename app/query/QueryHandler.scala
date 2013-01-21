package query

import play.api._

import de.congrace.exp4j._

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

	/**
	 @see http://www.objecthunter.net/exp4j/apidocs/index.html
	 */
	def resolve(exp: String): String = {
		val calc: Calculable  = new ExpressionBuilder(prepareIn(exp)).build
   		prepareOut(checkIfIntValue(calc.calculate))
  	}

  	def prepareIn(value: String) = value.replace(",", ".")
  	def prepareOut(value: String) = value.replace(".", ",")
  	def checkIfIntValue(x: Double): String = if (x.toInt == x) x.toInt.toString else x.toString
}

object Question {
	
	 def answer(question: String): String = question match {
		case "Quelle+est+ton+adresse+email" 				=> "o.jacquemart@gmail.com"
		case "Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" 	=> "NON"
		case _ 												=> "OUI"
	}

}

