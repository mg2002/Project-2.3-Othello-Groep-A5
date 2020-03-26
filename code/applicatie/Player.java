public interface Player {
    public int getMove();
    public int getPoints();
    public int getSide();
    public int getActive();
    public void setPoints(int newActive);
    public void setSide(int setSide);
    public void setBoard(Board newboard);
}
