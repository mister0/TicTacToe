package tic.tac.toe;

import exceptions.InvalidConfigurationException;

public class TicTacToe 
{
	static String configFIlePath = "./src/main/java/resources/config.properties" ;
	
    public static void main( String[] args )
    {
    	if(args.length > 0 && args[0] != null){
    		configFIlePath = args[0] ;
    	}else{
            System.err.println(String.format("Please provide a valid configuration.properties file as the first argument "));
            return ;
    	}
    	
        System.out.println(String.format("Loading configuration file from %s", configFIlePath));
        try{
        	GameMaster gm = GameMaster.loadGameMaster(configFIlePath);
            while(!gm.isGameEnded()){
            	gm.playTheTurn() ;
            }
            System.out.println(gm.endGameMessage);
        }catch(InvalidConfigurationException ex){
        	System.out.println("Game ended with the following error : ");
        	System.out.println(ex.getMessage());
        }
    }
}