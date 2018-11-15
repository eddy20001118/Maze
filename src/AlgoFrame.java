import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class AlgoFrame extends Application {

    public static String title = "Maze Solver";
    public static String filename = "map.in";
    public static MazeData data = new MazeData(filename);
    public static GridPane pane = new GridPane();
    public static BorderPane bp = new BorderPane();
    public static VBox vb = new VBox();
    public Algorithm algorithm = new Algorithm();
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        double SceneWidth =  data.GetRow() * (AlgoVisHelper.blockSide +0.5) - 2;
        double SceneHeight = data.GetCol() * (AlgoVisHelper.blockSide +1) + 85;
        pane.setStyle("-fx-background-color: white");
        bp.setPadding(new Insets(10,5,10,5));
        bp.setLeft(AlgoVisHelper.setScore(0)); //刷新分数
        bp.setRight(AlgoVisHelper.setScore(0));
        vb.getChildren().add(pane);
        vb.getChildren().add(bp);
        vb.setStyle("-fx-background-color: white;");
        CreateNodes();
        AlgoVisHelper.DrawMap();
        scene = new Scene(vb,SceneWidth,SceneHeight);
        scene.getStylesheets().add(getClass().getResource("res/components.css").toExternalForm());
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public void CreateNodes() {
        JFXButton next = new JFXButton("Go!");
        next.getStyleClass().add("button-raised");
        
        next.setOnMouseEntered(event -> {
            next.setStyle("-fx-background-color: #008074");
        });

        next.setOnMouseExited(event -> {
            next.setStyle("-fx-background-color: #009688");
        });

        next.setOnAction(event -> {
            algorithm.run();
            bp.setLeft(AlgoVisHelper.setScore(algorithm.getREDScore())); //刷新分数
            bp.setRight(AlgoVisHelper.setScore(algorithm.getBLUEScore()));
            data.reset();
        });
        next.setPrefSize(80,AlgoVisHelper.blockSide);
        bp.setCenter(next);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
