package edu.nju.view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import edu.nju.model.impl.UpdateMessage;

public class MineNumberLabel extends JLabel implements Observer {

	
	public MineNumberLabel(){
		
	}
	
	private int remainMinesNumber;

	public void update(Observable o, Object arg) {
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("mineNum")){
			int remainMines = (Integer) updateMessage.getValue();
			this.setRamainMinesNumber(remainMines);
			this.setText(remainMines+"");
		}
	}

	public int getRamainMinesNumber() {
		return remainMinesNumber;
	}

	public void setRamainMinesNumber(int remainMinesNumber) {
		this.remainMinesNumber = remainMinesNumber;
	}

}
