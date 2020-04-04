package applicatie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Application {
    public GameView(){

    }
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("reversi.fxml"));
        stage.setScene(new Scene(root, 335, 420));
        stage.setTitle("Reversi");
        stage.show();
    }


    public void createBoard(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){

            }
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
