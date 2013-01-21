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
		val calc: Calculable  = new ExpressionBuilder(preFormat(exp)).build
   		format(calc.calculate)
  	}

  	def preFormat(exp: String) = exp.replace(",", ".")

  	def format(x: Double): String = {
  		if (x.toInt == x) x.toInt.toString
  		else x.toString.replace(".", ",")
  	}

}

object Question {
	
	 def answer(question: String): String = question match {
		case "Quelle+est+ton+adresse+email" 				=> "o.jacquemart@gmail.com"
		case "Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" 	=> "NON"
		case _ 												=> "OUI"
	}

}

