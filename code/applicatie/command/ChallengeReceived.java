package applicatie.command;

import java.util.Objects;

public class ChallengeReceived extends ServerCommand {
    private final String challenger;
    private final String challengeNum;
    private final String gameName;

    public ChallengeReceived(String challenger, String challengeNum, String gameName) {
        this.challenger = challenger;
        this.challengeNum = challengeNum;
        this.gameName = gameName;
    }

    public String getChallenger() {
        return challenger;
    }

    public String getChallengeNum() {
        return challengeNum;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChallengeReceived that = (ChallengeReceived) o;
        return Objects.equals(challenger, that.challenger) &&
                Objects.equals(challengeNum, that.challengeNum) &&
                Objects.equals(gameName, that.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(challenger, challengeNum, gameName);
    }

    @Override
    public String toString() {
        return "ChallengeReceived{" +
                "challenger='" + challenger + '\'' +
                ", challengeNum='" + challengeNum + '\'' +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}
