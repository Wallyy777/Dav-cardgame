import java.util.LinkedList;
//I was going to originally make the discard pile a Stack because it is LIFO learned last semester
//and it would be good to use what was previously learned
//the issue is converting it over

public class Discardpile {
    private static LinkedList<card> discardPile = new LinkedList<card>();

    // method that allows move card to the stockpil
    public void toStockPile() {
        StockPile stock;
        stock = new StockPile();
        discardPile.pop();
        // while discardpile isnt empty
        while (discardPile.size() > 0) {
            card c = discardPile.pop();
            stock.addCard(c);
        }
    }

    // method that adds card to the top of a dicard pile usind push()
    public void addToDiscard(card discard) {
        discardPile.push(discard);
    }

    // draw from the top of the discardpile
    public card Draw() {
        return discardPile.pop();
    }

    // allows player to view the top of the discardpile
    public card ShowTopCard() {
        return discardPile.peek();
    }

}
