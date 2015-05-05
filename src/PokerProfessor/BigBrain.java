package PokerProfessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.sql.rowset.Predicate;

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
	char[]suits=new char[7];

	
	
	
	/*
	 * 
	 * 1 = call
	 * 2 = check
	 * 3 = raise/bet
	 * 4 = fold
	 * 
	 */
	public void inc(int a){}
	public int makeDecisionBasedOnAwesomeMathStuff(Player p,ArrayList<Player> players,String[]board,int gameStage,
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
		else s=b==1?flop(board, p):b==2?turn(board, p):river(board, p);//get strength for other game stages
		System.out.println("Preflop hand rank(higher=better) : "+s);
		return currentBet==p.getBet()?2:a>5?4:1;//2=check,4=fold randomly,1=call randomly
	}
	
	public int getPreFlopVal(Player p)
	{
		return pf.getPreFlopScore(new String[]{p.getCard(0),p.getCard(1)});
	}
	public int flop(String[]board, Player p){//this will eventually have to take hand strength into account at different stages of the game
		return 0;
	}
	public int turn(String[]board, Player p){//this will eventually have to take hand strength into account at different stages of the game
		return 0;
	}
	public int river(String[]board, Player p){//this will eventually have to take hand strength into account at different stages of the game
		return 0;
	}

	public long getAmountToBetOrRaise() {
		return atbor;
	}

	public ArrayList<Object[]>whoWins(Object[][]ps){
		ArrayList<Integer[]>allP=new ArrayList<>();
		ArrayList<Object[]>winning2=new ArrayList<>();
		
		List<Integer> winners = new ArrayList<Integer>();
		
		for(int i=0;i<ps.length;i++)
		{
			for(int a=0;a<ps[i].length;a++)
			{
				String[]peices=((String)ps[i][a]).split("\\.");
				cards[a]=Integer.parseInt(peices[0]);
				suits[a]=peices[1].charAt(0);
				//System.out.println("~~card&suit for person in seat "+i+" "+cards[a]+" "+suits[a]);
			}
			allP.add(sptfq(cards,suits));
		}

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
			Integer[]x=allP.get(i);	//cycle through each player as x
			Integer[]w=allP.get(winner); // w is the winner
			if(x[5]>w[5]){winner=i;winners.clear();winners.add(i);} //if handID of x larger than handID of w, x becomes w
			else if(x[5]==w[5]) //if handIDs are equal, check cards
				for(int g=0;g<5;g++)
					if(x[g]>w[g]){winner=i;winners.clear();winners.add(i);break;} // if x's best card is better, x becomes w
					else if(x[g]<w[g]){break;}	//if w's best card is better, w stays winner
					else
					{ //else cards are the same so
						++cnt;// we increase a counter
						if(cnt>4){winners.add(i);}// if all 5 cards are the same, they both get put into winners circle
					}
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
	
	@SuppressWarnings("unchecked")
	public Integer[]sptfq(int[]n,char[]u)// n = numbers, u = suits
	{
		ArrayList<Object>[]r8k=new ArrayList[4];
		for(int i=0;i<=3;i++)r8k[i]=new ArrayList<Object>();
		Integer[]la=new Integer[]{14,5,4,3,2};
		final Set<Integer>si=new HashSet<Integer>();//singles
		final Set<Integer>pS=new HashSet<Integer>();//pairs
		final Set<Integer>tR=new HashSet<Integer>();//trips
		final Set<Integer>qd=new HashSet<Integer>();//quads
		for(int i:n)if(!si.add(i))if(!pS.add(i))if(!tR.add(i))qd.add(i);//veryPowerful
		for(int i=0;i<u.length;i++)r8k[u[i]=='c'?0:u[i]=='d'?1:u[i]=='h'?2:3].add(i);//setup for flushes, how many of each suit
		
		//Flush and Straight Flush, if there is a flush out there, there cant be quads also, so we check these first
		for(int i=0;i<4;i++)
		if(r8k[i].size()>4) //check size each suit, i.e. how many of each suit, if 5 or more, check for flush
		{
			Integer[]x9=new Integer[6];
			int x=r8k[i].size(); // x is the amount of suits of that kind, like 5 spades
			Integer[]x8=new Integer[x]; // new array the size of the amount of suits
			for(int a=0;a<x;a++)x8[a]=n[(int)r8k[i].get(a)]; //get the numbers of the cards attached to the suits
			QuickSort.sort(x8); // sort the numbers 
			for(int a=0;a<5;a++)x9[a]=x8[a];x9[5]=5; //the hand has a flush, lets see if its a straight flush too
				for(int a=0;a<x-4;a++)
				{
					int cc = 0;
					for(int f=1;f<x8.length;f++)
					{
						cc=x8[f-1]==x8[f]+1?cc+1:0;
						if(cc==4)
							return new Integer[]
							{x8[f],x8[f-1],x8[f-2],x8[f-3],x8[f-4],8};
					}
					if(Arrays.equals(x8, la))// check for low ace straight
						return new Integer[]
								{la[1],la[2],la[3],la[4],la[0],8};//handID
				}
			return x9;
		}
		// quads with the highest kicker
		if (qd.size()>0)
		{
			Integer x = (int)qd.toArray()[0];
			QuickSort.sort(n);
			return new Integer[]{x,x,x,x,x!=n[0]?n[0]:n[1],7};
		}
		
		// Full houses, top 3 of a kind with top pair; no kicker
		if(tR.size()>0&&pS.size()>1)
		{
			Integer x = (int)tR.toArray()[tR.size()-1];
			Integer xx = x!=(int)pS.toArray()[pS.size()-1]
							?(int)pS.toArray()[pS.size()-1]
							:(int)pS.toArray()[pS.size()-2];
			return new Integer[]{x,x,x,xx,xx,6};
		}

		//find straights
		if(si.size()>4)
		{
			Integer x6[] = si.toArray(new Integer[si.size()]);
			QuickSort.sort(x6);
			int cc = 0;
			for(int i=1;i<x6.length;i++)
			{
				cc=x6[i-1]==x6[i]+1?cc+1:0;
				if(cc==4)
					return new Integer[]
					{x6[i],x6[i-1],x6[i-2],x6[i-3],x6[i-4],4};
			}
			if(si.containsAll(Arrays.asList(la)))// Separate check for low ace straight
				return new Integer[]
						{la[1],la[2],la[3],la[4],la[0],4};//handID
		}
		
		// 3 of a kind, with top two kickers
		if((tR.size()>0))
		{
			Integer[]x9=new Integer[6];
			Integer[]x8=si.toArray(new Integer[5]);
			x9[0]=(int)tR.toArray()[0];x9[1]=x9[0];x9[2]=x9[1];
			int c=0;
			for(int i=1;i<3;i++)
				if(x9[0]!=x8[5-i-c])x9[2+i]=x8[5-i-c];
				else{c++;x9[2+i]=x8[5-i-c];}x9[5]=3;//handID
			return x9;
		}
		
		// top two pair with top kicker
		else if((pS.size()>1))
		{
			Integer[]x9=new Integer[6];
			Integer[]x8=si.toArray(new Integer[si.size()]);
			int x5=x8.length;
			x9[0]=((int)pS.toArray()[pS.size()-1]);x9[1]=x9[0];
			x9[2]=((int)pS.toArray()[pS.size()-2]);x9[3]=x9[2];
			x9[4]=x9[0]!=x8[x5-1]&&x9[2]!=x8[x5-1]
					?x8[x5-1]:x9[0]!=x8[x5-2]&&x9[2]!=x8[x5-2]
					?x8[x5-2]:x8[x5-3];x9[5]=2;//handID
			return x9;		
		}
		
		//one pair
		else if(pS.size()==1)
		{
			Integer[]x8=si.toArray(new Integer[6]);
			Integer[]x9=new Integer[6];int c=0;
			x9[0]=(int)pS.toArray()[0];x9[1]=x9[0];
			for(int i=1;i<4;i++)
				x9[i+1]=x9[0]!=x8[6-i-c]
						?x8[x8.length-i-c]
						:x8[x8.length-i-++c];
			x9[5]=1;//handID
			return x9;
		}
		
		else 
		{
			Integer[]x8=si.toArray(new Integer[7]);
			Integer[]x9=new Integer[6];
			QuickSort.sort(x8);
			for(int i=0;i<5;i++)x9[i]=x8[i];x9[5]=0;//handID
			return x9;
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
