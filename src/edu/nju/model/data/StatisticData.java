package edu.nju.model.data;

import edu.nju.model.po.StatisticPO;
import java.io.*;

public class StatisticData implements Serializable{

    StatisticPO easy;
    StatisticPO hard;
    StatisticPO hell;
    StatisticPO custom;

    private ObjectInputStream is;

    private ObjectOutputStream os;

    private static int level;

    public static void setLevel(int l){
        level = l;
    }

    public StatisticPO getStatistic(int j) throws java.io.IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream("save.ser");
            is = new ObjectInputStream(fileInputStream);
            hell = (StatisticPO) is.readObject();
            hard = (StatisticPO) is.readObject();
            easy = (StatisticPO) is.readObject();
            custom = (StatisticPO) is.readObject();
            is.close();
        }catch (Exception e) {
            e.printStackTrace();
            hell = new StatisticPO(0, 0, 0, 2, 30, 16);
            hard = new StatisticPO(0, 0, 0, 1, 16, 16);
            easy = new StatisticPO(0, 0, 0, 0, 9, 9);
            custom = new StatisticPO(0, 0, 0, 3, 0, 0);
        }
        switch (level){
            case 2: {
                hell.setSum(hell.getSum() + 1);
                if(j == 1){
                    hell.setWins(hell.getWins() + 1);
                }
                saveStatistic();
                return hell;
            }
            case 1:{
                hard.setSum(hard.getSum() + 1);
                if(j == 1){
                    hard.setWins(hard.getWins() + 1);
                }
                saveStatistic();
                return hard;
            }
            case 0:{
                easy.setSum(easy.getSum() + 1);
                if(j == 1){
                    easy.setWins(easy.getWins() + 1);
                }
                saveStatistic();
                return easy;
            }
            default:{
                custom.setSum(custom.getSum()+1);
                if(j == 1){
                    custom.setWins(custom.getWins() + 1);
                }
                saveStatistic();
                return custom;
            }
        }

    }

    public boolean saveStatistic() throws java.io.IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("save.ser");
        os = new ObjectOutputStream(fileOutputStream);
        os.writeObject(hell);
        os.writeObject(hard);
        os.writeObject(easy);
        os.writeObject(custom);
        os.close();
        return false;
    }

    public void clearAll() throws java.io.IOException{
        try {
            FileInputStream fileInputStream = new FileInputStream("save.ser");
            is = new ObjectInputStream(fileInputStream);
            hell = (StatisticPO) is.readObject();
            hard = (StatisticPO) is.readObject();
            easy = (StatisticPO) is.readObject();
            custom = (StatisticPO) is.readObject();
            is.close();
        }catch (Exception e) {
            e.printStackTrace();
            hell = new StatisticPO(0, 0, 0, 2, 30, 16);
            hard = new StatisticPO(0, 0, 0, 1, 16, 16);
            easy = new StatisticPO(0, 0, 0, 0, 9, 9);
            custom = new StatisticPO(0, 0, 0, 3, 0, 0);
        }
        custom.setSum(0);
        custom.setWins(0);
        easy.setSum(0);
        easy.setWins(0);
        hard.setSum(0);
        hard.setWins(0);
        hell.setSum(0);
        hell.setWins(0);
        saveStatistic();
    }


    public int getSum(int level) {
        try {
            FileInputStream fileInputStream = new FileInputStream("save.ser");
            is = new ObjectInputStream(fileInputStream);
            hell = (StatisticPO) is.readObject();
            hard = (StatisticPO) is.readObject();
            easy = (StatisticPO) is.readObject();
            custom = (StatisticPO) is.readObject();
            is.close();
        }catch (Exception e) {
            e.printStackTrace();
            hell = new StatisticPO(0, 0, 0, 2, 30, 16);
            hard = new StatisticPO(0, 0, 0, 1, 16, 16);
            easy = new StatisticPO(0, 0, 0, 0, 9, 9);
            custom = new StatisticPO(0, 0, 0, 3, 0, 0);
        }
        switch (level) {
            case 0:
                return easy.getSum();
            case 1:
                return hard.getSum();
            case 2:
                return hell.getSum();
            case 3:
                return custom.getSum();
            default:
                return 0;
        }
    }

    public int getWins(int level) {
        try {
            FileInputStream fileInputStream = new FileInputStream("save.ser");
            is = new ObjectInputStream(fileInputStream);
            hell = (StatisticPO) is.readObject();
            hard = (StatisticPO) is.readObject();
            easy = (StatisticPO) is.readObject();
            custom = (StatisticPO) is.readObject();
            is.close();
        }catch (Exception e) {
            e.printStackTrace();
            hell = new StatisticPO(0, 0, 0, 2, 30, 16);
            hard = new StatisticPO(0, 0, 0, 1, 16, 16);
            easy = new StatisticPO(0, 0, 0, 0, 9, 9);
            custom = new StatisticPO(0, 0, 0, 3, 0, 0);
        }
        switch (level) {
            case 0:
                return easy.getWins();
            case 1:
                return hard.getWins();
            case 2:
                return hell.getWins();
            case 3:
                return custom.getWins();
            default:
                return 0;
        }
    }

}