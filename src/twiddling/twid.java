package twiddling;
import java.lang.Math;
import java.util.Random;

public class twid {

	
	static String[] handNames = 
		{
			"High Card", "Pair", "Two Pair", "Trips", 
			"Straight", "Flush", "Full House", "Quads", "Straight Flush"
		};



  static String[] allNames = new String[]
		  {
			  "2s","3s","4s","5s","6s","7s","8s","9s","10s","11s","12s","13s","14s",
			  "2h","3h","4h","5h","6h","7h","8h","9h","10h","11h","12h","13h","14h",
			  "2c","3c","4c","5c","6c","7c","8c","9c","10c","11c","11c","13c","14c",
			  "2d","3d","4d","5d","6d","7d","8d","9d","10d","11d","12d","13d","14d"
		  };
  static int[] allc = new int[]
		  {
			  65537,65538,65540,65544,65552,65568,65600,65664,65792,66048,66560,67584,69632,
			  32769,32770,32772,32776,32784,32800,32832,32896,33024,33280,33792,34816,36864,
			  16385,16386,16388,16392,16400,16416,16448,16512,16640,16896,17408,18432,20480,
			  8193,8194,8196,8200,8208,8224,8256,8320,8448,8704,9216,10240,12288
		  };
  //int[] straightDecs = new int[]{7936,3968,1984,992,496,248,124,62,31,4111};
  //int[] flushDecs = new int[] {65536,32768,16384,8192};
  static int straightDec = 32624896;
  static int flushDec = 65536;
  
  
  
  
  public static void main (String[] args) throws java.lang.Exception
	{
  
	 // makeHands();
	testEvaluator();
  
		
	}
	
  
  
  public static int evalHand(int[] u){
	  int[] m={(u[0]&8191),(u[1]&8191),(u[2]&8191),(u[3]&8191),(u[4]&8191)};
	  boolean h=(65536%((u[0]|u[1]|u[2]|u[3]|u[4])&122880))==0;
	  int xord = m[0]^m[1]^m[2]^m[3]^m[4];
	  int ord = m[0]|m[1]|m[2]|m[3]|m[4];
	  boolean s=32624896%ord==0;
	  boolean q = m[0]+m[1]+m[2]+m[3]+m[4]==(ord^xord)*4+xord;
	  int v = 2;
	  while((xord&=xord-1)!=0){v++;}while((ord&=ord-1)!=0){v++;}
	  return h&&s?8:v==3?q?7:6:h?5:s?4:v==6?3:v==4?2:v==7?1:0;
  }
  
/*  public static int evalHand(int[] u){
	  int e=0;
	  int[] m={(u[0]&8191),(u[1]&8191),(u[2]&8191),(u[3]&8191),(u[4]&8191)};
	  boolean h=(65536%((u[0]|u[1]|u[2]|u[3]|u[4])&122880))==0;
	  boolean s=32624896%(m[0]|m[1]|m[2]|m[3]|m[4])==0;
	  for(int i=0;i<m.length-1;i++){for(int j=i+1;j<m.length;j++){if(m[i]==m[j])e++;}}
	  
	  

	  return h&&s?8:e==6?7:e==4?6:h?5:s?4:e;
  }
  */
  
  
  
  public static void makeHands(){
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
		  c++;c--;
	  }
	  long endT1 = System.nanoTime();
	  
	  long startT = System.nanoTime();
	  for(int[] hand : hands){
		  //handCounter[evalHand(hand)]++;
		  evalHand(hand);
	  }
	  long endT = System.nanoTime();
	  for(int j=0;j<handCounter.length;j++)
		  System.out.println(handNames[j] +" : " + handCounter[j]);
	  
	  long total = (endT - startT) - (endT1 - startT1);
	  //double totalT = total/1000000;
	  double time = (double)total/1000000000;
	  System.out.println((int)((1/time)*(howMany/1000000)) + " million hands a second");
	  
	  
	  
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
		{allc[6],allc[33],allc[19],allc[32],allc[50],allc[17],allc[45]},//4x8s
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
							/*System.out.println("\n\n" + getName(cnm[i]) + " i\t" + bin(cnm[i]) + "\t"+(csm[i]&8191)
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
			  			}
		  			}
	  			}
			}
		}  
		
		
		System.out.println(counter + ", " + finalWinner[0]+", " + (finalWinner[0]==counter++));
  }
		
		
		
		
		System.out.println("\n\nFinal winner : " + handNames[finalWinner[0]]);
  }
	
	
	
	
	
	public static String bin(int i){
		return String.format("%17s", Integer.toBinaryString(i)).replace(' ', '0');
	}

	public static void sortLowUp(int[] n){
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
