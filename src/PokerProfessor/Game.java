package PokerProfessor;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

public class Game{

	BigBrain bigBrain;
	long pot, currentBet,currentBetOrRaise,bigBlind,smallBlind;
	int gameState = 0;
	Random rand=new Random();
	//String[]flop=new String[3];
	//String[]boardSeven=new String[7];
	Board board = new Board();
	//String turn,river,card,burn;
	String at="";
	Scanner sc=new Scanner(System.in);
	private int choice,action,gameStage;//, numPlayers;
	Deck deck;
	Player p;
	ArrayList<Player>players=new ArrayList<Player>();
	boolean notDone,handIsOver,midRoundOfBetting;
	boolean isDealt,isFlop,isTurn,isRiver,gameOver,everyoneFolded;//isUserFolded;
	Timer tm=new Timer(true);

	public Game(Player[]players,long bigBlind,long smallBlind){
		System.out.println("NEW Game!!!!!!!!!!!==");
		bigBrain=new BigBrain();
		deck=new Deck();
		deck.shuffleDeck();
	}
	
	public void startGame(){contGame();}
	
	public void deal(){
		for(int i=0;i<2;i++)
		for(Player p:players){p.dealPlayer((String)deck.getCard());}
		gameState=0;isDealt=true;
	}
	
	public void flop(){deck.getCard();board.setFlop(deck.getCard(), deck.getCard(), deck.getCard());incGameState();}
	public void turn(){deck.getCard();board.setTurn(deck.getCard());incGameState();}
	public void river(){deck.getCard();board.setRiver(deck.getCard());incGameState();}
	public void doBlinds(Player p,long bet){
		
		//this next line would find how many digits in pot value
		//for proper display formatting of pot amount
		//char[]xx=new char[(int)Math.log10(pot)+1];
		//so when its displayed on screen the numbers dont change position for increase digits
		p.setPlayerBet(bet);p.setChips(p.getChips() - bet);
		pot+=bet;currentBet=bet;incAction();
	}
	
	private void resetCurrentBet(){currentBet=0;}
	private void resetAction(){action=0;}
	private void resetPRoundOfBetting(){notDone=true;for(Player p:players){p.resetPlayer();p.isDone=false;}}
	private void resetPot(){pot=0;}
	
	public void betOrRaise(Player player,long betOrRaise){
		player.setToastString("Raise");
		player.raised();
		for(Player p2:players)p2.isDone=false;
		/*for(int a=0;a<players.size();a++)
			if(a==player)players.get(a).isDone=true;
			else players.get(a).isDone = false;*/
		//p = players.get(player);
		p.raised();//signal player has raised
		player.isDone=true;
		player.setChips(player.getChips() - (betOrRaise-player.getPlayerBet()));
		pot+=(betOrRaise-player.getPlayerBet());
		currentBet=betOrRaise;
		player.setPlayerBet(betOrRaise);
		incAction();
	}
	
	public void fold(Player player){
		player.folded();
		player.resetToasting();
		player.isDone=true;
		//player.isFolded=true;
		players.remove(player);
		//if(player.getID()==0)isUserFolded=true;
		if(action==players.size())action=0;
		if(players.size()==1)everyoneFolded=true;
	}
	public void call(Player player){
		player.called();
		player.setToastString("Call");
		player.isDone=true;
		player.setChips(player.getChips() - (currentBet-player.getPlayerBet()));
		// increase the pot by the call amount
		pot+=(currentBet-player.getPlayerBet());
		//show the players call is now good to go
		player.setPlayerBet(currentBet);
		incAction();
	}
	public void check(Player player){
		player.checked();
		player.setToastString("Check");
		player.isDone=true;incAction();
		}

	private boolean allPlayersAreDone(){
		int v=0;
		for(Player p:players){
			//uncomment me for developing System.out.println(p.name+" is done?>>" + p.isDone);
			if(p.isDone)v++;}
		if(v==players.size()){System.out.println("returning true" + p.isDone);return true;}
		else return false;
	}
	
