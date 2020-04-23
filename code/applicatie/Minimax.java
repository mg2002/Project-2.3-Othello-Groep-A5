//package applicatie;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Een instantie van Minimax wordt aangemaakt wanneer de AI een zet gaat berekenen.
// * De klasse maakt gebruik van de posities op het speelbord, de functie van Ai die de valide zetten kan berekenen
// * en de functie die de waarde van de zetten berekent.
// *
// * Minimax heeft een recursieve methode: minimax, deze vertelt de Ai wat de beste zet is
// * @author Marnix
// */
//public class Minimax{
//
//    private Ai ai;
//    private int points, side, active;
//    private Board board = ai.getBoard();
//    private HashMap<Integer, Node> legitNodes = ai.getLegitNodes();
//    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y
//    private Node bestNode;
//
//    public Minimax(){
//        ArrayList<Integer> up           = new ArrayList<>();//movement[0]
//        ArrayList<Integer> upRight      = new ArrayList<>();
//        ArrayList<Integer> upLeft       = new ArrayList<>();
//        ArrayList<Integer> right        = new ArrayList<>();
//        ArrayList<Integer> downRight    = new ArrayList<>();
//        ArrayList<Integer> down         = new ArrayList<>();
//        ArrayList<Integer> downLeft     = new ArrayList<>();
//        ArrayList<Integer> left         = new ArrayList<>();
//        up.add(0);
//        up.add(1);
//        upRight.add(1);
//        upRight.add(1);
//        upLeft.add(-1);
//        upLeft.add(1);
//        right.add(1);
//        right.add(0);
//        down.add(0);
//        down.add(-1);
//        downLeft.add(-1);
//        downLeft.add(-1);
//        downRight.add(1);
//        downRight.add(-1);
//        left.add(-1);
//        left.add(0);
//        movement.add(up);
//        movement.add(upRight);
//        movement.add(upLeft);
//        movement.add(right);
//        movement.add(downRight);
//        movement.add(down);
//        movement.add(downLeft);
//        movement.add(left);
//    }
//
//    public ArrayList<Node> findLegitNodes(Board board) {
//        ArrayList<Node> nodes = board.getNodes();
//        legitNodes.clear();
//        for (int i = 0; i < nodes.size(); i++) {
//            lookAround(i, nodes);
//        }
//        return nodes;
//    }
//
//    public void lookAround(int spot, ArrayList<Node> nodes){
//        int row = (int) Math.floor(spot/8);
//        int col = spot%8;
//        int newCol;
//        int newRow;
//        // int newSpot;
//        if(spot == 29){
//            int b = 1+2;
//        }
//        for(int i = 0; i < movement.size(); i++) {
//            newCol = col + movement.get(i).get(0);
//            if(newCol < 8 && newCol > -1){
//                newRow = row + movement.get(i).get(1);
//                if(newRow < 8 && newRow > -1){
//                    if(nodes.get(spot).getPlayer() == null){
//                        // newSpot = 8*newRow+newCol;
//                        if(isLegalMove(movement.get(i), nodes, newCol, newRow)){
//                            legitNodes.put(spot,nodes.get(spot));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public Boolean isLegalMove(ArrayList<Integer> move, ArrayList<Node> nodes, int col, int row){
//        Boolean sawOpponent = false;
//        int possibleSpot;
//        while((col > -1 && col < 8) && (row > -1 && row< 8)){
//            possibleSpot = row*8 + col;
//            if(nodes.get(possibleSpot).getPlayer() == null){
//                return false;
//            }else{
//                if(nodes.get(possibleSpot).getPlayer().getSide() == getSide()){
//                    if(sawOpponent){
//                        return true;
//                    }else{
//                        return false;
//                    }
//                }else{
//                    sawOpponent = true;
//                    col = col + move.get(0);
//                    row = row + move.get(1);
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * evaluateNodes bepaalt wat de waarde voor elke mogelijke move zou zijn door op elke legitNode minimax aan te roepen
//     */
//     public void evaluateNodes() {
//         for (Map.Entry<Integer, Node> entry : ai.getLegitNodes().entrySet()) {
//             entry.getValue().setValue(minimax(entry.getValue(), ai.getBoard(), 5, true));
//         }
//     }
//
//    /**
//     * Minimax is een methode die de waarde van een move teruggeeft. Dit doet hij door -depth- aantal keer de beste
//     * move voor zichzelf en zijn tegenstander te berekenen.
//     * Elke keer als hij een move berekent, maakt hij deze zet op een tempBoard, waarna hij kan gaan kijken wat de
//     * beste volgende move is.
//     *
//     * @param move the move after which minimax calculates the best move for the player
//     * @param board the board that is copies to make a hypothetical move on
//     * @param depth the depth in which minimax calculates possible outcomes
//     * @param isMaxing geeft aan of de speler voor wie de methode wordt aangeroepen de maximaliserende speler is.
//     * @return de waarde van de Node die waarschijnlijk tot winst leidt
//     */
//    public int minimax(Node move, Board board, int depth, boolean isMaxing) {
//        if (depth <= 0 || move.isWinning()) { return move.getValue(); }
//        // Probleem: nodes kunnen niet hun eigen waarde bepalen
//
//        Board tempBoard = board;
//        // TODO make move on tempBoard
//        HashMap<Integer, Node> tempLegitNodes = new HashMap<>();
//        // TODO ai.lookAround() on tempBoard to add valid moves to tempLegitNodes
//
//        if (isMaxing) {
//            int maxEval = -1000;
//            // Probleem
//            for(Map.Entry<Integer, Node> childEntry : tempLegitNodes.entrySet()) {
//                Node childMove = childEntry.getValue();
//                int eval = minimax(childMove, tempBoard, depth - 1, false);
//                maxEval = Math.max(maxEval, eval);
//            }
//            return maxEval;
//        }
//
//        else {
//            int minEval = 1000;
//            for(Map.Entry<Integer, Node> childEntry : tempLegitNodes.entrySet()) {
//                Node childMove = childEntry.getValue();
//                int eval = -1 * minimax(childMove, tempBoard, depth - 1, true);
//                minEval = Math.min(minEval, eval);
//            }
//            return minEval;
//        }
//    }
//
//    public Ai getAi() {
//        return ai;
//    }
//
//    public void setAi(Ai ai) {
//        this.ai = ai;
//    }
//
//    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }
//
//    public int getSide() {
//        return side;
//    }
//
//    public void setSide(int side) {
//        this.side = side;
//    }
//
//    public int getActive() {
//        return active;
//    }
//
//    public void setActive(int active) {
//        this.active = active;
//    }
//
//    public Board getBoard() {
//        return board;
//    }
//
//    public void setBoard(Board board) {
//        this.board = board;
//    }
//
//    public HashMap<Integer, Node> getLegitNodes() {
//        return legitNodes;
//    }
//
//    public void setLegitNodes(HashMap<Integer, Node> legitNodes) {
//        this.legitNodes = legitNodes;
//    }
//
//    public ArrayList<ArrayList<Integer>> getMovement() {
//        return movement;
//    }
//
//    public void setMovement(ArrayList<ArrayList<Integer>> movement) {
//        this.movement = movement;
//    }
//
//    public Node getBestNode() {
//        return bestNode;
//    }
//
//    public void setBestNode(Node bestNode) {
//        this.bestNode = bestNode;
//    }
//}
