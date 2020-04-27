package applicatie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinMax {
    Board b;
    int turn;
    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y
    private ArrayList<Integer> tilesToTurn = new ArrayList<>();// x, y
    private ArrayList<Node> nodes;
    private HashMap<Integer, Node>legit;
    private boolean bool;

    MinMax(Board newBoard){
        b = newBoard;
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

    public int loop(int depth, Player p){
        nodes = cloneNodes(b.getNodes());// needs a try catch : the catch results a random legit node
        Ai ai = (Ai) p;
        legit = (HashMap<Integer, Node>)ai.getPossibleMoves().clone();
        int highest = -99999999;
        int highest_move = -900;
        for(Map.Entry<Integer, Node> m : legit.entrySet()){
            bool = true;
            lookAround(m.getKey(), b.getPlayerTwo());
            int temp = recurse(depth,ai, m.getValue().getSpot());

            if(highest < temp || highest_move == -900){
                highest = temp;
                highest_move = m.getValue().getSpot();
            }
            b.setNodes(cloneNodes(nodes));
        }
        return highest_move;
    }

    public int recurse(int depth, Player p, int move){

        b.getBoardState();
        if(depth == 0){
            b.calculatePoints();
            if (p.getSide() == b.getPlayerOne().getSide()){
                System.out.println(b.getPointsPlayerOne() - b.getPointsPlayerTwo() + "utrn value");
                return b.getPointsPlayerOne() - b.getPointsPlayerTwo();
            }else{
                System.out.println(b.getPointsPlayerTwo() - b.getPointsPlayerOne() + "utrn value");
                return b.getPointsPlayerTwo() - b.getPointsPlayerOne();
            }
        }
        System.out.println(depth);
        if(bool) {
            System.out.println(move);

            lookAround(move, b.getPlayerTwo());
            b.doMove(tilesToTurn, b.getPlayerTwo());
            Ai p1 = (Ai) b.getPlayerOne();
            bool = false;
            return recurse(depth-1, p, p1.nextMove().getSpot());
        }else{
            System.out.println(move);

            lookAround(move, b.getPlayerOne());
            b.doMove(tilesToTurn, b.getPlayerOne());
            Ai p2 = (Ai) b.getPlayerTwo();
            bool = true;
            return recurse(depth-1, p, p2.nextMove().getSpot());
        }

    }
    public void lookAround(int spot, Player player){
        tilesToTurn.clear();
        ArrayList<Node> nodes = b.getNodes();
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
                        ArrayList<Integer> moves = isLegalMove(movement.get(i), nodes, newCol, newRow, player);
                        if(moves != null && moves.size() > 0){
                            tilesToTurn.add(spot);
                            tilesToTurn.addAll(moves);
                        }
                    }
                }
            }
        }
    }public ArrayList<Integer> isLegalMove(ArrayList<Integer> move, ArrayList<Node> nodes, int col, int row, Player player){
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

    public ArrayList<Node> cloneNodes(ArrayList<Node> temp){
        ArrayList<Node> ret = new ArrayList<>();
        for(Node n : temp){
            Node newNode = new Node(n.getSpot());
            newNode.setPlayer(n.getPlayer());
            ret.add(newNode);
        }
        return ret;
    }
    public void setBoard(Board b){
        this.b = b;
    }
    public void setNodes(ArrayList<Node> b){
        nodes = cloneNodes(b);
    }
}