	public void roundOfBetting(){
		//uncomment me for developing System.out.println("doing round");

				p=players.get(action);
				//uncomment me for developing System.out.println("\nAction is at "+action+":::"+players.size());
				//uncomment me for developing System.out.println("\n\ndoing round of betting --- doin "+p.getName());
			
				if(p.isHuman()&&!p.hasFolded()){
					//uncomment me for developing System.out.println("Human's turn");
					if(!p.isDone){
						//uncomment me for developing System.out.println("you are not done, breaking for you");
						at="Your Turn, current bet is "+currentBet;
					}
					else{System.out.println("player is done");}//incAction();}
				}
				
				else if(!p.isDone)
				{
					if(!p.isDoneThinking()){
						if(!p.isThinking()){
							p.startThinking();
							System.out.println(p.getName()+" started thinking");
							int aa=rand.nextInt(5);if(aa==0)aa=1;
							p.setThinkingTime(aa); 
							System.out.println(p.getThinkingTime()+" wait time");
							return;
						}
						else{if(p.getThinkingTime()>0){
							System.out.println(p.getName()+" is thinking");
							p.think();
							return;
						}else{
							System.out.println(p.getName()+" done thinking");
							p.resetIsThinking();p.resetIsDoneThinking();
							}
						}
					}
					at=players.get(action).getName()+" 's turn";
					//uncomment me for developing System.out.println("this guy is not done: "+p.getName());
				//giveOptions(p);
					doCompDecision(p);
					}
				else if(p.isDone){System.out.println("this guy IS done");incAction();}
		
				//uncomment me for developing System.out.println("\nAfterwards Action is at "+action);
				if(allPlayersAreDone()){System.out.println("all are done looks like, setting notDone to false");notDone=false;/*break x;*/}	
		
	}

	
	public void contGame(){
		if(everyoneFolded)gameStage=5;
			if(gameStage==0){ //pre-flop
				if(!allPlayersAreDone())roundOfBetting();
				else{
					gameStage++;
					System.out.println("FLOPPP!!!!");
					flop();
					resetCurrentBet();
					resetAction();
					resetPRoundOfBetting();
					isFlop=true;
				}	
			}
			if(gameStage==1){  //flop
				if(!allPlayersAreDone())roundOfBetting();
				else{
					gameStage++;
					System.out.println("TURNNN!!!!");
					turn();
					resetCurrentBet();
					resetAction();
					resetPRoundOfBetting();
					isTurn=true;
				}
			}
			if(gameStage==2){  //turn
				if(!allPlayersAreDone())roundOfBetting();
				else{
					gameStage++;
					System.out.println("RIVVVERRRRR!!!!");
					river();
					resetCurrentBet();
					resetAction();
					resetPRoundOfBetting();
					isRiver=true;
				}
			}
			if(gameStage==3){  //river
				if(!allPlayersAreDone())roundOfBetting();
				else gameStage++;
			}
			if(gameStage==4){  //end
				showDown();
				endGame();	
				gameOver=true;
			}
			if(gameStage==5){//everyone folded
				players.get(0).winMoney(pot);
				System.out.println("Everyone fOlded trying to end");
				endGame();
				handIsOver=true;
				gameOver=true;
				//everyone else folded, give last player the pot, end game
			}
	}
	
	public void userAction(int ua){
		Player p=players.get(getHumanPosition());
		if(ua==1)
			if(p.getBet()<currentBet){at="you must call "+currentBet;return;}
			else check(p);
		if(ua==2)
			if(currentBet>0&&p.getBet()-currentBet<0&&currentBet<=p.getChips())call(p);
			else {at="There is nothing to call";return;}
		//should be disabling buttons that shouldn't be pressed
		if(ua==3){betOrRaise(p,10);at="you raised";}//todo: check if enough chips, get how much to raise
		if(ua==4){fold(p);at="you folded";}
	}
	
	public void doCompDecision(Player player){
		choice=getChoiceBigBrain(player);
		//player.setToastString(choice);
		switch(choice){
		case 1://call
			if (p.getPlayerBet()<currentBet) {
				if((currentBet-p.getPlayerBet())<=p.getChips()){
					call(player);
					//System.out.println(player.getName()+" Called");
				} else {
					System.out.println("Sorry, you dont have enough to call");
					break;
				}
			} else {
				System.out.println("holy crap what the heck how did we get here...");
				//computer should never call if his player bet is not less than the current bet
				// else there is no bet to call, the players current bet is good
				//System.out.println("There is nothing for you to call.");
				//giveOptions(player);
				check(player);//doCompDecision(player);
				//Problem area, i put check here because if computer wants to call and there is nothing to call then just check for him, but should never get here,
				//This means there is a probelm with big brain or something making the wrong choice based on info passed into him
				
				break;
			}
			System.out.println("after call, current pot is " + pot);
			break;
		case 2://check
			check(player);
			System.out.println(p.getName()+" Checked");
			break;
		case 3://bet or raise
			betOrRaise(player, bigBrain.getAmountToBetOrRaise());
			System.out.println("Player "+p.getName()+" bet/raised");
			System.out.println("after bet/raise, current pot is "+pot);
			break;
		// fold
		case 4:
			System.out.println(p.getName() + " Folded");
			System.out.println("Before fold, current pot is " + pot);
			fold(player);
			break;
		default:
			break;
		}
	}

