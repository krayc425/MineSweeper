package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.HostControllerService;
import edu.nju.model.impl.*;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;

public class HostControllerImpl implements HostControllerService{

    public boolean serviceSetupHost() {

        HostServiceImpl host = new HostServiceImpl();
        HostInHandlerImpl hostH = new HostInHandlerImpl();

        ChessBoardModelImpl chessBoard = new ChessBoardModelImpl(new ParameterModelImpl());

        GameModelImpl game = new GameModelImpl(new StatisticModelImpl(), chessBoard);
        GameModelImpl.setServer();
        ChessBoardModelImpl.setServer();
        game.addObserver(host);

        if (host.init(hostH)) {
            OperationQueue.addMineOperation(new StartGameOperation());
        }

        return false;
    }

}
