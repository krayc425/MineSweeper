package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.*;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.data.StatisticData;
import edu.nju.model.impl.GameLevel;

public class SettingControllerImpl implements SettingControllerService{

	public boolean setEasyGameLevel() {
        MineOperation op = new SetLevelOperation("小");
        OperationQueue.addMineOperation(op);
        GameLevel gameLevel = new GameLevel();
        gameLevel.setLevel(0);
        return true;
	}

	public boolean setHardGameLevel() {
        MineOperation op = new SetLevelOperation("中");
        OperationQueue.addMineOperation(op);
        GameLevel gameLevel = new GameLevel();
        gameLevel.setLevel(1);
        StatisticData.setLevel(1);
        return true;
	}

	public boolean setHellGameLevel() {
        MineOperation op = new SetLevelOperation("大");
        OperationQueue.addMineOperation(op);
        GameLevel gameLevel = new GameLevel();
        gameLevel.setLevel(2);
        StatisticData.setLevel(2);
		return true;
	}

	public boolean setCustomizedGameLevel(int width, int height, int nums) {
        MineOperation op2 = new SetLevelOperation("自");
        MineOperation op = new CustomizedOperation(width,height,nums);
        OperationQueue.addMineOperation(op2);
        OperationQueue.addMineOperation(op);
        GameLevel gameLevel = new GameLevel();
        gameLevel.setLevel(3);
        StatisticData.setLevel(3);
		return true;
	}

}
