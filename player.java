import java.util.ArrayList;
import java.util.Scanner;

public class player {
        boolean ingame = true;

        private int gameScore;

        // player has cards in his hand
        protected Hand playerHand;
        // int variable to be used to call index
        private int playerId;
        // construct int variable
        private int score;
        // construct String variable
        private String playerName;
        private View view;

        // player will call tunk aftr dealt hande
        public player() {
                // player has a hand
                this.playerHand = new Hand();
                // player has a gmaeScore
                gameScore = 0;
                // player has a handscore
                score = 0;

        }

        // method that returns the string of the players Name
        public String getPlayerName() {
                playerName = " ";
                try (// need to add scanner for the playerNAme to be input and set
                                Scanner Inputt = new Scanner(System.in)) {
                        view.printView("Set your Player Name:...");
                        playerName = Inputt.nextLine();
                        // return playername
                }
                return playerName;
        }

        // method that sets the playername
        public void setPlayerName(String playerName) {
                this.playerName = playerName;
        }

        // method that returns the players name
        public String PlayerName() {
                return this.playerName;
        }

        // be able to get player int
        public Hand getPlayerHand() {
                return playerHand;
        }

        // the index position equals the playerId
        public void getPlayerId(int index) {
                // since only 1 player the player id can be set as 1
                // for player 1
                index = 1;
                this.playerId = index;
        }

        public int SetPlayerId() {
                return this.playerId;
        }

        // add cards to the hand
        public void addToTheHand(card card) {
                this.playerHand.add(card);
        }

        // discard card to discard pile using method created in the HAnd class
        public void discardFromHand(card card) {
                this.playerHand.Discard(card);
        }

        // method to add points from hand score to gameeScore
        public void totalPoints(int score) {
                gameScore += score;
        }

        public int GameScore() {
                return gameScore;
        }

        // method to call player hand score
        public int getScore() {

                return score;
        }

        // sets the player score of Hand
        public void setScore(int score) {
                this.score = score;
        }

        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + playerId;
                return result;
        }

        public void clear() {

                this.playerHand.clear();
        }

        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                player other = (CPlayer) obj;
                if (playerId != other.playerId)
                        return false;
                return true;
        }

        public void tablematch(ArrayList<card> match) {
                Table Table;
                Table = new Table();
                Table.newMatch(match);
        }
}