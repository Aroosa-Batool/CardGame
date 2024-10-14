import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;


public class CardGameTest {

    @Test
    void testNewDeckContains40Cards() {
        // Arrange
        int[] deck = CardGame.createShuffledDeck();

        // Act
        int numCards = deck.length;

        // Assert
        assertEquals(40, numCards);
    }

    @Test
    void testShuffleFunctionShufflesDeck() {
        // Arrange
        int[] originalDeck = new int[40];
        for (int i = 0; i < originalDeck.length; i++) {
            originalDeck[i] = i + 1; // Assigning unique values to cards
        }
        int[] shuffledDeck = CardGame.createShuffledDeck();

        // Act
        boolean isShuffled = false;
        for (int i = 0; i < originalDeck.length; i++) {
            if (originalDeck[i] != shuffledDeck[i]) {
                isShuffled = true;
                break;
            }
        }

        // Assert
        assertTrue(isShuffled);
    }

    @Test
    public void testPlayerDrawWithEmptyDrawPile() {
        // Arrange
        int[] drawPile = {}; // Some cards in discard pile
        int[] discardPile = { 3, 4, 1,6,7,5,9 }; // Expected draw pile after shuffle //

        // Act
        drawPile = CardGame.SendDiscardPileToDrawPile(discardPile, drawPile);
       
        // Assert
        boolean notEqual = false;
        for(int i=0; i<discardPile.length; i++)
        {
            if(drawPile[i] != discardPile[i]){
                notEqual = true;
                break;

        }
      
        }
        assertTrue (notEqual);


        Arrays.sort(discardPile);
        Arrays.sort(drawPile);
        // Check that the order of elements has changed (indicating a successful shuffle)

        //assertArrayEquals(drawPile, discardPile);
      
    }

    @Test
    public void testHigherCardWins() {
        // Arrange
        int player1Card = 8;
        int player2Card = 3;

        // Act
        int result = CardGame.compareCards(player1Card, player2Card);

        // Assert
        assertTrue(result > 0, "Player 1 wins this round!");
    }

    @Test
    void testWinnerOfNextRoundWinsTiedCards() {
        // Arrange
        int player1Card = 10; // Player 1's draw pile round 2
        int player2Card = 7; // Player 1's draw pile round 2


        int[] reservedPile ={6,6};

        int[] expectedplayer1DiscardPile = {10,7,6,6};

        int[] player1DiscardPile ={};

        
        // Act
        player1DiscardPile = CardGame.playerWins(player1DiscardPile, player1Card, player2Card, reservedPile);
        
        // Assert
        assertArrayEquals(expectedplayer1DiscardPile,  player1DiscardPile);
    }

}

