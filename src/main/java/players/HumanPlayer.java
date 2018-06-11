package players;

import exceptions.GameException;
import move.Move;

public class HumanPlayer extends Player{

	public HumanPlayer(int num, char mark, PLAYERTYPE type) {
		super(num, mark, type);
	}

	@Override
	public void play() {
		boolean isValidMove = false ;
        Move move = null ;
		while(!isValidMove){
			try{
				System.out.println(String.format("Player %s please enter a valid move : x,y", this.playerNumber));
				String input = this.gm.getScanner().next() ;
				move = validateMove(input) ;
				gm.checkMoveAvailability(move) ;
				isValidMove = true ;
			}catch(GameException gex){
				System.out.println(gex.getMessage());
			}
		}
		gm.doMove(move) ;
	}
}
