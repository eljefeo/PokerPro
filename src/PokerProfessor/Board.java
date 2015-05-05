package PokerProfessor;

public class Board {
	
	String[] cards;
	
	public Board(){
		//cards = new String[]{"back","back","back","back","back"};
		cards = new String[5];
	}
	
	
	public void setFlop(String a, String b, String c){
		cards[0] = a;
		cards[1] = b;
		cards[2] = c;
	}
	
	public String[] getFlop(){
		return new String[]{cards[0], cards[1], cards[2]};
	}
	
	public void setTurn(String d){
		cards[3] = d;
	}
	
	public String getTurn (){
		return cards[3];
	}
	
	public void setRiver(String e){
		cards[4] = e;
	}
	
	public String getRiver(){
		return cards[4];
	}
	
	
	public String[] getBoard(){
		return cards;
	}
	
	public void resetBoard(){
		cards[0] = null;
		cards[1] = null;
		cards[2] = null;
		cards[3] = null;
		cards[4] = null;
	}



}
