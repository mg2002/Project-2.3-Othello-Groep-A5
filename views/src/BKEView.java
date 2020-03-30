import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class BKEView extends Application {
    private Image img1;
    private Image img2;

    public BKEView(){
        this.img1 = new Image("x.gif");
        this.img2 = new Image("o.gif");
    }
    public void start(Stage stage){
        BorderPane bp = new BorderPane();
        GridPane pane = new GridPane();
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                StackPane stp = new StackPane();
                Rectangle rect = new Rectangle(100, 100);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                stp.getChildren().addAll(rect);
                pane.add(stp, i, j);
            }
        }
        HBox hBox = new HBox(20);
        hBox.setPadding(new Insets(15, 12, 15, 12));
        ImageView player1icon = new ImageView(img1);
        ImageView player2icon = new ImageView(img2);
        Button quit = new Button("Quit");
        quit.setOnAction(e -> Platform.exit());
        hBox.getChildren().addAll(new Label("Player 1"), player1icon, new Label("CPU1"), player2icon, quit);
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(e -> {
            stage.close();
            stage.show();
        });
        MenuItem helpItem = new MenuItem("Instructions");
        helpItem.setOnAction(e -> instructions());
        helpMenu.getItems().add(helpItem);
        fileMenu.getItems().addAll(newGame);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        VBox vb = new VBox(menuBar);
        bp.setTop(vb);
        bp.setCenter(pane);
        bp.setBottom(hBox);
        stage.setScene(new Scene(bp, 300, 400));
        stage.setTitle("Tic-tac-toe");
        stage.show();
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

    public static void main(String[] args) {
        launch(args);
    }
}
