package applicatie.command;

import java.util.Objects;

/**
 * Stores information such as playerName, move and details into an Object when the applicatie reports of the opponent having
 * done a move
 *
 * @author Merel Foekens
 * @version 1.1
 */
public class GetMove extends ServerCommand{
    private final String playerName;
    private final String move;
    private final String details;
    public GetMove(String playerName, String move, String details){
        this.playerName = playerName;
        this.move = move;
        this.details = details;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getMove() {
        return move;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetMove getMove = (GetMove) o;
        return Objects.equals(playerName, getMove.playerName) &&
                Objects.equals(move, getMove.move) &&
                Objects.equals(details, getMove.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, move, details);
    }

    @Override
    public String toString() {
        return "GetMove{" +
                "playerName='" + playerName + '\'' +
                ", move='" + move + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
