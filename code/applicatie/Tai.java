package applicatie;

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
    
    public void nextMinimaxMove() {
        int bestMoveValue = 0;
    }
    
    // @TODO Alpha/Beta-pruning
    //      public int minimax(Node node, int depth, int alpha, int beta, boolean isMaxing) {
    
    /**
     * Minimax is an algorithm that tries to find the best possible move for the Player who utilizes it.
     * this method is called on a specific Node, which gives it a value based on favor.
     * A node with a higher value means that making a move on this Node gives the Player a better chance of winning.
     *
     * @param node is the Node on which the method is called. Algorithm starts from here on
     * @param depth the limit of child nodes searched.
     * @param isMaxing  checking for each call for what Player you are picking a Node
     *                  for a maximizing Player, the algorithm picks the child Node with highest value
     * @return  value of the highest (or lowest) (child) Node
     */
    public int minimax(Node node, int depth, boolean isMaxing) {
        // if the root node is given, or winning move is found { return static value of the position }
        if (depth <= 0 || isWinningMove(node.getSpot())) {
            return node.getValue();
            // return value of ;
        }
        if (isMaxing) { // Player's turn who wins by high evaluation
            int maxEval = -99999; // if Player finds move with higher eval, saves value for comparison
            for (Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
                int childEval = minimax(entry.getValue(), depth -1, false);
                maxEval = Math.max(maxEval, childEval);
                // int alpha = Math.max(alpha, childEval);
                // if (beta <= alpha) { break; }
            }
            return maxEval;
        }
        else { // Player's turn who wins by low evaluation
            int minEval = 99999; // if Player finds move with lower eval, saves value for comparison
            for (Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
                int childEval = minimax(entry.getValue(), depth -1, true);
                minEval = Math.min(minEval, childEval);
                // int beta = Math.max(beta, childEval);
                // if (beta <= alpha) { break; }
            }
            return minEval;
        }
    }
    
    private boolean isWinningMove(int spot) {
        // implemented in Andre's branch
        return false;
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
                //
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
        // @TODO implement minimax
        Node highest = null;
        for(Map.Entry<Integer, Node> entry : legitNodes.entrySet()) {
            //
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
        return i == 1 || i == 3 || i == 5 || i == 7;
    }
    private Boolean isCorner(int i){
        return i == 0 || i == 2 || i == 6 || i == 8;
    }

    @Override
    public int getPoints(){return points;}
    @Override
    public int getSide(){return side;}
    @Override
    public int getActive(){return active;}
    
    // public HashMap<Integer, Node> getLegitNodes() { return legitNodes; }
    
    @Override
    public void setPoints(int newActive){active = newActive;}
    @Override
    public void setSide(int newSide){side = newSide;}
    public void setBoard(Board newBoard){board = newBoard;}
}
