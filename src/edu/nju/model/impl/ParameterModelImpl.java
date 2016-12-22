package edu.nju.model.impl;

import edu.nju.model.service.ParameterModelService;
import java.io.Serializable;

public class ParameterModelImpl extends BaseModel implements ParameterModelService, Serializable{
	
	private int maxMine;
	private int mineNum;

	public boolean setMineNum(int num) {
		mineNum = num;
		maxMine = num;
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	public boolean addMineNum() {
		mineNum++;
		if(mineNum>maxMine){
			mineNum--;
			return false;
		}
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

	public boolean minusMineNum() {
		mineNum--;
		if(mineNum<0){
			mineNum++;
			return false;
		}
		super.updateChange(new UpdateMessage("mineNum", mineNum));
		return true;
	}

}
