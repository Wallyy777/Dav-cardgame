import java.util.LinkedList;
import java.util.Random;

//will be subclass of hand
// create a Deck class of 52 cards
public class Deck {
	// create random variable
	private Random rand;
	// Linkede list deck of cards
	private LinkedList<card> deck;
	private card c;
	// view for the print method
	private View view;

	// pseudocode for populating researched on web types of code to do so
	// loop through each loop one at a time
	// then loop through card rank
	// add one cardRank for every suit
	// set _card to card with the ranks and suit
	// call cards.add with card
	public LinkedList<card> generate() {
		this.deck = new LinkedList<card>();
		// loop though suits
		for (Suits suit : Suits.values()) {
			for (cardRank rank : cardRank.values()) {
				this.c = new card(rank, suit);
				this.deck.push(this.c);
			}
		}
		return this.deck;

	}

	// Method to shuffle the new generated Deck of cards
	public void shuffle(LinkedList<card> deckCards) {
		rand = new Random();
		// Iterate backwards through the cards
		for (int i = deckCards.size() - 1; i > 0; i--) {
			// generate a random number within the stack
			int index = rand.nextInt(i + 1);
			// Generate 2 cards at two different placeholders one random
			// the other one through the iteration cycle
			card firstCard = deckCards.get(index);
			card secondCard = deckCards.get(i);
			// Remove the card at the current placeholder and
			// replace it with the one from the other placeholder swapping them out
			deckCards.remove(firstCard);
			deckCards.add(index, secondCard);
			deckCards.remove(secondCard);
			deckCards.add(i, firstCard);
		}
	}

	// deal method

	public card deal(LinkedList<card> deck) {
		// returns the top card or none if the Deck is empty
		if (deck.isEmpty()) {
			view.printView("No cards remain in the deck");
			return null;
		} else {
			// remove the top card
			return deck.pop();
		}
	}

}