package code.applicatie;

import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private int boardSize, pointsPlayerOne, pointsPlayerTwo;
    private Player pOne, pTwo;
    private ArrayList<Node> nodes;
    private HashSet<Integer> tilesToTurn = new HashSet<>();
    public Board(){
    }

    /**
     * initialiseert voor reversi
     */
    public void reversi(){
        boardSize = 8;
        nodes = new ArrayList<>(64);
        for(int i = 0; i < 8*8; i++){
            Node node = new Node(i);
            if(i == 27 || i == 36){
                node.setPlayer(pOne);
            }else if(i == 28 || i == 35){
                node.setPlayer(pTwo);
            }
            nodes.add(node);
        }
    }

    /**
     * initiliseerd voor tictactoe
     */
    public void ticTacToe(){
        boardSize = 3;
        nodes = new ArrayList<>(boardSize*boardSize);
        for(int i = 0; i < boardSize * boardSize; i++) {
            Node node = new Node(i);
            nodes.add(node);
        }
    }


    public void getBoardState(){
        for(int i = 0; i < nodes.size(); i++) {
            if(i % boardSize == 0){
                System.out.println("\u001b[0m" + i);
            }
            System.out.print(nodes.get(i).getPlayerName() + " ");
        }
        System.out.println("\u001b[0m");
    }

    public void calculatePoints(){
        pointsPlayerOne = 0;
        pointsPlayerTwo = 0;

        for(int i = 0; i < nodes.size(); i++) {
            if(nodes.get(i).getPlayer().getSide() == 0){
                pointsPlayerOne +=1;
            }else if(nodes.get(i).getPlayer().getSide() == 1){
                pointsPlayerTwo +=1;
            }
        }
    }

    public void doMove(ArrayList<Integer> moves, Player player){
        for(int i : moves){
            nodes.get(i).setPlayer(player);
        }
    }

    public void doMove(int move, Player player){
        System.out.println(move);
        nodes.get(move).setPlayer(player);
    }


    public void setPlayerOne(Player p){pOne = p;}
    public void setPlayerTwo(Player p){
        pTwo = p;
    }
    public Player getPlayerOne(){return pOne;}
    public Player getPlayerTwo(){return pTwo;}
    public int getPointsPlayerOne(){return pointsPlayerOne;}
    public int getPointsPlayerTwo(){return pointsPlayerTwo;}

    public ArrayList<Node> getNodes(){return nodes;}
    public void setNode(int spot, Player player){
        nodes.get(spot).setPlayer(player);
    }
    public void resetNodes(){
        for(Node n : nodes){
            if(n.getSpot() == 27 || n.getSpot() == 36){
                n.setPlayer(pOne);
            }
            else if(n.getSpot() == 28 || n.getSpot() == 35){
                n.setPlayer(pTwo);
            }
            else{
                n.reset();
            }
        }
    }
}
