import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tai extends Player{
    private Boolean firstMove;
    private int points, side, active, stratToUse;
    private Board board;
    private HashMap<Integer, Node> legitNodes = new HashMap<>();
    private HashMap<Integer, Node> friend = new HashMap<>();
    private HashMap<Integer, Node> enemy = new HashMap<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y

    Tai() {
        active = 1;
        side = -1; //0 == white, 1 == black, -1 == no side asinged
        points = 2;
        stratToUse = 2;
        firstMove = true;
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
        movement.add(downRight);
        movement.add(upRight);
        movement.add(upLeft);
        movement.add(right);
        movement.add(down);
        movement.add(downLeft);
        movement.add(left);
    }

    public void getNodes(){
        nodes = board.getNodes();
    }

    @Override
    public int getMove(){
        getNodes();
        if(side == -1){
            System.out.println("ERROR. Have not been given a side to play as");
            return 0;
        }else{
            Node n = nextMove();
            if(n != null){
                return n.getSpot();
            }
            return 28;
        }
    }

    public Node nextMove(){
        legitNodes.clear();
        for(int foo = 0; foo<nodes.size();foo++){
            lookAround(foo, nodes);
        }
        asingValues();
        return findHighestTile();
    }


    public void lookAround(int spot, ArrayList<Node> nodes){
        if(nodes.get(spot).getPlayer() == null){
            legitNodes.put(legitNodes.size(),nodes.get(spot));
        }else{
            if(firstMove){
                if(spot == 4){
                    stratToUse = 1;
                }
                firstMove = false;
            }
            if(nodes.get(spot).getPlayer() == this){
                friend.put(spot,nodes.get(spot));
            }else{
                enemy.put(spot,nodes.get(spot));
            }
        }
    }

    public void asingValues(){
        for(int i =0; i<legitNodes.size();i++){
            Node n = legitNodes.get(i);
            int j = n.getSpot();
            int block = blockingNeeded(n.getSpot());
            if(j == 8 ){
                int b = 3-3;
            }
            int win = isWinningMove(n.getSpot());
            if(win == -1) {
                if (block == -1) {
                    if (side == 1) {//Attacking
                        if (isCorner(j)) {
                            n.addValue(30);
                        } else if (isSide(j)) {
                            n.addValue(20);
                        } else if (j == 4) {
                            n.addValue(10);
                        }
                    } else {//defending
                        if(stratToUse == 2) {
                            if (isCorner(j)) {
                                n.addValue(20);
                            } else if (isSide(j)) {
                                n.addValue(40);
                            } else if (j == 4) {
                                n.addValue(80);
                            }
                        }else{
                            if (isCorner(j)) {
                                n.addValue(40);
                            } else if (isSide(j)) {
                                n.addValue(20);
                            } else if (j == 4) {
                                n.addValue(80);
                            }
                        }
                    }
                } else {
                    System.out.println("blocking Move");
                    if (j == block) {
                        n.setValue(99);
                    } else {
                        n.setValue(0);
                    }
                }
            }else{
                if (j == win) {
                    n.setValue(9999);
                } else {
                    n.setValue(0);
                }
            }
        }
    }
    private int isWinningMove(int spot) {
        int newSpot,newCol, newRow;
        if(friend.size()==1 && enemy.size() == 1 && side ==1 && enemy.get(8)== null){
            System.out.println("Special Move");
            return 8;
        }
        for(int i =0; i<movement.size();i++) {
            newCol = spot%3 + movement.get(i).get(0);
            if(-1 < newCol && newCol < 3){
                newRow = (int)Math.floor(spot/3) + movement.get(i).get(1);
                if(-1 <newRow && newRow < 3) {//if the node is in the board
                    newSpot = newRow*3 + newCol;
                    if (friend.containsKey(newSpot)) {//if it's the enemy's node
                        nodes.get(spot).addValue(4);
                        newCol = spot%3 - movement.get(i).get(0);
                        newRow = (int) Math.floor(spot/3) - movement.get(i).get(1);
                        if((-1 < newCol && newCol < 3) && (-1 < newRow && newRow < 3)) {
                            newSpot = newRow * 3 + newCol;
                            if (newSpot < 9 && newSpot > -1) {//if the oppisite node is in the board
                                if (friend.containsKey(newSpot)) {//if it's the enemy's node too
                                    if (nodes.get(spot).getPlayer() == null) {
                                        System.out.println("I AM WINNING!");
                                        return spot;
                                    }
                                }
                            }

                        }else{
                            newCol = spot%3 + movement.get(i).get(0)*2;
                            newRow = (int) Math.floor(spot/3) +movement.get(i).get(1)*2;
                            newSpot = newRow * 3 + newCol;
                            if (newSpot < 9 && newSpot > -1) {//if the oppisite node is in the board
                                if (friend.containsKey(newSpot)) {//if it's the enemy's node too
                                    if (nodes.get(spot).getPlayer() == null) {
                                        System.out.println("I AM WINNING!");
                                        return spot;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    private int blockingNeeded(int spot){
        int newSpot,newCol, newRow;
        for(int i =0; i<movement.size();i++) {
            newCol = spot%3 + movement.get(i).get(0);
            if(-1 < newCol && newCol < 3){
                newRow = (int)Math.floor(spot/3) + movement.get(i).get(1);
                if(-1 <newRow && newRow < 3) {//if the node is in the board
                    newSpot = newRow*3 + newCol;
                    if (enemy.containsKey(newSpot)) {//if it's the enemy's node
                        nodes.get(spot).addValue(-4);
                        newCol = spot%3 - movement.get(i).get(0);
                        newRow = (int) Math.floor(spot/3) - movement.get(i).get(1);
                        if((-1 < newCol && newCol < 3) && (-1 < newRow && newRow < 3)) {
                                newSpot = newRow * 3 + newCol;
                                if (newSpot < 9 && newSpot > -1) {//if the oppisite node is in the board
                                    if (enemy.containsKey(newSpot)) {//if it's the enemy's node too
                                        if (nodes.get(spot).getPlayer() == null) {
                                            return spot;
                                        }
                                    }
                                }

                        }else{
                            newCol = spot%3 + movement.get(i).get(0)*2;
                            newRow = (int) Math.floor(spot/3) +movement.get(i).get(1)*2;
                            newSpot = newRow * 3 + newCol;
                            if (newSpot < 9 && newSpot > -1) {//if the oppisite node is in the board
                                if (enemy.containsKey(newSpot)) {//if it's the enemy's node too
                                    if (nodes.get(spot).getPlayer() == null) {
                                        return spot;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    private Node findHighestTile(){
        Node highest = null;
        for(Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
            if(highest == null){
                highest = entry.getValue();
            }else {
                if(entry.getValue().getValue() > highest.getValue()){
                    highest = entry.getValue();
                }
            }
        }
        resetNodes();
        return highest;
    }

    private void resetNodes(){
        for(Node entry : nodes) {
            entry.setValue(0);
        }
    }

    private Boolean isSide(int i){
        if(i == 1 || i == 3 || i == 5 || i == 7){
            return true;
        }
        return false;
    }
    private Boolean isCorner(int i){
        if(i == 0 || i == 2 || i == 6 || i == 8){
            return true;
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
}
