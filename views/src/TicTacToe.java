import java.io.IOException;
import java.util.ArrayList;

public class TicTacToe {
    private Board gameboard;
    private ArrayList<Player> players;
    private Boolean turn, end;
    private Communication comm;

    public TicTacToe() throws IOException {
        end = false;
        turn = false;    //true == player one's (X) turn. false == player two's (O) turn

        comm = new Communication();
        players = new ArrayList<>(2);

    }


    public Boolean asignSides(String side){
        if(side.equals("O")){

            players.add(new Human());
            players.add(new Tai());
            System.out.println("I am O");
        }else if(side.equals("X")){
            players.add(new Tai());
            players.add(new Human());
            System.out.println("I am X");
        }else{
            System.out.println("ERROR IN ASSIGNING SIDES. gave an illegal side argument");
            System.out.println("Given argument: " + side);
            return false;
        }

        players.get(0).setSide(0);
        players.get(1).setSide(1);
        gameboard = new Board();
        players.get(0).setBoard(gameboard);
        players.get(1).setBoard(gameboard);
        return true;
    }

    public Board getGameboard(){
        return gameboard;
    }

    public void step(){
        int temp = -1;
        gameboard.getBoardState();
        Player p;
        if(turn){
            //Ai's turn
            turn = false;
            System.out.println("O's Turn = ");
            temp = players.get(0).getMove();
            p = players.get(0);
            System.out.println(temp);
        }else{
            turn = true;
            System.out.println("X's Turn = ");
            temp = players.get(1).getMove();
            p =players.get(1);
        }

        gameboard.doMove(temp, p);
        System.out.println("\u001b[0m");
    }

    public boolean getTurn(){
        return turn;
    }

    public Boolean getEnd(){return end;}

    public ArrayList<Player> getPlayers(){
        return players;
    }
}
