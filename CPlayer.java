import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//import java.util.List;
import java.util.Random;

public class CPlayer extends player {
    // construct int variable

    View print;
    String str;
    private Hand pc;

    public CPlayer() {
        // cpu has a hand and a name str

        pc = new Hand();
        str = "Davonta ";

    }

    public String CPUname() {
        return str;
    }

    // cpu turn
    public void cpu_Turn() {
        // Draw from the StockPile
        StockPile stockpile = new StockPile();
        stockpile.Draw();
        ArrayList<card> ahand = playerHand.getAllCards();
        // crate a arraylist for each suit

        ArrayList<card> spades = new ArrayList<card>();
        ArrayList<card> clubs = new ArrayList<card>();
        ArrayList<card> hearts = new ArrayList<card>();
        ArrayList<card> diamonds = new ArrayList<card>();

        // seperate cpu hand by the suit also will have to do this for player later as
        // well
        for (card cp : ahand) {
            // retieve the suit using method created in the card class
            Suits suit;
            suit = cp.gettheSuit();
            // adds each card to the array created above according to suit
            switch (suit) {
                case SPADES:
                    spades.add(cp);
                    break;
                case CLUBS:
                    clubs.add(cp);
                    break;
                case HEARTS:
                    hearts.add(cp);
                    break;
                case DIAMONDS:
                    diamonds.add(cp);
                    break;

            }
        }
        // sort each case of cards made above
        // if each Arraylist is not empty sort them all
        if (!spades.isEmpty()) {
            Collections.sort(spades);
        }
        if (!clubs.isEmpty()) {
            Collections.sort(clubs);
        }
        if (!hearts.isEmpty()) {
            Collections.sort(hearts);
        }
        if (!diamonds.isEmpty()) {
            Collections.sort(diamonds);
        }
        // Hash suits and the arraylist of cards labeled matching names
        HashMap<Suits, ArrayList<card>> GroupedSuits = new HashMap<Suits, ArrayList<card>>();
        GroupedSuits.put(Suits.SPADES, spades);
        GroupedSuits.put(Suits.CLUBS, clubs);
        GroupedSuits.put(Suits.HEARTS, hearts);
        GroupedSuits.put(Suits.DIAMONDS, diamonds);

        // put the suitarrays in a list of lists to be able to check better.
        ArrayList<ArrayList<card>> ListofSuits = new ArrayList<ArrayList<card>>();
        ListofSuits.add(clubs);
        ListofSuits.add(spades);
        ListofSuits.add(hearts);
        ListofSuits.add(diamonds);
        // match if there are any for the score
        match(ListofSuits);
        // Dish out to other player if needed
        DishOut();

        // Discard
        Discard(GroupedSuits);
    }

