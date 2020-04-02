package code.applicatie;

import javax.swing.text.View;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    private Board board;
    private TicTacToe game;

    public GameController(TicTacToe game){
        this.game = game;
        this.board = game.getGameboard();
    }

    public void updateViews(View view){
        view.update();
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
