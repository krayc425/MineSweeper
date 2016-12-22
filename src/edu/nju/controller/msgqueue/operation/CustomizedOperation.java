package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class CustomizedOperation extends MineOperation {

    public void execute() {
        GameModelService game = OperationQueue.getGameModel();
        game.setGameSize(width, height, mineNum);
        game.startGame();
    }

    public CustomizedOperation(int width, int height, int mineNum){
        this.width = width;
        this.height = height;
        this.mineNum = mineNum;
    }

    public void setWidth(int W) {
        width = W;
    }
    public void setHeight(int H) {
        height = H;
    }
    public void setMineNum(int M) {
        mineNum = M;
    }

    private int width;
    private int height;
    private int mineNum;

}
