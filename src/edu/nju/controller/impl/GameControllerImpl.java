package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.service.GameControllerService;

public class GameControllerImpl implements GameControllerService{

	public boolean handleLeftClick(int x, int y) {
		MineOperation op = new LeftClickOperation(x,y);
		OperationQueue.addMineOperation(op);
		return true;
	}

	public boolean handleRightClick(int x, int y) {
        MineOperation op = new RightClickOperation(x,y);
        OperationQueue.addMineOperation(op);
		return true;
	}

	public boolean handleDoubleClick(int x, int y) {
        MineOperation op = new DoubleClickOperation(x,y);
        OperationQueue.addMineOperation(op);
		return false;
	}

}