    private void Discard(HashMap<Suits, ArrayList<card>> groupedSuits) {
        Random rand = new Random();
        card toDiscardPile;
        // create arraylist for non m atched or run sets
        ArrayList<card> discardable = new ArrayList<card>();
        // arraylist for run and mastched sets
        ArrayList<card> nondiscardable = new ArrayList<card>();
        // for suit set view of keys in the hashmap
        // see the difference between same suit cards
        for (Suits suit : groupedSuits.keySet()) {
            ArrayList<card> _cards = groupedSuits.get(suit);
            // if the arraylist is empty
            if (!_cards.isEmpty()) {
                Collections.sort(_cards);
                ArrayList<ArrayList<card>> grouped = new ArrayList<ArrayList<card>>();
                int prevCIndex = 0;
                // start first card in group
                int startofgroup = 0;
                // initiate group first card index
                // loop over the rest of the cards
                // in the group starting with the second card
                // previous card at the prvios index and cuur card is the card index
                for (int currCardIndex = 1; currCardIndex < _cards.size(); currCardIndex++) {
                    card prevCard = _cards.get(prevCIndex);
                    card currCard = _cards.get(currCardIndex);

                    int prevcardRankValue = prevCard.gettheRank().getValue();
                    int currcardRankValue = currCard.gettheRank().getValue();
                    // get the rank difference
                    int rankDiff = currcardRankValue - prevcardRankValue;
                    // end group and add to subgroups arrayList
                    if (rankDiff > 2) {
                        ArrayList<card> group = new ArrayList<card>();

                        // loop over cards from the start of current subgroup to current card index
                        for (int subgroupCardIX = startofgroup; subgroupCardIX < currCardIndex; subgroupCardIX++) {
                            // add each card to new group arrayList
                            group.add(_cards.get(subgroupCardIX));
                        }
                        grouped.add(group);
                        // add new group to arrayList of grouped
                        startofgroup = currCardIndex; // start new subgroup with current card
                    }
                    prevCIndex = currCardIndex;
                }
                ArrayList<card> group2 = new ArrayList<card>();

                // loop over cards from the start of current subgroup to end of suit group
                for (int subgroupCardIX = startofgroup; subgroupCardIX < _cards.size(); subgroupCardIX++) {
                    // add each card to new subgroup arrayList
                    group2.add(_cards.get(subgroupCardIX));
                }
                grouped.add(group2); // add final subgroup to arrayList of subgroups

                // Subgroups with only one card are not a part of a possible run
                // thus are added to reserve cards to be matched for a set
                for (int i = 0; i < grouped.size(); i++) {
                    ArrayList<card> sg = grouped.get(i);
                    if (sg.size() == 1) {
                        nondiscardable.add(sg.get(0));
                        grouped.remove(i);
                    }
                }
            }
            Collections.sort(nondiscardable);
            // check non discardable cards for set matches
            // split the nondiscardable cards bby rank
            HashMap<cardRank, ArrayList<card>> cardsByRank = new HashMap<cardRank, ArrayList<card>>();
            // if the arraylist is empty
            if (!nondiscardable.isEmpty()) {
                for (int i = 0; i < nondiscardable.size(); i++) {
                    // get the card
                    card tcard = nondiscardable.get(i);
                    // then card Rank will be that cards VAlue
                    cardRank cardRank = tcard.gettheRank();

                    ArrayList<card> cards = new ArrayList<card>();

                    if (cardsByRank.keySet().contains(cardRank)) {
                        // get cards with same rank
                        cards = cardsByRank.get(cardRank);
                    }
                    // add all cards to the arraylistcards with the same rank
                    cards.add(tcard);
                    // put all cards with the same rank in the cardsByRank hashmap
                    cardsByRank.put(cardRank, cards);
                }
                // Get cards unmatched for a set and put them in "discardable" arrayList so it
                // can be discarded
                // at choice
                for (cardRank rank : cardsByRank.keySet()) {
                    ArrayList<card> rankSet = cardsByRank.get(rank);
                    // if there is only 1 card with that rank
                    if (rankSet.size() == 1) {
                        // add to discardable array
                        discardable.add(rankSet.get(0));
                    }
                }
                // if the array is empty
                if (discardable.isEmpty()) {
                    // if all nondiscardable cards fall into a set then discard random reserve card
                    int randomCard = rand.nextInt(nondiscardable.size());
                    toDiscardPile = nondiscardable.get(randomCard);
                } else {
                    // discard random "discardable" card
                    int randomCard = rand.nextInt(discardable.size());
                    toDiscardPile = discardable.get(randomCard);
                }
            } else {
                // if there are no reserve (all hand cards fall into a potential run from the
                // condition)
                // then discard random card from hand
                if (playerHand.getAllCards().isEmpty()) {
                    toDiscardPile = null;
                } else {
                    int randomCard = rand.nextInt(playerHand.getAllCards().size());
                    toDiscardPile = playerHand.getCard(randomCard);
                }
            }
            // Discard chosen card
            playerHand.Discard(toDiscardPile);
        }
    }

    private void match(ArrayList<ArrayList<card>> listofSuits) {
        // boolean local variable to be used for to check card sequence for scoring
        boolean Run = false;
        // make a arraylist for matches
        ArrayList<card> match = new ArrayList<card>();
        // go through the suit arrays made above ListOfSuits
        for (ArrayList<card> cds : listofSuits) { // for cards size greater than 3 check if they can be mathed
            if (cds.size() >= 3) {
                // start comparing the first card then loop down with each card to check match
                for (card c : cds) {
                    // if match arraylist is empty add the card to the arraylist from cds arraylist
                    if (match.isEmpty()) {
                        match.add(c);
                        // remove that card from the players hand
                        playerHand.getAllCards().remove(c);
                    }
                    // if the card value is 1 greater than the last by card in the array
                    else if (match.get(match.size() - 1).gettheRank().getValue() + 1 == c.gettheRank().getValue()) {
                        // add the card to matched arraylist // arraylist
                        match.add(c);
                        // sort the match arraylist
                        Collections.sort(match);
                    } else { // if we have at least one match set in the rules to match stop the search
                        if (match.size() >= 3) { // end the loop
                            break;
                        }
                        // else put the cards pack in the players hand
                        else {
                            for (card b : match) {
                                if (!playerHand.getAllCards().contains(b)) {
                                    playerHand.add(b);
                                }
                            }
                            // clear current match array and restart from the current card
                            match.clear();
                            // add the current card
                            match.add(c);
                            // remove the card from the players hand
                            playerHand.getAllCards().remove(c);
                        }
                    }

                }
                // if match was found previously then match it
                if (match.size() >= 3) {
                    Run = true;
                    // view.Viewprint(match)
                    break;
                }
                // else if there were no matche put last cards back in the players hand and
                // clear match array
                else {
                    for (card b : match) {
                        if (!playerHand.getAllCards().contains(b)) {
                            playerHand.add(b);
                        }
                    }
                    match.clear();

                }
            }
        }
        // see if there was no runs
        if (!Run) {// sort the hand to check for sets
            Collections.sort(playerHand.getAllCards());
            // iterarate cards to see if it has a set to match
            for (card cds : playerHand.getAllCards()) {
                // start with first card in player hand array and compare to the rest
                if (match.isEmpty()) {
                    match.add(cds);
                }
                // if the rank is the same add card to match set
                else if (match.get(0).gettheRank() == cds.gettheRank()) {
                    match.add(cds);
                }
                // else return the cards to the players hand
                else {
                    // clear the card aray and restart from the current card
                    match.clear();
                    match.add(cds);
                }
                // for sets of 3 or 4
                // view.ViewpPrint(match)
                // remove those cards from the players hand
                for (card a : match) {
                    playerHand.getAllCards().remove(a);
                }
                // stop the loop
                break;
            }
        }

    }

