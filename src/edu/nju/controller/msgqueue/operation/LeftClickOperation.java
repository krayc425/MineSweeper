package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.service.ChessBoardModelService;

public class LeftClickOperation extends MineOperation{
	private int x;
	private int y;

	public LeftClickOperation(int x ,int y){
		this.x = x;
		this.y = y;

        if(MineOperation.isClient()){
            ChessBoardModelImpl.setOperationClient();
        }

        if(MineOperation.isServer()){
            ChessBoardModelImpl.setOperationServer();
        }
	}

	public void execute() {
		ChessBoardModelService chess = OperationQueue.getChessBoardModel();
		chess.excavate(x, y);
        ChessBoardModelImpl.setAllOperationNot();
	}

}
