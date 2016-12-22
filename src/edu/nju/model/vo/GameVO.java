package edu.nju.model.vo;

import java.io.Serializable;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;

public class GameVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private GameState gameState;
	private int width;
	private int height;
	private String level;
	private GameResultState gameResultState;
	private int time;

	public GameVO(GameState gameState, int width, int height,
			String level, GameResultState gameResultState, int time) {
		super();
		this.gameState = gameState;
		this.width = width;
		this.height = height;
		this.level = level;
		this.gameResultState = gameResultState;
		this.time = time;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public GameResultState getGameResultState() {
		return gameResultState;
	}

	public void setGameResultState(GameResultState gameResultState) {
		this.gameResultState = gameResultState;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
