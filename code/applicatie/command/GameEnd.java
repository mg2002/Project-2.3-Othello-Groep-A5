package applicatie.command;

import java.util.Objects;

/**
 * Stores information about playerOneScore, playerTwoScore, comment and hasWon when the game ends.
 *
 * @author Merel Foekens
 * @version 1.1
 */
public class GameEnd extends ServerCommand {
    private final String playerOneScore;
    private final String playerTwoScore;
    private final String comment;
    private final boolean hasWon;

    public GameEnd(String playerOneScore, String playerTwoScore, String comment, boolean hasWon) {
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
        this.comment = comment;
        this.hasWon = hasWon;
    }

    public String getPlayerOneScore() {
        return playerOneScore;
    }

    public String getPlayerTwoScore() {
        return playerTwoScore;
    }

    public String getComment() {
        return comment;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean hasLost() {
        return !hasWon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEnd gameEnd = (GameEnd) o;
        return hasWon == gameEnd.hasWon &&
                Objects.equals(playerOneScore, gameEnd.playerOneScore) &&
                Objects.equals(playerTwoScore, gameEnd.playerTwoScore) &&
                Objects.equals(comment, gameEnd.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerOneScore, playerTwoScore, comment, hasWon);
    }

    @Override
    public String toString() {
        return "GameEnd{" +
                "playerOneScore='" + playerOneScore + '\'' +
                ", playerTwoScore='" + playerTwoScore + '\'' +
                ", comment='" + comment + '\'' +
                ", hasWon=" + hasWon +
                '}';
    }
}
