package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import edu.nju.model.data.StatisticData;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;

public class GameModelImpl extends BaseModel implements GameModelService{
	
	private StatisticModelService statisticModel;
	private ChessBoardModelService chessBoardModel;
	private List<GameLevel> levelList;
	private GameState gameState;
	private String level;
	private GameResultState gameResultState = GameResultState.INTERRUPT;
    private StatisticData statisticData;
    private int time;
    private long startTime;
    private static int width;
    private static int height;
    private static int mineNum;
    private static boolean isServer = false;
    private static boolean isClient = false;

	public GameModelImpl(StatisticModelService statisticModel, ChessBoardModelService chessBoardModel){
        this.statisticModel = statisticModel;
        this.chessBoardModel = chessBoardModel;
        gameState = GameState.RUN;
        chessBoardModel.setGameModel(this);
        levelList = new ArrayList<GameLevel>();
        levelList.add(new GameLevel(2,"大",30,16,99));
        levelList.add(new GameLevel(1,"中",16,16,40));
        levelList.add(new GameLevel(0,"小",9,9,10));
    }

    public boolean startGame(){

        GameLevel gl = null;
        gameState = GameState.RUN;
        Thread timeUpdateThread = new Thread(new TimeUpdate());
        startTime = Calendar.getInstance().getTimeInMillis();
        timeUpdateThread.start();


        if(!isServer && !isClient){
            statisticData = new StatisticData();
        }

		for(GameLevel tempLevel : levelList){
			if(tempLevel.getName().equals(level)){
				gl = tempLevel;
				break;
			}
		}

		if(gl == null && width == 0 && height == 0) {
            gl = levelList.get(2);
        }
		
		if(gl != null){
			width = gl.getWidth();
			height = gl.getHeight();
			mineNum = gl.getMineNum();
		}

        this.chessBoardModel.initialize(width, height, mineNum);
        super.updateChange(new UpdateMessage("start", this.convertToDisplayGame()));
		return true;
	}

	public boolean gameOver(GameResultState result) {

        this.gameState = GameState.OVER;
        this.gameResultState = result;
        this.time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
        super.updateChange(new UpdateMessage("time",this.time));

        if(!isServer && !isClient){
            statisticModel = new StatisticModelImpl();
            statisticModel.recordStatistic(this.gameResultState, this.time);
        }

        super.updateChange(new UpdateMessage("end", this.convertToDisplayGame()));
		return false;
	}

	public boolean setGameLevel(String level) {
		this.level = level;
		return true;
	}

	public boolean setGameSize(int w, int h, int m) {
		width = w;
		height = h;
		mineNum = m;
		return true;
	}
	
	private GameVO convertToDisplayGame(){
		return new GameVO(gameState, width, height,level, gameResultState, time);
	}

	public List<GameLevel> getGameLevel() {
		return this.levelList;
	}

    public GameState getGameState() {
        return this.gameState;
    }

    //新增的更新时间的内部类
    private class TimeUpdate implements Runnable{
        public void run(){
            while(gameState == GameState.RUN){
                time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
                updateChange(new UpdateMessage("time", time));
            }
        }
    }

    public static boolean isServer(){
        return isServer;
    }

    public static boolean isClient(){
        return isClient;
    }

    public static void setClient(){
        isClient = true;
        isServer = false;
    }

    public static void setServer(){
        isServer = true;
        isClient = false;
    }
}