    private void Draw() {
        Discardpile dp;
        dp = new Discardpile();
        StockPile Stock;
        Stock = new StockPile();
        // view the topcard using the method in Discardpile using the peek function
        card topcard = dp.ShowTopCard();
        // check if already drawn a card or not
        boolean draw = false;
        // loop through all cards in the hand
        for (card c : playerHand.getAllCards()) {
            // if the top card in discardpile and hand have the same suit
            if (c.gettheSuit() == topcard.gettheSuit()) {
                // see if able to achieve a run
                // if a card is discarded
                // use card method to retrieve card object from the card class
                // followed by the getValue to get the value thn
                // use the or || to make a compound argument if the card is
                // greater/less by 1
                if (c.gettheRank().getValue() + 1 == topcard.gettheRank().getValue() ||
                        c.gettheRank().getValue() - 1 == topcard.gettheRank().getValue()) {
                    // draw from the discard pile
                    playerHand.add(dp.Draw());
                    // player draws
                    draw = true;
                    // end loop
                    break;
                }
                // if the suit can be used to create a matched set
                else if (c.gettheRank() == topcard.gettheRank()) {
                    // draw
                    playerHand.add(dp.Draw());
                    draw = true; // player draw
                    break;
                }

                // if player did not draw tack from the StockPile
                if (!draw) {
                    playerHand.add(Stock.Draw());
                }

            }
        }

    }

    private void DishOut() {

        Table theTable = new Table();
        ArrayList<ArrayList<card>> table = theTable.getTableofCards();
        for (int x = 0; x < table.size(); x++) {
            // loop through matches on the table
            ArrayList<card> matches = table.get(x);

            Collections.sort(matches);

            ArrayList<card> toLayout = new ArrayList<card>();

            card firstCard = matches.get(0);
            card lastCard = matches.get(matches.size() - 1);

            // get first and last rank values
            int firstCardValue = firstCard.gettheRank().getValue();
            int lastCardValue = lastCard.gettheRank().getValue();

            if (firstCardValue == lastCardValue) {
                // same value will have different suits
                // for the card in the players hand
                for (card c : playerHand.getAllCards()) {
                    // if the card value eqquals the first cards rank value
                    if (c.gettheRank().getValue() == firstCardValue) {
                        // the card is good to layout
                        toLayout.add(c);
                    }
                }
            } else {
                // different values means same suit for run
                Suits firstCardSuit = firstCard.gettheSuit();

                // calculate values for higher and lower layed out cards
                int higherDishedout = lastCardValue + 1;
                int lowerDishedout = firstCardValue - 1;
                // for the cards in the players hand
                for (card c : playerHand.getAllCards()) {
                    // current value is starts at the first cards rank value
                    int currCardValue = c.gettheRank().getValue();
                    // check if the card also has the same suit
                    if (c.gettheSuit() == firstCardSuit) {
                        // card must have same suit
                        if (currCardValue == lowerDishedout) {
                            // current card is one rank lower than first card in match
                            toLayout.add(c);
                            lowerDishedout--; // higher match value increases by one
                        } else if (currCardValue == higherDishedout) {
                            // current card is one rank higher than last card match
                            toLayout.add(c);
                            higherDishedout++;
                            // lower card passed outs value decreases by one
                        }
                    }
                }
            }

            for (card c : toLayout) {
                // remove cards to layoff from hand
                playerHand.Remove(c);
            }

            // Dish out cards to table
            theTable.addToMatch(toLayout, x);
            print.printView("PC laid off some cards to table.");
        }
    }

}
