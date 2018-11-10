import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private Paint color;

    @Override
    public void start(Stage primaryStage) throws Exception {
        double SceneWidth =  data.GetRow() * (AlgoVisHelper.blockSide +1)-10;
        double SceneHeight = data.GetCol() * (AlgoVisHelper.blockSide +1) + 85;
        pane.setStyle("-fx-background-color: white;");
        bp.setPadding(new Insets(10,5,10,5));
        bp.setLeft(AlgoVisHelper.setScore(0)); //刷新分数
        bp.setRight(AlgoVisHelper.setScore(0));
        vb.getChildren().add(pane);
        vb.getChildren().add(bp);
        vb.setStyle("-fx-background-color: white;");
        CreateNodes();
        DrawMap();
        scene = new Scene(vb,SceneWidth,SceneHeight);
        scene.getStylesheets().add(getClass().getResource("res/components.css").toExternalForm());
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public void CreateNodes() {
        JFXButton next = new JFXButton("Go!");
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                algorithm.run();
                bp.setLeft(AlgoVisHelper.setScore(algorithm.getREDScore())); //刷新分数
                bp.setRight(AlgoVisHelper.setScore(algorithm.getBLUEScore()));
                data.reset();
            }
        });
        next.setPrefSize(80,AlgoVisHelper.blockSide);
        next.getStyleClass().add("button-raised");
        bp.setCenter(next);
    }

    public void DrawMap() {
        for (int i = 0; i < data.GetRow(); i++) {
            for (int m = 0; m < data.GetCol(); m++) {
                if (data.getMaze(i, m) == MazeData.WALL) {
                    color = AlgoVisHelper.orange;
                } else if (data.getMaze(i, m) == MazeData.ROAD) {
                    color = AlgoVisHelper.white;
                } else if (data.getMaze(i, m) == MazeData.StartPoint) {
                    color = AlgoVisHelper.red;
                } else {
                    color = AlgoVisHelper.blue;
                }
                pane.add(AlgoVisHelper.drawRectangle(color), m, i); //先列后行
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
