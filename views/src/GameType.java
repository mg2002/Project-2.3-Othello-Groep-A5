import java.io.IOException;
import java.util.ArrayList;

public class GameType {
    public Boolean getEnd(){
        System.out.println("ERROR: Subclass has't implemented getEnd().");
        return true;
    }
    public boolean getTurn(){
        System.out.println("ERROR: Subclass has't implemented getTurn().");
        return true;
    }
    public ArrayList<Player> getPlayers(){
        ArrayList<Player> a = new ArrayList<>();
        System.out.println("ERROR: Subclass has't implemented getPlayers().");
        return a;
    }
    public void step(){
        System.out.println("ERROR: Subclass has't implemented step().");
    }
    public void setPlayers(Player playerOne, Player playerTwo){
        System.out.println("ERROR: Subclass has't implemented setPlayers().");
    }
    public Board getGameboard(){
        System.out.println("ERROR: Subclass has't implemented getGameboard().");
        return new Board();
    }
}
