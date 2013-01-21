package query

import play.api._

import de.congrace.exp4j._

object QueryHandler {
	
	def handle(question: String): String = {
		Logger.info("question=[%s]".format(question))

		val eqPattern = """(\d+)(.+)(\d+)""".r
	    eqPattern findFirstIn question match {
	      case (Some(eqPattern(a, op, b))) => Equation.resolve(question).toString
	      case _ => {
	        question match {
	          case _ => Question.answer(question)
	        }
	      }
	    }
  	}	

}

object Equation {

	/**
	 @see http://www.objecthunter.net/exp4j/apidocs/index.html
	 */
	def resolve(exp: String): String = {
		val calc: Calculable  = new ExpressionBuilder(exp).build
   		val result = calc.calculate
   		format(result)
  	}

  	def format(x: Double): String = if (x.toInt == x) x.toInt.toString else x.toString
  	
}

object Question {
	
	 def answer(question: String): String = question match {
		case "Quelle+est+ton+adresse+email" => "o.jacquemart@gmail.com"
		case "Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" => "NON"
		case _ => "OUI"
	}

}

