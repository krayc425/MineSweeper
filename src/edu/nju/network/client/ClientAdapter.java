package edu.nju.network.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientAdapter {

	protected static ClientThread socket;
	protected static ClientInHandler handler;
	
	static boolean init(String addr,ClientInHandler h){
		try {
			socket = new ClientThread(addr);
			handler = h;
			socket.start();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static void write(Object o){
		socket.write(o);
	}

	public static void readData(Object data){
		handler.inputHandle(data);
	}
	
	public static void close(){
		socket.close();
	}

}
