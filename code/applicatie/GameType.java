import java.util.ArrayList;

public class GameType {
    public Boolean getEnd(){
        System.out.println("ERROR: Subclass has't implemented getEnd().");
        return true;
    }
    public void step(){
        System.out.println("ERROR: Subclass has't implemented step().");
    }
    public void setPlayers(Player playerOne, Player playerTwo){
        System.out.println("ERROR: Subclass has't implemented setPlayers().");
    }
}
