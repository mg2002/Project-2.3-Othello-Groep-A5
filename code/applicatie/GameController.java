package applicatie;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used for interaction between the view and the board and the nodes and players on it.
 */
public class GameController {

    /**
     * private fields
     */
    private Board board;
    private GameType game;
    private ReversiView view;

    /**
     * Controller of a gameboard
     * @param game the type of game that the controller is used on.
     */
    public GameController(GameType game){
        this.game = game;
        this.board = game.getGameboard();
        this.view = ReversiView.view;
    }

    /**
     * This method updates the views for the player and/or the AI.
     * @param player the player that did a move.
     */
    public void update(Player player){
        if(player instanceof Human){
            view.drawPlayerIcon();
        }
        else{
            view.drawAIIcon();
        }
    }

    /**
     * @return the player on the first side
     */
    public Player getPlayerOne(){
        return board.getPlayerOne();
    }

    /**
     * @return the player on the other side
     */
    public Player getPlayerTwo(){
        return board.getPlayerTwo();
    }

    /**
     * @return the nodes (tiles) with players of the board.
     */
    public ArrayList<Node> getNodes(){
        return board.getNodes();
    }

    /**
     * @return the list of players in the game.
     */
    public ArrayList<Player> getPlayers(){
        return game.getPlayers();
    }

}