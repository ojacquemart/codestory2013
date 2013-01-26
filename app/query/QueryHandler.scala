package query

import play.api._

import groovy.lang._

object QueryHandler {
	
	def handle(question: String): String = {
		Logger.info("question=[%s]".format(question))

		val eqPattern = """([0-9])+""".r
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
  	def prepareOut(value: String) = value.replaceAll("""^(\d+)(\.0+)$""", "$1").replace(".", ",")
}

object Question {
	
	 def answer(question: String): String = question match {
		case "Quelle+est+ton+adresse+email" 				=> "o.jacquemart@gmail.com"
		case "Est+ce+que+tu+reponds+toujours+oui(OUI/NON)" 	=> "NON"
		case "As+tu+passe+une+bonne+nuit+malgre+les+bugs+de+l+etape+precedente(PAS_TOP/BOF/QUELS_BUGS)" => "QUELS_BUGS"
		case "q=As+tu+copie+le+code+de+ndeloof(OUI/NON/JE_SUIS_NICOLAS" => "NON"
		case _ => "OUI"
	}

}

