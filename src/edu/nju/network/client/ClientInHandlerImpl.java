package edu.nju.network.client;

import java.util.Observable;
import edu.nju.network.TransformObject;

public class ClientInHandlerImpl extends Observable implements ClientInHandler{
	public void inputHandle(Object data) {
		TransformObject obj = (TransformObject) data;
		this.setChanged();
		this.notifyObservers(obj);
	}
}