	public void showDown() {
		// compare hands
		at="Time for showdown !";
		for(Player g:players)
			System.out.println(g.toString());
		
		String[] tableCards = board.getBoard();
		Object[][]pP=new Object[players.size()][7];
		for(int i=0;i<players.size();i++){
			pP[i][0]=players.get(i).getCard(0);
			pP[i][1]=players.get(i).getCard(1);
			pP[i][2]=tableCards[0];
			pP[i][3]=tableCards[1];
			pP[i][4]=tableCards[2];
			pP[i][5]=tableCards[3];
			pP[i][6]=tableCards[4];
		}
		ArrayList<Object[]>winners=bigBrain.whoWins(pP);
		
		for(int g=0;g<winners.size();g++){
			System.out.println("Winner is Player "+players.get((int)winners.get(g)[0]).getName()
					+" with a "+winners.get(g)[6]);
			for(int i=1;i<6;i++)
			System.out.print(winners.get(g)[i]+", ");
			System.out.println();
			}
		
		long moneyForWinners=pot/winners.size();
		for(int j=0;j<winners.size();j++){
			System.out.println("chip count before of winner "+j+" is "+players.get((int)winners.get(j)[0]).getChips());//prints chips before win
			players.get((int)winners.get(j)[0]).winMoney(moneyForWinners);
			System.out.println("chip count after of winner "+j+" is "+players.get((int)winners.get(j)[0]).getChips());//prints chips after win
		}
	}

	
	public void endGame() {
		deck.resetDeckCount();
		for(Player i:players){i.setPlayerBet(0);i.clearHand();}
		deck.resetDeckCount();
	}


	private int getChoiceBigBrain(Player player) {
		int pos=0;
		for(int i=0;i<players.size();i++)
			if(players.get(i)==player)pos=i;
		return bigBrain
				.makeDecisionBasedOnAwesomeMathStuff(player,players,board.getBoard(),gameStage,pos,currentBet,
						bigBlind,smallBlind);
	}
	
	//private long getAmountToBetOrRaise(){return bigBrain.getAmountToBetOrRaise();}
	public int getHumanPosition(){while(true){for(int i=0;i<players.size();i++)if(players.get(i).getID()==0)return i;}}
	public int getWhoeversTurn(){return players.get(action).getID();}
	public boolean isHumansTurn(){return getCurrentPlayer().isHuman();}
	public String[]getFlop(){ String[] tempFlop = board.getFlop(); if(tempFlop!=null)return tempFlop; return new String[]{"back","back","back"};}
	public String getTurn(){String tempTurn = board.getTurn(); if(tempTurn!=null)return tempTurn;return"back";}
	public String getRiver(){String tempRiver = board.getRiver(); if(tempRiver!=null)return tempRiver;return"back";}
	private void incAction(){if(action<players.size()-1)action++;else action=0;}
	public boolean isGameOver(){at="Game is Over";return gameOver;}
	public long getPot(){return pot;}
	public char[]getPotSplit(){return String.valueOf(pot).toCharArray();}
	public String getActivityText(){return at;}
	public int getAction(){return action;}
	
	public void newGame(Player[] pla, long bigBlind,long smallBlind)
	{
			System.out.println("NEW GAME!!!!!!!!!!!=======================================");
		
		isDealt=false;
		isFlop=false;
		isTurn=false;
		isRiver=false;
		everyoneFolded=false;
		players.clear();
		for(Player pl:pla)
		{
			System.out.println(pl.getName()+":::id::"+pl.getID());
			players.add(pl);
		}
		board.resetBoard();
		getHumanPosition();
		deck.shuffleDeck();
		deal();
		resetAction();
		resetCurrentBet();
		resetPRoundOfBetting();
		resetPot();
		doBlinds(players.get(0),smallBlind);
		doBlinds(players.get(1),bigBlind);
		currentBet=bigBlind;
		gameStage=0;
		resetGameState();
		gameOver=false;
	}

	public Player getCurrentPlayer() {
		return players.get(action);
	}
	
	public void incGameState(){
		gameState++;
	}
	
	public void resetGameState(){
		gameState = 0;
	}
	
	public int getGameState(){
		return gameState;
	}

	public String[] getBoard() {
		return board.getBoard();
	}


	


}