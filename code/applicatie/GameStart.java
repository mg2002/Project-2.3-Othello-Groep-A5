package code.applicatie;

import java.util.Objects;

public class GameStart {
    private final String playerToMove;
    private final String gameName;
    private final String opponent;

    GameStart(String playerToMove, String gameName, String opponent){
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
