import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tai implements Player{
    private Boolean firstMove;
    private int points, side, active, stratToUse;
    private Board board;
    private HashMap<Integer, Node> legitNodes = new HashMap<>();
    private HashMap<Integer, Node> friend = new HashMap<>();
    private HashMap<Integer, Node> enemy = new HashMap<>();
    private ArrayList<Node> nodes = new ArrayList<>();

    Tai() {
        active = 1;
        side = -1; //0 == white, 1 == black, -1 == no side asinged
        points = 2;
        stratToUse = 2;
        firstMove = true;
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
            int block = blockingNeeded();
            Node n = legitNodes.get(i);
            int j = n.getSpot();
            if(block == -1) {
                if (side == 1 || (stratToUse == 2 && side == 0)) {//Attacking
                    if (isCorner(j)) {
                        n.setValue(30);
                    } else if (isSide(j)) {
                        n.setValue(20);
                    } else if (j == 4) {
                        n.setValue(10);
                    }
                } else {//defending
                    if (isCorner(j)) {
                        n.setValue(40);
                    } else if (isSide(j)) {
                        n.setValue(30);
                    } else if (j == 4) {
                        n.setValue(50);
                    }
                }
            }else{
                System.out.println("I AM BLOCKING!");
                if(j == block){
                    n.setValue(99);
                }else{
                    n.setValue(0);
                }
            }
        }
    }

    private int blockingNeeded(){
        int foo;
        for(Map.Entry<Integer, Node> entry : enemy.entrySet()) {
            foo = entry.getKey();
            for(int i = 0; i<3 ;i++){

                for(int j = 1; j<3 ;j++){
                    if(enemy.containsKey(foo-i*j)){
                        if(foo + i*j <9){
                            if (nodes.get(foo + i*j).getPlayer() == null){
                                System.out.println("YOU NEED TO BLOCK!");
                                return foo+i*j;
                            }
                        }
                    }else if(enemy.containsKey(foo+i*j)) {
                        if (foo - i*j > -1) {
                            if (nodes.get(foo - i*j).getPlayer() == null) {
                                System.out.println("YOU NEED TO BLOCK!");
                                return foo - i*j;
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
        return highest;
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