package edu.nju.model.impl;

import edu.nju.model.data.StatisticData;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;

public class StatisticModelImpl extends BaseModel implements StatisticModelService{

    public StatisticModelImpl(){

    }

    public void recordStatistic(GameResultState result, int time) {
        StatisticData statisticDao = new StatisticData();
        try{
            if(result == GameResultState.SUCCESS){
                //1 = Win
                //0 = Lose
                statisticDao.getStatistic(1);
            }else{
                statisticDao.getStatistic(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showStatistics() {

    }

}
