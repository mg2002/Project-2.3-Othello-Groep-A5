package applicatie;

import javafx.application.Application;
import javafx.application.Platform;
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
    public ReversiView() throws IOException {
        this.icon1 = new Circle(20);
        this.icon2 = new Circle(20);
        icon2.setStroke(Color.BLACK);
        icon2.setFill(Color.WHITE);
        view = this; // singleton design pattern
        this.reversi = new Game();
        this.gameType = reversi.game;
        this.panes = new HashMap<>();
        this.controller = new GameController(gameType);
        this.pane = drawBoard();
        this.label1 = new Label("Player\n" +
                "Score: " + 2);
        this.label2 = new Label("CPU\n" +
                "Score: " + 2);
    }
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
    public void setLabel1(){
        Player p = controller.getPlayers().get(0);
        if(p instanceof Ai && p.getSide() == 1){
            label1.setText("CPU\n" +
                    "Score: " + p.getPoints());
        }
        else{
            label1.setText("Player\n" +
                    "Score: " + p.getPoints());
        }
    }
    public void setLabel2(){
        Player p = controller.getPlayers().get(1);
        if(p instanceof Human && p.getSide() == 0){
            label2.setText("Player\n" +
                    "Score: " + p.getPoints());
        }
        else{
            label2.setText("CPU\n" +
                    "Score: " + p.getPoints());
        }
    }

    public void launchGame(Stage stage){
        BorderPane bp = new BorderPane();
        HBox box = new HBox(20);
        box.setPadding(new Insets(15, 12, 15, 12));
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(e -> {
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
            this.pane = drawBoard();
            bp.setCenter(pane);
        });
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
        login(stage);
    }

    public boolean checkEnd(){
        for(Map.Entry<Integer, StackPane> entry : panes.entrySet()){
            if(entry.getValue().getChildren().size() < 2){
                return false;
            }
        }
        return true;
    }

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
                            b.setOnAction(a -> Platform.exit());
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
    public boolean checkPane(int s){
        Player p = controller.getNodes().get(s).getPlayer();
        return p == null;
    }

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
     * Draw icons
     */
    public void updateViews(){
        drawPlayerIcon();
        drawAIIcon();
    }

    /**
     * This is the login prompt before logging into the game (not working because
     * the client automatically waits for server to start tournament after AI has
     * logged in)
     * @param stg stage to appear after the login has been done.
     */
    public void login(Stage stg){
        Stage stage = new Stage();
        BorderPane bp = new BorderPane();
        Label l = new Label("Type a username: ");
        TextField field = new TextField();
        String loginName = field.getText();
        Button ok = new Button("Login");
        ok.setOnAction(e -> {
            try {
                if(reversi.comm.logIn(loginName)){
                    launchGame(stg);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        bp.setTop(l);
        bp.setCenter(field);
        bp.setBottom(ok);
        stage.setScene(new Scene(bp, 400, 250));
        stage.show();
    }

    public String getWinner(){
        Player p1 = controller.getPlayers().get(0);
        Player p2 = controller.getPlayers().get(1);
        if((p1 instanceof Human && p1.getPoints() > p2.getPoints()) || (p2 instanceof Human && p2.getPoints() > p1.getPoints())){
            return "Player wins!";
        }
        else if((p1 instanceof Human && p1.getPoints() < p2.getPoints()) || (p2 instanceof Human && p2.getPoints() < p1.getPoints())){
            return "CPU wins!";
        }
        else{
            return "It's a draw!";
        }
    }
}