##Le Régime du Geek
### Description

Chez JUGL Inc., les employés ont droit à tout un tas de services et d'activités gratuites directement sur leur lieu de travail.
Salle de gym, sauna, consoles de jeux, table de ping pong et bouffe à volonté...
Dans ces conditions, un challenge pour nous autres ingénieurs (fan de Coca, snacks et pizzas) est de garder un équilibre entre ingestion et dépense de calories pour garder la ligne (euh... bon ok on va plutôt dire la forme).
Comme vous êtes un des cerveaux de cette compagnie (et que vous savez garder la forme), le chef vient vous voir et vous demande de concocter un petit programme qui proposerait aux employés une liste d'activités quotidiennes pour garder une balance énergétique aussi équilibrée que possible.

###Serveur

### Instructions
Votre serveur doit répondre aux requêtes http POST de la forme `http://serveur/diet/resolve` avec un payload de la forme :
  [
          { "name" : "croissant", "value" : 180 },
          { "name" : "au-travail-a-velo", "value" : -113 },
          { "name" : "coca-light", "value" : 1 },
          { "name" : "guitar-hero", "value" : -181 }
  ]

Vous recevez donc en entrée une liste d'activités avec leur impact énergétique. Le programme doit simplement renvoyer une liste contenant les noms des activités à choisir afin que leur impact énergétique soit équivalent à 0.
Notez qu'une activité ne peut être choisie qu'une seule fois.

Vos réponses doivent être renvoyées au format JSON. Si la liste d'activités ne permet pas d'arriver à une somme de 0, votre serveur doit renvoyer :
  ["no solution"]
sinon la liste des activités dont la somme des valeurs énergétiques est égale à 0 :
  ["coca-light", "croissant", "guitar-hero"]
  ["croissant", "coca-light", "guitar-hero"]

Vous aurez compris que je n'attends qu'une seule réponse. Pas besoin de me renvoyer toutes les combinaisons possibles.
Egalement. L'ordre n'est pas significatif.

Les noms des activités ne contiennent que des caractères ASCII (plus exactement que des lettres minuscules et le signe -).