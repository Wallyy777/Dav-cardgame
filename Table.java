import java.util.ArrayList;
import java.util.Collections;

public class Table {
    // implements Comparable<Cards>{
    private ArrayList<ArrayList<card>> TableshowingCards = new ArrayList<ArrayList<card>>();

    // view whatever is on the table
    public ArrayList<ArrayList<card>> getTableofCards() {
        return TableshowingCards;
    }

    // add to the Array if you add a run
    public void newMatch(ArrayList<card> match) {
        Collections.sort(match);
        TableshowingCards.add(match);

    }

    // add to Array index
    public void addToMatch(ArrayList<card> toAdd, int tableCardIndex) {
        ArrayList<card> addCardsHere = TableshowingCards.remove(tableCardIndex);
        for (card c : toAdd) {
            addCardsHere.add(c);
        }
        Collections.sort(addCardsHere);
        TableshowingCards.add(addCardsHere);
    }
}