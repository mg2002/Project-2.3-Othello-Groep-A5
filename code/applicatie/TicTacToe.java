import java.util.ArrayList;

public class TicTacToe {

    private Board gameboard;
    private ArrayList<Player> players;
    private Boolean turn, end;
    private Communication comm;

    public TicTacToe(){
        end = false;
        turn = true;    //false == player one's (white) turn. true == player two's (black) turn

        comm = new Communication();
        players = new ArrayList<>(2);

        createPlayers();
        gameboard = new Board(players.get(0), players.get(1));
        players.get(0).setBoard(gameboard);
        players.get(1).setBoard(gameboard);
    }

    private void createPlayers(){
        players.add(new Human());
        players.add(new Ai());
    }

    public Boolean asignSides(String side){
        if(side.equals("white")){
            players.get(0).setSide(1);
            players.get(1).setSide(0);
            System.out.println("I am white");
        }else if(side.equals("black")){

            players.get(0).setSide(0);
            players.get(1).setSide(1);

            System.out.println("I am black");
        }else{
            System.out.println("ERROR IN ASSIGNING SIDES. gave an illegal side argument");
            System.out.println("Given argument: " + side);
            return false;
        }
        return true;
    }

    public void step(){
        int temp = -1;
        gameboard.getBoardState();
        Player p;
        if(turn){
            //Ai's turn
            turn = false;
            System.out.println("AI's Turn = ");
            temp = players.get(1).getMove();
            p = players.get(1);
            System.out.println(temp);
        }else{
            turn = true;
            System.out.println("Humans Turn = ");
            if(players.get(0).getActive() == 1){
                temp = comm.getMove();
            }else {
                temp = players.get(0).getMove();
            }
            p =players.get(0);
        }

        gameboard.doMove(temp, p);
        System.out.println("\u001b[0m");
    }

    public Boolean getEnd(){return end;}
}


