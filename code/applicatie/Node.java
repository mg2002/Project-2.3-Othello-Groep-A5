package applicatie;

public class Node {
    private int spot;
    private Player player;
    private int value;
    private boolean isWinning = false;
    
    /**
     * A node is a position a Player can make a move in, or has already played a move in
     * its spot indicates where on the Board the Node lies. A Reversi Board has 64 Nodes
     * its initial value is determined by its position. Corner Nodes have the highest value,
     * then edges, then regular Nodes. Buffer Nodes have a negative value and are therefor
     * the least favorable Nodes to make a move on.
     * @param newSpot
     */
    public Node(int newSpot){
        value = 0;
        spot = newSpot;
        
        // Value corner, buffer and edge Nodes based on their position
        switch(newSpot) {
            case 0:         // Values for corner Nodes
            case 7:
            case 56:
            case 63:
                value = 99;
                break;
                
            case 1:         // Values for buffers (Nodes around corners)    upper left
            case 8:
            case 9:
            case 6:         // upper right
            case 14:
            case 15:
            case 48:        // bottom left
            case 49:
            case 57:
            case 54:        // bottom right
            case 55:
            case 62:
                value = -24;
                break;
    
            case 2:         // Values for edges | top
            case 3:
            case 4:
            case 5:
            case 16:        // left
            case 24:
            case 32:
            case 40:
            case 23:        // right
            case 31:
            case 39:
            case 47:
            case 58:        // bottom
            case 59:
            case 60:
            case 61:
                value = 5;
                break;
        }
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
    public void decValue(int newValue){value -= newValue;}

    public int getSpot(){return spot;}
    public void setSpot(int newSpot){spot = newSpot;}
    public Player getPlayer(){return player;}
    public void setPlayer(Player newPlayer){player = newPlayer;}
    public void reset(){player = null;value = 0;}
    
    public boolean isWinning() { return isWinning; }
}
