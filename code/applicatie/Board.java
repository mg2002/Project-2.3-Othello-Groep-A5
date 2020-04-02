package code.applicatie;

import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private int boardSize;
    private Player pOne, pTwo;
    private ArrayList<Node> nodes;
    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y
    private HashSet<Integer> tilesToTurn = new HashSet<>();
    public Board(){
    }

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
        ArrayList<Integer> up           = new ArrayList<>();
        ArrayList<Integer> upRight      = new ArrayList<>();
        ArrayList<Integer> upLeft       = new ArrayList<>();
        ArrayList<Integer> right        = new ArrayList<>();
        ArrayList<Integer> downRight    = new ArrayList<>();
        ArrayList<Integer> down         = new ArrayList<>();
        ArrayList<Integer> downLeft     = new ArrayList<>();
        ArrayList<Integer> left         = new ArrayList<>();
        up.add(0);
        up.add(1);
        upRight.add(1);
        upRight.add(1);
        upLeft.add(-1);
        upLeft.add(1);
        right.add(1);
        right.add(0);
        down.add(0);
        down.add(-1);
        downLeft.add(-1);
        downLeft.add(-1);
        downRight.add(1);
        downRight.add(-1);
        left.add(-1);
        left.add(0);
        movement.add(up);
        movement.add(upRight);
        movement.add(upLeft);
        movement.add(right);
        movement.add(downRight);
        movement.add(down);
        movement.add(downLeft);
        movement.add(left);
    }

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

    public void doMove(int move, Player player){
        System.out.println(move);
        tilesToTurn.clear();
        tilesToTurn.add(move);
        /*lookAround(move, player);// DIT GEDEELTE NAAR GAMETYPE VERPLAATSTEN VOOR REVERSI

        if(nodes.get(move).getPlayer() == null) {
            tilesToTurn.add(move);
        }*/
        for(int i : tilesToTurn){
            nodes.get(i).setPlayer(player);
        }
    }

    public void lookAround(int spot, Player player){
        int row = (int) Math.floor(spot/8);
        int col = spot%8;
        int newCol;
        int newRow;
        for(int i = 0; i < movement.size(); i++) {
            newCol = col + movement.get(i).get(0);
            if(newCol < 8 && newCol > -1){
                newRow = row + movement.get(i).get(1);
                if(newRow < 8 && newRow > -1){
                    if(nodes.get(spot).getPlayer() == null){
                        tilesToTurn.add(spot);
                        ArrayList<Integer> moves = isLegalMove(movement.get(i), nodes, newCol, newRow, player);
                        if(moves != null){
                            tilesToTurn.addAll(moves);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Integer> isLegalMove(ArrayList<Integer> move, ArrayList<Node> nodes, int col, int row, Player player){
        ArrayList<Integer> possible = new ArrayList<>();
        int possibleSpot;
        while((col > -1 && col < 8) && (row > -1 && row< 8)){
            possibleSpot = row*8 + col;
            if(nodes.get(possibleSpot).getPlayer() == null){
                possible.clear();
                return possible;
            }else{
                if(player != null) {
                    if (nodes.get(possibleSpot).getPlayer().getSide() == player.getSide()) {
                        return possible;
                    } else {
                        possible.add(row * 8 + col);
                        col = col + move.get(0);
                        row = row + move.get(1);
                    }
                }
            }
        }
        possible.clear();
        return possible;
    }

    public void setPlayerOne(Player p){pOne = p;}
    public void setPlayerTwo(Player p){pTwo = p;}
    public Player getPlayerOne(){return pOne;}
    public Player getPlayerTwo(){return pTwo;}

    public ArrayList<Node> getNodes(){return nodes;}
    public void setNode(int spot, Player player){
        nodes.get(spot).setPlayer(player);
    }
    public void resetNodes(){
        for(Node n : nodes){
            n.reset();
        }
    }
}
