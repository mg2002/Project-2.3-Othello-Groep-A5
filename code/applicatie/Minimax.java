package applicatie;

import java.util.HashMap;
import java.util.Map;

/**
 * Een instantie van Minimax wordt aangemaakt wanneer de AI een zet gaat berekenen.
 * De klasse maakt gebruik van de posities op het speelbord, de functie van Ai die de valide zetten kan berekenen
 * en de functie die de waarde van de zetten berekent.
 *
 * Minimax heeft een recursieve methode: minimax, deze vertelt de Ai wat de beste zet is
 * @author Marnix
 */
public class Minimax{
    
    private Ai ai;
    
    public Minimax(){ }
    
    /**
     *
     */
     public void evaluateNodes() {
         for (Map.Entry<Integer, Node> entry : ai.getLegitNodes().entrySet()) {
             Node move = entry.getValue();
             move.setValue(minimax(move, ai.getBoard(), 5, true));
         }
     }
    
    public int minimax(Node move, Board board, int depth, boolean isMaxing) {
        if (depth <= 0 || move.isWinning()) { return move.getValue(); }
        // Probleem: nodes kunnen niet hun eigen waarde bepalen
        
        Board tempBoard = board;
        // TODO make move on tempBoard
        // TODO make new HashMap tempLegitNodes after doing move on tempBoard
        
        if (isMaxing) {
            int maxEval = -1000;
            // Probleem: op tempBoard moet de move worden gespeeld en opnieuw de legitNodes worden berekend
            // voor de andere speler
            HashMap<Integer, Node> legitNodes = new HashMap<>();
            for(Map.Entry<Integer, Node> childEntry : legitNodes.entrySet()) {
                Node childMove = childEntry.getValue();
                int eval = minimax(childMove, tempBoard, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        }
        
        else {
            int minEval = 1000;
            // Probleem: op tempBoard moet de move worden gespeeld en opnieuw de legitNodes worden berekend
            // voor de andere speler
            HashMap<Integer, Node> legitNodes = new HashMap<>();
            for(Map.Entry<Integer, Node> childEntry : legitNodes.entrySet()) {
                Node childMove = childEntry.getValue();
                int eval = -1 * minimax(childMove, tempBoard, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}
