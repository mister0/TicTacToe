package test.tic.tac.toe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidConfigurationException;
import move.Move;
import players.HumanPlayer;
import players.PLAYERTYPE;
import tic.tac.toe.Board;


public class BoardTest extends BaseTest{
	  
	HumanPlayer player = null ;
	
	@Before
	public void setUpPlayer() {
		player = new HumanPlayer(1, 'X', PLAYERTYPE.HUMAN) ;
	}
	
    @Test
    public void testInvalidBoardSize() {
    	try{
    		new Board(2) ;
        	Assert.fail("Expected InvalidConfigurationException");
    	}catch(InvalidConfigurationException ex){
    		Assert.assertEquals(ex.getMessage(), "Board size must be a number from 3 to 10");
    	}
    }
    
    @Test
    public void testValidBoardSize() throws InvalidConfigurationException {
    	new Board(5) ;
    	Assert.assertEquals("Board loaded ... size is 5x5\n.....\n.....\n.....\n.....\n.....\n", outContent.toString());
    }
    
    @Test
    public void testMark() throws InvalidConfigurationException {
    	Board b = new Board(3) ;
    	b.mark(new Move(2,2,this.player));
        Assert.assertTrue(b.getBoard()[1][1] == player.getMark());
    }
    
    @Test
    public void testCheckCellAvailability() throws InvalidConfigurationException {
    	Board b = new Board(3) ;    	
    	b.mark(new Move(2,2,this.player));
        Assert.assertTrue(b.checkCellAvailability(0, 0));
        Assert.assertFalse(b.checkCellAvailability(1, 1));
    }
    
    @Test
    public void testCheckHorizontally() throws InvalidConfigurationException {
    	Board b = new Board(8) ;
    	b.mark(new Move(1,2, this.player));
    	b.mark(new Move(3,2, this.player));
    	Move lastMove = new Move(2,2,this.player) ;
    	b.mark(lastMove);
        Assert.assertTrue(b.checkHorizontally(lastMove, 3));
    }
    
    @Test
    public void testCheckHorizontallyFalse() throws InvalidConfigurationException {
    	Board b = new Board(8) ;
    	b.mark(new Move(1,2, this.player));
    	b.mark(new Move(4,2, this.player));
    	Move lastMove = new Move(2,2,this.player) ;
    	b.mark(lastMove);
        Assert.assertFalse(b.checkHorizontally(lastMove, 3));
    }
    
    @Test
    public void testCheckVertically() throws InvalidConfigurationException {
    	Board b = new Board(7) ;    	
    	b.mark(new Move(4,3, this.player));
    	b.mark(new Move(4,5, this.player));
    	Move lastMove = new Move(4,4,this.player) ;
    	b.mark(lastMove);
        Assert.assertTrue(b.checkVertically(lastMove, 3));
    }

    @Test
    public void testCheckVerticallyFalse() throws InvalidConfigurationException {
    	Board b = new Board(7) ;    	
    	b.mark(new Move(4,3, this.player));
    	b.mark(new Move(4,5, this.player));
    	Move lastMove = new Move(4,6,this.player) ;
    	b.mark(lastMove);
        Assert.assertFalse(b.checkVertically(lastMove, 3));
    }
    
    @Test
    public void testCheckDiagonallyRight() throws InvalidConfigurationException {
    	
    	Board b = new Board(7) ;
    	
    	b.mark(new Move(3,4, this.player));
    	b.mark(new Move(5,2, this.player));
    	Move lastMove = new Move(4,3,this.player) ;
    	b.mark(lastMove);
    	
        Assert.assertTrue(b.checkDiagonallyRight(lastMove, 3));
    }
    
    
    @Test
    public void testCheckDiagonallyLeft() throws InvalidConfigurationException {
    	
    	Board b = new Board(7) ;
    	
    	b.mark(new Move(3,3, this.player));
    	b.mark(new Move(5,5, this.player));
    	Move lastMove = new Move(4,4,this.player) ;
    	b.mark(lastMove);
    	
        Assert.assertTrue(b.checkDiagonallyLeft(lastMove, 3));
    }
    
    @Test
    public void testPrintBoard() throws InvalidConfigurationException {
    	Board b = new Board(3) ;
    	outContent.reset();
    	
    	b.printBoard();
    	Assert.assertEquals("...\n...\n...\n", outContent.toString());
    	outContent.reset();
    	
    	b.mark(new Move(2,2,this.player));
    	b.printBoard();
    	Assert.assertEquals(String.format("...\n.%s.\n...\n", player.getMark()), outContent.toString());
    }
    
    @Test
    public void testAvailableCells() throws InvalidConfigurationException {
    	Board b = new Board(3) ;
    	outContent.reset();
    	
    	Assert.assertEquals(b.getNumberOfAvailableCells(), 9);    	
    	b.mark(new Move(2,2,this.player));
    	b.mark(new Move(3,3,this.player));
    	Assert.assertEquals(b.getNumberOfAvailableCells(), 7);
    }
}