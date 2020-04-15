package applicatie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ViewController {

    @FXML
    private GridPane VisualBoard;

    public void initialize(){
        System.out.println("Controller initialized");
    }

    @FXML
    public void createBoard(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                StackPane stp = new StackPane();
                Rectangle rect = new Rectangle(40, 40);
                rect.setFill(Color.GREEN);
                rect.setStroke(Color.BLACK);
                stp.getChildren().add(rect);
                VisualBoard.add(stp, j, i);
            }
        }
    }

}

