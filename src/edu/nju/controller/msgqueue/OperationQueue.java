package edu.nju.controller.msgqueue;

import java.io.Serializable;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.network.client.ClientService;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.host.HostService;
import edu.nju.network.host.HostServiceImpl;

/**
 * 操作队列，所有的操作需要加入队列，该队列自行按操作到达的先后顺序处理操作。
 * @author 晨晖
 *
 */

public class OperationQueue implements Runnable, Serializable{
	
	private static BlockingQueue<MineOperation> queue;
	
	public static boolean isRunning;
	public static boolean singleUpdateSwitch = true;
	
	private static ChessBoardModelService chessBoard;
	private static GameModelService gameModel;

    ClientService clientService = new ClientServiceImpl();
    HostService hostService = new HostServiceImpl();

    private boolean isServer = false;
    private boolean isClient = false;

	public OperationQueue(ChessBoardModelService chess, GameModelService game){
		queue = new ArrayBlockingQueue<MineOperation>(1000);
		isRunning = true;
		chessBoard = chess;
		gameModel = game;
	}

	public void run() {
		while(isRunning){
			MineOperation operation = getNewMineOperation();
            UpdateMessage updateMessage = new UpdateMessage("execute",operation);
            if(GameModelImpl.isClient() && MineOperation.isClient()){
                MineOperation.setNotClient();
                clientService.submitOperation(operation);
            }
            else if(GameModelImpl.isServer() && MineOperation.isServer()){
                MineOperation.setNotServer();
                hostService.update((Observable) gameModel, updateMessage);
            }
            operation.execute();
		}
	}

	public static boolean addMineOperation (MineOperation operation){
		try {
			queue.put(operation);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static MineOperation getNewMineOperation (){
		MineOperation operation = null;
		try {
			operation = queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return operation;
	}
	
	public static ChessBoardModelService getChessBoardModel(){
        return chessBoard;
	}
	
	public static GameModelService getGameModel(){
		return gameModel;
	}

    public boolean isServer(){
        this.isServer = true;
        return true;
    }

    public boolean isClient(){
        this.isClient = true;
        return true;
    }
}
