package PokerProfessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import util.SuperSort;

public class BigBrain{
	private int rs,cs,chs,fs;
	//private int answer=0;
	private long atbor=0;
	private int com=0;
	private PreFlopStrat pf=new PreFlopStrat();
	//private final String[][]pF=new String[169][2];
	private final String[] handNames = new String[]
			{"High Card","Pair","Two Pair","Trips",
			"Straight","Flush","Full House",
			"Quads","Straight Flush"};

	int[]cards=new int[7];
	int[]suits=new int[7];

	
	
	
	/*
	 * 
	 * 1 = call
	 * 2 = check
	 * 3 = raise/bet
	 * 4 = fold
	 * 
	 */
	public void inc(int a){}
	public int makeDecisionBasedOnAwesomeMathStuff(Player p,ArrayList<Player> players,int[] is,int gameStage,
			int position,long currentBet,long bigBlind,long smallBlind){
		rs=0;cs=0;chs=0;fs=0;
		Random r=new Random();int a=r.nextInt(10);atbor=10;//change amount to bet or raise(atbor), to zero here
		int s=0;
		
		for(Player pp:players)inc(pp.hasCalled()?cs++:pp.hasChecked()?chs++:pp.hasRaised()?rs++:pp.hasFolded()?fs++:0);
		//inc(pp.hasCalled()?cs:pp.hasChecked()?chs:pp.hasRaised()?rs:pp.hasFolded()?fs:0);
		System.out.println("raisers = "+rs);
		/*
		 * 
		 *for preflop if hand<50 fold
		 *if no raisers and hand>120 raise
		 *if one raiser and hand>140  call else fold
		 * else check or call
		 * 
		 */

		int b=gameStage;
		if(b==0)
		{
			s=getPreFlopVal(p);
			System.out.println("preflop hand rank : "+s);
			return s<80?4:rs==0&&s>120?3:rs==1&&!p.hasRaised()?s>140?1:4:currentBet-p.getBet()==0?2:1;
		}
		else s=b==1?flop(is, p):b==2?turn(is, p):river(is, p);//get strength for other game stages
		System.out.println("Preflop hand rank(higher=better) : "+s);
		return currentBet==p.getBet()?2:a>5?4:1;//2=check,4=fold randomly,1=call randomly
	}
	
	public int getPreFlopVal(Player p)
	{
		return pf.getPreFlopScore(new int[]{p.getCard(0),p.getCard(1)});
	}
	public int flop(int[] is, Player p){//this will eventually have to take hand strength into account at different stages of the game
		return 0;
	}
	public int turn(int[] is, Player p){//this will eventually have to take hand strength into account at different stages of the game
		return 0;
	}
	public int river(int[] is, Player p){//this will eventually have to take hand strength into account at different stages of the game
		return 0;
	}

	public long getAmountToBetOrRaise() {
		return atbor;
	}

