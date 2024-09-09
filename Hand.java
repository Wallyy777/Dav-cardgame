import java.util.ArrayList;

public class Hand {
	// construct ArrayList of cards variable cards and ans
	// construct int variables Points, score, and int gameScore
	protected ArrayList<card> cards;
	private int points;

	// constructor
	/**
	 * 
	 */
	public Hand() {
		// construct new arrayList of cards using the variables above
		cards = new ArrayList<card>();

	}

	// get card method to retrieve card in hand from index position
	public card getCard(int index) {
		return cards.get(index);
	}

	// get cards in hand seperately using for hand manipulation without changing
	// the hand
	public ArrayList<card> getAllCards() {
		return cards;
	}

	// method that reomoves card from the cards ArrayList
	public void Remove(card _card) {
		cards.remove(_card);

	}

	// clears players hand so a new set of cards can be dealt
	public void clear() {

		cards.clear();
	}

	public void Discard(card _card) {
		// construct a discard pile variable
		Discardpile discard;
		discard = new Discardpile();
		// remove card from arraylist to discardpile
		discard.addToDiscard(_card);
		// remove card from the array
		cards.remove(_card);
	}

	// be able to add card object or hand
	public void add(card _card) {
		cards.add(_card);
	}

	// check if able to give card in hand
	public boolean maxCard(card _card, Hand player) { // if cards not in the array
		if (!cards.contains(_card)) {
			return false;
		} else {
			// if able to remove card and add card to the player
			cards.remove(_card);
			player.add(_card);
			return true;
		}
	}

	// method to call computer player hand score
	public int addScore() {
		points = 0;
		ArrayList<card> cpcds = new ArrayList<card>();
		for (card c : cpcds)
			points += c.getValue();

		return points;
	}

}
