package code.applicatie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class BKEView extends Application implements View {
    private Image img1;
    private Image img2;
    private GameType game;
    private Game game2;
    private GameController controller;
    private HashMap<Integer, StackPane> stackPanes;
    private GridPane pane;

    public BKEView() throws IOException {
        this.img1 = new Image("x.gif");
        this.img2 = new Image("o.gif");
        stackPanes = new HashMap<>();
        game2 = new Game();
        game = game2.game;
    }

    public void setController(){
        controller = new GameController(game);
    }
    public ImageView getPlayerIcon(){
        Player human = controller.getPlayers().get(0);
        if(human instanceof Human && human.getSide() == 0){
            return new ImageView(img1);
        }
        else{
            return new ImageView(img2);
        }
    }

    public ImageView getAIIcon(){
        Player ai = controller.getPlayers().get(0);
        if(controller.getPlayers().get(0) instanceof Tai){
            return new ImageView(img1);
        }
        else{
            return new ImageView(img2);
        }
    }
    public void start(Stage stage){
        Scanner scanner = new Scanner(System.in);
//        System.out.println("What side am i?");
//        while (!game2.asignSides(scanner.next())) {
//
//        }
        setController();
        BorderPane bp = new BorderPane();
        pane = getGrid();

        /*HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(20, 0, 20, 93.75));
        Label turnLabel;
        if(controller.getPlayers().get(0) instanceof Human){
            turnLabel = new Label(getTurn()?"X's Turn!":"O's Turn!");
        }
        else{
            turnLabel = new Label("asd");
            //turnLabel = new Label(getTurn()?"O's Turn!":"X's Turn!");
        }
        hbox.getChildren().add(turnLabel);*/
        HBox hBox = new HBox(20);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        ImageView player1icon = new ImageView(img1);
        ImageView player2icon = new ImageView(img2);
        Button quit = new Button("Quit");
        quit.setOnAction(e -> Platform.exit());
        hBox.getChildren().addAll(new Label("Player 1"), player1icon, new Label("Player 2"), player2icon, quit);
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(e -> {
                    game.getGameboard().resetNodes();
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            StackPane stp = (StackPane) this.pane.getChildren().get(j * 3 + i);
                            if (stp.getChildren().size() > 1) {
                                for(Iterator<javafx.scene.Node> it = stp.getChildren().iterator(); it.hasNext();){
                                    javafx.scene.Node child = it.next();
                                    it.remove();
                                }
                            }
                        }
                    }
                    this.pane = getGrid();
                    bp.setCenter(pane);
                }
        );
        MenuItem helpItem = new MenuItem("Instructions");
        helpItem.setOnAction(e -> instructions());
        helpMenu.getItems().add(helpItem);
        fileMenu.getItems().addAll(newGame);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        VBox vb = new VBox(menuBar);
        bp.setTop(vb);
        bp.setCenter(pane);
        //bp.setRight(hbox);
        bp.setBottom(hBox);
        stage.setScene(new Scene(bp, 300, 400));
        stage.setTitle("Tic-tac-toe");
        stage.show();
    }

    public GridPane getGrid(){
        GridPane pane = new GridPane();
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                int s = j * 3 + i;
                StackPane stp = new StackPane();
                Rectangle rect = new Rectangle(100, 100);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                stp.setOnMouseClicked(e -> {
                    if(checkPane(s)){
                        if(controller.getPlayers().get(0) instanceof Human) {
                            controller.getPlayers().get(0).setMove(s);
                        }
                        else{
                            controller.getPlayers().get(1).setMove(s);
                        }
                        stp.getChildren().add(getPlayerIcon());
                        game.step();
                        game.step();
                        drawPlayerIcon();
                        drawAIIcon();
                    }
                });
                stp.getChildren().addAll(rect);
                pane.add(stp,i, j);
                stackPanes.put(s, stp);
            }
        }
        return pane;
    }

    public boolean getTurn(){
        return game.getTurn();
    }
    public boolean checkPane(int s){
        return controller.getNodes().get(s).getPlayer() == null;
    }
    public void drawPlayerIcon(){
        if(!game.getEnd()){
            ArrayList<Node> nodes = controller.getNodes();
            for (Node node : nodes) {
                if (node.getPlayer() != null && node.getPlayer() instanceof Human) {
                    stackPanes.get(node.getSpot()).getChildren().add(getPlayerIcon());
                }
            }
        }
    }
    public void drawAIIcon(){
        if(!game.getEnd()){
            ArrayList<Node> nodes = controller.getNodes();
            for(Node node : nodes){
                if(node.getPlayer() != null && node.getPlayer() instanceof Tai){
                    stackPanes.get(node.getSpot()).getChildren().add(getAIIcon());
                }
            }
        }
    }

    public void instructions(){
        Stage stage = new Stage();
        BorderPane b = new BorderPane();
        StackPane s = new StackPane();
        VBox v = new VBox();
        v.setPadding(new Insets(20, 0, 20, 125));
        Label instructions = new Label("Instructions\n" +
                "Below are the instructions for Tic Tac Toe, however they don't\n" +
                "apply if two AIs are playing the game.\n" + "\n" +
                "The first player to mark three of his/her symbols in a row (also diagonally)\n" +
                "wins. If none of the players/AIs have 3 marks in a row, the match will be\n" +
                "treated as a draw.");
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

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    private class ThreeInARow{
        private StackPane tile1;
        private StackPane tile2;

        public ThreeInARow(StackPane tile1, StackPane tile2, StackPane tile3){

        }
    }
}
