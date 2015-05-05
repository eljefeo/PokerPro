package PokerProfessor;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class Deck {

	String[] deck = new String[52];
	AtomicReferenceArray<String> ara;
	char[] suits = { 's', 'd', 'c', 'h' };
	String card;
	int deckCount;

	public Deck() {
		card = "";
		deckCount = 0;
		int cntr = 0;
		for (int i = 0; i <= 3; i++) {
			for (int a = 2; a <= 14; a++) {
				card = a + "." + suits[i];
				deck[cntr] = card;
				cntr++;
			}
		}
	}

	public void showDeck() {
		String a = "";
		for (int i = 0; i < deck.length; i++) {
			a += deck[i];
		}
		System.out.println(a);
	}

	public void shuffleDeck() {

		int N = deck.length;
		for (int i = 0; i < N; i++) {
			int r = i + (int) (Math.random() * (N - i));
			String t = deck[r];
			deck[r] = deck[i];
			deck[i] = t;
		}
	}

	public String getCard() {
		
	//using atomic ref to avoid possible thread/memory problems ive been experiencing
		//prev line instead of ara was 'String card = deck[deckCount];'
		ara = new AtomicReferenceArray<String>(deck);
		String card = (String) ara.get(deckCount);
		deckCount++;
		return card;
	}

	public void resetDeckCount() {
		deckCount = 0;
	}
}
