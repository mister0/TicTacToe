package tic.tac.toe;

import java.util.Arrays;

import exceptions.InvalidConfigurationException;
import exceptions.NotAvailableMoveException;
import move.Move;

public class Board {

	char[][] board;
	int size = -1;
	int minBoardSize = 3;
	int maxBoardSize = 10;
	int numberOfAvailableCells = -1;

	public Board(int size) throws InvalidConfigurationException {

		if (size < minBoardSize || size > maxBoardSize) {
			throw new InvalidConfigurationException("Board size must be a number from 3 to 10");
		}

		this.size = size;
		this.board = new char[size][size];
		this.numberOfAvailableCells = size * size;

		// initialize the board with '.' character
		for (int i = 0; i < this.size; i++) {
			Arrays.fill(board[i], '.');
		}
		System.out.println(String.format("Board loaded ... size is %sx%s", size,size));
		printBoard();
	}

	public boolean checkCellAvailability(int x, int y) {
		if (board[x][y] == '.') {
			return true;
		}
		return false;
	}

	public void checkMoveAvailability(Move move) throws NotAvailableMoveException {
		if (!checkCellAvailability(move.getX(), move.getY())) {
			printBoard();
			throw new NotAvailableMoveException(
					String.format("The cell you want to mark is already marked with symbol ", board[move.getX()][move.getY()]));
		}
	}

	public void mark(Move move) {
		this.board[move.getX()][move.getY()] = move.getPlayer().getMark();
		this.numberOfAvailableCells--;
	}

	public boolean checkHorizontally(Move move, int winningInRow) {
		char playerMark = move.getPlayer().getMark();
		int counter = 0;
		// get all forward ...
		for (int i = move.getX(); i < this.size; i++) {
			if (board[i][move.getY()] == playerMark) {
				counter++;
			} else {
				break;
			}

			if (counter == winningInRow) {
				return true;
			}
		}

		// then all backward ...
		for (int i = move.getX() - 1; i > -1; i--) {
			if (board[i][move.getY()] == playerMark) {
				counter++;
			} else {
				break;
			}

			if (counter == winningInRow) {
				return true;
			}
		}
		return false;
	}

	public boolean checkVertically(Move move, int winningInRow) {
		char playerMark = move.getPlayer().getMark();
		int counter = 0;
		// get all downwards ...
		for (int j = move.getY(); j < this.board[0].length; j++) {
			if (board[move.getX()][j] == playerMark) {
				counter++;
			} else {
				break;
			}

			if (counter == winningInRow) {
				return true;
			}
		}

		// then all upwards ...
		for (int j = move.getY() - 1; j > -1; j--) {
			if (board[move.getX()][j] == playerMark) {
				counter++;
			} else {
				break;
			}

			if (counter == winningInRow) {
				return true;
			}
		}
		return false;
	}

	public boolean checkDiagonallyRight(Move move, int winningInRow) {

		// check diagonal /
		int counter = 1;
		char playerMark = move.getPlayer().getMark();

		for (int i = 1; i < this.size; i++) {
			if (move.getX()+i < this.size && move.getY()-i > -1 && board[move.getX()+i][move.getY()-i] == playerMark) {
				counter++;
			} else {
				break;
			}
		}

		for (int i = 1; i < this.size; i++) {
			if (move.getX()-i > -1 && move.getY()+i < this.size && board[move.getX()-i][move.getY()+i] == playerMark) {
				counter++;
			} else {
				break;
			}
		}

		if (counter == winningInRow) {
			return true;
		}

		return false;
	}

	public boolean checkDiagonallyLeft(Move move, int winningInRow) {

		// check diagonal \
		int counter = 1;
		char playerMark = move.getPlayer().getMark();
		
		for (int i = 1; i < this.size; i++) {
			if (move.getX()+i < this.size && move.getY()+i < this.size && board[move.getX()+i][move.getY()+i] == playerMark) {
				counter++;
			} else {
				break;
			}
		}

		for (int i = 1; i < this.size; i++) {
			if (move.getX()-i > -1 && move.getY()-i > -1 && board[move.getX()-i][move.getY()-i] == playerMark) {
				counter++;
			} else {
				break;
			}
		}

		if (counter == winningInRow) {
			return true;
		}

		return false;
	}

	public void printBoard() {
		for (char[] array : this.board) {
			System.out.println(new String(array));
		}
	}

	
	// Getters and setters
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMinBoardSize() {
		return minBoardSize;
	}

	public void setMinBoardSize(int minBoardSize) {
		this.minBoardSize = minBoardSize;
	}

	public int getMaxBoardSize() {
		return maxBoardSize;
	}

	public void setMaxBoardSize(int maxBoardSize) {
		this.maxBoardSize = maxBoardSize;
	}

	public int getNumberOfAvailableCells() {
		return numberOfAvailableCells;
	}

	public void setNumberOfAvailableCells(int numberOfAvailableCells) {
		this.numberOfAvailableCells = numberOfAvailableCells;
	}

	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

}