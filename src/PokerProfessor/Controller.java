package PokerProfessor;

import java.util.Scanner;

/**
 * 
 * @author Jefe
 */
public class Controller {

	Player p;
	String playerName = "";
	String at="";
	Player[] contPlayers,viewPlayers;
	Scanner sc;
	Game game;
	private int numOponents,numPlayers,incMult;
	private long bigBlind;
	private long smallBlind;
	private boolean gameOn;
	
	public boolean isFlop,isTurn,isRiver,isFirstGame,isNewGame;

	public Controller() {
		//sc=new Scanner(System.in);
		getAndSetPlayers();
		getUserInfo();
		incMult=0;
		game=new Game(contPlayers,bigBlind,smallBlind);
		
	}

	public boolean isNumber(String s){
		try{Integer.parseInt(s);}
		catch(Exception e){return false;}
		return true;
	}

	public void getUserInfo() {
		/*System.out.println("So what is your name...");
		playerName = sc.nextLine();
		System.out.println("ok your name is " + playerName
				+ " and we are giving you 1000 chips to start off.");
		p = new Player(0, playerName, 1000);
		contPlayers[0] = p;*/
		
		playerName = "TESTER";
		p = new Player(0, playerName, 1000, true);
		contPlayers[0] = p;
		viewPlayers[0] = p;
		

	}

	public void getAndSetPlayers() {
		/*System.out
				.println("Welcome how many other players do you want? min 1 - max 8...");
		String s = "";

		
		boolean isNumber = false;
		while (!isNumber) {
			System.out.println("asking");
			s = sc.nextLine();
			if (s.equalsIgnoreCase("exit")) {
				System.out.println("pussy");
				System.exit(0);
			}
			if (isNumber(s)) {
				if (Integer.parseInt(s) >= 1 && Integer.parseInt(s) <= 8) {
					numOponents = Integer.parseInt(s);
					isNumber = true;
				} else {
					System.out
							.println("try again, remember, at least 1, at most 8...");
				}
			} else {
				System.out
						.println("That is not a valid number of players...try again or type 'exit' to exit.");
			}
		}*/
		//commented this out for developing, instead of asking every time, just set to 5 opponenets and your name is TESTER
		numOponents=5;
		numPlayers=numOponents+1;
		contPlayers=new Player[numPlayers];
		viewPlayers=new Player[numPlayers];
		for(int i=1;i<=numOponents;i++){
			String st;
			st="player"+i;
			p=new Player(i,st,1000, false);
			contPlayers[i]=p;
			viewPlayers[i]=p;
		}
	}

	public int getDealer() {
		return contPlayers[numPlayers-1].getID();
	}

	public void incDealer(){
		if(incMult==numPlayers)incMult=0;else incMult++;
		Player[]tPlayers=new Player[numPlayers];
		tPlayers[numPlayers-1]=contPlayers[0];
		for(int i=0;i<numPlayers-1;i++)tPlayers[i]=contPlayers[i+1];
		for(int i=0;i<numPlayers;i++)contPlayers[i]=tPlayers[i];
	}
	
	public Player getHumanPlayer(){
		while(true)for(Player p:contPlayers)if(p.getID()==0)return p;
	}
	
	public void turnGameOn(){gameOn=true;}
	
	public void turnGameOff(){gameOn=false;}

	public void go() {
		
		if(!isGameOn())
		{
			game.newGame(contPlayers,bigBlind,smallBlind);
			turnGameOn();
			game.startGame();
		}
		else if(game.gameOver){
			turnGameOff();
			incDealer();
		}
		else
		{
			game.contGame();
		}
	}

	public String showPlayers(){
		String allPlayers="";
		for(int i=0;i<=numOponents;i++)
			allPlayers+=contPlayers[i].toString()+"\n\n";
		return allPlayers;
	}
	
	public String getActivityText(){
		if(gameOn)at=game.getActivityText();
		else at="In Between Games";
		return at;
	}
	public int getIncMult(){return incMult;}
	public boolean isGameOn(){return gameOn;}
	public int getNumPlayers(){return numPlayers;}
	public long getBigBlind(){return bigBlind;}
	public long getSmallBlind(){return smallBlind;}
	public void setBigBlind(long bb){bigBlind=bb;}
	public void setSmallBlind(long sb){smallBlind=sb;}
	
}
