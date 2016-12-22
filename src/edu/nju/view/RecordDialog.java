package edu.nju.view;

import edu.nju.model.data.StatisticData;
import java.text.DecimalFormat;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class RecordDialog {

	public RecordDialog(JFrame parent) {
		super();
		initialization(parent);
	}

	public boolean set(String[] rank, double[] rate) {
		clear = false;
		this.rank = rank;
		this.rate = rate;
		dialog.setVisible(true);
		return clear;
	}

    StatisticData statisticData = new StatisticData();;

	public void show(){
		this.rank = new String[]{"Easy","Hard","Hell","Customized"};
        DecimalFormat df1 = new DecimalFormat("00.00%");
        for(int i = 0; i < 4; i++){
            score[i] = statisticData.getSum(i);
            wins[i] = statisticData.getWins(i);
            if(score[i] != 0){
                rate[i] = (double)wins[i] / (double)score[i];
                toShowRate[i] = df1.format(rate[i]);
            }else{
                rate[i] = 0;
                toShowRate[i] = "00.00%";
            }
        }
		set(rank,rate);
	}

	private void initialization(JFrame parent) {

		dialog = new JDialog(parent, "Record", true);

		okBtn = new JButton("OK");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(100, 187, 70, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});

		clearBtn = new JButton("Clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(192, 187, 70, 23);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
                try{
                    statisticData.clearAll();
                }catch (Exception exx){
                    exx.printStackTrace();
                }
				clear = true;
				int length = rank.length;
				for (int i = 0; i != length; ++i) {
					toShowRate[i] = "00.00%";
                    wins[i] = 0;
                    score[i] = 0;
				}
				textPanel.repaint();
                show();
			}
		});

		line = new JSeparator();
		line.setBounds(20, 170, 240, 4);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);
		panel.add(okBtn);
		panel.add(clearBtn);
		panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(parent.getLocation().x + 50,
				parent.getLocation().y + 50,290, 260);

		clear = false;

	}

	private class DescribeTextPanel extends JPanel {

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 290, 155);
		}

		public void paintComponent(Graphics g) throws NullPointerException{
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
            try{
                for (int i = 0; i < rank.length + 1; i++) {
                    if(i == 0){
                        g.drawString("Rank", 20, 30 * (i + 1));
                        g.drawString("Wins", 100, 30 * (i + 1));
                        g.drawString("Total", 160, 30 * (i + 1));
                        g.drawString("Rate", 220, 30 * (i + 1));
                    }else {
                        g.drawString(rank[i-1], 20, 30 * (i + 1));
                        g.drawString(String.valueOf(wins[i-1]), 100, 30 * (i + 1));
                        g.drawString(String.valueOf(score[i-1]), 160, 30 * (i + 1));
                        g.drawString(String.valueOf(toShowRate[i-1]), 220, 30 * (i + 1));
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
		}
	}

	private String[] rank;
  	private JDialog dialog;
	private JPanel panel;
	private JButton okBtn;
	private JButton clearBtn;
	private JSeparator line;
	private int score[] = {0,0,0,0};
    private int wins[] = {0,0,0,0};
    private double rate[] = {0,0,0,0};
    private String toShowRate[] = {"","","",""};
	private JPanel textPanel;
	boolean clear;

}