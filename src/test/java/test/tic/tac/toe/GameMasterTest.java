package test.tic.tac.toe;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidConfigurationException;
import exceptions.InvalidInputException;
import move.Move;
import players.HumanPlayer;
import players.PLAYERTYPE;
import players.Player;
import tic.tac.toe.GameMaster;

public class GameMasterTest extends BaseTest {

	HashMap<Integer, Player> playersMap = new HashMap<Integer, Player>();

	final String prefix = "src/test/java/test/resources/";
	final String correct_three_different_types_players = "config.properties";
	final String simple_2_players = "config_simple_2_players.properties";
	final String invalid_board = "invalid_config_board.properties";
	final String invalid_board_string = "invalid_config_board2.properties";
	final String invalid_number_of_players_excess = "invalid_config_more_number_of_players.properties";
	final String invalid_player_mark = "invalid_config_player_character.properties";
	final String invalid_player_type = "invalid_config_player_type.properties";
	final String invalid_player_details = "invalid_config_player_details.properties";

	@Before
	public void setUpPlayers() {
		this.playersMap.put(1, new HumanPlayer(1, 'X', PLAYERTYPE.HUMAN));
		this.playersMap.put(2, new HumanPlayer(2, 'O', PLAYERTYPE.HUMAN));
	}

	@Test
	public void testGMConstructor() {
		try {
			GameMaster gm = new GameMaster(3, this.playersMap, 2);
			Assert.assertEquals(gm.getBoard().getSize(), 3);
			Assert.assertEquals(gm.getPlayersMap().size(), 2);
			for (Map.Entry<Integer, Player> entry : gm.getPlayersMap().entrySet()) {
				Assert.assertEquals(entry.getValue().getGm(), gm);
			}
		} catch (InvalidConfigurationException e) {
			Assert.fail("Shouldn't throw an InvalidConfigurationException");
			e.printStackTrace();
		}
	}

	@Test
	public void testGMInvalidBoard() {
		try {
			GameMaster.loadGameMaster(prefix + invalid_board);
        	Assert.fail("Expected InvalidConfigurationException");
		} catch (InvalidConfigurationException e) {
			Assert.assertEquals(e.getMessage(), "Board size must be a number from 3 to 10");
		}
	}

	@Test
	public void testGMInvalidBoardString() {
		try {
			GameMaster.loadGameMaster(prefix + invalid_board_string);
        	Assert.fail("Expected InvalidConfigurationException");
		} catch (InvalidConfigurationException e) {
			Assert.assertEquals(e.getMessage(), "Invalid board size number : bla");
		}
	}

	@Test
	public void testGMInvalidMoreNumberOfPlayers() {
		try {
			GameMaster.loadGameMaster(prefix + invalid_number_of_players_excess);
        	Assert.fail("Expected InvalidConfigurationException");
		} catch (InvalidConfigurationException e) {
			Assert.assertEquals(e.getMessage(),
					"Number of players is larger than number of configured players in the file");
		}
	}

	@Test
	public void testGMInvalidfPlayerCharacter() {
		try {
			GameMaster.loadGameMaster(prefix + invalid_player_mark);
        	Assert.fail("Expected InvalidConfigurationException");
		} catch (InvalidConfigurationException e) {
			Assert.assertEquals(e.getMessage(),
					"Player mark must be one character !! player 1 has invalid mark : Xoxoxoxo");
		}
	}

	@Test
	public void testGMInvalidfPlayerType() {
		try {
			GameMaster.loadGameMaster(prefix + invalid_player_type);
        	Assert.fail("Expected InvalidConfigurationException");
		} catch (InvalidConfigurationException e) {
			Assert.assertEquals(e.getMessage(), "Player 1 has invalid type : HUMANOIDY. Player type must be HUMAN or AI ");
		}
	}

	@Test
	public void testGMInvalidfPlayerDetails() {
		try {
			GameMaster.loadGameMaster(prefix + invalid_player_details);
        	Assert.fail("Expected InvalidConfigurationException");
		} catch (InvalidConfigurationException e) {
			Assert.assertEquals(e.getMessage(), "Player details must be in the format MARK-TYPE");
		}
	}

	@Test
	public void testGMIncrementTurn() {
		try {
			GameMaster gm = GameMaster.loadGameMaster(prefix + correct_three_different_types_players);
			Move move = new Move(1, 1, this.playersMap.get(1));
			gm.doMove(move);
			Assert.assertEquals(gm.getHistoryOfMoves().size(), 1);
			Assert.assertEquals(gm.getHistoryOfMoves().get(gm.getHistoryOfMoves().size() - 1), move);
		} catch (InvalidConfigurationException e) {
			Assert.fail("Shouldn't throw an InvalidConfigurationException");
			e.printStackTrace();
		}
	}

	@Test
	public void testGMisThereMoreAvailableMoves() {
		try {
			GameMaster gm = GameMaster.loadGameMaster(prefix + simple_2_players);
			Move move = new Move(1, 1, this.playersMap.get(1));
			gm.doMove(move);
			move = new Move(1, 2, this.playersMap.get(2));
			gm.doMove(move);
			move = new Move(1, 3, this.playersMap.get(1));
			gm.doMove(move);

			move = new Move(2, 1, this.playersMap.get(2));
			gm.doMove(move);
			move = new Move(2, 2, this.playersMap.get(1));
			gm.doMove(move);
			move = new Move(2, 3, this.playersMap.get(2));
			gm.doMove(move);

			move = new Move(3, 1, this.playersMap.get(1));
			gm.doMove(move);
			move = new Move(3, 2, this.playersMap.get(2));
			gm.doMove(move);
			Assert.assertTrue(gm.isThereMoreAvailableMoves());

			move = new Move(3, 3, this.playersMap.get(1));
			gm.doMove(move);
			Assert.assertFalse(gm.isThereMoreAvailableMoves());

		} catch (InvalidConfigurationException e) {
			Assert.fail("Shouldn't throw an InvalidConfigurationException");
			e.printStackTrace();
		}
	}

	@Test
	public void testGMValidateInRange() {
		try {
			GameMaster gm = GameMaster.loadGameMaster(prefix + correct_three_different_types_players);
			Assert.assertEquals(gm.validateInRange(4, 1, 10), 4);
			gm.validateInRange(1, 4, 9);
        	Assert.fail("Expected InvalidInputException");
		} catch (InvalidInputException e) {
			Assert.assertEquals(e.getMessage(), "Number 1 is out of range 4 and 9");
		} catch (InvalidConfigurationException e) {
			Assert.fail("Shouldn't throw an InvalidConfigurationException");
			e.printStackTrace();
		}
	}


	@Test
	public void testIsThereAWinner() {
		//TODO
	}
	
}
