package applicatie;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private Board board;
    private GameType game;
    private ReversiView view;

    public GameController(GameType game){
        this.game = game;
        this.board = game.getGameboard();
        this.view = ReversiView.view;
    }
    public GameController(){
    }

    public void update(Player player){
        if(player instanceof Human){
            view.drawPlayerIcon();
        }
        else{
            view.drawAIIcon();
        }
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