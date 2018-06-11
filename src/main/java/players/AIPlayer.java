package players;

import java.util.Random;

import exceptions.GameException;
import move.Move;

public class AIPlayer extends Player {

	public AIPlayer(int num, char mark, PLAYERTYPE type) {
		super(num, mark, type);
	}

	@Override
	public void play() {
		// this is very dump AI ... it doesn't search with a strategy ...		
		// only trying (once) to generate a random move ... 
		// do it if it is available .... if it is not available ... 
		// searching the board for any available move ...

		Move move = null;
		try{
			move = getRandomMove() ;
		}catch(GameException gex){
			move = getAnyAvailableMove() ;
		}
		System.out.println("Computer is playing .... buahahahahahahaha ... ");
		System.out.println(String.format("Marking cell %s,%s ", move.getX(), move.getY()));
		gm.doMove(move);
	}

	public Move getAnyAvailableMove() {
		for (int i = 0; i < gm.getBoard().getSize() ; i++) {
			for (int j = 0; j < gm.getBoard().getSize() ; j++) {
				if (gm.checkCellAvailability(i, j)) {
					return new Move(i+1, j+1, this);
				}
			}
		}
		return null;
	}

	public Move getRandomMove() throws GameException {
		Random random = new Random();
		int x = random.nextInt(gm.getBoard().getSize()) + 1;
		gm.validateInRange(x, 1, gm.getBoard().getSize()) ;
		int y = random.nextInt(gm.getBoard().getSize()) + 1;
		gm.validateInRange(y, 1, gm.getBoard().getSize()) ;
		Move move = new Move(x, y, this);
		gm.checkMoveAvailability(move);
		return move;
	}
}