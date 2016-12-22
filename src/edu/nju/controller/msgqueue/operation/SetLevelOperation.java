package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class SetLevelOperation extends MineOperation {

    private String level;

    public SetLevelOperation(String level){
        this.level = level;
    }

    public void execute() {
        GameModelService game = OperationQueue.getGameModel();
        game.setGameLevel(level);
    }

}
