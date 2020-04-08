/**
 * this class is a blue print of a distinct player
 * @author M.Safari
 * @version 1399.01.20
 */
public class Player {
    // players marbles
    public final House marble;

    // players opponent
    private Player Opponent;

    /**
     * 
     * @param marble will be set players marble
     */
    public Player(House marble) {
        this.marble = marble;
    }

    /**
     * @param opponent the players opponent to set
     */
    public void setOpponent(Player opponent) {
        Opponent = opponent;
    }

    /**
     * @return the players opponent
     */
    public Player getOpponent() {
        return Opponent;
    }
}