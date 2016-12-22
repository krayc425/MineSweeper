package edu.nju.network.modelProxy;

import java.util.Observable;
import java.util.Observer;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.model.impl.BaseModel;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.po.BlockPO;
import edu.nju.network.TransformObject;
import edu.nju.network.client.ClientService;

/**
 * 所有的代理类的基类。
 *
 */
public class ModelProxy extends BaseModel implements Observer{

	protected ClientService net;

	public void update(Observable o, Object arg) {
		TransformObject obj = (TransformObject) arg;
		String trigger_class = obj.getSource();
		UpdateMessage msg = obj.getMsg();
        Class<?> super_class = this.getClass().getInterfaces()[0];
		try {
			if(super_class.isAssignableFrom(Class.forName(trigger_class))){
				this.updateChange(msg);
                //执行 operation
                if(msg.getValue() instanceof MineOperation){
                    OperationQueue.addMineOperation((MineOperation)msg.getValue());
                }
                //初始化棋盘
                if(msg.getValue() instanceof BlockPO[][]){
                    (OperationQueue.getChessBoardModel()).setBlockMatrix((BlockPO[][]) msg.getValue());
                }
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
