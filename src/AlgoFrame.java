import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class AlgoFrame extends Application {

    public static String title = "Maze Solver";
    public static String filename = "map.in";
    public static MazeData data = new MazeData(filename);
    public static GridPane pane = new GridPane();
    public static BorderPane bp = new BorderPane();
    public static VBox vBox = new VBox();
    public Algorithm algorithm = new Algorithm();
    private int ClickCount1 = 0, ClickCount2 = 0;
    private Scene scene;
    private Paint color;

    @Override
    public void start(Stage primaryStage) throws Exception {
        double SceneWidth = (data.GetRow()) * AlgoVisHelper.blockSide;
        double SceneHeight = (data.GetCol() + 2) * AlgoVisHelper.blockSide;
        vBox.getChildren().add(pane);
        vBox.getChildren().add(bp);
        bp.setPadding(new Insets(0,0,0,0));
        bp.setLeft(AlgoVisHelper.setText(0)); //刷新分数
        bp.setRight(AlgoVisHelper.setText(0));
        CreateNodes();
        DrawMap();
        scene = new Scene(vBox, SceneWidth, SceneHeight);
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
                if (ClickCount1 < MazeData.BlueBlockNumbers) {
                    DrawMap();
                    algorithm.run(true);
                }
                bp.setLeft(AlgoVisHelper.setText(algorithm.getREDScore())); //刷新分数
                bp.setRight(AlgoVisHelper.setText(algorithm.getBLUEScore()));
                data.reset();
                ClickCount1++;
            }
        });
        next.setPrefSize(50,50);
        bp.setCenter(next);
    }

    public void DrawMap() {
        for (int i = 0; i < data.GetRow(); i++) {
            for (int m = 0; m < data.GetCol(); m++) {
                if (data.getMaze(i, m) == MazeData.WALL) {
                    color = Color.ORANGE;
                } else if (data.getMaze(i, m) == MazeData.ROAD) {
                    color = Color.WHITE;
                } else if (data.getMaze(i, m) == MazeData.BlueBlock) {
                    color = Color.BLUE;
                } else {
                    color = Color.RED;
                }
                pane.add(AlgoVisHelper.drawRectangle(color), m, i); //先列后行
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
