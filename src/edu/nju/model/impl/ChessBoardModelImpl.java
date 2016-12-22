package edu.nju.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import edu.nju.model.po.BlockPO;
import edu.nju.model.po.StatisticPO;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.model.state.BlockState;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.BlockVO;
import edu.nju.network.host.HostService;
import edu.nju.network.host.HostServiceImpl;
import edu.nju.view.MainFrame;

public class ChessBoardModelImpl extends BaseModel implements ChessBoardModelService, Serializable{
	
	private GameModelService gameModel;
	private ParameterModelService parameterModel;
    private HostService hostService = new HostServiceImpl();

    private GameState gameState;

    private static BlockPO[][] blockMatrix;
    private StatisticPO statisticPO;

    private int width;
    private int height;
    private int mineNum;
    private int markedNum;

    private int markedNumServer;
    private int markedNumClient;

    private static boolean isServer = false;
    private static boolean isClient = false;
    private static boolean isOperationServer = false;
    private static boolean isOperationClient = false;

    private int i = 0;

	public ChessBoardModelImpl(ParameterModelService parameterModel){
		this.parameterModel = parameterModel;
	}

    public void setStatisticPOLevel(){
        if(this.width == 9 && this.height == 9){
            statisticPO.setLevel(1);
        }else if(this.width == 16 && this.height == 16){
            statisticPO.setLevel(2);
        }else if(this.width == 30 && this.height == 16){
            statisticPO.setLevel(3);
        }else{
            statisticPO.setLevel(4);
        }
    }

	public boolean initialize(int width, int height, int mineNum) {

        statisticPO = new StatisticPO();

        if(!isClient && !isServer) {
            blockMatrix = new BlockPO[height][width];
            setBlock(mineNum);
        }
        if(isServer){
            blockMatrix = new BlockPO[height][width];
            setBlock(mineNum);
            hostService.update((Observable) gameModel, new UpdateMessage("blockMatrix", blockMatrix));
        }
        if(isClient){
            blockMatrix = getBlockMatrix();
            setBlock(mineNum);
        }

        this.i = 0;
        this.width = width;
        this.height = height;
        this.mineNum = mineNum;
        this.markedNum = 0;
        this.markedNumClient = 0;
        this.markedNumServer = 0;
        this.parameterModel.setMineNum(mineNum);
        setStatisticPOLevel();
		this.printBlockMatrix();
        List<BlockPO> blocks = new ArrayList<BlockPO>();
        super.updateChange(new UpdateMessage("execute", this.getDisplayList(blocks, GameState.PAUSE)));
		return false;
	}

    public void setBlockMatrix(BlockPO[][] blockMatrix1){
        blockMatrix = blockMatrix1;
    }

    public BlockPO[][] getBlockMatrix(){
        return blockMatrix;
    }

