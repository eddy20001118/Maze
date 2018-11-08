import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class AlgoVisHelper {
    public static double blockSide = 50.0;
    private AlgoVisHelper() {
    }


    public static Rectangle drawRectangle(Paint color){
        Rectangle rectangle = new Rectangle(blockSide, blockSide, color);
        rectangle.setStroke(Color.GREY);
        return  rectangle;
    }

    public static TextField setText(int te){
        String text = Integer.toString(te);
        TextField tfMessage = new TextField(text);
        tfMessage.setStyle("-fx-text-fill: black");
        tfMessage.setPrefSize(50,50);
        tfMessage.setAlignment(Pos.CENTER);
        tfMessage.setFont(Font.font("Times", 13));
        return tfMessage;
    }
}
