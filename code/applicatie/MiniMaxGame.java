package applicatie;

public class MiniMaxGame {
    GameType b;
    int d;
    MiniMaxGame(GameType newBoard, int newD){
        b = newBoard;
        d = newD;
    }

    public int getDepth(){return d;}
    public GameType getGame(){return b;}
    public void setGame(GameType newGame){b = newGame;}
    public void setBoard(int newDepth){d = newDepth;}
}