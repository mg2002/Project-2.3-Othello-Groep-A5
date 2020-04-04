package applicatie;
import java.util.ArrayList;

import java.util.ArrayList;

public class TicTacToe extends GameType{
    private Board gameboard;
    private ArrayList<Player> players;
    private Boolean turn, end;

    public TicTacToe(Board newGameboard){
        gameboard = newGameboard;
        end = false;
        turn = false;    //true == player one's (X) turn. false == player two's (O) turn
        players = new ArrayList<>(2);
    }

    public void step(){
        int temp = -1;
        gameboard.getBoardState();
        Player p;
        if(turn){
            //Ai's turn
            turn = false;
            System.out.println("X's Turn = ");
            temp = players.get(0).getMove();
            p = players.get(0);
            System.out.println(temp);
        }
        else{
            turn = true;
            System.out.println("O's Turn = ");
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

    public Board getGameboard(){
        return gameboard;
    }
    public boolean getTurn(){
        return turn;
    }
    public ArrayList<Player> getPlayers(){return players;}
    public Boolean getEnd(){return end;}
}


