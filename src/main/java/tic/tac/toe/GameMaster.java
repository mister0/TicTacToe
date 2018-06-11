package tic.tac.toe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import constants.Constants;
import exceptions.InvalidConfigurationException;
import exceptions.InvalidInputException;
import exceptions.NotAvailableMoveException;
import move.Move;
import players.AIPlayer;
import players.HumanPlayer;
import players.PLAYERTYPE;
import players.Player;

public class GameMaster {

	//this is the wining condition ... assumed to be 3 as default ... 
	//of course we can load it from the config file ...
	int winningInRow = 3 ;

	
	Board board = null;
	int numberOfPlayers = -1 ;
	int turn = 0 ;	
	boolean gameEnded = false ;
	String endGameMessage = "" ;
	HashMap<Integer, Player> playersMap = new HashMap<Integer, Player>();
	ArrayList<Move> historyOfMoves = new ArrayList<Move>() ;
    Scanner scanner = new Scanner(System.in);
	
	public GameMaster(int boardSize, HashMap<Integer, Player> playersMap, int numberOfPlayers) throws InvalidConfigurationException {
		this.board = new Board(boardSize) ;
		this.playersMap = playersMap ;
		this.numberOfPlayers = numberOfPlayers ;

		//give every player a reference to the current game master
		for(Map.Entry<Integer, Player> entry : playersMap.entrySet()){
			entry.getValue().setGm(this);
		}		
	}

	public static GameMaster loadGameMaster(String configFilePath) throws InvalidConfigurationException {

		HashMap<Integer, Player> map = new HashMap<Integer, Player>() ;
		HashMap<Character, Integer> marks = new HashMap<Character, Integer>() ;
		
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream(configFilePath);
			prop.load(input);
			
			String boardSize = prop.getProperty(Constants.PROPERTY_BOARD_SIZE);
			int boardSizeNumber = -1 ;
			try{
				boardSizeNumber = Integer.parseInt(boardSize) ;
			}catch(Exception ex){
				throw new InvalidConfigurationException(String.format("Invalid board size number : %s", boardSize)) ;
			}
			
			// this is not in the requirements but it makes our program more generic ... 
			String numberOfPLayers = prop.getProperty(Constants.NUMBER_OF_PLAYERS);			
			int numPLayers = -1 ;
			try{
				numPLayers = Integer.parseInt(numberOfPLayers) ;
			}catch(Exception ex){
				throw new InvalidConfigurationException(String.format("Invalid players number : %s", numberOfPLayers)) ;
			}
			System.out.println(String.format("number of players : %s", numPLayers));
			
			for(int i = 1 ; i <= numPLayers ; i++){
				parsePLayerDetails(prop, i, map, marks);
			}
			GameMaster gm = new GameMaster(boardSizeNumber, map, numPLayers);
			return gm;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private static void parsePLayerDetails(Properties prop , int i,HashMap<Integer, Player> map, HashMap<Character, Integer> marks) throws InvalidConfigurationException{
		
		String playerString = null ;
		String playerMarkString = null ;
		char playerMark  ;
		playerString = prop.getProperty(i+"");
		if(playerString == null){
			throw new InvalidConfigurationException("Number of players is larger than number of configured players in the file") ;
		}
		
		String[] playerDetails = playerString.split("-") ;
		if(playerDetails.length != 2){
			throw new InvalidConfigurationException("Player details must be in the format MARK-TYPE") ;
		}
		
		playerMarkString = playerDetails[0] ;
		if(playerMarkString.length() > 1){
			throw new InvalidConfigurationException(String.format("Player mark must be one character !! player %s has invalid mark : %s",i ,playerMarkString)) ;
		}
		
		playerMark = playerMarkString.charAt(0) ;
		if(marks.containsKey(playerMark)){
			throw new InvalidConfigurationException(String.format("All players must have distinct marks !! player %s and player %s have the same mark : %s", i, marks.get(playerMark) , playerMark)) ;
		}
		marks.put(playerMark, i) ;
		
		String playerType = playerDetails[1];
		try {
			PLAYERTYPE.valueOf(playerType) ;
		}catch (IllegalArgumentException ex){
			// TODO player type should be dynamic in this string
			throw new InvalidConfigurationException(String.format("Player %s has invalid type : %s. Player type must be HUMAN or AI ", i,playerType)) ;
			
		}
		
		if(playerType.equals(PLAYERTYPE.AI.toString())){
			map.put(i, new AIPlayer(i, playerMark, PLAYERTYPE.AI)) ;
		}
		
		if(playerType.equals(PLAYERTYPE.HUMAN.toString())){
			map.put(i, new HumanPlayer(i, playerMark, PLAYERTYPE.HUMAN)) ;
		}
		System.out.println(String.format("Player number %s was loaded with mark : %s and type : %s", i ,playerMarkString, playerType));
	}
	
	public void playTheTurn(){
		int playerNumber = this.turn + 1 ;
		Player player = this.playersMap.get(playerNumber) ;
		player.play();
		this.board.printBoard();
		isThereAWinner();
		isThereMoreAvailableMoves();		
		incrementTurn();
	}
	
	private void incrementTurn(){
		this.turn++ ;
		this.turn = turn%numberOfPlayers ;
	}
	
	public void doMove(Move move){
		this.board.mark(move);
		historyOfMoves.add(move);
	}	

	public boolean isThereMoreAvailableMoves(){
		if(this.board.getNumberOfAvailableCells() == 0){
			this.gameEnded = true ;
			this.endGameMessage = "There is no more available moves !!!! GAMEOVER !!! No winner !!!" ;
			return false ;
		}
		return true ;
	}

	
	private void isThereAWinner(){
		if(historyOfMoves.size() > numberOfPlayers*(winningInRow-1)){
			Move lastMove = historyOfMoves.get(historyOfMoves.size()-1) ;
			if(this.board.checkHorizontally(lastMove, winningInRow) || this.board.checkVertically(lastMove, winningInRow) 
					|| this.board.checkDiagonallyLeft(lastMove, winningInRow) || this.board.checkDiagonallyRight(lastMove, winningInRow)){
				this.gameEnded = true ;
				this.endGameMessage = String.format("Player %s wins !!!!", lastMove.getPlayer().getPlayerNumber()) ;
			}
		}
	}
	
	public int validateInRange(int number,int rangeBegining, int rangeEnd) throws InvalidInputException{
		if(rangeBegining <= number && number <= rangeEnd){
			return number ;
		}else{
			throw new InvalidInputException(String.format("Number %s is out of range %s and %s", number, rangeBegining, rangeEnd+"")) ;
		}
	}

	public void checkMoveAvailability(Move move) throws NotAvailableMoveException{
		this.board.checkMoveAvailability(move);
	}
	
	public boolean checkCellAvailability(int x, int y) {
		return this.board.checkCellAvailability(x, y) ;
	}
	
	// getters and setters
	public boolean isGameEnded() {
		return gameEnded;
	}

	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public HashMap<Integer, Player> getPlayersMap() {
		return playersMap;
	}

	public void setPlayersMap(HashMap<Integer, Player> playersMap) {
		this.playersMap = playersMap;
	}

	public ArrayList<Move> getHistoryOfMoves() {
		return historyOfMoves;
	}

	public void setHistoryOfMoves(ArrayList<Move> historyOfMoves) {
		this.historyOfMoves = historyOfMoves;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

}