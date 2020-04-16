package applicatie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is the JavaFX application for the view for Reversi.
 * It is also the general user interface.
 */
public class ReversiView extends Application implements View {
    public static ReversiView view;
    private Circle icon1;
    private Circle icon2;
    private GridPane pane;
    private GameController controller;
    private GameType gameType;
    public Game reversi;
    private HashMap<Integer, StackPane> panes;
    private Label label1;
    private Label label2;
    private long turnTimeLimit = 20000;
    private long elapsedTime = System.currentTimeMillis();
    private BorderPane bp;

    /**
     * Constructor of the Reversi view.
     * @throws IOException
     */
    public ReversiView() throws IOException {
        this.icon1 = new Circle(20);
        this.icon2 = new Circle(20);
        icon1.setStroke(Color.BLACK);
        icon1.setFill(Color.WHITE);
        view = this; // singleton design pattern
        this.reversi = new Game();
        this.gameType = reversi.game;
        this.panes = new HashMap<>();
        this.controller = new GameController(gameType);
        this.bp = new BorderPane();
        this.pane = drawBoard();
        this.label1 = new Label("Player\n" +
                "Score: " + 2);
        this.label2 = new Label("CPU\n" +
                "Score: " + 2);
    }
    /**
     * Set the icon that represents the side that the player is on.
     * @return a circle icon.
     */
    public Circle getPlayerIcon(){
        Player p = controller.getPlayers().get(0);
        if(p instanceof Human && p.getSide() == 0){
            Circle circle = new Circle(20);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITE);
            return circle;
        }
        else{
            return new Circle(20);
        }
    }

    /**
     * Set the icon that represents the side that the AI is on.
     * @return a circle icon.
     */
    public Circle getAIIcon(){
        Player p = controller.getPlayers().get(0);
        if(p instanceof Ai && p.getSide() == 0){
            Circle circle = new Circle(20);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITE);
            return circle;
        }
        else{
            return new Circle(20);
        }
    }
    /**
     * Set the first label so it corresponds to the players' side.
     */
    public void setLabel1(){
        Player p = controller.getPlayerOne();
        if(p instanceof Ai){
            label1.setText("CPU\n" +
                    "Score: " + p.getPoints());
        }
        else{
            label1.setText("Player\n" +
                    "Score: " + p.getPoints());
        }
    }

    /**
     * Set the second label so it corresponds to the players' side.
     */
    public void setLabel2(){
        Player p = controller.getPlayerTwo();
        if(p instanceof Human){
            label2.setText("Player\n" +
                    "Score: " + p.getPoints());
        }
        else{
            label2.setText("CPU\n" +
                    "Score: " + p.getPoints());
        }
    }

    /**
     * Launches the JavaFX application and the game.
     * @param stage the stage passed in the function that will be shown.
     */
    public void launchGame(Stage stage){
        HBox box = new HBox(20);
        box.setPadding(new Insets(15, 12, 15, 12));
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(e -> newGame());
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(e -> Platform.exit());
        MenuItem helpItem = new MenuItem("Instructions");
        helpItem.setOnAction(e -> instructions());
        helpMenu.getItems().add(helpItem);
        fileMenu.getItems().addAll(newGame, quit);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        VBox vb = new VBox(menuBar);
        bp.setTop(vb);
        box.getChildren().addAll(label1,
                icon1,
                label2,
                icon2);
        bp.setCenter(pane);
        bp.setBottom(box);
        stage.setScene(new Scene(bp, 330, 420));
        stage.setTitle("Reversi");
        stage.show();
        if(controller.getPlayers().get(1) instanceof Ai && controller.getPlayers().get(1).getSide() == 1){
            gameType.MiniMaxStep();
        }
        setPoints();
    }
    public void start(Stage stage){
        launchGame(stage);
    }

    /**
     * Checks to see if all tiles are filled to see if the game ends.
     * @return true when all tiles have a circle on them.
     */
    public boolean checkEnd(){
        for(Map.Entry<Integer, StackPane> entry : panes.entrySet()){
            if(entry.getValue().getChildren().size() < 2){
                return false;
            }
        }
        return true;
    }

    /**
     * Starts a new game by resetting the board tiles.
     */
    public void newGame(){
        gameType.getGameboard().resetNodes();
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                StackPane stp = (StackPane) this.pane.getChildren().get(j*8+i);
                if(stp.getChildren().size() > 1){
                    for(Iterator<Node> it = stp.getChildren().iterator(); it.hasNext();){
                        javafx.scene.Node child = it.next();
                        it.remove();
                    }
                }
            }
        }
        setLabels();
        this.pane = drawBoard();
        bp.setCenter(pane);
    }

    /**
     * Opens the window with the instructions.
     */
    public void instructions(){
        Stage stage = new Stage();
        BorderPane b = new BorderPane();
        StackPane s = new StackPane();
        VBox v = new VBox();
        v.setPadding(new Insets(20, 0, 20, 125));
        Label instructions = new Label("Instructions\n\n" +
                "For this game there are black and white circles that need to be\n" +
                "placed on this board and each player has a different color.\n" +
                "If one player has the majority of the pieces of their color at the end,\n" +
                "that player wins." +
                "\n" +
                "The instructions do not apply to computers playing together\n" +
                "as they have been programmed to play.");
        Button quit = new Button("I understand the rules.");
        quit.setOnAction(e -> stage.close());
        s.getChildren().add(instructions);
        v.getChildren().add(quit);
        b.setCenter(s);
        b.setBottom(v);
        stage.setTitle("Instructions");
        stage.setScene(new Scene(b, 400, 250));
        stage.show();
    }

    /**
     * Draws the icons for the Player on the board
     */
    public void drawPlayerIcon(){
        if(!gameType.getEnd()){
            ArrayList<applicatie.Node> nodes = controller.getNodes();
            for (applicatie.Node node : nodes) {
                if (node.getPlayer() != null && node.getPlayer() instanceof Human) {
                    panes.get(node.getSpot()).getChildren().add(getPlayerIcon());
                }
            }
        }
    }

    /**
     * Draws the icons for the AI on the board
     */
    public void drawAIIcon(){
        if(!gameType.getEnd()){
            ArrayList<applicatie.Node> nodes = controller.getNodes();
            for(applicatie.Node node : nodes){
                if(node.getPlayer() != null && node.getPlayer() instanceof Ai){
                    panes.get(node.getSpot()).getChildren().add(getAIIcon());
                }
            }
        }
    }

    /**
     * Draws the reversi board
     * @return grid board
     */
    public GridPane drawBoard(){
        GridPane pane = new GridPane();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                StackPane stp = new StackPane();
                Integer position = j*8+i;
                Rectangle rectangle = new Rectangle(40, 40);
                stp.setOnMouseClicked(e -> {
                    if(checkPane(position)) {
                        if(controller.getPlayerOne() instanceof Human) {
                            controller.getPlayers().get(0).setMove(position);
                        }
                        else{
                            controller.getPlayers().get(1).setMove(position);
                        }
                        if(gameType.getEnd()){
                            Platform.exit();
                        }
                        gameType.MiniMaxStep();
                        gameType.MiniMaxStep();
                        if(checkEnd()){
                            Stage stg = new Stage();
                            BorderPane bp = new BorderPane();
                            Button b = new Button("OK");
                            b.setOnAction(a -> {
                                newGame();
                                stg.close();
                            });
                            bp.setTop(new Label("The game has been finished.\n" +
                                    getWinner()));
                            bp.setCenter(b);
                            stg.setScene(new Scene(bp, 200, 150));
                            stg.setTitle("Game end");
                            stg.show();
                        }
                        setPoints();
                        setLabels();
                    }
                });
                rectangle.setFill(Color.GREEN);
                rectangle.setStroke(Color.BLACK);
                stp.getChildren().addAll(rectangle);
                pane.add(stp, i, j);
                panes.put(position, stp);
            }
        }
        updateViews();
        return pane;
    }

    /**
     * Check if there is no player on a spot.
     * @param s the node to check on.
     * @return p == null == true
     */
    public boolean checkPane(int s){
        Player p = controller.getNodes().get(s).getPlayer();
        return p == null;
    }

    /**
     * Update the scores
     */
    public void setLabels(){
        setLabel1();
        setLabel2();
    }

    /**
     * Set player points to display the correct score on the view.
     */
    public void setPoints(){
        Player p1 = controller.getPlayers().get(0);
        Player p2 = controller.getPlayers().get(1);
        p1.setPoints(0);
        p2.setPoints(0);
        ArrayList<applicatie.Node> nodes = controller.getNodes();
        for(applicatie.Node node : nodes){
            if(node.getPlayer() != null){
                if(node.getPlayer() == p1){
                    p1.setPoints(p1.getPoints() + 1);
                }
                if(node.getPlayer() == p2){
                    p2.setPoints(p2.getPoints() + 1);
                }
            }
        }
    }

    /**
     * Draw icons (update the views everytime a move has been done)
     */
    public void updateViews(){
        drawPlayerIcon();
        drawAIIcon();
    }

    /**
     * Returns the winner of the current game.
     * @return String representing the winning player
     */
    public String getWinner(){
        Player p1 = controller.getPlayerOne();
        Player p2 = controller.getPlayerTwo();
        if((p1 instanceof Human && p1.getPoints() > p2.getPoints()) || (p2 instanceof Human && p2.getPoints() > p1.getPoints())){
            return "Player wins!\n" +
                    p1.getPoints() + " points vs " + p2.getPoints() + " points.";
        }
        else if((p1 instanceof Human && p1.getPoints() < p2.getPoints()) || (p2 instanceof Human && p2.getPoints() < p1.getPoints())){
            return "CPU wins!\n" +
                    p1.getPoints() + " points vs " + p2.getPoints() + " points.";
        }
        else if(p1.getPoints() == p2.getPoints()){
            return "It's a draw!";
        }
        return "";
    }
}