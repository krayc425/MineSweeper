package edu.nju.model.po;

import edu.nju.model.state.BlockState;
import edu.nju.model.state.DisplayBlockState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import java.io.Serializable;

public class BlockPO implements Serializable {

	private BlockState state;
	private int mineNum;
	private boolean isMine;
	private int x;
	private int y;

    private static boolean isClient = false;

	public BlockPO(int x, int y){
		state = BlockState.UNCLICK;
		this.mineNum = 0;
		this.x = x;
		this.y = y;
	}

	public BlockVO getDisplayBlock(GameState gameState){
		DisplayBlockState dbs = null;

		if(state == BlockState.CLICK&&(!isMine)){
			dbs = DisplayBlockState.getClickState(mineNum);
		}
		else if(state == BlockState.UNCLICK){
			dbs = DisplayBlockState.UNCLICK;
		}
		else if(state == BlockState.FLAG){
			dbs = DisplayBlockState.FLAG;
		}
        else if(state == BlockState.MINE){
            dbs = DisplayBlockState.MINE;
        }
        else if(state == BlockState.ERROFLAG){
            dbs = DisplayBlockState.ERROFLAG;
        }
        else if(state == BlockState.FLAG_CLIENT){
            dbs = DisplayBlockState.FLAG_CLIENT;
        }

		if(gameState == GameState.OVER){//当游戏为结束状态时
			if(state == BlockState.CLICK && isMine){
				if(isClient){
                    dbs = DisplayBlockState.Bomb_CLIENT;
                    isClient = false;
                }else{
                    dbs = DisplayBlockState.Bomb;
                }
			}
            else if(state == BlockState.MINE){
                dbs = DisplayBlockState.MINE;
            }
            else if(state == BlockState.FLAG){
                dbs = DisplayBlockState.FLAG;
            }
            else if(state == BlockState.ERROFLAG){
                dbs = DisplayBlockState.ERROFLAG;
            }
            else if(state == BlockState.FLAG_CLIENT){
                dbs = DisplayBlockState.FLAG_CLIENT;
            }
		}

		BlockVO db = new BlockVO(dbs,x,y);

		return db;

	}

	public BlockState getState() {
		return state;
	}

	public void setState(BlockState state) {
		this.state = state;
	}

	public int getMineNum() {
		return mineNum;
	}

	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addMine(){
		this.mineNum++;
	}

    public static void setIsClient(){
        isClient = true;
    }
}