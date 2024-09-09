import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
	// private View view;
	private Model model;
	private View view;

	// private int pp;
	public Game() {
		model = new Model();
		view = new View();
	}

	public void play() {// game win lose condition
		boolean exitgame = false;
		boolean turn;
		// boolean turn;
		// boolean handmatch;
		int answer;

		while (((model.player1.GameScore() < 100 && !exitgame || model.comp_player.GameScore() < 100 && !exitgame))) {
			// Add both players to an arrayList
			model.players.add(model.player1);
			model.players.add(model.comp_player);
			// when users game score is at least 70 ask if the want to quit the game

			// deck is a new deck generated and shuffled
			model.gamedeck = model.deck.generate();
			model.deck.shuffle(model.gamedeck);
			// deal 10 cards to player
			for (int x = 0; x < 7; x++) {
				model.player1.addToTheHand(model.deck.deal(model.gamedeck));
				model.comp_player.addToTheHand(model.deck.deal(model.gamedeck));
			}

			// set the rest of the cards onto the Stockpile
			model.stockpile.setStock(model.gamedeck);
			// add a card from the stockpile to the discardpile
			model.discardpile.addToDiscard(model.stockpile.Draw());

			// check that there are still cards in the stockpile if not turn discard
			if (model.stockpile.getStockPile().isEmpty()) {
				model.discardpile.toStockPile();
			}
			// get the users name
			// model.player1.getPlayerName();

			// set the users name
			// model.player1.setPlayerName(model.player1.getPlayerName());

			// let user know that it is their turn
			// view.printView(model.player1.PlayerName() + "Turn..." + "\n");

			// Show cards the player1 hand and score
			view.printView("\n" + "Player 1 current Hand:" + "\n"
					+ model.player1.getPlayerHand().getAllCards().toString() + "\n");

			// show top of discard
			view.printView("The Discardpile has : " + model.discardpile.ShowTopCard() + "\n");

			// Ask the player whar do they want to draw from
			view.printView("Where do you want to draw a card from?\n 1-Discard Pile\n 2-Stock Pile\n");
			// validate input
			answer = choice(1, 2);

			// draw by choice
			if (answer == 1) {
				model.player1.addToTheHand(model.discardpile.Draw());
			}
			if (answer == 2) {
				model.player1.addToTheHand(model.stockpile.Draw());
			}
			//
			turn = true;
			// player matches sets once per turn
			boolean turnmatch = false;
			// the player can match or just discard a card their turn
			while (turn) {
				if (model.player1.getPlayerHand().getAllCards().isEmpty()) {
					break;
				}
				// Display the table
				view.printView("On the Table: " + model.Table.getTableofCards().toString() + "\n");
				// show cards
				view.printView("Your have: " + model.player1.getPlayerHand().getAllCards().toString() + "\n");
				// give player the option to match their cards or just discard a card
				view.printView("Would you like to? " + "\n");
				view.printView("1-Match\n2-Lay Off\n3-Discard and end Turn\n");
				// validate input
				int play = choice(1, 3);
				// by choice
				if (play == 1) {
					// if player matches and lays out cards calls tunk
					view.printView("Tunk!\n");
					if (turnmatch) {
						view.printView(" you can only match once per turn!\n");
					} else {
						ArrayList<card> ma = new ArrayList<card>();
						while (!model.player1.getPlayerHand().getAllCards().isEmpty()) {
							// chooche cards to cancel
							int endRange = model.player1.getPlayerHand().getAllCards().size() + 1;
							view.printView(
									"choose cards at least 3 cards to match. Cards must not be the same value as current match\n");
							view.printView(endRange
									+ "- to cancel all and return ( Will alce cancel current matched cards and put them at the end of the hand\n");
							// Show cards with corresponding int number
							showUserInput(model.player1.getPlayerHand().getAllCards());
							// make exit possible
							int e = choice(1, endRange);
							if (e == endRange) {
								// if player started matching and changed mind
								// put matches back into their hand
								if (!ma.isEmpty()) {
									for (card c : ma) {
										model.player1.getPlayerHand().add(c);
									}
									ma.clear();
								}
								break;

							} else {
								card t = model.player1.getPlayerHand().getCard(e - 1);
								// if matched/run array is empty add card
								if (ma.isEmpty()) {
									ma.add(t);
									model.player1.getPlayerHand().Remove(t);
								}
								// if the Suit is the same add to array
								else if (ma.get(0).gettheSuit() == t.gettheSuit()) {
									// if the current card value is greater than the last
									if (ma.get(ma.size() - 1).gettheRank().getValue() - 1 == t.gettheRank()
											.getValue()) {
										// move to the array list and remove from the hand
										ma.add(t);
										model.player1.getPlayerHand().Remove(t);
										Collections.sort(ma);
									}
								}
								// if the card is the same rank then add
								else if (ma.get(0).gettheRank() == t.gettheRank()) {
									ma.add(t);
									model.player1.getPlayerHand().Remove(t);
								} else {
									view.printView("The card must have the same rank or suit\n");
								}
								// if 3 or more cards are in the array give the player the option to quit or
								// continue
								if (ma.size() >= 3) {
									// match array
									model.player1.tablematch(ma);
									turnmatch = true;
								}
							}
						}
					}
				} else if (play == 2) {
					// collect cards to lay out
					ArrayList<card> collected = new ArrayList<card>();
					Table Table = new Table();
					if (Table.getTableofCards().isEmpty()) {
						view.printView("There are no cards to add to yet\n");
					} else {
						while (true) {
							// Show the players Hand
							view.printView(model.player1.PlayerName() + " have: "
									+ model.player1.getPlayerHand().getAllCards().toString() + "\n");
							view.printView("What cards do you want to add to?\n ");
							for (int y = 1; y <= model.Table.getTableofCards().size(); y++) {
								view.printView(y + "-" + model.Table.getTableofCards().get(y - 1).toString());
							} // show the table of cards
							view.printView(model.Table.getTableofCards().size() + 1 + "-End\n");
							int ta = choice(1, model.Table.getTableofCards().size() + 1);
							// if player adds a card to the table stop
							if (ta == model.Table.getTableofCards().size() + 1) {
								break;
							} else {
								while (true) {
									// show where is being to add to
									view.printView("Table :" + model.Table.getTableofCards().get(ta - 1));
									view.printView("What card do you want to put here ?\n");
									showUserInput(model.player1.getPlayerHand().getAllCards());
									// awy to cancel player dishing out a card
									int endDish = model.player1.getPlayerHand().getAllCards().size() + 1;
									view.printView(endDish + "-to cancel and return to viewing te table!\n");
									// get table selection for players match method
									ArrayList<card> Dishto = model.Table.getTableofCards().get(ta - 1);
									// user input
									int in = choice(1, endDish);
									// player can return to table choice
									if (in == endDish) {
										break;
									} else {
										card q = model.player1.getPlayerHand().getCard(in - 1);
										if (Dishto.get(0).gettheSuit() == q.gettheSuit()) {
											// if the card has same suit and value is 1 greater than the last one in
											// array
											if (Dishto.get(Dishto.size() - 1).gettheRank().getValue() + 1 == q
													.gettheRank().getValue()) {
												// add to match list
												collected.add(q);
												// remove the card from player hand
												model.player1.getPlayerHand().Remove(q);
											}
											// if the card has 1 lower value
											else if (Dishto.get(0).gettheRank().getValue() - 1 == q.gettheRank()
													.getValue()) {
												collected.add(q);
												model.player1.getPlayerHand().Remove(q);
											} // if the card has the same rank
											else if (Dishto.get(0).gettheRank() == q.gettheRank()) {
												collected.add(q);
												model.player1.getPlayerHand().Remove(q);
											} // if wrong entry make player retry
											else {
												view.printView("The cards must be the same rank or suit\n");
												continue;
											}
											model.Table.addToMatch(collected, ta - 1);
											// clear for next round
											collected.clear();
											break;
										}
									}
								}
							}
						}
					}
				}
				// else dicard to finish te turn
				else {
					view.printView("Choose the position of the card that you want to discard\n");
					// show cards to discard
					showUserInput(model.player1.getPlayerHand().getAllCards());
					// let player put in a number for the card pos to discrd
					int discard = choice(1, model.player1.getPlayerHand().getAllCards().size());
					// discard that card
					model.player1.discardFromHand(model.player1.getPlayerHand().getCard(discard - 1));
					// end player turn
					turn = false;
				}
			}
			// ask player if they want to quit
			EndGame();
			// let the computer play
			view.printView(model.comp_player.CPUname() + " turn...\n");
			// show cpu hand
			view.printView(model.comp_player.getPlayerHand().getAllCards().toString());

			model.comp_player.cpu_Turn();
			// if stock pile is not empty compute scoring
			if (!model.stockpile.getStockPile().isEmpty()) {
				// get player hand score
				model.player1.setScore(model.player1.getPlayerHand().addScore());
				model.comp_player.setScore(model.comp_player.getPlayerHand().addScore());
				// if player hand score is greater than computer
				if (model.player1.getScore() > model.comp_player.getScore()) {
					// adds player(hand) score to their game GameScore
					model.player1.totalPoints(model.player1.getScore());
					view.printView(model.player1.PlayerName() + " Game Score: " + model.player1.GameScore());
				} // if computer score is greater compute computer gamescore
				else if (model.comp_player.getScore() > model.player1.getScore()) {
					model.comp_player.totalPoints(model.comp_player.getScore());
					view.printView(model.comp_player.CPUname() + "Game Score:" + model.comp_player.GameScore());
				} else {
					break;
				}
			}

			// players losing condition
			if (model.player1.GameScore() > 100) {
				view.printView(model.player1.PlayerName() + " You Lose\n");
				view.printView(model.comp_player.CPUname() + " Won");
				System.exit(0);
			}
			if (model.comp_player.GameScore() > 100) {
				view.printView(model.player1.PlayerName() + " You Win\n");
				view.printView(model.comp_player.CPUname() + " Lose");
			}
			// if player score is above 70 prompt player if they want to end the game
			if (model.player1.GameScore() >= 70) {

			}

			// clear both players hands
			model.comp_player.clear();
			model.player1.clear();

			if (model.comp_player.GameScore() > 100) {
				view.printView(model.player1.PlayerName() + " Win!");
				EndGame();
				System.exit(0);
			}

		}
		// I know I messed up I took the EngGame() method and added it to the view class
		// and it broke my code
	}

	// player choose between range set
	private int choice(int min, int max) {

		while (true) {
			try {
				String stringInput = view.Input.nextLine();
				int intInput = Integer.parseInt(stringInput);
				if (intInput >= min && intInput <= max) {
					return intInput;
				} else {
					view.printView("Please enter a positive number, within the range " + min + " " + max);
				}
			} catch (NumberFormatException ime) {
				view.printView("Please type a positive number");
			}
		}
	}

	// method to choose card in the hand base off of the int position
	private void showUserInput(ArrayList<card> i) {
		// construct empty string var
		String input = " ";
		// for the index in greater or equal the size of the cards in hand
		for (int x = 1; x <= i.size(); x++) {
			// index is the index -1 card
			input += x + "-" + i.get(x - 1).toString() + ", ";
		}
		view.printView(input);
	}
	// prompt player if they want to quit

	private void EndGame() {

		// Input is the new scanner to be input by the user
		view.Input = new Scanner(System.in);
		// printView method creaded above to print this method
		view.printView("Would you like to quit the game? Yes or No" + "\n");
		String reader = view.Input.nextLine();
		if (reader.equals("Yes")) {
			// Let player know the game has Ended
			view.printView("Game is Ended");
			System.exit(0);
		}
		// if reader is no resume the game
		else if (reader.equals("No")) {
			view.printView("Game resumed");

		}

		// for security make it loop until user gives a valid input
		else {
			view.printView(" Input not Valid type Yes or No");
			EndGame();

		}
	}

}
