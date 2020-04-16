package code.applicatie;

import java.io.IOException;
import java.util.ArrayList;

public class Reversi extends GameType{
    private Board gameboard;
    private ArrayList<Player> players;
    private Boolean turn, end;
    private Communication comm;
    private ArrayList<ArrayList<Integer>> movement = new ArrayList<>();// x, y
    private ArrayList<Integer> tilesToTurn = new ArrayList<>();// x, y

    public Reversi(Board newGameboard) throws IOException {
        gameboard = newGameboard;
        end = false;
        turn = false;    //true == player one's (white) turn. false == player two's (black) turn

        comm = new Communication();
        players = new ArrayList<>(2);

        ArrayList<Integer> up           = new ArrayList<>();
        ArrayList<Integer> upRight      = new ArrayList<>();
        ArrayList<Integer> upLeft       = new ArrayList<>();
        ArrayList<Integer> right        = new ArrayList<>();
        ArrayList<Integer> downRight    = new ArrayList<>();
        ArrayList<Integer> down         = new ArrayList<>();
        ArrayList<Integer> downLeft     = new ArrayList<>();
        ArrayList<Integer> left         = new ArrayList<>();
        up.add(0);
        up.add(1);
        upRight.add(1);
        upRight.add(1);
        upLeft.add(-1);
        upLeft.add(1);
        right.add(1);
        right.add(0);
        down.add(0);
        down.add(-1);
        downLeft.add(-1);
        downLeft.add(-1);
        downRight.add(1);
        downRight.add(-1);
        left.add(-1);
        left.add(0);
        movement.add(up);
        movement.add(upRight);
        movement.add(upLeft);
        movement.add(right);
        movement.add(downRight);
        movement.add(down);
        movement.add(downLeft);
        movement.add(left);
    }

    /**
     * doet een stap voor de ai of human | dit wordt alleen in combinatie met de gui gebruikt
     */
    public void step(){
        int temp = -1;
        gameboard.getBoardState();
        Player p;
        if(turn){
            //Ai's turn
            turn = false;
            System.out.println("AI's Turn = ");
            temp = players.get(0).getMove();
            p = players.get(0);
            System.out.println(temp);
        }else{
            turn = true;
            System.out.println("Humans Turn = ");
            temp = players.get(1).getMove();
            p =players.get(1);
        }
        doMove(temp, p);
        System.out.println("\u001b[0m");
    }
    
    public int MiniMaxStep(){ // alleen voor human vs ai
        int temp = -1;
        gameboard.getBoardState();
        Player p;
        if(turn){
            //Ai's turn
            turn = false;
            System.out.println("AI's Turn = ");
            temp = players.get(0).getMove();
            p = players.get(0);
            System.out.println(temp);
        }else{
            turn = true;
            System.out.println("Humans Turn = ");
            temp = players.get(1).getMove();
            p =players.get(1);
            System.out.println(temp);
        }
        if(temp == -1){

        }else{
            doMove(temp, p);
            controller.update(p);
        }
        System.out.println("\u001b[0m");
        return temp;
    }

    /**
     * de stap dat de ai wil gaan maken
     * @param move de move dat gedaan wordt
     * @param p de speler die de move doet
     */
    public void doMove(int move, Player p) {
        lookAround(move, p);
        gameboard.doMove(tilesToTurn, p);
    }

    /**
     * kijk naar mogelijke fisches die omgezet moetten worden
     * @param spot
     * @param player
     */
    public void lookAround(int spot, Player player){
        tilesToTurn.clear();
        ArrayList<Node> nodes = gameboard.getNodes();
        int row = (int) Math.floor(spot/8);
        int col = spot%8;
        int newCol;
        int newRow;
        for(int i = 0; i < movement.size(); i++) {
            newCol = col + movement.get(i).get(0);
            if(newCol < 8 && newCol > -1){
                newRow = row + movement.get(i).get(1);
                if(newRow < 8 && newRow > -1){
                    if(nodes.get(spot).getPlayer() == null){
                        tilesToTurn.add(spot);
                        ArrayList<Integer> moves = isLegalMove(movement.get(i), nodes, newCol, newRow, player);
                        if(moves != null){
                            tilesToTurn.addAll(moves);
                        }
                    }
                }
            }
        }
    }

    /**
     * of de fisches in een bepaalde rigting omgezet moetten worden
     * @param move de move directie waarin gekeken wordt
     * @param nodes lijst met alle nodes
     * @param col de col
     * @param row de row
     * @param player de player die de move wilt maken
     * @return of de fisches omgezet moetten worden
     */
    public ArrayList<Integer> isLegalMove(ArrayList<Integer> move, ArrayList<Node> nodes, int col, int row, Player player){
        ArrayList<Integer> possible = new ArrayList<>();
        int possibleSpot;
        while((col > -1 && col < 8) && (row > -1 && row< 8)){
            possibleSpot = row*8 + col;
            if(nodes.get(possibleSpot).getPlayer() == null){
                possible.clear();
                return possible;
            }else{
                if(player != null) {
                    if (nodes.get(possibleSpot).getPlayer().getSide() == player.getSide()) {
                        return possible;
                    } else {
                        possible.add(row * 8 + col);
                        col = col + move.get(0);
                        row = row + move.get(1);
                    }
                }
            }
        }
        possible.clear();
        return possible;
    }

    /**
     * zet de spelers goed in de array en zegt tegen de board klasse welke spele wit is en welke swart
     * @param playerOne
     * @param playerTwo
     */
    public void setPlayers(Player playerOne, Player playerTwo){
        players.add(playerOne);
        players.add(playerTwo);

        //Asing the middle nodes to players
        gameboard.setNode(27,players.get(0));
        gameboard.setNode(36,players.get(0));
        gameboard.setNode(28,players.get(1));
        gameboard.setNode(35,players.get(1));
    }

    /**
     * returend de instansie van board
     * @return de board isntancie
     */
    public Board getGameboard(){
        return gameboard;
    }
    public Boolean getEnd(){return end;}
}
