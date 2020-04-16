package code.applicatie;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Application {
    private ViewController controller;
    public GameView(){
        controller = new ViewController();
    }
    public void start(Stage stage) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("reversi.fxml"));
        controller.createBoard();
        stage.setScene(new Scene(root, 335, 420));
        stage.setTitle("Reversi");
        stage.show();
    }

    public void instructions(ActionEvent event){
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

    public void quit(ActionEvent event){
        Platform.exit();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
