package edu.nju.network.modelProxy;


import java.util.List;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.model.impl.GameLevel;
import edu.nju.model.service.GameModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.network.client.ClientService;

/**
 * GameModel的代理，在网络对战时替代Client端的相应Model。
 * @author 晨晖
 *
 */
public class GameModelProxy extends ModelProxy implements GameModelService{

	public GameModelProxy(ClientService client){
		this.net = client;
	}

	public boolean setGameLevel(String level) {
	    return true;
    }

	public boolean startGame() {
		MineOperation op = new StartGameOperation();
		net.submitOperation(op);
		return true;
	}

	public boolean gameOver(GameResultState result) {
		return false;
	}

	public List<GameLevel> getGameLevel() {
		return null;
	}

	public boolean setGameSize(int width, int height, int mineNum) {
		return false;
	}

}
