package PokerProfessor;

public class Board {
	
	int[] cards;
	
	public Board(){
		//cards = new String[]{"back","back","back","back","back"};
		cards = new int[5];
	}
	
	
	public void setFlop(int i, int j, int k){
		cards[0] = i;
		cards[1] = j;
		cards[2] = k;
	}
	
	public int[] getFlop(){
		return new int[]{cards[0], cards[1], cards[2]};
	}
	
	public void setTurn(int d){
		cards[3] = d;
	}
	
	public int getTurn (){
		return cards[3];
	}
	
	public void setRiver(int e){
		cards[4] = e;
	}
	
	public int getRiver(){
		return cards[4];
	}
	
	
	public int[] getBoard(){
		return cards;
	}
	
	public void resetBoard(){
		cards[0] = 0;
		cards[1] = 0;
		cards[2] = 0;
		cards[3] = 0;
		cards[4] = 0;
	}



}