	public boolean excavate(int x, int y) throws ArrayIndexOutOfBoundsException {

        int excavated = 0;
        gameState = GameState.PAUSE;
		if(blockMatrix == null){
            return false;
        }
		List<BlockPO> blocks = new ArrayList<BlockPO>();
        BlockPO block = blockMatrix[x][y];

        if(block.getState() == BlockState.CLICK || block.getState() == BlockState.FLAG
                || block.getState() == BlockState.ERROFLAG || block.getState() == BlockState.MINE
                || block.getState() == BlockState.FLAG_CLIENT){
            return false;
        }

        int xl,xu,yl,yu;
        if(x == 0){xl = 0;}
        else{xl = x - 1;}
        if(y == 0){yl = 0;}
        else{yl = y - 1;}
        if(x == height - 1){xu = height - 1;}
        else{xu = x + 1;}
        if(y == width - 1){yu = width - 1;}
        else{yu = y + 1;}

        block.setState(BlockState.CLICK);
        blocks.add(block);

        if(block.getMineNum() == 0){
            for(int i = xl; i <= xu; i++){
                for(int j = yl; j <= yu; j++){
                    try{
                        excavate(i,j);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                if(!blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.CLICK
                        && excavated < width * height - mineNum){
                    excavated += 1;
                }
            }
        }

        if(block.isMine()){
            //resultCode: 1 = Lose
            //resultCode: 2 = Win
            if(!isClient() && !isServer()) {
                setGameResult(1);
            }else if(isServer()) {
                if (isOperationServer) {
                    setGameResult(1);
                } else {
                    BlockPO.setIsClient();
                    setGameResult(2);
                }
            }else if(isClient()) {
                if (isOperationClient) {
                    BlockPO.setIsClient();
                    setGameResult(1);
                } else {
                    setGameResult(2);
                }
            }

            excavate(x, y);

            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++) {
                    if(!(i == x && j == y)){
                        if((!isClient && !isServer)) {
                            if ((!blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.FLAG)) {
                                markWrongMine(i, j);
                            } else if ((blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG))) {
                                markMine(i, j);
                            } else if ((blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.FLAG)) {
                                mark(i, j);
                                mark(i, j);
                            } else{
                                excavate(i, j);
                            }
                        }else if(isClient){
                            if ((!blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.FLAG_CLIENT)) {
                                markWrongMine(i, j);
                            } else if ((blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG_CLIENT))
                                    && (blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG))) {
                                markMine(i, j);
                            } else {
                                excavate(i, j);
                            }
                        }else if(isServer){
                            if ((!blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.FLAG)) {
                                markWrongMine(i, j);
                            } else if ((blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG))
                                    && (blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG_CLIENT))) {
                                markMine(i, j);
                            } else{
                                excavate(i, j);
                            }
                        }
                    }
                }
            }

            gameState = GameState.OVER;

        }

        if(!isClient() && !isServer()) {
            if (excavated == width * height - mineNum) {
                setGameResult(2);
                gameState = GameState.OVER;
            }
        }

        super.updateChange(new UpdateMessage("execute",this.getDisplayList(blocks, gameState)));

		return true;
	}

    public int setGameResult(int resultCode){
        i ++ ;
        if(i == 1){
            switch (resultCode){
                case 1:
                    this.gameModel.gameOver(GameResultState.FAIL);
                    return resultCode;
                case 2:
                    if(!isClient && !isServer) {
                        allMark();
                    }
                    this.gameModel.gameOver(GameResultState.SUCCESS);
                    return resultCode;
                case 3:
                    this.gameModel.gameOver(GameResultState.INTERRUPT);
                    return resultCode;
                default:
                    return resultCode;
            }
        }else{
            return resultCode;
        }
    }

    public boolean quickExcavate(int x, int y) throws ArrayIndexOutOfBoundsException{

        if(blockMatrix == null){
            return false;
        }

        BlockPO block;
        block = blockMatrix[x][y];

        if(block.getState() == BlockState.UNCLICK || block.getState() == BlockState.FLAG
                || block.getState() == BlockState.FLAG_CLIENT){
            return false;
        }

        int numOfMarked = 0;

        int xl,xu,yl,yu;
        if(x == 0){xl = 0;}
        else{xl = x - 1;}
        if(y == 0){yl = 0;}
        else{yl = y - 1;}
        if(x == height - 1){xu = height - 1;}
        else{xu = x + 1;}
        if(y == width - 1){yu = width - 1;}
        else{yu = y + 1;}

        for(int i = xl; i <= xu; i++){
            for(int j = yl; j <= yu; j++){
                try{
                    if(blockMatrix[i][j].getState() == BlockState.FLAG
                            || blockMatrix[i][j].getState() == BlockState.FLAG_CLIENT){
                        numOfMarked ++ ;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(numOfMarked == blockMatrix[x][y].getMineNum()){
            for(int i = xl; i <= xu; i++){
                for(int j = yl; j <= yu; j++){
                    try{
                        if(blockMatrix[i][j].getState() != BlockState.FLAG
                                || blockMatrix[i][j].getState() != BlockState.FLAG_CLIENT){
                            excavate(i,j);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;

    }

    public boolean markMine(int x, int y) {
        List<BlockPO> blocks = new ArrayList<BlockPO>();
        BlockPO block = blockMatrix[x][y];
        block.setState(BlockState.MINE);
        blocks.add(block);
        super.updateChange(new UpdateMessage("execute",this.getDisplayList(blocks, GameState.RUN)));
        return true;
    }

    public boolean markWrongMine(int x, int y) {
        List<BlockPO> blocks = new ArrayList<BlockPO>();
        BlockPO block = blockMatrix[x][y];
        block.setState(BlockState.ERROFLAG);
        blocks.add(block);
        super.updateChange(new UpdateMessage("execute",this.getDisplayList(blocks, GameState.RUN)));
        return true;
    }

    public boolean allMark(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                if(blockMatrix[i][j].getState() == BlockState.UNCLICK && blockMatrix[i][j].isMine()){
                    mark(i,j);
                }
            }
        }
        return false;
    }

	public boolean mark(int x, int y) {

        List<BlockPO> blocks = new ArrayList<BlockPO>();
        BlockPO block = blockMatrix[x][y];

        if(blockMatrix[x][y].getState() == BlockState.CLICK){
            return false;
        } else if(blockMatrix[x][y].getState() == BlockState.FLAG
                || blockMatrix[x][y].getState() == BlockState.FLAG_CLIENT){
            //以上这个判断条件 还没满足：不能取消标记另一方的旗子
            block.setState(BlockState.UNCLICK);
            this.parameterModel.addMineNum();

            if(isServer()) {
                if (isOperationServer) {
                    markedNumServer -= 1;
                } else {
                    markedNumClient -= 1;
                }
            }else if(isClient()){
                if (isOperationClient) {
                    markedNumClient -= 1;
                } else {
                    markedNumServer -= 1;
                }
            }
            markedNum -= 1;
        } else if(blockMatrix[x][y].getState() == BlockState.UNCLICK && markedNum < mineNum){
            if(isServer()) {
                if(blockMatrix[x][y].isMine()){
                    if(isOperationServer) {
                        block.setState(BlockState.FLAG);
                        markedNumServer += 1;
                    }else{
                        block.setState(BlockState.FLAG_CLIENT);
                        markedNumClient += 1;
                    }
                    this.parameterModel.minusMineNum();
                    markedNum += 1;
                }else{
                    if (isOperationServer) {
                        setGameResult(1);
                    } else {
                        setGameResult(2);
                    }
                    for(int i = 0; i < height; i++){
                        for(int j = 0; j < width; j++) {
                            if (blockMatrix[i][j].getState() != BlockState.CLICK
                                    && ((blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG))
                                    || (blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG_CLIENT)))) {
                                excavate(i, j);
                            }

                        }
                    }
                    for(int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.CLICK) {
                                markMine(i, j);
                                break;
                            }
                        }
                    }
                }
            }else if(isClient()){
                if(blockMatrix[x][y].isMine()) {
                    if (isOperationClient) {
                        block.setState(BlockState.FLAG_CLIENT);
                        markedNumClient += 1;
                    } else {
                        block.setState(BlockState.FLAG);
                        markedNumServer += 1;
                    }
                    this.parameterModel.minusMineNum();
                    markedNum += 1;
                }else{
                    if (isOperationClient) {
                        setGameResult(1);
                    } else {
                        setGameResult(2);
                    }
                    for(int i = 0; i < height; i++){
                        for(int j = 0; j < width; j++) {
                            if (blockMatrix[i][j].getState() != BlockState.CLICK
                                    && ((blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG))
                                    || (blockMatrix[i][j].isMine() && !(blockMatrix[i][j].getState() == BlockState.FLAG_CLIENT)))) {
                                excavate(i, j);
                            }
                        }
                    }
                    for(int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (blockMatrix[i][j].isMine() && blockMatrix[i][j].getState() == BlockState.CLICK) {
                                markMine(i, j);
                                break;
                            }
                        }
                    }
                }
            }else {
                block.setState(BlockState.FLAG);
                this.parameterModel.minusMineNum();
                markedNum += 1;
            }
        }

        blocks.add(block);

        if(isClient) {
            if (markedNumClient + markedNumServer == mineNum) {
                if (markedNumClient > markedNumServer) {
                    setGameResult(2);
                    gameState = GameState.OVER;
                } else if (markedNumClient < markedNumServer) {
                    setGameResult(1);
                    gameState = GameState.OVER;
                } else if (markedNumClient == markedNumServer) {
                    setGameResult(3);
                    gameState = GameState.OVER;
                }
            }
        }else if(isServer){
            if (markedNumClient + markedNumServer == mineNum) {
                if(markedNumClient > markedNumServer){
                    setGameResult(1);
                    gameState = GameState.OVER;
                }else if(markedNumClient < markedNumServer){
                    setGameResult(2);
                    gameState = GameState.OVER;
                }else if(markedNumClient == markedNumServer){
                    setGameResult(3);
                    gameState = GameState.OVER;
                }
            }
        }

        super.updateChange(new UpdateMessage("execute",this.getDisplayList(blocks, GameState.RUN)));

        return true;
	}

	private boolean setBlock(int mineNum){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		for(int i = 0 ; i < width; i++){
			for (int j = 0 ; j < height; j++) {
                blockMatrix[i][j] = new BlockPO(i, j);
            }
        }
        while(mineNum > 0) {
            int a = (int) (Math.random() * width);
            int b = (int) (Math.random() * height);
            if (!blockMatrix[a][b].isMine()) {
                blockMatrix[a][b].setMine(true);
                addMineNum(a, b);
                mineNum--;
            }
        }
		return false;
	}

	private void addMineNum(int i, int j){
		int width = blockMatrix.length;
		int height = blockMatrix[0].length;
		int tempI = i-1;
		for(;tempI<=i+1;tempI++){
			int tempJ = j-1;
			for(;tempJ <= j+1; tempJ++){
				if((tempI > -1 && tempI < width) && (tempJ >-1 && tempJ < height)){
					blockMatrix[tempI][tempJ].addMine();
				}
			}
		}
	}

	private List<BlockVO> getDisplayList(List<BlockPO> blocks, GameState gameState){
		List<BlockVO> result = new ArrayList<BlockVO>();
		for(BlockPO block : blocks){
			if(block != null){
				BlockVO displayBlock = block.getDisplayBlock(gameState);
				if(displayBlock.getState() != null)
				result.add(displayBlock);
			}
		}
		return result;
	}

	public void setGameModel(GameModelService gameModel) {
		this.gameModel = gameModel;
	}
	
	private void printBlockMatrix(){
		for(BlockPO blocks[] : blockMatrix){
			for(BlockPO b :blocks){
				String p = b.getMineNum()+" ";
				if(b.isMine())
					p="* ";
				System.out.print(p + " ");
			}
			System.out.println();
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
    }

    public static void setServer(){
        isServer = true;
    }

    public static void setOperationClient(){
        isOperationClient = true;
        isOperationServer = false;
    }

    public static void setOperationServer(){
        isOperationServer = true;
        isOperationClient = false;
    }

    public static void setAllOperationNot(){
        isOperationClient = false;
        isOperationServer = false;
    }

}
