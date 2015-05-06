package PokerProfessor;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class Deck {

	int[] deck = new int[52];
	AtomicReferenceArray<String> ara;
	//char[] suits = { 's', 'd', 'c', 'h' };
	int[] suits = {0,1,2,3};
	//String card;
	int card;
	int deckCount;

	public Deck() {
		card = 0;
		deckCount = 0;
		int cntr = 0;
		for (int i = 0; i <= 3; i++) {
			for (int a = 2; a <= 14; a++) {
				card = a*10 + suits[i];
				deck[cntr] = card;
				cntr++;
			}
		}
	}

	public void showDeck() {
		String a = "";
		for (int i = 0; i < deck.length; i++) {
			a += deck[i]+", ";
		}
		System.out.println(a);
	}

	public void shuffleDeck() {

		int N = deck.length;
		for (int i = 0; i < N; i++) {
			int r = i + (int) (Math.random() * (N - i));
			int t = deck[r];
			deck[r] = deck[i];
			deck[i] = t;
		}
	}

	public int getCard() {
		
	//using atomic ref to avoid possible thread/memory problems ive been experiencing
		//prev line instead of ara was 'String card = deck[deckCount];'
		//ara = new AtomicReferenceArray<int>(deck);
		int card = deck[deckCount];
		//String card = (String) ara.get(deckCount);
		deckCount++;
		return card;
	}

	public void resetDeckCount() {
		deckCount = 0;
	}
}
