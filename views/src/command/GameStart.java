package command;

import java.util.Objects;

/**
 * Stores information such as playerToMove, gameName and opponent into an Object when a game starts.
 *
 * @author Merel Foekens
 * @version 1.1
 */
public class GameStart extends ServerCommand{
    private final String playerToMove;
    private final String gameName;
    private final String opponent;

    public GameStart(String playerToMove, String gameName, String opponent){
        this.playerToMove = playerToMove;
        this.gameName = gameName;
        this.opponent = opponent;
    }

    public String getPlayerToMove() {
        return playerToMove;
    }

    public String getGameName() {
        return gameName;
    }

    public String getOpponent() {
        return opponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStart gameStart = (GameStart) o;
        return Objects.equals(playerToMove, gameStart.playerToMove) &&
                Objects.equals(gameName, gameStart.gameName) &&
                Objects.equals(opponent, gameStart.opponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerToMove, gameName, opponent);
    }

    @Override
    public String toString() {
        return "GameStart{" +
                "playerToMove='" + playerToMove + '\'' +
                ", gameName='" + gameName + '\'' +
                ", opponent='" + opponent + '\'' +
                '}';
    }
}