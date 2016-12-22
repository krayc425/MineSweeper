package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.ClientControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.modelProxy.GameModelProxy;

public class ClientControllerImpl implements ClientControllerService {

    public boolean setupClient(String ip){

        ClientServiceImpl client = new ClientServiceImpl();
        ClientInHandlerImpl clientH = new ClientInHandlerImpl();

        GameModelProxy gameModelProxy = new GameModelProxy(client);
        clientH.addObserver(gameModelProxy);

        GameModelImpl.setClient();
        ChessBoardModelImpl.setClient();

        if(client.init(ip, clientH)) {
            OperationQueue.addMineOperation(new StartGameOperation());
        }

        return false;
    }

}