	public ArrayList<Object[]>whoWins(int[][]ps){
		ArrayList<int[]>allP=new ArrayList<>();
		ArrayList<Object[]>winning2=new ArrayList<>();
		
		List<Integer> winners = new ArrayList<Integer>();
		
		for(int i=0;i<ps.length;i++)
			allP.add(sptfq(ps[i]));
		
		//this can all be taken out later, just for showing on console
		for(int s=0;s<allP.size();s++){
			System.out.println("Seat "+s);
			for(int h=0;h<allP.get(s).length;h++)
			System.out.print(allP.get(s)[h]+", ");
			System.out.println(handNames[allP.get(s)[5]]);
			}
		
		int winner=0; // winner, just start at zero
		winners.add(winner);
		for(int i=1;i<allP.size();i++)
		{
			int cnt = 0;
			int[]x=allP.get(i);	//cycle through each player as x
			int[]w=allP.get(winner); // w is the winner
			if(x[5]>w[5]){winner=i;winners.clear();winners.add(i);} //if handID of x larger than handID of w, x becomes w
			else if(x[5]==w[5]) //if handIDs are equal, check cards
				for(int g=0;g<5;g++)
					if(x[g]>w[g]){winner=i;winners.clear();winners.add(i);break;} // if x's best card is better, x becomes w
					else if(x[g]<w[g]){break;}	//if w's best card is better, w stays winner
					else //else cards are the same so
						if(++cnt>4){winners.add(i);}//we increase a counter and if all 5 cards are the same, they both get put into winners circle
					
		}
		
		for(int i=0;i<winners.size();i++)
		{
			Object[]winning=new Object[7];
			winning[0]=winners.get(i);
			for(int a=1;a<6;a++)
				winning[a]=allP.get(winners.get(i))[a-1];
			winning[6]=handNames[allP.get(winners.get(i))[5]];
			winning2.add(winning);
		}

		if(winning2.size()>1)
			for(int i=1;i<winning2.size();i++)
			System.out.println("Split?? "+winning2.get(i));
		else System.out.println("Only one winner i guess ");
		return winning2;
	}
	
