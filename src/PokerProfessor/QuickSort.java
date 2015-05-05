package PokerProfessor;

public class QuickSort  {
	  private static int[] numbers;
	  private static int number;
	  private static Integer[] numbers2;
	  private static int number2;
	  private char[] suits;
	  

	  public static void sort(int[]values) {
	    if(values==null||values.length==0)return;
	    numbers=values;
	    number=values.length;
	    quicksort(0,number-1);
	  }
	  
	  public static void sort(Integer[]values){
		    if (values==null||values.length==0)return;
		    numbers2=values;
		    number2=values.length;
		    quicksort3(0, number2 - 1);
		  }	  
	  
	  public void sort(int[]values,char[]values2) {
		    if (values==null||values.length==0)return;
		    if (values2==null||values2.length==0)return;
		    numbers=values;
		    suits=values2;
		    number=values.length;
		    quicksort2(0,number-1);
		  }

	  private static void quicksort(int low, int high) {
	    int i = low, j = high;
	    // Get the pivot element from the middle of the list
	    int pivot = numbers[low + (high-low)/2];

	    // Divide into two lists
	    while (i <= j) {
	      // If the current value from the left list is smaller then the pivot
	      // element then get the next element from the left list
	      while (numbers[i] > pivot) {
	        i++;
	      }
	      // If the current value from the right list is larger then the pivot
	      // element then get the next element from the right list
	      while (numbers[j] < pivot) {
	        j--;
	      }

	      // If we have found a values in the left list which is larger then
	      // the pivot element and if we have found a value in the right list
	      // which is smaller then the pivot element then we exchange the
	      // values.
	      // As we are done we can increase i and j
	      if(i <= j){exchange(i,j);i++;j--;
	      }
	    }
	    
	    
	    // Recursion
	    if (low < j)
	      quicksort(low, j);
	    if (i < high)
	      quicksort(i, high);
	  }
	  
	  private void quicksort2(int low, int high) {
		    int i = low, j = high;
		    // Get the pivot element from the middle of the list
		    int pivot = numbers[low + (high-low)/2];

		    // Divide into two lists
		    while (i <= j) {
		      // If the current value from the left list is smaller then the pivot
		      // element then get the next element from the left list
		      while (numbers[i] > pivot) {
		        i++;
		      }
		      // If the current value from the right list is larger then the pivot
		      // element then get the next element from the right list
		      while (numbers[j] < pivot) {
		        j--;
		      }

		      // If we have found a values in the left list which is larger then
		      // the pivot element and if we have found a value in the right list
		      // which is smaller then the pivot element then we exchange the
		      // values.
		      // As we are done we can increase i and j
		      if (i <= j) {
		        exchange3(i, j);
		        i++;
		        j--;
		      }
		    }
		    
		    
		    // Recursion
		    if (low < j)
		      quicksort2(low, j);
		    if (i < high)
		      quicksort2(i, high);
		  }
	  
	  
	  private static void quicksort3(int low,int high){
		    int i=low,j=high;
		    // Get the pivot element from the middle of the list
		    int pivot=numbers2[low+(high-low)/2];

		    // Divide into two lists
		    while(i<=j){
		      // If the current value from the left list is smaller then the pivot
		      // element then get the next element from the left list
		      while(numbers2[i]>pivot)i++;
		      
		      // If the current value from the right list is larger then the pivot
		      // element then get the next element from the right list
		      while(numbers2[j]<pivot)j--;
		      

		      // If we have found a values in the left list which is larger then
		      // the pivot element and if we have found a value in the right list
		      // which is smaller then the pivot element then we exchange the
		      // values.
		      // As we are done we can increase i and j
		      if(i<=j){exchange2(i,j);i++;j--;}
		    }
		    // Recursion
		    if(low<j)quicksort3(low,j);
		    if(i<high)quicksort3(i,high);
		  }
		  
	  
	  
	  private static void exchange(int i,int j){
		  int temp=numbers[i];
		    numbers[i]=numbers[j];
		    numbers[j]=temp;
	  }
	  
	  private void exchange3(int i,int j){
			char tempc=suits[i];
			suits[i]=suits[j];
			suits[j]=tempc;
		    int temp=numbers[i];
		    numbers[i]=numbers[j];
		    numbers[j]=temp;
		  }

	  private static void exchange2(int i,int j){
	    int temp=numbers2[i];
	    numbers2[i]=numbers2[j];
	    numbers2[j]=temp;
	  }
	} 
