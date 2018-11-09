import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
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
    public static SplitPane sp = new SplitPane();
    public Algorithm algorithm = new Algorithm();
    private Scene scene;
    private Paint color;

    @Override
    public void start(Stage primaryStage) throws Exception {
        double SceneWidth = (data.GetRow()) * AlgoVisHelper.blockSide;
        double SceneHeight = (data.GetCol() + 2) * AlgoVisHelper.blockSide;
        bp.setPadding(new Insets(0,0,0,0));
        bp.setLeft(AlgoVisHelper.setText(0)); //刷新分数
        bp.setRight(AlgoVisHelper.setText(0));
        sp.getItems().addAll(pane,bp);
        sp.setOrientation(Orientation.VERTICAL);
        CreateNodes();
        DrawMap();
        scene = new Scene(sp, SceneWidth, SceneHeight);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

    public void CreateNodes() {
        Button next = new Button("Next");
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                algorithm.run();
                bp.setLeft(AlgoVisHelper.setText(algorithm.getREDScore())); //刷新分数
                bp.setRight(AlgoVisHelper.setText(algorithm.getBLUEScore()));
                data.reset();
            }
        });
        next.setPrefSize(50,25);
        bp.setCenter(next);
    }

    public void DrawMap() {
        for (int i = 0; i < data.GetRow(); i++) {
            for (int m = 0; m < data.GetCol(); m++) {
                if (data.getMaze(i, m) == MazeData.WALL) {
                    color = Color.ORANGE;
                } else if (data.getMaze(i, m) == MazeData.ROAD) {
                    color = Color.WHITE;
                } else if (data.getMaze(i, m) == MazeData.StartPoint) {
                    color = Color.RED;
                } else {
                    color = Color.BLUE;
                }
                pane.add(AlgoVisHelper.drawRectangle(color), m, i); //先列后行
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
