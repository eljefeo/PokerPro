package PokerProfessor;

import java.util.ArrayList;

public class TestBigBrain {
	
	BigBrain bigBrain = new BigBrain();
	int[][]pP=new int[9][7];
	
	
	
	public TestBigBrain(){
		pP[0]=new int[]{51,60,81,91,71,122,30};//single cards
		pP[1]=new int[]{143,20,33,141,53,92,70};//pairs
		pP[2]=new int[]{70,73,33,43,41,92,143};//2 pair
		pP[3]=new int[]{70,73,33,43,71,92,143};//trips
		pP[4]=new int[]{61,23,32,43,53,92,71};//straight
		pP[5]=new int[]{81,83,51,41,20,141,131};//flush
		pP[5]=new int[]{81,83,132,130,120,121,131};//fullhouse
		pP[5]=new int[]{122,123,132,130,120,121,131};//quads
		pP[5]=new int[]{91,111,132,130,101,121,131};//straight flush
		

	}
	
	public void goo(){
		ArrayList<Object[]>winners=bigBrain.whoWins(pP);
		
		for(int g=0;g<winners.size();g++){
			System.out.println("Winner is Player "+g//this will always show winner is player 0, deal with it
					+" with a "+winners.get(g)[6]);
			for(int i=1;i<6;i++)
			System.out.print(winners.get(g)[i]+", ");
			System.out.println();
			}
	}
	
}
