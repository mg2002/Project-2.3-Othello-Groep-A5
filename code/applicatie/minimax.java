//package applicatie;
//
//import java.util.ArrayList;
//
///**
// * have an array of boards
// * 1 reversi object with removeable boards.
// *  have a customobject that contains the move + board + if at the end of the move the ai won
// */
//public class minimax {
//    Reversi current;
//    ArrayList<Reversi> b;
//    minimax(Reversi newCurrent){
//        current = newCurrent;
//        b = new ArrayList<>();
//    }
//    public void step(){
//        Reversi r = initializeGame();
//
//    }
//
//
//    public Reversi initializeGame() {
//        try {
//            Player p1, p2;
//            p1 = new Ai();
//            p2 = new Ai();
//            Board b = new Board();
//            Reversi game = new Reversi(b);
//
//            b.reversi();
//            game.setPlayers(p1, p2);
//
//            b.setPlayerOne(p1);
//            b.setPlayerTwo(p2);
//
//            p1.setSide(0);
//            p2.setSide(1);
//            p1.setBoard(b);
//            p2.setBoard(b);
//
//            game.step();
//            return game;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//    public static void Main(String[] args){
//        minimax m = new minimax();
//    }
//}
