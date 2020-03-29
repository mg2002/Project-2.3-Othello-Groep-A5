package code.applicatie.command.server;

import java.util.Objects;

public class YourTurn extends ServerCommand {
    private final String turnMessage;

    public String getTurnMessage() {
        return turnMessage;
    }

    public YourTurn(String turnMessage){
        this.turnMessage = turnMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YourTurn yourTurn = (YourTurn) o;
        return Objects.equals(turnMessage, yourTurn.turnMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turnMessage);
    }

    @Override
    public String toString() {
        return "YourTurn{" +
                "turnMessage='" + turnMessage + '\'' +
                '}';
    }
}
