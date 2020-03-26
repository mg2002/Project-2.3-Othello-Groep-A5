import java.util.Scanner;

public class Game {
    public Game(){

    }
    public static void main(String[] agrs){
        GameType game = new GameType();
        Scanner scanner = new Scanner(System.in);

        System.out.println("What side am i?");
        while( !game.asignSides(scanner.next()) ){

        }
        while(!game.getEnd()){
            game.step();
        }
    }
}
