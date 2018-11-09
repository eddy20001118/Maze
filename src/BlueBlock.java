
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BlueBlock extends Position{
    private int score;
    private int position;
    public static MazeData data = AlgoFrame.data;
    public static GridPane pane = AlgoFrame.pane;
    private int BlueBlockNumbers = data.getBlockNumbers();
    public static int curBlockNumber = data.getBlockNumbers();
    public static int[] BlueScore = data.getBlueScore();

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
        curBlockNumber--;
        return new BlueBlock(curBlock.getX(),y,BlueScore[pos],pos);
    }

    public void cancelCurrentBlock(Algorithm.Player curPlayer){
        data.setMaze(this.getX(),this.getY(),'0'); //将当前方块设为路
        //清空路径属性
        data.visited[this.getX()][this.getY()] = false;
        //取消方块分数
        this.setScore(0);
        if(curPlayer == Algorithm.Player.RED){
            pane.add(AlgoVisHelper.drawRectangle(Color.YELLOW),this.getY(),this.getX());
        } else {
            pane.add(AlgoVisHelper.drawRectangle(Color.FORESTGREEN),this.getY(),this.getX());
        }
    }

}
