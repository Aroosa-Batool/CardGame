
public class CardGame {
    private static final int NUM_CARDS = 40;
    private static final int CARDS_PER_PLAYER = 20;

    public static void main(String[] args) {
        int[] deck = createShuffledDeck();
        int[] player1DrawPile = player1DistributeCards(deck);
        int[] player2DrawPile = player2DistributeCards(deck);
        int[] reservedPile = initializePile(); // handling ties
        int[] player1DiscardPile = initializePile();
        int[] player2DiscardPile = initializePile();
        int round = 1;

        // Simulating several rounds of play
        while (true) {
            System.out.println("Round " + round + ":");
            if (player1DrawPile.length + player1DiscardPile.length == 0) {
                System.out.println("Player 2 wins this Game!");
                break;
            } else if (player2DrawPile.length + player2DiscardPile.length == 0) {
                System.out.println("Player 1 wins this Game!");
                break;
            } else {
                int player1Card = drawCard(player1DrawPile);
                System.out.println("Player 1 (" + (player1DrawPile.length + player1DiscardPile.length) + " cards): "
                        + player1Card); // card is already drawn that is why we are adding one manually
                if (player1DrawPile.length > 0)
                    player1DrawPile = removeCard(player1DrawPile);
                if (player1DrawPile.length == 0) {
                    // Draw pile is empty, shuffle the discard pile into the draw pile
                    player1DrawPile = SendDiscardPileToDrawPile(player1DiscardPile, player1DrawPile);
                    player1DiscardPile = new int[0];
                }
                int player2Card = drawCard(player2DrawPile);
                System.out.println("Player 2 (" + (player2DrawPile.length + player2DiscardPile.length) + " cards): "
                        + player2Card);
                if (player2DrawPile.length > 0)
                    player2DrawPile = removeCard(player2DrawPile);
                if (player2DrawPile.length == 0) {
                    // Draw pile is empty, shuffle the discard pile into the draw pile
                    player2DrawPile = SendDiscardPileToDrawPile(player2DiscardPile, player2DrawPile);
                    player2DiscardPile = new int[0];
                }

                int result = compareCards(player1Card, player2Card);
                if (result > 0) { // the bolean value we are getting after calling compare function. the compare
                                  // function will check its argument and if the first argument is lesser than the
                                  // second argument the function will return -1 and if they are equal it will
                                  // return 0 and otherwise it will return 1
                    System.out.println("Player 1 wins this round!");
                    player1DiscardPile = playerWins(player1DiscardPile, player1Card, player2Card, reservedPile);
                    reservedPile = initializePile();

                } else if (result < 0) {
                    System.out.println("Player 2 wins this round!");
                    player2DiscardPile = playerWins(player2DiscardPile, player1Card, player2Card, reservedPile);

                        reservedPile = initializePile();

                } else {// Handling tied rounds
                    System.out.println("It's a tie! Next round winner takes all tied cards.");
                    reservedPile = addCardToPile(reservedPile, player1Card);
                    reservedPile = addCardToPile(reservedPile, player2Card);
                }
                round++;

            }

        }
    }
    public static int[] SendDiscardPileToDrawPile(int[] playerDiscardPile, int[] playerDrawPile) {
        playerDiscardPile = createShuffledPile(playerDiscardPile);
        playerDrawPile = ReplacePile(playerDiscardPile);
        return playerDrawPile;
    }
    public static int[] playerWins(int[] playerDiscardPile, int player1Card, int player2Card, int[] reservedPile) {
        playerDiscardPile = addCardToPile(playerDiscardPile, player1Card);
        playerDiscardPile = addCardToPile(playerDiscardPile, player2Card);
        if (reservedPile.length > 0) {
            playerDiscardPile = addReservedPileToPile(playerDiscardPile, reservedPile);

        }

        return playerDiscardPile;
    
    }
    private static int[] initializePile() {
        return new int[0];
    }
    // Creating a shuffled deck of cards
    public static int[] createShuffledDeck() {
        int[] deck = new int[NUM_CARDS];
        for (int i = 0; i < NUM_CARDS; i++) {
            deck[i] = (i % 10) + 1; // Each card shows a number from 1 to 10
        }
        shuffleDeck(deck);
        return deck;
    }

    private static int[] createShuffledPile(int[] pile) {
        int[] newPile = new int[pile.length];
        for (int i = 0; i < pile.length; i++) {
            newPile[i] = (i % 10) + 1; // Each card shows a number from 1 to 10
        }
        shuffleDeck(newPile);
        return newPile;
    }

    // Fisher-Yates Shuffle Algorithm
    private static void shuffleDeck(int[] deck) {
        int r, temp;
        for (int i = deck.length - 1; i > 0; i--) {
            r = (int) (Math.random() * (i + 1)); // Get a random index from 0 to i
            // System.out.println("Hi I am random index " + r);
            temp = deck[r];
            deck[r] = deck[i];
            deck[i] = temp;
        }
    }

    // Distribute cards to players
    private static int[] player1DistributeCards(int[] deck) {
        int[] playerDrawPile = new int[CARDS_PER_PLAYER];
        for (int i = 0; i < 20; i++) {
            playerDrawPile[i] = deck[i];
        }
        return playerDrawPile;
    }

    private static int[] player2DistributeCards(int[] deck) {
        int[] playerDrawPile = new int[CARDS_PER_PLAYER];
        int deckIndex = 20;
        for (int i = 0; i < 20; i++) {
            playerDrawPile[i] = deck[deckIndex];
            deckIndex++;
        }
        return playerDrawPile;
    }

    public static int[] ReplacePile(int[] newPile) {
        int[] tempPile = new int[newPile.length];
        for (int i = 0; i < newPile.length; i++) {
            tempPile[i] = newPile[i];
        }
        return tempPile;
    }

    // Drawing a card from the draw pile
    private static int drawCard(int[] player1_DrawPile) {
        if (player1_DrawPile.length == 0) {
            return 0; // if the draw pile is empty then simply end this function
        }
        int drawnCard = player1_DrawPile[0];
        // player1_DrawPile = removeCard(player1_DrawPile);
        return drawnCard;
    }

    // Removing a card from a pile
    private static int[] removeCard(int[] arr) {
        int[] newArr = new int[arr.length - 1];
        for (int i = 1; i < arr.length; i++) {
            newArr[i - 1] = arr[i];
        }
        return newArr;
    }
    // Comparing two cards and return the result
    public static int compareCards(int card1, int card2) {
        return Integer.compare(card1, card2);
    }
    // Adding a card to a player's reserved pile
    private static int[] addCardToPile(int[] pile, int card) {
        int[] newPile = new int[pile.length + 1];
        for (int i = 0; i < pile.length; i++) {
            newPile[i] = pile[i];
        }
        newPile[pile.length] = card; // replacing a garbage value with card value
        return newPile;
    }

    /*
     * private static int[] addReservedPileToPile(int[] pile, int[] reservedCards) {
     * int[] newPile = new int[pile.length + reservedCards.length];
     * for(int i=0; i< pile.length; i++){
     * newPile[i] = pile[i];
     * }
     * for(int i=pile.length; i< reservedCards.length; i++){
     * newPile[i] = reservedCards[i];
     * }
     * return newPile;
     * }
     */
    public static int[] addReservedPileToPile(int[] pile, int[] reservedCards) {
        int[] newPile = new int[pile.length + reservedCards.length];
        // Copying the existing pile
        System.arraycopy(pile, 0, newPile, 0, pile.length);
        // Copying the reserved cards
        System.arraycopy(reservedCards, 0, newPile, pile.length, reservedCards.length);
        return newPile;
    }

}
