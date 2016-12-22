package edu.nju.model.impl;

import java.io.Serializable;

public class GameLevel implements Serializable {

	private int level;
	private String name;
	private int width;
	private int height;
	private int mineNum;

    public GameLevel(){
        super();
    }

	public GameLevel(int level, String name, int width, int height, int mineNum) {
		super();
		this.level = level;
		this.name = name;
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getMineNum() {
		return mineNum;
	}

	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}
	
}
