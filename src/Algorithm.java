import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Algorithm {
    public static MazeData data = AlgoFrame.data;
    public static GridPane pane = AlgoFrame.pane;
    private int runMethodCount = 0;
    public static enum Side {Left, Right, NotEndPoint}
    public static enum Player {RED, BLUE}
    public BlueBlock[] blueBlock = new BlueBlock[data.getBlockNumbers()];
    public BlueBlock leftBlock, rightBlock;
    public boolean[][] curPath = new boolean[data.GetRow()][data.GetCol()];
    public int REDScore = 0,BlueScore = 0;
    private cal play;
    private static final int direction[][] = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; //9点钟方向逆时针

    public Algorithm() {
        initBlueBlock();
        leftBlock = blueBlock[0];
        rightBlock = blueBlock[data.getBlockNumbers() - 1];

    }

    public void run(){
        if(runMethodCount < data.getBlockNumbers()) {
            Player player;
            if (runMethodCount % 2 == 0) {
                player = Player.RED;
            } else {
                player = Player.BLUE;
            }
            play = new cal(player, leftBlock, rightBlock);
            play.clearPath(curPath);
            play.runNext();
            curPath = play.getPath();

            if (player == Player.RED) {
                REDScore += play.getScore();
                System.out.println(REDScore);
            } else {
                BlueScore += play.getScore();
                System.out.println(BlueScore);
            }

            if (play.getSide() == Side.Left) {
                leftBlock.cancelCurrentBlock(player);
                setBlueBlock(leftBlock.getX() + 1, leftBlock.getY(), leftBlock.getScore()); //刷新方块的分数
                leftBlock = leftBlock.nextBlock(Side.Left);
            } else if (play.getSide() == Side.Right) {
                rightBlock.cancelCurrentBlock(player);
                setBlueBlock(rightBlock.getX() + 1, rightBlock.getY(), rightBlock.getScore()); //刷新方块的分数
                rightBlock = rightBlock.nextBlock(Side.Right);
            }
            runMethodCount++;
        }
    }

    public int getREDScore(){
        return REDScore;
    }

    public int getBLUEScore(){
        return BlueScore;
    }

    public void initBlueBlock() {
        int count = 0; //记录蓝色方块在分数数组中的位置
        for (int i = 0; i < data.GetRow(); i++) {
            for (int j = 0; j < data.GetCol(); j++) {
                if (data.getMaze(i, j) == 'B') {
                    blueBlock[count] = new BlueBlock(i, j, BlueBlock.BlueScore[count], count);
                    setBlueBlock(i+1, j, blueBlock[count].getScore()); //刷新初始时各方块的分数
                    count++;
                }
            }
        }
    }

    public void setBlueBlock(int x, int y, int score) { //刷新蓝色方块得分
        pane.add(AlgoVisHelper.setText(score), y, x);
    }

    private class cal {
        int leftScore = 0, rightScore = 0;
        boolean[][] pathLeft = new boolean[data.GetRow()][data.GetCol()];
        boolean[][] pathRight = new boolean[data.GetRow()][data.GetCol()];
        BlueBlock exitLeft,exitRight;
        Player curPlayer;

        cal(Player curPlayer, BlueBlock exitLeft, BlueBlock exitRight) {
            this.curPlayer = curPlayer;
            this.exitLeft = exitLeft;
            this.exitRight = exitRight;
        }

        private void runNext(){
            leftScore = exitLeft.getScore() - calculate(Side.Left,exitLeft);
            rightScore = exitRight.getScore() - calculate(Side.Right,exitRight);
            drawPath();
        }

        private int calculate(Side side, BlueBlock exit) {
            int endX, endY;
            int score = 0;
            endX = exit.prevBlock(side).getX();
            endY = exit.prevBlock(side).getY();
            LinkedList<Position> queue = new LinkedList<Position>();
            Position entrance = new Position(data.getEnteranceX(), data.getEnteranceY());
            queue.addLast(entrance); //由队尾加入队列
            data.visited[entrance.getX()][entrance.getY()] = true;
            while (queue.size() != 0) {
                Position curPos = queue.pop();  //获取并自动删除队列中的第一个元素
                if (curPos.getX() == endX && curPos.getY() == endY) {
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

        private int findPath(Position des, Side side) {
            Position cur = des;
            int count = 0;
            while (cur != null) {
                if (side == Side.Left) {
                    pathLeft[cur.getX()][cur.getY()] = true; //代表左边路径
                } else if (side == Side.Right) {
                    pathRight[cur.getX()][cur.getY()] = true; //代表右边路径
                }
                cur = cur.getPrev();
                count++;
            }
            return count;
        }

        private int getScore() {
            return leftScore >= rightScore ? leftScore : rightScore;
        }

        private boolean[][] getPath(){
            if(leftScore >= rightScore){
                return pathLeft;
            } else {
                return pathRight;
            }
        }

        //返回计算出的最短路径是那一边的，也就是该路径的终点在哪一边
        private Side getSide(){
            if(leftScore >= rightScore){
                return Side.Left;
            } else {
                return Side.Right;
            }
        }

        public void setData(int x, int y, Color color) {
            pane.add(AlgoVisHelper.drawRectangle(color), y, x);
        }

        private void drawPath() {
            for (int i = 0; i < data.GetRow(); i++) {
                for (int j = 0; j < data.GetCol(); j++) {
                    if(data.getMaze(i,j) != MazeData.StartPoint){
                        if(curPlayer == Player.RED && this.getSide() == Side.Left){
                            if(pathLeft[i][j]){
                                setData(i,j,AlgoVisHelper.REDpath);
                            }
                        } else if(curPlayer == Player.RED && this.getSide() == Side.Right){
                            if(pathRight[i][j]){
                                setData(i,j,AlgoVisHelper.REDpath);
                            }
                        } else if(curPlayer == Player.BLUE && this.getSide() == Side.Left){
                            if(pathLeft[i][j]){
                                setData(i,j,AlgoVisHelper.BLUEpath);
                            }
                        } else if(curPlayer == Player.BLUE && this.getSide() == Side.Right){
                            if(pathRight[i][j]){
                                setData(i,j,AlgoVisHelper.BLUEpath);
                            }
                        }
                    }

                }
            }
        }

        private void clearPath(boolean path[][]) {
            for (int i = 0; i < data.GetRow(); i++) {
                for (int j = 0; j < data.GetCol(); j++) {
                    if(data.getMaze(i,j) != MazeData.StartPoint) {
                        if (path[i][j]) {
                            setData(i, j, Color.web("#eeeeee"));
                        }
                        path[i][j] = false;
                    }
                }
            }
            setData(leftBlock.prevBlock(Side.Left).getX(), leftBlock.prevBlock(Side.Left).getY(), Color.web("#eeeeee"));
            setData(rightBlock.prevBlock(Side.Right).getX(), rightBlock.prevBlock(Side.Right).getY(), Color.web("#eeeeee"));
        }

    }
}

