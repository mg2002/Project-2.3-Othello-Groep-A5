package code.applicatie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Ai extends Player{
    private int points, side, active;
    private Board board;
    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y
    private HashMap<Integer, Node> legitNodes = new HashMap<>();

    public Ai(){
        active = 1;
        side = -1; //0 == white, 1 == black, -1 == no side asinged
        points = 2;
        ArrayList<Integer> up           = new ArrayList<>();//movement[0]
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

    @Override
    public int getMove(){
        String temp;
        if(side == -1){
            System.out.println("ERROR. Have not been given a side to play as");
            return 0;
        }else{
            Node n = nextMove();
            if(n != null){
                return n.getSpot();
            }
            return -1;
        }
    }

    public Node findHighestTile(){
        Node highest = null;
        for(Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
            if(highest == null){
                highest = entry.getValue();
            }else {
                int row = (int) Math.floor(entry.getKey()/8);
                int col = entry.getKey()%8;

                if((row == 0 || row == 7) && (col == 0 || col == 7)){
                    entry.getValue().setValue(16);
                }else if(((row == 1 || row == 6) && (col == 0 || col == 7)) || ((row == 0 || row == 7) && (col == 1 || col == 6))){// spaces around corners except the diagonal
                    entry.getValue().decValue(-4);
                }else if((row == 1 || row == 6) && (col == 1 ||  col == 6)){//the diagonal
                    entry.getValue().decValue(-2);
                }else if (row == 0 || row == 7 || col == 0 || col == 7) {//edges
                    entry.getValue().addValue(1);
                }else{
                    entry.getValue().addValue(1);
                }
                if(entry.getValue().getValue() > highest.getValue()){
                    highest = entry.getValue();
                }
            }
            entry.getValue().setValue(0);
        }
        return highest;
    }

    public Node nextMove(){
        ArrayList<Node> nodes = board.getNodes();
        legitNodes.clear();
        for(int foo = 0; foo<nodes.size();foo++){
            lookAround(foo, nodes);
        }
        return findHighestTile();
    }

    /**
     *
     * @param spot origionele locatie om te kijken of de node een legeale move kan zijn
     * @param nodes alle nodes in de board.
     */

    public void lookAround(int spot, ArrayList<Node> nodes){
        int row = (int) Math.floor(spot/8);
        int col = spot%8;
        int newCol;
        int newRow;
        int newSpot;
        if(spot == 29){
            int b = 1+2;
        }
        for(int i = 0; i < movement.size(); i++) {
            newCol = col + movement.get(i).get(0);
            if(newCol < 8 && newCol > -1){
                newRow = row + movement.get(i).get(1);
                if(newRow < 8 && newRow > -1){
                    if(nodes.get(spot).getPlayer() == null){
                        newSpot = 8*newRow+newCol;
                        if(isLegalMove(movement.get(i), nodes, newCol, newRow)){
                            legitNodes.put(spot,nodes.get(spot));
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param move the direction of the mnove in relaltion to the origional spot
     * @param nodes the nodes array
     * @param col colum of the spot to look at
     * @param row row of the spot ro look at
     * @return True is it is a legal move
     */
    public Boolean isLegalMove(ArrayList<Integer> move, ArrayList<Node> nodes, int col, int row){
        Boolean sawOpponent = false;
        int possibleSpot;
        while((col > -1 && col < 8) && (row > -1 && row< 8)){
            possibleSpot = row*8 + col;
            if(nodes.get(possibleSpot).getPlayer() == null){
                return false;
            }else{
                if(nodes.get(possibleSpot).getPlayer().getSide() == getSide()){
                    if(sawOpponent){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    sawOpponent = true;
                    col = col + move.get(0);
                    row = row + move.get(1);
                }
            }
        }
        return false;
    }

    @Override
    public int getPoints(){return points;}
    @Override
    public int getSide(){return side;}
    @Override
    public int getActive(){return active;}
    @Override
    public void setPoints(int newActive){active = newActive;}
    @Override
    public void setSide(int newSide){side = newSide;}
    public void setBoard(Board newBoard){board = newBoard;}
    public HashMap<Integer,Node> getLegitNodes(){return legitNodes;};

}
