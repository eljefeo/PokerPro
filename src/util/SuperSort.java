package util;

public class SuperSort {

	public static void sort(int[] n){
		int temp = 0;
		int size = n.length - 1;
		for(int i=0;i<size;i++)
			for(int j=0;j<(size-i);j++)
				if(n[j]>n[j+1]){
					temp=n[j];
					n[j]=n[j+1];
					n[j+1]=temp;
				}
	}
	
}