	//diamonds = 0, clubs = 1, hearts = 2, spades = 3
	@SuppressWarnings("unchecked")
	public int[]sptfq(int[]allz)
	{
		int[]r8k=new int[4];int[]la=new int[]{14,5,4,3,2};
		int[] si = new int[7];int[] pS = new int[3];
		int[] tR = new int[2];int qd = 0;
		
		f:for(int i =0;i< allz.length;i++){
			int num = allz[i]/10;//check for pairs, trips, quads
			for(int sint=0;sint<si.length;sint++)
				if(si[sint]==0){si[sint]=num;continue f;}
				else if(si[sint]==num)break;
			for(int pint=0;pint<pS.length;pint++)
				if(pS[pint]==0){pS[pint]=num;continue f;}
				else if(pS[pint]==num)break;
			for(int tint=0;tint<tR.length;tint++)
				if(tR[tint]==0){tR[tint]=num;continue f;}
				else if(tR[tint]==num)break;
			qd = num;
		}
		
		for(int i=0;i<si.length;i++)if(si[i]==0){int[]t=new int[i];
		for(int j=0;j<i;j++)t[j]=si[j];si=t;break;}SuperSort.sort(si);
		for(int i=0;i<pS.length;i++)if(pS[i]==0){int[]t=new int[i];
		for(int j=0;j<i;j++)t[j]=pS[j];pS=t;break;}SuperSort.sort(pS);
		for(int i=0;i<tR.length;i++)if(tR[i]==0){int[]t=new int[i];
		for(int j=0;j<i;j++)t[j]=tR[j];tR=t;break;}SuperSort.sort(tR);

		for(int i=0;i<allz.length;i++)r8k[allz[i]%10]++;//setup for flushes, how many of each suit
		//Flush and Straight Flush, if there is a flush out there, there cant be quads also, so we check these first
		for(int i=0;i<4;i++){
			if(r8k[i]>4){ //check size each suit, i.e. how many of each suit, if 5 or more, check for flush
				int[]x9=new int[6];
				int x=r8k[i]; // x is the amount of suits of that kind, like 5 spades, or 6 hearts
				int[]x8=new int[x]; // new array the size of the amount of suits
				int acntr=0;
				for(int a=0;a<allz.length;a++)//get the numbers of the cards attached to the suits
					if(allz[a]%10==i)
						x8[acntr++]=allz[a]/10;
				SuperSort.sort(x8); // sort the numbers 
				 //the hand has a flush, lets see if its a straight flush too
					for(int a=0;a<x-4;a++){
						for(int f=x8.length-5;f>=0;f--){
							if(x8[f]==x8[f+1]-1&&x8[f+1]==x8[f+2]-1&&x8[f+2]==x8[f+3]-1&&x8[f+3]==x8[f+4]-1)
								return new int[]{x8[f],x8[f+1],x8[f+2],x8[f+3],x8[f+4],8};
						}
						if(x8[0]==la[0]&&x8[1]==la[1]&&x8[2]==la[2]&&x8[3]==la[3]&&x8[4]==la[4])// check for low ace straight
							return new int[] {la[1],la[2],la[3],la[4],la[0],8};//handID
					}
					//setup the flush only since not straight flush
					for(int a=0;a<5;a++)x9[a]=x8[a];x9[5]=5;
					return x9;
			}
		}
		// quads with the highest kicker
		if (qd!=0){
			return new int[]{qd,qd,qd,qd,qd!=si[si.length-1]?si[si.length-1]:si[si.length-2],7};
		}
		// Full houses, top 3 of a kind with top pair; no kicker
		if(tR.length>0&&pS.length>1){
			int x = tR[tR.length-1];
			int xx = x!=pS[pS.length-1]?pS[pS.length-1]:pS[pS.length-2];
			return new int[]{x,x,x,xx,xx,6};
		}

		//find straights
		if(si.length>4)
		{
			for(int s : si)System.out.println("singles : " + s);

			for(int i=si.length-5;i>=0;i--)
				if(si[i]==si[i+1]-1&&si[i+1]==si[i+2]-1&&si[i+2]==si[i+3]-1&&si[i+3]==si[i+4]-1)
					return new int[]{si[i],si[i+1],si[i+2],si[i+3],si[i+4],4};
				if(si[0]==la[0]&&si[1]==la[1]&&si[2]==la[2]&&si[3]==la[3]&&si[4]==la[4])// Separate check for low ace straight
				return new int[]{la[1],la[2],la[3],la[4],la[0],4};
		}

		
		
		// 3 of a kind, with top two kickers
		if(tR.length>0){
			int[]x9=new int[6];
			x9[0]=tR[0];x9[1]=x9[0];x9[2]=x9[1];
			int c=0;
			for(int i=1;i<3;i++)
				if(x9[0]!=si[5-i-c])x9[2+i]=si[5-i-c];
				else{c++;x9[2+i]=si[5-i-c];}x9[5]=3;//handID
			return x9;
		}
		
		// top two pair with top kicker
		else if(pS.length>1){
			int[]x9=new int[6];int x5=si.length;
			x9[0]=(pS[pS.length-1]);x9[1]=x9[0];
			x9[2]=(pS[pS.length-2]);x9[3]=x9[2];
			x9[4]=x9[0]!=si[x5-1]&&x9[2]!=si[x5-1]
					?si[x5-1]:x9[0]!=si[x5-2]&&x9[2]!=si[x5-2]
					?si[x5-2]:si[x5-3];x9[5]=2;//handID
			return x9;		
		}
		
		//one pair
		else if(pS.length==1){
			int[]x9=new int[6];int c=0;
			x9[0]=pS[0];x9[1]=x9[0];
			for(int i=1;i<4;i++)
				x9[i+1]=x9[0]!=si[6-i-c]
						?si[si.length-i-c]
						:si[si.length-i-++c];
			x9[5]=1;//handID
			return x9;
		}
		else {
			int[]x9=new int[6];for(int i=0;i<5;i++)x9[i]=si[i];x9[5]=0;return x9;
		}

	}

		
	
	
	
	
	

	public int currenthand(String card1, String card2) {
		// highCard,pair,twoPair,tR,straight,flush,fullHouse,straightFlush,Royal
		/*
		 * 
		 * how many people at the table position chip stack what the blinds are
		 * compared to ???other peoples stack pot odds out odds hand stats on
		 * flop turn and river implied odds implied hands
		 * 
		 * 2 3 2 3 5 7 11 13 17 19 23 27 29 31 33 37 39 41 43 47 51 53 57 59 61
		 * 67 71 73 77
		 * 
		 * 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71
		 */
		
		

		return 0;
	}
	


}
