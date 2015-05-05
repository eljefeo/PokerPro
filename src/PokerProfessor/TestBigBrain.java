package PokerProfessor;

import java.util.ArrayList;

public class TestBigBrain {
	
	BigBrain bigBrain = new BigBrain();
	Object[][]pP=new Object[2][7];
	
	
	
	public TestBigBrain(){
		pP[0]=new Object[]{"5.c","6.d","8.c","9.c","7.c","12.h","3.d"};
		pP[1]=new Object[]{"14.s","2.d","3.s","4.s","5.s","9.h","7.d"};
		

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
