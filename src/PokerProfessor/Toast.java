package PokerProfessor;

public class Toast {
	
	int x,y,a;
	String z;
	Object[]t=new Object[4];
	
	
	public Toast(){
		x=0;y=0;a=100;z="";
		t[0]=x;t[1]=y;t[2]=a;t[3]=z;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getA(){
		return a;
	}
	
	public String getToastString(){
		return z;
	}
}
