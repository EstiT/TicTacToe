	/**
	* COMP1406 - Winter2016 - Assignment 5
	* 
	* This class encapsulates a player in a tic-tac-toe game.
	* See https://en.wikipedia.org/wiki/Tic-tac-toe for more
	* information about tic-tac-toe.
	* 
	* @author <Esti Tweg>
	* @since <February 28, 2016>
	* @custom.citations <Collier, R. "Lectures Notes for COMP1406C - Introduction to Computer 
		Science II" [PDF documents]. Retrieved from cuLearn: https://www.carleton.ca/culearn/ (Winter 2016).
		
		Daisy, W. (Personal Communication). Feb 24, 2016.
		>
	*/

public class TicTacToePlayer{
	private final String name;  // DO NOT CHANGE THIS
	private char plyr; //variable for the players symbol for this class
	
	public String getName(){   // DO NOT CHANGE THIS
		return name; 
	}
 
	/* Constructor for a tic tac toe player.
	* @param name is the name of the player.
	* @param p must be either 'x' or 'o'. */
	public TicTacToePlayer(String name, char p){
	this.name = name;
	plyr = p;
	}
	
	/* Checks if a given game instance is over or not.
	* @param game is an instance of a tic tac toe game (the board)
	* @return true if the game instance is over 
	* if a player has won or if there is a tie return true, otherwise false.*/
	public static boolean gameOver(TicTacToeGame game){
		//initalize variables
		int d= game.getDimension();
		char[] line;
		int startNum;
		//ROWS:
		for (int i=0; i<d; i++){
			startNum = i+1;
			//get array of each row
			line = rowList (game, startNum, d);
			//check for a winner in that row
			if (checkEndGame(line)){
				return true;
				}
		}
		//COLOUMNS:
		for (int i=0; i<d; i++){
			startNum = i+1;
			//get array of each coloumn
			line = coloumnList (game, startNum, d);
			//check for a winner in that row
			if (checkEndGame(line)){
				return true;
				}
		}
		//check the diagonal up for a winner
		line = diagonalUpList (game, d);
		if (checkEndGame(line)){
			return true;
			}
		//check the diagonal down for a winner
		line = diagonalDownList(game, d);
		if (checkEndGame(line)){
			return true;
			} 
		//there is not a winner if its gotten this far
		//check if there is a tie 
		//count how many not empty spaces there are 
		int counter=0;
		for (int i=0; i<d*d; i++){
			if (game.getAtPosition(i)!='\0'){
				counter++;
			}
		}
		//if the number of filled spaces is equal to the number 
		//of spaces in the board then its a tie
		if (counter==d*d){
			return true;
		}
		//if no one won and its not a tie
		//then the game is not over
		return false;
	}

	/* Checks if a given game instance is over or not.
	* @param game is an instance of a tic tac toe game (the board)
	* @param p1 is one player in the game
	* @param p2 is the other player in the game
	* @return p1 if player p1 has won the game, p2 if player p2 has 
	* won the game and returns null otherwise (if there is no winner in the given game).*/
	public static TicTacToePlayer winner(TicTacToeGame game, TicTacToePlayer p1, TicTacToePlayer p2){
		//get each players symbol 
		char p1Char = p1.getXO();
		char p2Char = p2.getXO();
		//initialize winner charecter
		char winner = ' ';
		// check if the game is over
		if (gameOver(game)){
			//initlaize variables to keep track of things
			int d = game.getDimension();
			char[] line;
			int startNum;
			boolean flag = false;
			//check each row if there is a winner
			for (int i=0; i<d; i++){
				startNum = i +1;
				line = rowList (game, startNum, d);
				if (checkEndGame(line)){
					flag = true;
					winner = line[0]; 
					}
				}
			//check each coloumn if there is a winner
			for (int i=0; i<d; i++){
				startNum = i +1;
				line = coloumnList (game, startNum, d);
				if (checkEndGame(line)){
					flag = true;
					winner = line[0];
					}
				}
			//check the diagonal down right for a winner
			line = diagonalUpList (game, d);
			if (checkEndGame(line)){
				flag = true;
				winner = line[0];
				}
			//check the diagonal up right for a winner
			line = diagonalDownList(game, d);
			if (checkEndGame(line)){
				flag = true;
				winner = line[0];
				} 
			//if someone won
			//check who it was and return that player
			if (flag){
				if (winner == p1Char){
					return p1;
					}
				else if (winner == p2Char){
					return p2;
					}
				}
			}
	//otherwise no one won, its a tie 
	return null;
	}

