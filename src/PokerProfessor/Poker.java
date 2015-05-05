	package PokerProfessor;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


//import framework.Animation;

public class Poker extends Applet implements Runnable,MouseListener {

	int xu,yu;
	private static final long serialVersionUID = 1L;
	//private Toaster toaster=new Toaster();
	private Player curP;
	private int toastX,toastY,toastA,curA;
	private PlayerLayout playerLayout;
	private String[] playersCards=new String[2];
	private Image image,tempImage,backGround,dealerChip,back,skull,playByPlay,timer,toastBox;
	private Image check,call,raise,fold,pot,activities,arrow,potBack,playerChips,playerName;
	private Image one,two,three,four,five,six,seven,eight,nine,zero,potText;
	//private Image chip1,chip5,chip25,chip100,chip500,chip1k;
	private URL base;
	private Graphics second;
	private String cardUrl;
	private int screenSizeX,screenSizeY,playerX,playerY,btnH,btnW;
	private int startX, startY, endX, endY, currentX, currentY,clickX,clickY;
	//private boolean isFlop, isTurn, isRiver, isDealt,firstGame;
	//private boolean mouseEntered,clicked;
	private boolean isToasting;
	Font f1;
	Font f2;

	Controller cont;
	int numPlayers;

	@Override
	public void init() {

		screenSizeX=800;
		screenSizeY=480;
		xu=screenSizeX/40;
		yu=screenSizeY/40;
		f1=new Font("Dialog",Font.PLAIN,screenSizeY/20);
		f2=new Font("Dialog",Font.PLAIN,screenSizeY/40);
		cont=new Controller();
		cont.setBigBlind(2);
		cont.setSmallBlind(1);
		numPlayers=cont.getNumPlayers();
		//cont.newGame();
		//firstGame=true;
		playerLayout=new PlayerLayout(screenSizeX,screenSizeY,numPlayers);
		playerX=playerLayout.getPlayerX(0);
		playerY=playerLayout.getPlayerY(0);
		toastX=0;toastY=0;toastA=0;curA=0;
		setSize(screenSizeX, screenSizeY);
		setBackground(Color.BLACK);
		setFocusable(true);
		addMouseListener(this);
		Frame frame=(Frame)this.getParent().getParent();
		frame.setTitle("Poker Professor");

		try{base=getDocumentBase();}catch(Exception e){}
		setupImages();
	}

	@Override
	public void start(){Thread thread=new Thread(this);thread.start();}

	@Override
	public void stop(){}

	@Override
	public void destroy(){}

	@Override
	public void run() {
		//TestBigBrain ttt = new TestBigBrain();ttt.goo();
		while(true){cont.go(); repaint();}
	}

	
	public void update(Graphics g) {


		if(image==null){
			image=createImage(this.getWidth(),this.getHeight());
			second=image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0,0,this.getWidth(),this.getHeight());
		second.setColor(this.getForeground());
		paint(second);
		g.drawImage(image,0,0,this);
	}
	
	

	@Override
	public void paint(Graphics g) {
		
		drawUsualStuff(g);
		
		drawPlayers(g);
		
		drawCommunityCards(g);
		
		drawTimerBarThing(g);
		
		drawDealerChip(g);

	}
	
	public void drawUsualStuff(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(f1);
		g.drawImage(backGround,0,0,this);// display background regardless
		g.drawImage(playByPlay,0,screenSizeY-playByPlay.getHeight(this),this);//attempt at description of whats currently happening
		//display check,call,raise,fold buttons regardless
		g.drawImage(check, 0, screenSizeY-check.getHeight(this), this);
		g.drawImage(call, call.getWidth(this), screenSizeY-call.getHeight(this), this);
		g.drawImage(raise, raise.getWidth(this)*2, screenSizeY-raise.getHeight(this), this);
		g.drawImage(fold, fold.getWidth(this)*3, screenSizeY-fold.getHeight(this), this);
		System.out.println("git?");
		//box for the pot amount
		g.drawImage(potBack,screenSizeX/5*2-xu/2, screenSizeY/5, this);
		g.drawString(cont.getActivityText(), xu*2,screenSizeY-call.getHeight(this));

		g.drawString("Pot",screenSizeX/20*8,screenSizeY/20*5);//draw pot
		
		g.drawString(String.valueOf(cont.game.getPot()),xu*22,yu*10);//draw pot
	}
	
	public void drawCommunityCards(Graphics g){
		//draw community cards
		String[] board = cont.game.getBoard();
		for(int i=0;i<board.length;i++){
			if(board[i] != null){
				cardUrl="cards2/"+board[i]+".png";
				tempImage=getImage(base,cardUrl);
				g.drawImage(tempImage,(int)screenSizeX/40*(11+(i*4)),screenSizeY/5*2,this);
			}
			
		}
	}
	
