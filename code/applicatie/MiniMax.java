package code.applicatie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

class MiniMax {
    private int depth;
    private ArrayList<MiniMaxGame> games;

    MiniMax(int newDepth){
        depth = newDepth;
        games = new ArrayList<>();
        initilizGame();

    }

    private void step() {
        for(MiniMaxGame mb : games){
            Reversi game = (Reversi) mb.getGame();
            Ai p1 = (Ai)game.getGameboard().getPlayerOne();
            Ai p2 = (Ai)game.getGameboard().getPlayerTwo();
            if (p1.getLegitNodes().size() == 0 && p2.getLegitNodes().size()==0){

            }else{
            }
            game.getGameboard().getBoardState();

            try{
                Thread.sleep(100);
            }catch (Exception e){

            }
        }
        // base case
//        if (depth <= 0 || ){
//            return node;
//        }
//
//        Board tempBoard = boardCopy;
//        boardCopy.findLegitNodes;
//
//        if (isMaxing) {
//            int maxEval = -inf;
//            Node bestNode = findHighestTile();
//            if (bestNode.getValue() > maxEval) { maxEval = bestNode.getValue(); }
//            tempBoard.doMove(bestNode);
//            minimax(tempBoard, depth-1, false);
//            }
//        else {
//            int minEval = inf;
//            Node bestNode = findLowestTile();
//            if(bestNode.getValue() < minEval) { minEval = best.nodeGetvalue(); }
//            tempBoard.doMove(bestNode);
//            minimax(tempBoard, depth-1, true);
//        }
    }
//    public static void main(String[] agrs) throws IOException {
//        MiniMax miniMax = new MiniMax(5);
//        while(true) {
//            miniMax.step();
//        }
//    }

    private MiniMaxGame initilizGame(){
        try{
            Player p1, p2;
            p1 = new Ai();
            p2 = new Ai();
            Board b = new Board();
            Reversi game = new Reversi(b);

            b.reversi();
            game.setPlayers(p1,p2);
            MiniMaxGame temp = new MiniMaxGame(game, 0);

            b.setPlayerOne(p1);
            b.setPlayerTwo(p2);

            p1.setSide(0);
            p2.setSide(1);
            p1.setBoard(b);
            p2.setBoard(b);

            games.add(temp);
            game.step();
            return temp;

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void Main(String[] args){
        MiniMax m = new MiniMax(5);
    }
}