package code.applicatie;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Human extends Player {
    private int points, side, active;
    private Board board;

    public Human(){
        active = 0;
        side = -1;
        points = 2;
    }

    @Override
    public int getMove(){
        Boolean legit = false;

        Scanner scanner = new Scanner(System.in);
        int move = 0;
        while(!legit){
            legit = true;
            try {
                move = parseInt(scanner.next());
            }catch(NumberFormatException e){
                legit = false;
            }
        }
        return move;
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
    public void setActive(){active = 1;}
    public void setBoard(Board newBoard){board = newBoard;}

}
