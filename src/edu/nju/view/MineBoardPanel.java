package edu.nju.view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.vo.BlockVO;
import edu.nju.view.listener.CoreListener;

public class MineBoardPanel extends JPanel implements Observer {
	
	static MyButton[][] jLabelButtons;
	private static int rows;//height
	private static int columns;//width
 	private CoreListener coreListener;

    public MineBoardPanel(){}

	/*
	 * 根据高和宽新建棋盘，并且将每一个雷格监听到coreListener
	 */
	public MineBoardPanel(int rows,int columns){
  		coreListener = new CoreListener();
		MineBoardPanel.rows = rows;
		MineBoardPanel.columns = columns;

		jLabelButtons = new MyButton[rows][columns];

		this.setLayout(null);
		for(int i = 0;i < columns;i++){
			for(int j = 0;j < rows;j++){
				jLabelButtons[j][i] = new MyButton(j,i);
                jLabelButtons[j][i].setBounds(bodyMarginOther+ i * buttonSize,
                        bodyMarginNorth +j * buttonSize, buttonSize,
                        buttonSize);
                jLabelButtons[j][i].setIcon(Images.UNCLICKED);
				this.add(jLabelButtons[j][i]);
                jLabelButtons[j][i].addMouseListener(coreListener);//将雷格监听到coreListener
			}
		}
	}

	public void update(Observable o, Object arg) { 
		//如果棋盘发生变化要体现在这边
		UpdateMessage updateMessage = (UpdateMessage) arg;
		if(updateMessage.getKey().equals("execute")){
			List<BlockVO> changedCells = (List<BlockVO>) updateMessage.getValue();
			BlockVO displayBlock;
            for(int i = 0;i < changedCells.size();i++){
            	displayBlock = changedCells.get(i);
            	jLabelButtons[displayBlock.getX()][displayBlock.getY()]
    					.setIcon(Images.getImageIconByState(displayBlock.getState()));
            	jLabelButtons[displayBlock.getX()][displayBlock.getY()].repaint();
            }
		}


	}

	public static MyButton[][] getjLabelButtons() {
		return jLabelButtons;
	}

	public static void setjLabelButtons(MyButton[][] jLabelButtons) {
		MineBoardPanel.jLabelButtons = jLabelButtons;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		MineBoardPanel.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		MineBoardPanel.columns = columns;
	}
	
	private final int buttonSize = 16;

	private final int bodyMarginNorth = 20;

	private final int bodyMarginOther = 12;

}
