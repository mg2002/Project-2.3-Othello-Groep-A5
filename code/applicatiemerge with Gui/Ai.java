package code.applicatie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * ai van Reversi
 */
public class Ai extends Player{
    private int points, side, active;
    private Board board;
    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y
    private HashMap<Integer, Node> legitNodes = new HashMap<>();

    /**
     * initialiseerd alle moggelijke directies
     */
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

    /**
     * geeft de zet de ai wilt doen
     * @return de zet de ai wilt doen
     */
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

    /**
     * vind de zet met de hoogste waarde
     * @return
     */
    public Node findHighestTile(){
        Node highest = null;
        for(Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
            if(highest == null){
                highest = entry.getValue();
            }else {

                int row = (int) Math.floor(entry.getKey()/8);
                int col = entry.getKey()%8;
                if ((row == 1 || row == 6) || col == 1 || col == 6) {
                    entry.getValue().setValue(-24);
                } else if ((row == 0 || row == 7) && (col == 0 || col == 7)) {
                    if (entry.getValue().getValue() < 99) {
                        entry.getValue().setValue(99);
                    }
                } else {
                    entry.getValue().setValue(5);
                }
                if(entry.getValue().getValue() > highest.getValue()){
                    highest = entry.getValue();
                }
            }
        }
        for(Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
            entry.getValue().setValue(0);
        }
        legitNodes.clear();
        return highest;
    }

    /**
     * zoekt mogelijke zetten en returened de zet met de hoogte waarde
     * @return
     */
    public Node nextMove(){
        ArrayList<Node> nodes = board.getNodes();
        legitNodes.clear();
        for(int foo = 0; foo<nodes.size();foo++){
            lookAround(foo, nodes);
        }
        return findHighestTile();
    }

    /**
     * kijkt of zet legaal is
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
     *  of er fisches van de andere speler tussen zitten en dus legaal is
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
