package twiddling;
import java.awt.List;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class twid {

	
	static String[] handNames = 
		{
			"High Card", "Pair", "Two Pair", "Trips", 
			"Straight", "Flush", "Full House", "Quads", "Straight Flush"
		};




  
  static int straightDec = 32624896;
  static int flushDec = 65536;
  
  
  
  
  public static void main (String[] args) throws java.lang.Exception
	{  

	  makeShitLoadOfHands();
	  //testEvaluator();
	// testRandom();
	  //binaryDupCheck();
	  //make4Bits();
  
	}
  
  public static void make4Bits(){
	  int[] nums = {1,2,4,8,16,32,64,128,256,512,1024,2048,4096};
	  ArrayList<int[]> allints = new ArrayList<int[]>();
	  for(int i=0;i<nums.length-1;i++){
		  for(int j=i+1;j<nums.length;j++){
			  for(int k=j+1;k<nums.length;k++){
				  for(int l=k+1;l<nums.length;l++){
					  
					  allints.add(new int[]{nums[i],nums[j],nums[k],nums[l]});
				  }
			  }
		  }
	  }
		System.out.println("count " + allints.size());
		
		
	int[] all4Bits=new int[allints.size()];
		for(int j = 0;j<allints.size();j++){
			int[] jint = allints.get(j);
			
			all4Bits[j]=jint[0]^jint[1]^jint[2]^jint[3];
			
			
		}
		
/*		for(int i =0;i<all4Bits.length;i++){
			for(int j =0;j<all4Bits.length;j++){
				if(all4Bits[i]==all4Bits[j] ){
					System.out.println("MATCH DAMN at index  i " + i + " , j " + j +" "+ all4Bits[i] + " " + all4Bits[j] );
				}
			}
		}
		*/
		
		
		int biggest = 0, bigInd = 0;
		for(int i=2; i<2147483647;i++){
			int smallCounter =0;
			long freshnum = all4Bits[0]/i;
			for ( int j=1;j<all4Bits.length;j++){
				if(all4Bits[j]/i==freshnum)smallCounter++;
				else break;
			}
			if (smallCounter>biggest){biggest = smallCounter;bigInd = i;System.out.println("bigger " + biggest + " at index " + i + " with mod " + freshnum);}
			if(smallCounter == all4Bits.length){System.out.println("POSSIBLE " + i);break;}
			//else System.out.println("matches /715 : " + i + " :: " + smallCounter);
		}
			System.out.println("biggest " + biggest + ", at index " + bigInd);
		
		
		/*	  for(int[] ii : allints){
				  for(int i : ii){
					System.out.println(i);
				  }
				System.out.println();
			  }*/
			  
			  
  }

  static String[] allNames = new String[]
		  {
			  "2s","3s","4s","5s","6s","7s","8s","9s","10s","11s","12s","13s","14s",
			  "2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h","13h","14h",
			  "2c","3c","4c","5c","6c","7c","8c","9c","10c","11c","12c","13c","14c",
			  "2d","3d","4d","5d","6d","7d","8d","9d","10d","11d","12d","13d","14d"
		  };
  static int[] allc = new int[]
		  {
			  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
			  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
			  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
			  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
		  };
  
  public static int evalHand(int[] u){
	int m=0x1FFF, xm=(u[0]^u[1]^u[2]^u[3]^u[4])&m;
	int o=(u[0]|u[1]|u[2]|u[3]|u[4]),om=o&m,v=2,t=om;
	if((t&=t-1)>0)if((t&=t-1)>0)if((t&=t-1)>0)v+=3;else v+=2;else v+=1;
	t=xm;while((t&=t-1)>0){v++;}
	boolean s=0x1F1D100%om==0,h=(0x10000%(o&0x1E000))==0;
	boolean f=(u[0]&m)+(u[1]&m)+(u[2]&m)+(u[3]&m)+(u[4]&m)-xm*3-(om^xm)*2==0;
	return v==7?1:v==4?2:v==6?3:h&&s?8:h?5:s?4:v==3?f?6:7:0;
  }
	

  
  public static void makeShitLoadOfHands(){
	  int howMany = 5000000;
	  int[] allc2 = allc.clone();
	  int[][] hands = new int[howMany][5];
	  Random r = new Random();
	  int ran=0;
	  int x = 0;
	  for(int i=0;i<howMany;i++){
		  for(int j=0;j<5;j++){
			  while(x==0){
				  ran = r.nextInt((51 - 1) + 1) +1;
				  x = allc2[ran];
			  }
			  hands[i][j] = x;
			  allc2[ran]=0;
			  x=0;
		  }
		  allc2=allc.clone();
	  }
	  int[] handCounter = new int[9];

	  int c=0;
	  long startT1 = System.nanoTime();
	  for(int[] hand : hands){
		  c++;
	  }
	  long endT1 = System.nanoTime();
	  
	  long startT = System.nanoTime();
	  for(int[] hand : hands){
		  //handCounter[evalHand(hand)]++;
		  evalHand(hand);
	  }
	  long endT = System.nanoTime();
	 // for(int j=0;j<handCounter.length;j++)
		 // System.out.println(handNames[j] +" : " + handCounter[j] + " : " + ((double)handCounter[j]/howMany*100) + "%");
	  
	  long total = (endT - startT) - (endT1 - startT1);
	  //double totalT = total/1000000;
	  double time = (double)total/1000000000;
	  System.out.println((int)((1/time)*(howMany/1000000)) + " million hands a second");
	  
	  
  }
  
  public static void binaryDupCheck(){
	  
	  
	  int[][] ranHands = 
		  {
			  makeHighCardHand(),
			  makePairHand(),
			  makeTwoPairHand(),
			  makeTripHand(),
			  makeStraightHand(),
			  makeFlushHand(),
			  makeFullHouseHand(),
			  makeQuadsHand(),
			  //makeStraightFlushHand()
		  };
	  
	  for(int i=0;i<ranHands.length;i++){
		  if(i==0||i==4||i==5||i==8)continue;
		  
		  System.out.println(handNames[i]+"\t"+handNames[evalHand(ranHands[i])]);
		  int added = 0, ord = 0, xord = 0;
		  
		  for(int f :  ranHands[i]){
			  int d=f;
			  f&=0x1FFF;added+=f;ord|=f;xord^=f;
			  System.out.println(getName(d)+"\t"+f+"\t"+bin(f));
		  }
		  System.out.println("\nadded\t"+added+"\t"+bin(added) + "\t"+cb(added)
				  +"\nord\t"+ord+"\t"+bin(ord)+ "\t"+cb(ord)
				  +"\nxord\t"+xord+"\t"+bin(xord)+ "\t"+cb(added)
				  +"\nadd+ord"+"\t"+(added+ord)+"\t"+bin(added+ord)+ "\t"+cb(added+ord)
				  +"\nadd|ord"+"\t"+(added|ord)+"\t"+bin(added|ord)+"\t"+cb(added|ord)
				  +"\nadd^ord"+"\t"+(added^ord)+"\t"+bin(added^ord)+"\t"+cb(added^ord)
				  +"\nadd+xrd"+"\t"+(added+xord)+"\t"+bin(added+xord)+ "\t"+cb(added+xord)
				  +"\nadd|xrd"+"\t"+(added|xord)+"\t"+bin(added|xord)+"\t"+cb(added|xord)
				  +"\nadd^xrd"+"\t"+(added^xord)+"\t"+bin(added^xord)+"\t"+cb(added^xord)
				  +"\nadd-ord"+"\t"+(added-ord)+"\t"+bin(added-ord)+"\t"+cb(added-ord)
				  +"\nlog/or"+"\t"+(Math.log(added))//+"\t"+bin(Math.log(ord)/Math.log(2))+("\t")//+cb(Math.log(ord)/Math.log(2))
				  +"\n");
		  
		  
	  }
	  

  }
  
  public static int cb(int t){
	  int v = 0;
	  
	  
	  while(t>0){
		  t&=t-1;
		  v++;
		  }
	  return v;
  }
  
  public static void testEvaluator(){
	 
	  int[][] allTestHands = {
		{allc[1],allc[17],allc[15],allc[24],allc[35],allc[45],allc[51]},
		{allc[1],allc[17],allc[15],allc[25],allc[35],allc[45],allc[51]}, //aces
		{allc[1],allc[16],allc[0],allc[25],allc[35],allc[13],allc[51]},//aces
		{allc[12],allc[17],allc[15],allc[25],allc[35],allc[45],allc[51]},//aces
		{allc[1],allc[2],allc[17],allc[3],allc[18],allc[48],allc[51]},// 34567
		{allc[1],allc[44],allc[9],allc[6],allc[3],allc[8],allc[45]},//spades
		{allc[4],allc[33],allc[46],allc[48],allc[35],allc[17],allc[30]},// 6 9 9 11 11 6 6 //6s full of 11s or 9s
		//{allc[6],allc[33],allc[19],allc[32],allc[50],allc[17],allc[45]},//4x8s
		{allc[12],allc[25],allc[19],allc[32],allc[38],allc[17],allc[51]},//4x8s
		{allc[21],allc[16],allc[20],allc[18],allc[19],allc[17],allc[22]}//78910J
	  };
		//int[] cnm = trips;
	  	int counter = 0;
	  	int[] finalWinner = {0,0};
		for(int[] cnm : allTestHands){
			
			int[] csm = new int[cnm.length];
			//sortHighDown(cnm);
			for(int i=0;i<cnm.length;i++){csm[i]=(cnm[i]&8191);}
			
			
		finalWinner[0] = 0;finalWinner[1] = 0;
		for(int i=0;i<csm.length-1;i++){
			for(int j=i+1;j<csm.length;j++){
				for(int k=j+1;k<csm.length;k++){
					for(int l=k+1;l<csm.length;l++){
						for(int m=l+1;m<csm.length;m++){
						/*	System.out.println("\n\n" + getName(cnm[i]) + " i\t" + bin(cnm[i]) + "\t"+(csm[i]&8191)
									+"\n"+getName(cnm[j]) + " j\t" + bin(cnm[j]) + "\t"+(csm[j]&8191)
									+"\n"+getName(cnm[k]) + " k\t" + bin(cnm[k]) + "\t"+(csm[k]&8191)
									+"\n"+getName(cnm[l]) + " l\t" + bin(cnm[l]) + "\t"+(csm[l]&8191)
									+"\n"+getName(cnm[m]) + " m\t" + bin(cnm[m]) + "\t"+(csm[m]&8191)
									);
							*/
							int[] cu = {cnm[i],cnm[j],cnm[k],cnm[l],cnm[m]};
							int fsm = (csm[i]|csm[j]|csm[k]|csm[l]|csm[m]);
							int[]c={csm[i],csm[j],csm[k],csm[l],csm[m]};
							
							
							
							int winningHand = evalHand(cu);
							//System.out.println(handNames[winningHand]);
							if(winningHand>finalWinner[0]){
								finalWinner[0] = winningHand;
								finalWinner[1] = fsm;
							}
							else if (winningHand == finalWinner[0]){
								if(fsm > finalWinner[1]){
									finalWinner[1] = fsm;
								}
							}
							//System.out.println(handNames[winningHand]);
							
			  			}
		  			}
	  			}
			}
		}  
		
		
		System.out.println(counter + ", " + finalWinner[0]+", " + (finalWinner[0]==counter++));
  }
		
		
		
		
		System.out.println("\n\nFinal winner : " + handNames[finalWinner[0]]);
  }
	
	
  public static void testRandom(){

	  int passes = 0;
	  int failures = 0;
	  for(int j=0;j<100;j++){
	  int[][] ranHands = 
		  {
			  makeHighCardHand(),
			  makePairHand(),
			  makeTwoPairHand(),
			  makeTripHand(),
			  makeStraightHand(),
			  makeFlushHand(),
			  makeFullHouseHand(),
			  makeQuadsHand(),
			  makeStraightFlushHand()
		  };
	  
	  for(int i=0;i<ranHands.length;i++){
		  mixUpCards(ranHands[i]);
		  //for(int k : ranHands[i])System.out.print(getName(k) + "," );
		  int w = evalHand(ranHands[i]);
		  if(w==i)passes++;
		  else{
			for(int k : ranHands[i])System.out.print(getName(k) + "," );
			  failures++;
			  System.out.println("\n"+ "expected:"+handNames[i]+", actual:"+ handNames[w] + "\n" + (i==w?"***PASS***":"^^^FAIL^^^")+"\n");
		  }
		  
		  
	  }
	  
	  }
	  
	  System.out.println("Passes = "+passes + ", Failures = " + failures);
	  

	  
  }
  
	public static int[] makeHighCardHand(){
		//System.out.println("Making high card hand");
		  int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ra,rb,rc,rd,re;
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  ra=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){
			  ran=r.nextInt((12-0)+1)+0;
			  if(ran>0&&allc2[ran-1]!=0&ran<12&&allc2[ran+1]!=0)
			  x=allc2[ran];
			  
			  }
		  b=x;rb=ran;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((25-13)+1)+13;x=allc2[ran];}
		  c=x;rc=ran;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((38-25)+1)+25;x=allc2[ran];}
		  d=x;rd=ran;allc2[ran]=0;allc2[ran+13]=0;x=0;
		  
		  while(x==0){ran=r.nextInt((38-25)+1)+25;x=allc2[ran];}
		  e=x;re=ran;
		  
		  x=0;
		  if(a==b||a==c||a==d||a==e){
			  while(x==0){ran=r.nextInt((51-1)+1)+1;x=allc2[ran];}
			  allc2[ran]=0;
			  a=x;
		  }
		  
		  x=0;
		  if(b==a||b==c||b==d||b==e){
			  while(x==0){ran=r.nextInt((51-1)+1)+1;x=allc2[ran];}
			  allc2[ran]=0;
			  b=x;
		  }
		  
		  x=0;
		  if(c==b||c==a||c==d||c==e){
			  while(x==0){ran=r.nextInt((51-1)+1)+1;x=allc2[ran];}
			  allc2[ran]=0;
			  c=x;
		  }
		  x=0;
		  if(d==b||d==c||d==a||d==e){
			  while(x==0){ran=r.nextInt((51-1)+1)+1;x=allc2[ran];}
			  allc2[ran]=0;
			  d=x;
		  }
		  x=0;
		  if(e==b||e==c||e==d||e==a){
			  while(x==0){ran=r.nextInt((51-1)+1)+1;x=allc2[ran];}
			  allc2[ran]=0;
			  e=x;
		  }
		  
		  int[] fhigh = {ra,rb,rc,rd,re};
		  sortHighDown(fhigh);
		  int scounter = 0;
		  for(int i=0;i<fhigh.length-1;i++){
			  if(fhigh[i]==fhigh[i+1]+1){
				  scounter++;
				  System.out.println("corrected straight");
			  }

		  }
		
		  if(scounter==4){
			  while((re=re-1) !=rd && re!=rc && re!=rb && re!=ra)
			  {if(re==0)re=rd+2;else e=allc2[re];};
		  }
			  
		  
		  
		  return new int[]{a,b,c,d,e};
		  
		  

		  
	}
	
	public static int[] makePairHand(){
		//System.out.println("Making Pair hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  a=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  b=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((25-13)+1)+13;x=allc2[ran];}
		  c=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  while(x==0){ran=r.nextInt((38-25)+1)+25;x=allc2[ran];}
		  d=x;allc2[ran]=0;
		  e=allc2[ran+13];
		  
		  return new int[]{a,b,c,d,e};
	}
	
	public static int[] makeTwoPairHand(){
		//System.out.println("Making Two Pair hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  a=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;allc2[ran+39]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  b=x;allc2[ran]=0;x=0;
		  
		  e=allc2[ran+13];allc2[ran+13]=0;
		  
		  while(x==0){ran=r.nextInt((25-13)+1)+13;x=allc2[ran];}
		  c=x;
		  
		  d=allc2[ran+13];
		  
		  return new int[]{a,b,c,d,e};
	}
	
	public static int[] makeTripHand(){
		//System.out.println("Making Trip hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  a=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  b=x;allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  while(x==0){ran=r.nextInt((25-13)+1)+13;x=allc2[ran];}
		  c=x;
		  
		  d=allc2[ran+26];
		  e=allc2[ran+13];
		  
		  return new int[]{a,b,c,d,e};
	}
	
	public static int[] makeStraightHand(){
		//System.out.println("Making Straight hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  while(x==0){ran=r.nextInt((8-1)+1)+1;x=allc2[ran];}
		  a=x;
		  
		  b=allc2[ran+1];
		  c=allc2[ran+15];
		  d=allc2[ran+29];
		  e=allc2[ran+43];
		  
		  return new int[]{a,b,c,d,e};
	}
	
	public static int[] makeFlushHand(){
		//System.out.println("Making Flush hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  int ranSuit = ran=r.nextInt((4-1)+1)+1;
		  
		 int max = (13*ranSuit)-1, min = max-12;
		  
		  while(x==0){ran=r.nextInt((max-min)+1)+min;x=allc2[ran];}
		  a=x;allc2[ran]=0;x=0;
		  while(x==0){ran=r.nextInt((max-min)+1)+min;x=allc2[ran];}
		  b=x;allc2[ran]=0;x=0;
		  while(x==0){ran=r.nextInt((max-min)+1)+min;x=allc2[ran];}
		  c=x;allc2[ran]=0;x=0;
		  while(x==0){ran=r.nextInt((max-min)+1)+min;x=allc2[ran];}
		  d=x;allc2[ran]=0;x=0;
		  while(x==0){
			  ran=(r.nextInt((max-min)+1)+min);
			  if(ran>0&&allc2[ran-1]!=0&ran<max&&allc2[ran+1]!=0)
				  x=allc2[ran];
			  }
		  e=x;

		  return new int[]{a,b,c,d,e};
	}
	
	public static int[] makeFullHouseHand(){
		//System.out.println("Making Fullhouse hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  a=x;b=allc2[ran+13];allc2[ran]=0;allc2[ran+13]=0;allc2[ran+26]=0;x=0;
		  
		  
		  
		  while(x==0){ran=r.nextInt((25-13)+1)+13;x=allc2[ran];}
		  c=x;
		  
		  d=allc2[ran+26];
		  e=allc2[ran+13];
		  
		  return new int[]{a,b,c,d,e};
	}
	
	public static int[] makeQuadsHand(){
		//System.out.println("Making Quads hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  while(x==0){ran=r.nextInt((12-0)+1)+0;x=allc2[ran];}
		  b=x;x=0;
		  allc2[ran]=0;
		  c=allc2[ran+39];allc2[ran+39]=0;
		  d=allc2[ran+26];allc2[ran+26]=0;
		  e=allc2[ran+13];allc2[ran+13]=0;

		  while(x==0){ran=r.nextInt((51-0)+1)+0;x=allc2[ran];}
		  a=x;
		  
		  return new int[]{a,b,c,d,e};
	}

	public static int[] makeStraightFlushHand(){
		//System.out.println("Making straightFlush hand");
		 int[] allc2 = allc.clone();
		  Random r = new Random();
		  int ran=0,x=0,a=0,b=0,c=0,d=0,e=0;
		  
		  
		  int ranSuit = ran=r.nextInt((4-1)+1)+1;
		  
		  int max = (13*ranSuit)-1-4, min = max-8;
		  
		  while(x==0){ran=r.nextInt((max-min)+1)+min;x=allc2[ran];}
		  a=x;
		  
		  b=allc2[ran+1];
		  c=allc2[ran+2];
		  d=allc2[ran+3];
		  e=allc2[ran+4];
		  
		  return new int[]{a,b,c,d,e};
	}
	
	
	public static void mixUpCards(int[] c){
		int N = c.length;
		for (int i = 0; i < N; i++) {
			int r = i + (int) (Math.random() * (N - i));
			int t = c[r];
			c[r] = c[i];
			c[i] = t;
		}
	}
	
	
	public static String bin(int i){
		return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
	}
	

/*	public static void sortLowUp(int[] n){
		int temp = 0;
		int size = n.length - 1;
		for(int i=0;i<size;i++)
			for(int j=0;j<(size-i);j++)
				if((n[j]&8191)>(n[j+1]&8191)){
					temp=n[j];
					n[j]=n[j+1];
					n[j+1]=temp;
				}
	}
	*/
	
	public static void sortHighDown(int[] n){
		int temp = 0;
		int size = n.length - 1;
		for(int i=0;i<size;i++)
			for(int j=0;j<(size-i);j++)
				if((n[j]&8191)<(n[j+1]&8191)){
					temp=n[j];
					n[j]=n[j+1];
					n[j+1]=temp;
				}
	}
	
	
	
	public static String getName(int j){
		for(int i=0;i<allc.length;i++){
			if(allc[i]==j){
				return allNames[i];
			}
		}
		return j+"";
	}

}
