package applicatie;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private Board board;
    private GameType game;

    public GameController(GameType game){
        this.game = game;
        this.board = game.getGameboard();
    }
    public GameController(){
    }

    public void updateViews(View view){
        //view.update();
    }

    public Player getPlayerOne(){
        return board.getPlayerOne();
    }

    public Player getPlayerTwo(){
        return board.getPlayerTwo();
    }

    public ArrayList<Node> getNodes(){
        return board.getNodes();
    }

    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }

}