	/* Checks if current player is playing x's or o's.
	* @param none
	* @return true if this object is playing x's and false if the object is playing o's. */
	public boolean isX(){
		//if the player is x return true otherwise the player is o and return false
		if (plyr=='x'){ 
			return true;
			}
		else{
			return false;
		}
	}

	/**
	* Getter method that tells if player is playing x's or o's.
	* @return 'x' if this player is playing x's and returns 'o' if this player is playing o's. */
	public char getXO(){
		//if the player is playing x then return x
		if (isX()){
			return 'x';
		}
		//otherwise they are playing o and return that
		else{
			return 'o';
		}
	}

	/**
	* Finds a valid move in a tic-tac-toe game for this player
	* @param game is an instance of a tic-tac-toe game
	* @return A position in the board [0,D-1] that is a valid move for this player to make, 
	* where D = dimention*dimension. 
	* If there are multiple valid moves any is acceptable. 
	* If there are no valid moves then the function returns -1. */
	public int findMove(TicTacToeGame game){
		int size = game.getDimension()*game.getDimension();
		int [] moves;
		moves = new int[size];
		//if there is itleast one valid move to be made then return the first one
		if (findAllMoves(game)!= null){
			moves=findAllMoves(game);
			return moves[0];
		}
		//otherwise there are no valid moves and return -1
		else{
			return -1;
		}
	}

	/**
	* Finds a valid move in a tic-tac-toe game for this player
	* @param game is an instance of a tic-tac-toe game
	* @return An array of positions in the board [0,D-1] that are each a valid move 
	* for this player to make, where D = dimension*dimension. 
	* All valid moves must be included in the output. 
	* The order of the positions in the output array do not matter. If there are no
	* valid moves then the function return null. */
	public int[] findAllMoves(TicTacToeGame game){
		//calcualate the total number of possible moves
		int size = game.getDimension()*game.getDimension();
		//make a temporary array of the max number of possibilities
		int [] tempMoves;
		tempMoves = new int[size];
		int numOfMoves=0;
		//run through every position
		//if at that position the board is empty then add its location to the temp array
		//increase the number of possible moves
		for (int i=0; i<size; i++){
			if (game.getAtPosition(i)== '\0'){
				tempMoves[numOfMoves]= i;
				numOfMoves++;
			}
		}
		//if there are no moves to be made then return null
		if (numOfMoves==0){
			return null;
		}
		else{
			//make the actual moves array of the correct size
			int [] moves;
			moves = new int[numOfMoves];
			//fill the array with the positions of all of the moves
			for (int i=0; i<numOfMoves; i++){
				moves[i]= tempMoves[i];
			}
			return moves;
		}
	}

