##Minesweeper

###Problem Description

Have you ever played Minesweeper? It's a cute little game which comes within a certain Operating System whose name we can't really remember. 
Well, the goal of the game is to find all the mines within an MxN field.
To help you, the game shows a number in a square which tells you how many mines there are adjacent to that square.
For instance, take the following 4x4 field with 2 mines (which are represented by an * character):
*...
....
.*..
....

The same field including the hint numbers described above would look like this:
You should write an algorithm that takes an input as follows:
*100
2210
1*10
1110

###Suggested Test Cases
The first line of each field contains two integers n and m (0 < n,m <= 100) which stands for the number of lines and columns of the field respectively.
The next n lines contain exactly m characters and represent the field.
Each safe square is represented by an "." character (without the quotes) and each mine square is represented by an "*" character (also without the quotes).
The first field line where n = m = 0 represents the end of input and should not be processed.
This is the acceptance test input:
Your program should produce output as follows:

Each line should contain the field with the "." characters replaced by the number of adjacent mines to that square.
There must be an empty line between field outputs.

4 4
*...
.*..
....
....

and output:
2210
*100
1*10
1110

### Serveur
Votre serveur doit répondre aux requêtes http POST de la forme `http://serveur/minesweeper/resolve` avec un payload de la forme :
4 4
....
*...
.*..
....

Vous devrez répondre le résultat suivant au format text:
2210
*100
1*10
1110

Have fun!
