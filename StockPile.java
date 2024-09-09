import java.util.LinkedList;

public class StockPile {
    // stock pile is a new linked list of cards
    private LinkedList<card> stockPile = new LinkedList<card>();

    // private Deck Stockpile = new Deck();
    // method that retrieves card from the top of deck
    public card Draw() {
        return stockPile.pop();
    }

    // add card to the stockpile adds the element first
    public void addCard(card card) {
        stockPile.push(card);
    }

    // method to get the stockpile and return it
    public LinkedList<card> getStockPile() {
        return stockPile;
    }

    // methot to set stockpile
    public void setStock(LinkedList<card> thecards) {
        stockPile = thecards;
    }

    // method to empty the stockpile
    public void stock_clear() {

        stockPile.clear();
    }

}
