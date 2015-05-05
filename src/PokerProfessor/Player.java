package PokerProfessor;

import java.util.ArrayList;

public class Player {

	private int id;
	//private Toast toast=new Toast();
	private String toastString="";
	private String name="";
	private long chips,playerBet;
	private String[]hand=new String[2];
	private int handCount=0;
	int position=0;
	String[]curMap;
	Player[]opps;
	Player[]raisedBy;
	int[] myRaises;
	int myPFlop=0;
	int myFlop=0;
	int myTurn=0;
	int myRiver=0;
	int choice = 0;
	ArrayList[]hasRaised;
	private boolean raised,checked,called,folded,overTheTop;
	boolean isDone;//isFolded;
	private boolean isWaiting=false,hasWaited=false;
	private boolean isToasting;
	private boolean human = false;
	private int waitTime=0;
	

	public Player(int id,String name,long chips, boolean human){
		this.id=id;
		this.name=name;
		this.setChips(chips);
		this.human = human;
		setPlayerBet(0);
		hand[0]=null;
		hand[1]=null;
		isDone=false;
		raised=false;
	}


	public void dealPlayer(String card){
		if (handCount==0){hand[0]=card;handCount=1;}
		else{hand[1]=card;handCount=0;}
	}

	public void clearHand() {
		hand[0] = null;
		hand[1] = null;
	}

	public void winMoney(long m){
		setChips(getChips() + m);
	}
	
	public void setPlayerBet(long playerBet){
		this.playerBet = playerBet;
	}
	public int getID(){return id;}

	public String getCard(int i){
		if(hand[i]!=null)
		return hand[i];
		else return "0.s";
	}
	
	public String[] getCards(){
		return hand;
	}
	
	public void setHasWaited(){hasWaited=true;}
	
	public void resetHasWaited(){hasWaited=false;}
	
	public boolean hasWaited(){return hasWaited;}
	
	public void startWaiting(){isWaiting=true;}
	
	public boolean isWaiting(){return isWaiting;}
	
	public void setWaitTime(int a){waitTime=a;}
	
	public int getWaitTime(){return waitTime;}
	
	public void decWaitTime(){waitTime--;}
	
	public void resetWaiting(){isWaiting=false;waitTime=0;}
	
	public void setToastString(int v){
		isToasting=true;
		if(v==1)toastString="Call";
		if(v==2)toastString="Check";
		if(v==3)toastString="Raise";
		if(v==4)toastString="Fold";
	}
	
	public void setToastString(String c){
		isToasting=true;
		toastString=c;
	}

	public boolean isToasting(){
		return isToasting;
	}
	
	public void resetToasting(){
		isToasting=false;
	}
	
	public String getToastString(){
		return toastString;
	}
	
	public void resetToastString(){
		toastString="";
	}
	
	public String getName(){
		return name;
	}

	public long getBet(){
		return getPlayerBet();
	}
	public long getChips(){
		return chips;
	}
	
	public void checked(){
		checked=true;
		raised=false;
		called=false;
		folded=false;
	}
	
	public boolean hasChecked(){
		return checked;
	}
	
	public void called(){
		called=true;
		checked=false;
		raised=false;
		folded=false;
	}
	
	public boolean hasCalled(){
		return called;
	}
	
	public void folded(){
		folded=true;
		raised=false;
		checked=false;
		called=false;
	}
	
	public boolean hasFolded(){
		return folded;
	}
	public void raised(){
		raised=true;
		called=false;
		checked=false;
		folded=false;
	}
	
	public boolean hasRaised(){
		return raised;
	}
	
	
	public void resetRaise(){
		raised=false;
	}
	public void resetPlayerBet(){
		setPlayerBet(0);
	}
	
	
	private void resetChoice(){
		checked=false;
		called=false;
		raised=false;
		folded=false;
	}
	public void resetPlayer(){
		resetToastString();
		resetToasting();
		resetWaiting();
		resetRaise();
		resetHasWaited();
		resetPlayerBet();
		resetChoice();
	}
	public String toString() {
		String s = "";
		s = "Player: " + name + " : Chip Count - " + getChips() + " : Position - "
				+ position + " : " + hand[0] + "," + hand[1];

		return s;
	}


	public void setChips(long chips) {
		this.chips = chips;
	}


	public long getPlayerBet() {
		return playerBet;
	}
	
	public boolean isHuman(){
		return human;
	}
	
	public void check(){
		choice = 0;
	}
	
	public void call(){
		choice = 1;
	}
	
	public void bet(){
		choice = 2;
	}
	
	public void fold(){
		choice = 3;
	}
	
	public int getChoice(){
		return choice;
	}
}
