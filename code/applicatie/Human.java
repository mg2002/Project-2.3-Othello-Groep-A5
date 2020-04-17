package applicatie;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Human extends Player {
    private int points, side, active, currentMove;
    private Board board;
    private Communication comm;

    public Human(Communication c){
        comm = c;
        active = 0;
        side = -1;
        points = 2;
        currentMove = -1;
    }

    @Override
    public int getMove(){ return currentMove;}
    public void setMove(int newMove){ currentMove = newMove;}

    @Override
    public int getPoints(){return points;}
    @Override
    public int getSide(){return side;}
    @Override
    public int getActive(){return active;}
    @Override
    public void setPoints(int points){this.points = points;}
    @Override
    public void setSide(int newSide){side = newSide;}
    public void setActive(){active = 1;}
    public void setBoard(Board newBoard){board = newBoard;}

}
