package edu.nju.network.client;

import edu.nju.controller.msgqueue.operation.MineOperation;

public class ClientServiceImpl extends ClientService{
	public void submitOperation(MineOperation op) {
		ClientAdapter.write(op);
	}
}
