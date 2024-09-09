//package Tonkpkg;

public enum Suits {
	SPADES("Spades"), DIAMONDS("Diamonds"), CLUBS("Clubs"), HEARTS("Hearts");

	// construct variable val for value just to give a different name
	private String val;
	private Suits idsuit;

	// Method that returns the String Value of the Suit
	private Suits(String value) {// String variable this.val becomes the value of the string
		this.val = value;
	}

	// returns the string value
	public String getSuit() {
		return val;

	}

	// Method returns the suit itself making it easier to manipulate the code when
	// refering to the string
	public Suits gettheSuit() {
		return idsuit;
	}
}
