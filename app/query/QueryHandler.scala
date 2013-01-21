package query

object QueryHandler {
	
	def handle(question: String): String = {
		val eqPattern = """(\d+)(\+)(\d+)""".r
	    eqPattern findFirstIn question match {
	      case (Some(eqPattern(a, op, b))) => Equation.resolve(a.toFloat, op, b.toFloat).toString
	      case _ => {
	        question match {
	          case _ => Question.answer(question)
	        }
	      }
	    }
  	}	

}

object Question {
	
	 def answer(question: String): String = question match {
		case "Quelle+est+ton+adresse+email" => "o.jacquemart@gmail.com"
		case "q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" => "NON"
		case _ => "OUI"
	}

}

object Equation {
	
	def resolve(a: Float, op: String, b: Float): Float = {
	    op match {
	      case "+" => a + b
	      case "-" => a - b
	      case "/" => a.toFloat / b
	      case _  => 0
	    }
  	}
  	
}
