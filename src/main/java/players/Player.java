package players;

import exceptions.GameException;
import exceptions.InvalidInputException;
import move.Move;
import tic.tac.toe.GameMaster;

public abstract class Player {

	int playerNumber;
	char mark;
	PLAYERTYPE playerType ;
	GameMaster gm;
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public char getMark() {
		return mark;
	}

	public void setMark(char mark) {
		this.mark = mark;
	}

	public GameMaster getGm() {
		return gm;
	}

	public void setGm(GameMaster gm) {
		this.gm = gm;
	}


	public Player(int num, char mark, PLAYERTYPE type) {
		this.playerNumber = num;
		this.mark = mark;
		this.playerType = type;
	}

	public int validateIsNumber(String s) throws InvalidInputException {
		try {
			return Integer.parseInt(s);
		} catch (Exception ex) {
			throw new InvalidInputException("Input must be a valid number") ;
		}
	}

	public Move validateMove(String input) throws GameException {

		String[] array = input.split(",");
		if (array.length != 2) {
			throw new InvalidInputException(
					"A valid move should be in the format x,y ... please separate the 2 numbers with , and make sure they are only 2 numbers");
		} else {
			int x = validateIsNumber(array[0]);
			gm.validateInRange(x, 1, gm.getBoard().getSize());
			int y = validateIsNumber(array[1]);
			gm.validateInRange(y, 1, gm.getBoard().getSize());
			return new Move(x,y, this);
		}
	}

	public abstract void play();
	
}