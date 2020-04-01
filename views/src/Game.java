import java.io.IOException;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    Board board = new Board();
    GameType game = new GameType();
    Player ai, p1, p2;

    public Game() {
        System.out.println("What game would you like to play?");
        while (!assignGame(scanner.next())) {

        }
        System.out.println("What side is the AI?");
        while (!assignSides(scanner.next())) {

        }
    }
    public void run() throws IOException {
        while(!game.getEnd()){
            game.step();
        }
    }

    public static void main(String[] agrs) throws IOException {
        Game game = new Game();
        game.run();
    }

    public Boolean assignSides(String side){
        if(side.equals("white") || side.equals("X") || side.equals("x")){
            p1 = ai;
            p2 = new Human();
            System.out.println("AI is white");
        }else if(side.equals("black")|| side.equals("O") || side.equals("o") || side.equals("0")){
            p1 = new Human();
            p2 = ai;
            System.out.println("AI is black");
        }else{
            System.out.println("ERROR IN ASSIGNING SIDES. gave an illegal side argument");
            System.out.println("Given argument: " + side);
            return false;
        }

        board.setPlayerOne(p1);
        board.setPlayerTwo(p2);

        p1.setSide(0);
        p2.setSide(1);
        game.setPlayers(p1, p2);
        p1.setBoard(board);
        p2.setBoard(board);
        return true;
    }

    private Boolean assignGame(String selectedGame){
        switch(selectedGame){
            case("Reversi"):
                game = new Reversi(board);
                board.reversi();
                ai = new Ai();
                break;
            case("TicTacToe"):
                game = new TicTacToe(board);
                board.ticTacToe();
                ai = new Tai();
                break;
            default:
                System.out.println("ERROR: wrong game selected.");
                return false;
        }
        return true;
    }



}
