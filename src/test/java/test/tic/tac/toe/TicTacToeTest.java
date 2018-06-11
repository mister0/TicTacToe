package test.tic.tac.toe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import tic.tac.toe.TicTacToe;

public class TicTacToeTest extends BaseTest
{
	/**
	 * this acts as integration test in which we test (in each test case) 
	 * a simple but full flow of the game ...
	 * the whole (human players input) is provided from the beginning to an
	 * input stream to act as System.in ... 
	 * This is ugly as it is not easy to maintain and debug ... 
	 * can be written in a better way in which we create a (game flow) 
	 * object containing list of moves and expected output for each move ... 
	 * and then run this game flow thing !!!!
	 * 
	 * Note : this test also doesn't include any AI players ... 
	 */
	
	final String prefix = "src/test/java/test/resources/" ;
	final String simple_2_players = "config_simple_2_players.properties";
	
	public void feedStringToSystemIn(String s){
	    InputStream in = new ByteArrayInputStream(s.getBytes());
	    System.setIn(in);
	}
	
	@After
	public void restoreSystemIn() {
		System.setIn(System.in) ;
	}
	
	
    @Test
    public void testPlayer1WinsVertical() {
    	String[] args = {prefix+simple_2_players} ;
    	feedStringToSystemIn("1,1\n1,2\n2,1\n2,2\n3,1\n") ;
    	TicTacToe.main(args);
    	String output = outContent.toString() ;
    	String[] lines = output.split("\n");
    	Assert.assertEquals(lines[lines.length-1], "Player 1 wins !!!!");
    }

    @Test
    public void testPlayer1WinsDiagonal() {
    	String[] args = {prefix+simple_2_players} ;
    	feedStringToSystemIn("1,1\n1,2\n2,2\n2,1\n3,3\n") ;
    	TicTacToe.main(args);
    	String output = outContent.toString() ;
    	String[] lines = output.split("\n");
    	Assert.assertEquals(lines[lines.length-1], "Player 1 wins !!!!");
    }

    @Test
    public void testPlayer2WinsHorizontal() {
    	String[] args = {prefix+simple_2_players} ;
    	feedStringToSystemIn("1,1\n2,1\n1,2\n2,2\n3,3\n2,3\n") ;
    	TicTacToe.main(args);
    	String output = outContent.toString() ;
    	String[] lines = output.split("\n");
    	Assert.assertEquals(lines[lines.length-1], "Player 2 wins !!!!");
    }
    
    @Test
    public void testPlayer2WinsDiagonal() {
    	String[] args = {prefix+simple_2_players} ;
    	feedStringToSystemIn("1,1\n1,3\n1,2\n2,2\n3,3\n3,1\n") ;
    	TicTacToe.main(args);
    	String output = outContent.toString() ;
    	String[] lines = output.split("\n");
    	Assert.assertEquals(lines[lines.length-1], "Player 2 wins !!!!");
    }
    
    @Test
    public void testInvalidMove() {
    	String[] args = {prefix+simple_2_players} ;
    	feedStringToSystemIn("1-1\n1+1\n1,1\n1,2\n3$2\n2,1\n2,2\n5,5\n3,1\n") ;
    	TicTacToe.main(args);
    	String output = outContent.toString() ;
    	String[] lines = output.split("\n");
    	Assert.assertEquals(lines[lines.length-1], "Player 1 wins !!!!");    }
}
