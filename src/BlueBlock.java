import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BlueBlock extends Position{
    private int score;
    private int position;
    public static int BlueBlockNumbers = MazeData.BlueBlockNumbers;
    public static int[] BlueScore = MazeData.BlueScore;
    public static MazeData data = AlgoFrame.data;
    public static GridPane pane = AlgoFrame.pane;

    public int[] getBlueScore() {
        return BlueScore;
    }

    public void setBlueScore(int[] blueScore) {
        BlueScore = blueScore;
    }

    public BlueBlock(int x, int y, int score, int position){
        super(x,y);
        this.score = score;
        this.position = position;

    }

    public int getPosition() {
        return position;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Position prevBlock(Algorithm.Side side){
        int y = 2; //default y
        BlueBlock curBlock = this;
        if(side == Algorithm.Side.Right){
            y = curBlock.getY()+1; //从右边出发向右移动
        } else if(side == Algorithm.Side.Left){
            y = curBlock.getY()-1;
        }
        return new Position(curBlock.getX(),y);
    }

    public BlueBlock nextBlock(Algorithm.Side side){
        int y = 2; //default y
        int pos = 0; //default position
        BlueBlock curBlock = this;
        if(side == Algorithm.Side.Left){
            y = curBlock.getY()+1; //从左边出发向右移动
            pos = curBlock.getPosition()+1;
        } else if(side == Algorithm.Side.Right){
            y = curBlock.getY()-1;
            pos = curBlock.getPosition()-1;
        }
        BlueBlockNumbers--;
        return new BlueBlock(curBlock.getX(),y,BlueScore[pos],pos);
    }

    public void cancelCurrentBlock(){
        data.setMaze(this.getX(),this.getY(),'0'); //将当前方块设为路
        //清空路径属性
        data.visited[this.getX()][this.getY()] = false;
        data.pathLeft[this.getX()][this.getY()] = false;
        data.pathRight[this.getX()][this.getY()] = false;
        //取消方块分数
        this.setScore(0);
        pane.add(AlgoVisHelper.drawRectangle(Color.YELLOW),this.getY(),this.getX());
    }
}
