import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AlgoVisHelper {
    public static double blockSide = 50.0;
    public static Color orange = Color.web("rgb(255, 109, 0)");
    public static Color red = Color.web("rgb(255, 23, 68)");
    public static Color blue = Color.web("rgb(65,105,225)");
    public static Color white = Color.web("rgb(245, 245, 245)");
    public static Color REDpath = Color.web("rgb(206, 147, 216)");
    public static Color BLUEpath = Color.web("rgb(0, 188, 212)");
    private AlgoVisHelper() {
    }


    public static Rectangle drawRectangle(Paint color){
        Rectangle rectangle = new Rectangle(blockSide, blockSide, color);
        rectangle.setStroke(Color.web("#ffffff"));
        rectangle.setStrokeWidth(1);
        return  rectangle;
    }

    public static JFXTextField setText(int te){
        String text = Integer.toString(te);
        JFXTextField tfMessage = new JFXTextField(text);
        tfMessage.setPrefSize(blockSide,blockSide/2);
        tfMessage.setAlignment(Pos.CENTER);
        tfMessage.setFont(Font.font("Times", 14));
        tfMessage.setStyle("text-field");

        return tfMessage;
    }

    public static BorderPane setScore(int te){
        String text = Integer.toString(te);
        BorderPane pane = new BorderPane();
        pane.setPrefSize(80,blockSide);
        Text tf = new Text(text);
        tf.setFont(Font.font("Times", 17));
        pane.setCenter(tf);
        return pane;
    }
}
