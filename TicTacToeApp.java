/**
 * COMP1406 - Winter2016 - Assignment 5
 * 
 * This is a Java program that allows a use to play a game
 * against the computer (TicTacToePlayer)
 * 
 * @author <Esti Tweg>
 * @since <February 28, 2016>
 * @custom.citations 
<Collier, R. "Lectures Notes for COMP1406C - Introduction to Computer Science II" [PDF documents]. 
Retrieved from cuLearn: https://www.carleton.ca/culearn/ (Winter 2016).

Java - String toLowerCase() Method. (Feb 28, 2016). 
Retrieved from URL http://www.tutorialspoint.com/java/java_string_tolowercase.htm

How to convert a String to char. (Feb 28, 2016). 
Retrieved from URL http://www.coderanch.com/t/552234/java/java/convert-String-char

Converting String to Int in Java?.(Feb 28, 2016). 
Retrieved from URL http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java

Adding numbers in Java.(Feb 28, 2016). 
Retrieved from URL http://stackoverflow.com/questions/6413118/adding-numbers-in-java

How do I check for null pointer exception. (Feb 28, 2016). 
Retrieved from URL http://stackoverflow.com/questions/11981365/how-do-i-check-for-null-pointer-exception>
 */

import java.util.Scanner;  // used for input

public class TicTacToeApp{
  
  public static void main(String[] args){
	//initialize variables to keep track of whats going on
	String pos = "";
	int wins=0;
	int loses=0;
	int ties=0;
	int numOfMoves=0;
	char compSymbl;
	//create new instance of the scanner class for input
	Scanner keyboard = new Scanner(System.in);  
	//ask the user what their name is save as name
    System.out.println("What is your name? ");
	String name = keyboard.next();
	//get them to choose what symbol they want to be, change to lowercase and save as symbl
	System.out.println("Do you want to be x's or o's");
	String symbl = keyboard.next().toLowerCase();
	//make a charSymbol which is the string symbl as a char variable
	char charSymbl = symbl.charAt(0); 
	//create new instance of the tic tac toe game and player using above input
	TicTacToeGame board = new TicTacToeGame();
	TicTacToePlayer p = new TicTacToePlayer(name, charSymbl);
	
	//assign the computer the opposite symbol
	if (charSymbl=='x'){
		compSymbl = 'o';
		}
	else{
		compSymbl = 'x';
	}
	
	//make the computer player
	TicTacToePlayer pComp = new TicTacToePlayer("Computer", compSymbl);
	//while the position input is not quit
	while( !pos.equals("quit") ){
		//if the player is o  and its the first move of the game then the computer goes first
		if (charSymbl=='o' && numOfMoves==0){
			int compPos = pComp.findMove(board);
			pComp.play(board, compPos); 
			System.out.println("I went in position: " + compPos);
			System.out.println(board.show());
		}	
		//ask where the user wants to play
		System.out.println("Where do you want to play?");
		pos = keyboard.next();
		
		//check if the input is quit
		//if it is then end the while loop
		if (pos.equals("quit")){
			break;
		}
		//if its not quit then the input is a number position
		//change the input string to an integer
		int intPos =  Integer.parseInt(pos);
		//play the specified position 
		p.play(board, intPos);
		System.out.println(board.show());
		//increase the counter of moves
		numOfMoves++;
		//check if the game is over 
        if( p.gameOver(board) ){
			//if the game is over check why, display who won and increase the corrisponding counter
			//if the winner is null/ no one, then its a tie
			if (null==p.winner(board, p, pComp)){
				System.out.println("Its a tie!");
				ties++;
			}
			//if the name of the winner is computer then the computer won
			else if (p.winner(board, p, pComp).getName().equals("Computer")){
				loses++;
				System.out.println("I won!");
			}
			//otherwise its the user that won
			else{
				wins++;
				System.out.println("The winner is " + p.winner(board, p, pComp).getName());
			}
			//make a new board
			TicTacToeGame anotherBoard = new TicTacToeGame();
			//have the old board point to the new fresh board 
			board = anotherBoard;
			//reset the muber of moves to be 0
			numOfMoves=0;
        }
		//if the play is x and its the first move of a new game then
		//ask where they want to play
		if (charSymbl=='x' && numOfMoves==0){
			System.out.println("Where do you want to play?");
			pos = keyboard.next();
			//check if the input is quit
			if (pos.equals("quit")){
				break;
			}
			//if its not quit then change the input string to an integer
			intPos =  Integer.parseInt(pos);
			//play the specified position 
			p.play(board, intPos);
			System.out.println(board.show());
		}
		//get a move for the computer
		int compPos = pComp.findMove(board);
		//if the move is not -1 then play that move and display the position played
		if (compPos != -1){
			pComp.play(board, compPos); 
			System.out.println(board.show());
			System.out.println("I went in position: " + compPos);
			//check if the game is over 
	        if( pComp.gameOver(board) ){
				//if it is then display the winner and increase the corrisponding counter
				if (null==p.winner(board, p, pComp)){
					System.out.println("Its a tie!");
					ties++;
				}
				else if (p.winner(board, p, pComp).getName().equals("Computer")){
					loses++;
					System.out.println("I won!");
				}
				else{
					wins++;
					System.out.println("The winner is " + p.winner(board, p, pComp).getName());
				}
				//make a new board
				TicTacToeGame anotherBoard = new TicTacToeGame();
				//have the old baord point to the new fresh board 
				board = anotherBoard;
				//reset the muber of moves to be 0
				numOfMoves=0;
	        }
		}
	}//end while loop
	
		// if the user typed quit then output summary of games
		if (pos.equals("quit")){
			//how many games were played, won, lost and tied.
			System.out.println("You played " + (wins+loses+ties) + " game(s)");
			System.out.println("You won "+ wins +" time(s)");
			System.out.println("You lost "+ loses +" time(s)");
			System.out.println("There were "+ ties +" tie(s)");
		}
    
  }
}