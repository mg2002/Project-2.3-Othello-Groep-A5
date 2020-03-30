import javafx.stage.Stage;

public interface View {
    void start(Stage stage);
    void update();
    void instructions();
    void scoreBoard();
}
