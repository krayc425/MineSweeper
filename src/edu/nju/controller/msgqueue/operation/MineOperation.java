package edu.nju.controller.msgqueue.operation;

import edu.nju.model.impl.GameModelImpl;

import java.io.Serializable;

public abstract class MineOperation implements Serializable {

    public abstract void execute();

    private static boolean isServer = false;
    private static boolean isClient = false;

    public static boolean isServer(){
        return isServer;
    }

    public static boolean isClient(){
        return isClient;
    }

    public MineOperation(){
        if(GameModelImpl.isClient()) {
            isClient = true;
        }
        if(GameModelImpl.isServer()) {
            isServer = true;
        }
    }

    public static void setNotClient(){
        isClient = false;
    }

    public static void setNotServer(){
        isServer = false;
    }

}