	/**
	* Finds a winning move if possible for this player.
	* @param game is an instance of a tic-tac-toe game
	* @return A position in the board [0,D-1] that is a valid winning move for this player to make, 
	* where D = dimension*dimension.
	* If there are multiple winning moves then any is acceptable. 
	* Returns -1 if there is no winning move for the player. */
	public int findWinningMove(TicTacToeGame game){
		//initiliaze variables to keep track of things
		char winner = ' ';
		int d = game.getDimension();
		char[] line;
		int startNum;
		boolean flag = false;
		int winningStartPosition = 0;
		String winningDirection = "";
		//check each row, coloumn and diagonal if there is a place to win
		//if there is then flag = true
		for (int i=0; i<d; i++){
			startNum = i +1;
			line = rowList (game, startNum, d);
			if (checkWinMoveOrBlockMove(line)){
				flag = true;
				winningStartPosition = i;
				winningDirection = "row";
				}
			}
		//coloumn 
		for (int i=0; i<d; i++){
			startNum = i+1;
			line = coloumnList (game, startNum, d);
			if (checkWinMoveOrBlockMove(line)){
				flag = true;
				winningStartPosition = i;
				winningDirection = "coloumn";
				}
			}
		//diagonal down right
		line = diagonalUpList (game, d);
		if (checkWinMoveOrBlockMove(line)){
			flag = true;
			winningStartPosition = d*d-d;
			winningDirection = "diagU";
			}
		//diagonal up right
		line = diagonalDownList(game, d);
		if (checkWinMoveOrBlockMove(line)){
			flag = true;
			winningStartPosition = 0;
			winningDirection = "diagD";
			} 
		//if the flag is true then there is a line full of one colour except for one blank
		char value = ' ';
		int i;
		if (flag){
			//check if that line is this players symbol
			// if it is then return the position of the space
			if (winningDirection.equals("row")){
				for (i=d*winningStartPosition ; i<d*winningStartPosition+d ; i++){
					value = game.getAtPosition(i);
					if (value=='\0' && game.getAtPosition(i+1)==plyr){
						return i;
					}
				}
			}
			if (winningDirection.equals("coloumn")){
				for (i=winningStartPosition ; i<d*d ; i+=d){
					value = game.getAtPosition(i);
					if (value=='\0'&& game.getAtPosition(i+1)==plyr){
						return i;
					}
				}
			}
			if (winningDirection.equals("diagU")){
				for (i=winningStartPosition ; i<d*d ; i-=d-1){
					value = game.getAtPosition(i);
					if (value=='\0'&& game.getAtPosition(i+1)==plyr){
						return i;
					}
				}
			}
			if (winningDirection.equals("diagD")){
				for (i=winningStartPosition ; i<d*d ; i+=d+1){
					value = game.getAtPosition(i);
					if (value=='\0'&& game.getAtPosition(i+1)==plyr){
						return i;
					}
				}
			}
		}
		//there is not a winning move then 
		return -1;
	}

	/**
	* Finds a blocking move if possible for this player
	* @param game is an instance of a tic-tac-toe game
	* @return A position in the board [0,D-1] that is a valid bocking 
	* move for this player to make, where D = dimension*dimension.
	* If there are multiple blocking moves then any is acceptable.
	* Returns -1 if there is no blocking move for this player.
	*/
	public int findBlockingMove(TicTacToeGame game){
		char winner = ' ';
		int d = game.getDimension();
		char[] line;
		int startNum;
		boolean flag = false;
		int winningStartPosition = 0;
		String winningDirection = "";
		//check each row, coloumn and diagonal if there is a place to block
		//if there is then flag = true
		for (int i=0; i<d; i++){
			startNum = i+1;
			line = rowList (game, startNum, d);
			if (checkWinMoveOrBlockMove(line)){
				flag = true;
				winningStartPosition = i;
				winningDirection = "row";
				}
			}
		//coloumn 
		for (int i=0; i<d; i++){
			startNum = i+1;
			line = coloumnList (game, startNum, d);
			if (checkWinMoveOrBlockMove(line)){
				flag = true;
				winningStartPosition = i;
				winningDirection = "coloumn";
				}
			}
		//diagonal down right
		line = diagonalUpList (game, d);
		if (checkWinMoveOrBlockMove(line)){
			flag = true;
			winningStartPosition = d*d-d;
			winningDirection = "diagU";
			}
		//diagonal up right
		line = diagonalDownList(game, d);
		if (checkWinMoveOrBlockMove(line)){
			flag = true;
			winningStartPosition = 0;
			winningDirection = "diagD";
			} 
		//if the flag is true then there is a line full of one colour except for one blank
		char value = ' ';
		int i;
		if (flag){
			//check if that line is not this players symbol
			// return the position of the space if it is a blocking move
			if (winningDirection.equals("row")){
				for (i=d*winningStartPosition ; i<d*winningStartPosition+d ; i++){
					value = game.getAtPosition(i);
					if (value=='\0' && game.getAtPosition(i+1)!=plyr){
						return i;
					}
				}
			}
			if (winningDirection.equals("coloumn")){
				for (i=winningStartPosition ; i<d*d ; i+=d){
					value = game.getAtPosition(i);
					if (value=='\0'&& game.getAtPosition(i+1)!=plyr){
						return i;
					}
				}
			}
			if (winningDirection.equals("diagU")){
				for (i=winningStartPosition ; i<d*d ; i-=d-1){
					value = game.getAtPosition(i);
					if (value=='\0'&& game.getAtPosition(i+1)!=plyr){
						return i;
					}
				}
			}
			if (winningDirection.equals("diagD")){
				for (i=winningStartPosition ; i<d*d ; i+=d+1){
					value = game.getAtPosition(i);
					if (value=='\0'&& game.getAtPosition(i+1)!=plyr){
						return i;
					}
				}
			}
		}
		//if there is not a blocking move then 
		return -1;
	}