	public void drawPlayers(Graphics g){
		
		curP=cont.game.getCurrentPlayer();
		g.setFont(f2);
		for(int a=0;a<cont.getNumPlayers();a++){
			
			int px=playerLayout.getPlayerX(a);//get X coordinate of player
			int py=playerLayout.getPlayerY(a);//get Y coordinate of player			
			int pyChips=py+back.getHeight(this)+playerChips.getHeight(this);//get player chip count Y coordinate
			int pyName=py-playerName.getHeight(this);// get player name Y coordinate
			g.drawImage(playerChips,px,pyChips,this);
			g.drawImage(playerName,px,pyName,this);
			g.drawString(cont.viewPlayers[a].getName(),px,pyName+playerName.getHeight(this));
			g.drawString("Chips: "+cont.viewPlayers[a].getChips(),px,pyChips+playerChips.getHeight(this));
			
			
			if(!cont.viewPlayers[a].hasFolded()){
				if(cont.viewPlayers[a].isHuman()){
					// display our player's cards
					playersCards=cont.viewPlayers[a].getCards();
					//if(cont.isNewGame){dealAn...)}
					cardUrl="cards2/"+playersCards[0]+".png";
					tempImage=getImage(base,cardUrl);
					g.drawImage(tempImage,playerX,playerY,this);
					
					cardUrl="cards2/"+playersCards[1]+".png";
					tempImage=getImage(base,cardUrl);
					g.drawImage(tempImage, playerX+screenSizeX/40, playerY+screenSizeY/48, this);				
				}
				else{
					g.drawImage(back,px,py,this);
					g.drawImage(back,px+(screenSizeX/40),py+(screenSizeY/48),this);
					if(cont.viewPlayers[a].isToasting()){
						g.setColor(Color.BLACK);
						g.fillRect(px,py,screenSizeX/20,screenSizeY/20);
						g.setColor(Color.WHITE);
						g.drawString(cont.viewPlayers[a].getToastString(),px+3,py+13);
					}
			}
			}
			else{
				g.drawImage(skull,px,py,this);
			}
			
		}
	}
	
	public void drawTimerBarThing(Graphics g){

		//the Timer bar thing
		int pm=cont.game.players.get(cont.game.getAction()).getWaitTime();
		if(pm==0)pm=1;
		g.setColor(Color.BLUE);
		g.fillRect(playerLayout.getPlayerX(cont.game.getWhoeversTurn())-xu,
				playerLayout.getPlayerY(cont.game.getWhoeversTurn())+yu/pm,xu/2,yu*8-(yu/pm));
		
	}
	
	public void drawDealerChip(Graphics g){
		g.drawImage(dealerChip,playerLayout.getPlayerX(cont.getDealer()),
				playerLayout.getPlayerY(cont.getDealer())+yu*2,this);
	}

	private void toastAnim(){
		
	}
	
	private int getToastA(){
		return 50;
	}
	
	private int getToastX(Player p){
		return playerLayout.getPlayerX(curA);
		
	}
	private int getToastY(Player p){
		return playerLayout.getPlayerY(curA);
	}
	
	public void dealAnim() {
		startX=0;
		currentX=startX;
		endX=playerX;
		startY=0;
		currentY=startY;
		endY=playerY;
		for(int i=0;i<=48;i++){
			currentX+=endX/48;
			currentY+=endY/48;
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		currentX=playerX;
		currentY=playerY;
		repaint();
	}

	private void setupImages() {
		// image setups
		backGround=getImage(base,"cards2/backGround.png");
		check=getImage(base,"cards2/check.png");
		call=getImage(base,"cards2/call.png");
		raise=getImage(base,"cards2/raise.png");
		fold=getImage(base,"cards2/fold.png");
		dealerChip=getImage(base,"cards2/d.png");
		back=getImage(base,"cards2/back.png");
		//chip1=getImage(base,"cards2/chip1.png");
		skull=getImage(base,"cards2/skull.png");
		arrow=getImage(base,"cards2/arrow.png");
		activities=getImage(base,"cards2/activities.png");
		//=getImage(base,"cards2/pot.png");
		potBack=getImage(base,"cards2/potBack.png");
		potText= getImage(base,"cards2/potText.png");
		activities=getImage(base,"cards2/activities.png");
		playerChips=getImage(base,"cards2/playerChips.png");
		playerName=getImage(base,"cards2/playerName.png");
		playByPlay=getImage(base,"cards2/playByPlay.png");
		timer=getImage(base,"cards2/timer.jpg");
		toastBox=getImage(base,"cards2/toastBox.png");
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		
		clickX=me.getX();
		clickY=me.getY();
		btnH=check.getHeight(this);
		btnW=check.getWidth(this);
		
		if(cont.game.isHumansTurn())System.out.println("it is your turn");
		else System.out.println("Please wait your turn");
		if(clickY>this.getHeight()-btnH&&cont.game.isHumansTurn()){
		if(clickX>0&&clickX<btnW)
			{System.out.println("checked");cont.game.userAction(1);}
		else if(clickX>btnW&&clickX<btnW*2)
			{System.out.println("call");cont.game.userAction(2);}
		else if(clickX>btnW*2&&clickX<btnW*3)
			{System.out.println("raise");cont.game.userAction(3);}
		else if(clickX>btnW*3&&clickX<btnW*4)
			{System.out.println("fold");cont.game.userAction(4);}
		
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
