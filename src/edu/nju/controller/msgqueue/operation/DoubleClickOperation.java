package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.ChessBoardModelService;

public class DoubleClickOperation extends MineOperation{
    private int x;
    private int y;

    public DoubleClickOperation(int x ,int y){
        this.x = x;
        this.y = y;
    }

    public void execute() {
        ChessBoardModelService chess = OperationQueue.getChessBoardModel();
        chess.quickExcavate(x,y);
    }

}
