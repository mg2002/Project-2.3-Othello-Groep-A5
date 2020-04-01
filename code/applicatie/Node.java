package code.applicatie;

public class Node {
    private int spot;
    private Player player;
    private int value;

    public Node(int newSpot){
        spot = newSpot;
    }

    public String getPlayerName() {
        String name;
        if(player != null) {
            if (player.getSide() == 1) {
                name = "\u001B[47m B";
            } else if (player.getSide() == 0) {
                name = "\u001B[40m W";
            } else {
                System.out.println("ERROR IN GETTING SIDE");
                name = "NULL";
            }
        }else{
            name = "\u001B[43m N";
        }
        return name;
    }

    public int getValue(){return value;}
    public void setValue(int newValue){value = newValue;}
    public void addValue(int newValue){value += newValue;}
    public int getSpot(){return spot;}
    public void setSpot(int newSpot){spot = newSpot;}
    public Player getPlayer(){return player;}
    public void setPlayer(Player newPlayer){player = newPlayer;}
    public void reset(){player = null;value = 0;}
}
