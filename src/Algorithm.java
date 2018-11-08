import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Algorithm {
    public static MazeData data = AlgoFrame.data;
    public static GridPane pane = AlgoFrame.pane;

    public static enum Side {Left, Right}
    public static enum Player {RED, BLUE}

    private int REDScore = 0;
    private int BLUEScore = 0;
    private int ClickCount = 0;
    public LinkedList<Integer> ScoreQuene = new LinkedList<Integer>();
    private BlueBlock[] blueBlock = new BlueBlock[MazeData.getBlueBlockNumbers()];
    private static final int direction[][] = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; //9点钟方向逆时针
    private int runMethodCount = 0;
    private BlueBlock leftBlock, rightBlock;

    public Algorithm() {
        initBlueBlock(true);
        leftBlock = blueBlock[0];
        rightBlock = blueBlock[MazeData.getBlueBlockNumbers() - 1];
    }

    private class cal {
        int score = 0;

        private int calculate(BlueBlock exit, Side side) {
            LinkedList<Position> queue = new LinkedList<Position>();
            Position entrance = new Position(data.getEnteranceX(), data.getEnteranceY());
            queue.addLast(entrance); //由队尾加入队列
            data.visited[entrance.getX()][entrance.getY()] = true;
            boolean isSolved = false;
            while (queue.size() != 0) {
                Position curPos = queue.pop();  //获取并自动删除队列中的第一个元素
                if (curPos.getX() == exit.prevBlock(side).getX() && curPos.getY() == exit.prevBlock(side).getY()) {
                    isSolved = true;
                    score = findPath(curPos, side);
                    //System.out.println("Solve "+isSolved);
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    int newX = curPos.getX() + direction[i][0];
                    int newY = curPos.getY() + direction[i][1];

                    if (data.inArea(newX, newY)
                            && !data.visited[newX][newY]
                            && data.getMaze(newX, newY) == MazeData.ROAD) {
                        queue.addLast(new Position(newX, newY, curPos));
                        data.visited[newX][newY] = true;
                    }
                }
            }
            data.reset();
            return score;
        }
    }

    public void setData(int x, int y) {
        pane.add(AlgoVisHelper.drawRectangle(Color.YELLOW), y, x);
    }

    public void setBlueBlock(int x, int y, int score) { //刷新蓝色方块得分
        pane.add(AlgoVisHelper.setText(score), y, x);
    }

    public void run(boolean isClicked) {
        if (BlueBlock.BlueBlockNumbers > 0 && isClicked) {
            if (runNextBlock(leftBlock, rightBlock, Player.RED) == Side.Left) {
                leftBlock = leftBlock.nextBlock(Side.Left);
            } else {
                rightBlock = rightBlock.nextBlock(Side.Right);
            }
            ClickCount++;
        }

        if (ClickCount % 2 != 0) {
            REDScore += ScoreQuene.pop();
        } else {
            BLUEScore += ScoreQuene.pop();
        }
        System.out.println("红方： " + REDScore + ", 蓝方： " + BLUEScore);
        System.out.println("剩余蓝色方块数量" + BlueBlock.BlueBlockNumbers);
    }

    public Side runNextBlock(BlueBlock leftBlock, BlueBlock rightBlock, Player player) {
        cal left = new cal();
        cal right = new cal();
        int CurScore = 0;
        int leftScore = leftBlock.getScore() - left.calculate(leftBlock, Side.Left);
        int rightScore = rightBlock.getScore() - right.calculate(rightBlock, Side.Right);
        if (leftScore >= rightScore) {
            drawMap(Side.Left);
            leftBlock.cancelCurrentBlock();
            setBlueBlock(leftBlock.getX()+1, leftBlock.getY(), leftBlock.getScore()); //取消当前蓝色方块后要在屏幕上刷新得分
            CurScore += leftScore;
            ScoreQuene.addLast(CurScore);
            return Side.Left;
        } else {
            drawMap(Side.Right);
            rightBlock.cancelCurrentBlock();
            setBlueBlock(rightBlock.getX()+1, rightBlock.getY(), rightBlock.getScore()); //取消当前蓝色方块后要刷新得分
            CurScore += rightScore;
            ScoreQuene.addLast(CurScore);
            return Side.Right;
        }
    }

    public void initBlueBlock(boolean initial) {
        int count = 0; //记录蓝色方块在分数数组中的位置
        for (int i = 0; i < data.GetRow(); i++) {
            for (int j = 0; j < data.GetCol(); j++) {
                if (data.getMaze(i, j) == 'B') {
                    blueBlock[count] = new BlueBlock(i, j, BlueBlock.BlueScore[count], count);
                    setBlueBlock(i+1, j, blueBlock[count].getScore());
                    //System.out.println("方块" + (count + 1) + "分数是: " + blueBlock[count].getScore());
                    count++;
                }
            }
        }
    }

    public int getREDScore(){
        return REDScore;
    }

    public int getBLUEScore(){
        return BLUEScore;
    }

    private int findPath(Position des, Side side) {
        Position cur = des;
        int count = 0;
        while (cur != null) {
            if (side == Side.Left) {
                data.pathLeft[cur.getX()][cur.getY()] = true; //1代表左边路径
            } else if (side == Side.Right) {
                data.pathRight[cur.getX()][cur.getY()] = true; //2代表右边路径
            }
            cur = cur.getPrev();
            count++;
        }
        return count;
    }

    private void drawMap(Side side) {
        for (int i = 0; i < data.GetRow(); i++) {
            for (int j = 0; j < data.GetCol(); j++) {
                if (side == Side.Left
                        && data.pathLeft[i][j]
                        && data.getMaze(i, j) != MazeData.StartPoint) {
                    setData(i, j);
                } else if (side == Side.Right
                        && data.pathRight[i][j]
                        && data.getMaze(i, j) != MazeData.StartPoint) {
                    setData(i, j);
                }
                data.pathLeft[i][j] = false;
                data.pathRight[i][j] = false;
            }
        }
    }

}