	/** 
	* Plays a move for this player in a game
	* @param game is a tic-tac-toe game that the player is playing.
	* @param pos is the position [-1,D-1] in the game that the player is playing a move,
	* where D=dimension*dimension.
	* @return Nothing. The function has the side effect of playing a move on the board, 
	* using this player's symbol (x or o) at the specified position. If the position 
	* is -1 then the function does nothing.
	*/
	public void play(TicTacToeGame game, int pos){
		// if the position is not -1 then play a move
		if (pos != -1){
			game.play(pos, this); 
		}
	}
	
	public static boolean checkEndGame(char[] line){
		//the parameter line is an array of one line (row/ coloumn/ diagonal) in the game board 
		//checkEndGame checks if the given line is a winner (return true if winner, false if not winner)
		//get the first value in the line
		char value = line[0];
		//if the value is a blank then that line cant be a winner 
		if (value=='\0'){
			return false;
		}
		//run through the rest of the array checking if the other elements are equal to the first
		//if its not equal to the first then return false (that line is not a winner)
		for (int i=1; i< line.length; i++){
			if (line[i] != value){
				return false;
			}
		}
		//else, all the charecters in that line are the same
		//return true because that line has a winner 
		return true;
	}
	
	public boolean checkWinMoveOrBlockMove(char[] line){
		//the parameter line is an array of one line (row/ coloumn/ diagonal) in the game board 
		//checkWinOrBlockMove checks if the given line has all of the same piece type (x/o) except for one blank
		//check how many empty charecters are in each line
		int numOfBlanks=0;
		char value;
		for (int i=0; i< line.length; i++ ){
			value = line[i];
			if (value =='\0'){
				numOfBlanks++ ;
			} 
		}	
		//if there is not exactly one blank space in that line
		//then that line cant be used to win or block
		if  (numOfBlanks !=1){
			return false;
		} 
		//get the first value in the line array
		value = line[0];
		int num=1;
		//if the first value in the line happens to be the one blank
		//then use the second value and increase the number to start 
		//looking through the line to be the third element
		if (value == '\0'){
			value = line[1];
			num = 2;
		}
		for (int i=num; i< line.length; i++){
			//if line at i is the blank then increase the counter to the next element
			if (line[i]=='\0'){
				i++;
			}
			//if the charecters are not the same (x!=o) then that line cant be used to win or block
			if (line[i] !=value){
				return false;
			} 
		} 
		//if its made it this far then the line can be used to block or win
		return true;
	}
	
	public static char [] rowList (TicTacToeGame game, int startNum, int d){
		//make an array of a row
		//start at the specified start number and end at the end of that row
		//add each charecter in that row to the line array and return line 
		char[] line;
		line = new char[d];
		int pos = (startNum-1) * d;
		for (int i=0; i<d; i++){
			line[i]= game.getAtPosition(pos);
			pos++;
			}
			return line;
		}
		
	public static char [] coloumnList (TicTacToeGame game, int startNum, int d){
		//make an array for each coloumn
		//start at the specified start number and end at the end of that coloumn
		//add each charecter in that coloumn to the line array and return line 
		char[] line;
		line = new char[d];
		int index=startNum-1;
		for (int i=0; i<d; i++){
			line[i]= game.getAtPosition(index);			
			index+=d;
			}
			return line;
		}
		
	public static char [] diagonalDownList (TicTacToeGame game, int d){		
		//make an array for diagonal down right 
		//add each charecter in the diagonal to the line array and return line 
		char[] line;
		line = new char[d];
		int increment=d+1;
		int index=0;
		for (int i=0; i<d; i++){
			line[i]= game.getAtPosition(index);
			index+=increment;
			}
			return line;
		}
		
	public static char [] diagonalUpList (TicTacToeGame game, int d){
		//make an array for diagonal up right 
		//add each charecter in the diagonal to the line array and return line 
		char[] line;
		line = new char[d];
		int increment=d-1;
		int index = d-1;
		for (int i=0; i<d; i++){
			line[i]= game.getAtPosition(index);
			index+=increment;
			}
		return line;
		}		
}