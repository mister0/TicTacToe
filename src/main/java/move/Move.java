package move;

import players.Player;

public class Move {

	int x;
	int y;
	Player player ;
	
	public Move(int x, int y, Player player){
		this.x = x ; this.y = y ; this.player = player ;
	}

	public int getX() {
		//this decrement because players use from 1 to 10 ... 
		// but the board is from 0 to 9 ... 
		return x-1;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		//this decrement because players use from 1 to 10 ... 
		// but the board is from 0 to 9 ... 
		return y-1;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
