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
     * evaluateNodes bepaalt wat de waarde voor elke mogelijke move zou zijn door op elke legitNode minimax aan te roepen
     */
     public void evaluateNodes() {
         for (Map.Entry<Integer, Node> entry : ai.getLegitNodes().entrySet()) {
             Node move = entry.getValue();
             move.setValue(minimax(move, ai.getBoard(), 5, true));
         }
     }
    
    /**
     * Minimax is een methode die de waarde van een move teruggeeft. Dit doet hij door -depth- aantal keer de beste
     * move voor zichzelf en zijn tegenstander te berekenen.
     * Elke keer als hij een move berekent, maakt hij deze zet op een tempBoard, waarna hij kan gaan kijken wat de
     * beste volgende move is.
     *
     * @param move
     * @param board
     * @param depth
     * @param isMaxing geeft aan of de speler voor wie de methode wordt aangeroepen de maximaliserende speler is.
     * @return de waarde van de Node die waarschijnlijk tot winst leidt
     */
    public int minimax(Node move, Board board, int depth, boolean isMaxing) {
        if (depth <= 0 || move.isWinning()) { return move.getValue(); }
        // Probleem: nodes kunnen niet hun eigen waarde bepalen
        
        Board tempBoard = board;
        // TODO make move on tempBoard
        HashMap<Integer, Node> tempLegitNodes = new HashMap<>();
        // TODO ai.lookAround() on tempBoard to add valid moves to tempLegitNodes
        
        if (isMaxing) {
            int maxEval = -1000;
            // Probleem
            for(Map.Entry<Integer, Node> childEntry : tempLegitNodes.entrySet()) {
                Node childMove = childEntry.getValue();
                int eval = minimax(childMove, tempBoard, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        }
        
        else {
            int minEval = 1000;
            for(Map.Entry<Integer, Node> childEntry : tempLegitNodes.entrySet()) {
                Node childMove = childEntry.getValue();
                int eval = -1 * minimax(childMove, tempBoard, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
}
