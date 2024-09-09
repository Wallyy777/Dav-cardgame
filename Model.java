import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Model {

    public player player1;
    public CPlayer comp_player;
    public Deck deck;
    public LinkedList<card> gamedeck;
    public StockPile stockpile;
    public Discardpile discardpile;
    public Table Table;
    // was gonna make discard pile a Stack but I feel I will be using to many
    // different
    // public static Stack<card> discardpile;
    public List<player> players;

    /// Construce Model of player, hand,, deck, gameScore
    // so i can be able to call them
    public Model() {

        player1 = new player();
        comp_player = new CPlayer();
        deck = new Deck();
        stockpile = new StockPile();
        discardpile = new Discardpile();
        gamedeck = new LinkedList<card>();
        players = new ArrayList<player>();
        Table = new Table();

    }

}