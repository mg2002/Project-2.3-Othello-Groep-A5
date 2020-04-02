package code.applicatie;

import java.io.IOException;
import java.util.ArrayList;

public class Reversi extends GameType{
    private Board gameboard;
    private ArrayList<Player> players;
    private Boolean turn, end;
    private Communication comm;

    public Reversi(Board newGameboard) throws IOException {
        gameboard = newGameboard;
        end = false;
        turn = false;    //true == player one's (white) turn. false == player two's (black) turn

        comm = new Communication();
        players = new ArrayList<>(2);
    }


    public void step(){
        int temp = -1;
        gameboard.getBoardState();
        Player p;
        if(turn){
            //Ai's turn
            turn = false;
            System.out.println("AI's Turn = ");
            temp = players.get(0).getMove();
            p = players.get(0);
            System.out.println(temp);
        }else{
            turn = true;
            System.out.println("Humans Turn = ");
            temp = players.get(1).getMove();
            p =players.get(1);
        }

        gameboard.doMove(temp, p);
        System.out.println("\u001b[0m");
    }

    public void setPlayers(Player playerOne, Player playerTwo){
        players.add(playerOne);
        players.add(playerTwo);
    }

    public Boolean getEnd(){return end;}
}
