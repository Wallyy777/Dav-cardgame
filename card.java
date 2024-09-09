//package Tonkpkg;

//super class of subclass enums card rank and suits
public class card implements Comparable<card> {
	private String suit = "";
	private String cardRank = "";
	private int rank_Val = 0;
	public boolean allcards;
	private Suits thesuit;
	private cardRank therank;

	// create card method
	// indicate the data type
	public card(cardRank rank, Suits suit) {
		this.suit = suit.getSuit();
		cardRank = rank.getName();
		rank_Val = rank.getValue();
		this.thesuit = suit.gettheSuit();
		this.therank = rank.gettheRank();
	}

	// creat format of how the card info is displayed
	// so it don't return the memory
	public String toString() {
		return String.format(cardRank + " of " + suit + "\n");
	}

	// gets the value of the rank
	public int getValue() {
		// returns the rankvalue also the only integer of the class in line 4
		return rank_Val;
	}

	// allow me to get and return string value suit of a card
	public String getSuit() {
		return this.suit;
	}

	// allow me to return the suit itself
	public Suits gettheSuit() {
		return this.thesuit;
	}

	// allows me to return the Rank
	public cardRank gettheRank() {
		return this.therank;
	}

	// gets String value of rank
	public String getName() {
		return cardRank;
	}
	// organize cards by rank value

	public int compareTo(card otherCard) {
		if (this.therank.getValue() < otherCard.therank.getValue()) {
			// lower ranks at the front
			return -1;
		} else if (this.therank.getValue() > otherCard.therank.getValue()) {
			// higher ranks at the back
			return 1;
		} else {
			return 0;
		}
	}
}