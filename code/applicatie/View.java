package applicatie;

import javafx.stage.Stage;

public interface View {
    void start(Stage stage);
    void drawPlayerIcon();
    void drawAIIcon();
    void instructions();
}

