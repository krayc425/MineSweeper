package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class StartGameOperation extends MineOperation{
	public void execute() {
        GameModelService game = OperationQueue.getGameModel();
		game.startGame();
	}
}
