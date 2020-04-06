//package code.applicatie;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//
//public class GameView extends Application {
//    public GameView(){
//
//    }
//    public void start(Stage stage){
//        BorderPane bp = new BorderPane();
//        GridPane pane = new GridPane();
//        //pane.setHgap(2);
//        //pane.setVgap(2);
//        for(int i = 0; i < 8; i++){
//            for(int j = 0; j < 8; j++){
//                StackPane stp = new StackPane();
//                Rectangle rectangle = new Rectangle(40, 40);
//                rectangle.setFill(Color.GREEN);
//                rectangle.setStroke(Color.BLACK);
//                stp.getChildren().addAll(rectangle);
//                pane.add(stp, i, j);
//            }
//        }
//        HBox box = new HBox(20);
//        box.setPadding(new Insets(15, 12, 15, 12));
//        Button button = new Button("Quit");
//        button.setOnAction(e -> Platform.exit());
//        Circle circle1 = new Circle(20);
//        Circle circle2 = new Circle(20);
//        circle2.setFill(Color.WHITE);
//        circle2.setStroke(Color.BLACK);
//        Menu fileMenu = new Menu("File");
//        Menu helpMenu = new Menu("Help");
//        MenuItem newGame = new MenuItem("New Game");
//        MenuItem helpItem = new MenuItem("Instructions");
//        helpMenu.getItems().add(helpItem);
//        fileMenu.getItems().add(newGame);
//        MenuBar menuBar = new MenuBar();
//        menuBar.getMenus().addAll(fileMenu, helpMenu);
//        VBox vb = new VBox(menuBar);
//        bp.setTop(vb);
//        box.getChildren().addAll(new Label("Player1"),
//                circle1,
//                new Label("CPU1"),
//                circle2,
//                button);
//        bp.setCenter(pane);
//        bp.setBottom(box);
//        stage.setScene(new Scene(bp, 330, 420));
//        stage.setTitle("Reversi");
//        stage.show();
//    }
//
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
