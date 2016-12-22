package edu.nju.view;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Observable;
import javax.swing.*;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.UpdateMessage;
import edu.nju.model.state.GameResultState;
import edu.nju.model.vo.GameVO;
import edu.nju.view.listener.CoreListener;
import edu.nju.view.listener.MenuListener;
import java.util.Observer;

public class MainFrame implements Observer {

	private JFrame mainFrame;
	private JPanel head;
	private JMenuBar aJMenuBar;
	private JMenu game;
	private HashMap<String, JMenuItem> menuItemMap;
	private JMenuItem startItem;
	private JSeparator jSeparator;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;
	private ButtonGroup group;
	private JCheckBoxMenuItem easy;
	private JCheckBoxMenuItem hard;
	private JCheckBoxMenuItem hell;
	private JCheckBoxMenuItem custom;
	private JMenuItem record;
	private JMenuItem exit;
	private JMenu online;
	private JMenuItem host;
	private JMenuItem client;
	private MineNumberLabel mineNumberLabel;
	private JButton startButton;
	private JLabel time;
	private MineBoardPanel body;
	private final int buttonSize = 16;
	private final int bodyMarginNorth = 20;
	private final int bodyMarginOther = 12;
	private int defaultWidth = 9;
	private int defaultHeight = 9;
	private CoreListener coreListener;
	private MenuListener menuListener;
    private JLabel showTime;
    private JLabel showMineNum;

	public MainFrame() {
		try {
			UIManager
//					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		componentsInstantiation();
		initComponents();
		mainFrame.setVisible(true);
	}

	private void componentsInstantiation() {
        mainFrame = new JFrame();
		head = new JPanel();
		mineNumberLabel = new MineNumberLabel();
		startButton = new JButton();
		time = new JLabel();
        showMineNum = new JLabel();
        showTime = new JLabel();
		aJMenuBar = new JMenuBar();
		game = new JMenu();
		startItem = new JMenuItem();
		jSeparator = new JSeparator();
		jSeparator1 = new JSeparator();
		jSeparator2 = new JSeparator();
		easy = new JCheckBoxMenuItem();
		hard = new JCheckBoxMenuItem();
		hell = new JCheckBoxMenuItem();
		custom = new JCheckBoxMenuItem();
 		record = new JMenuItem();
		exit = new JMenuItem();
		online = new JMenu();
		host = new JMenuItem();
		client = new JMenuItem();
		menuItemMap = new HashMap<String,JMenuItem>();
		group = new ButtonGroup();
		body = new MineBoardPanel(defaultHeight,defaultWidth);
		coreListener = new CoreListener(this);
		menuListener = new MenuListener(this);
	}

	private void initComponents() {
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		game.setText("Game");

		group.add(easy);
		group.add(hard);
		group.add(hell);
		group.add(custom);

		startItem.setText("Start");
		game.add(startItem);
		startItem.addActionListener(menuListener);
		menuItemMap.put("start", startItem);

		game.add(jSeparator1);

		easy.setText("Easy");
		game.add(easy);
		easy.addActionListener(menuListener);
		menuItemMap.put("easy", easy);

		hard.setText("Hard");
		hard.addActionListener(menuListener);
		game.add(hard);
		menuItemMap.put("hard", hard);

		hell.setText("Hell");
		hell.addActionListener(menuListener);
		game.add(hell);
		menuItemMap.put("hell", hell);

		custom.setText("Custom");
		custom.addActionListener(menuListener);
		game.add(custom);
		menuItemMap.put("custom", custom);

		game.add(jSeparator2);

		record.setText("Record");
		game.add(record);
		menuItemMap.put("record", record);
		record.addActionListener(menuListener);

		game.add(jSeparator);

		exit.setText("Exit");
		exit.addActionListener(menuListener);
		game.add(exit);
		menuItemMap.put("exit", exit);

		aJMenuBar.add(game);

		online.setText("Online");
		host.setText("Register as host");
		host.addActionListener(menuListener);
		online.add(host);
		menuItemMap.put("host", host);

		client.setText("Register as client");
		client.addActionListener(menuListener);
		online.add(client);
		menuItemMap.put("client", client);

		aJMenuBar.add(online);
		mainFrame.setJMenuBar(aJMenuBar);

		mainFrame.getContentPane().setLayout(null);

		head.setBorder(new javax.swing.border.TitledBorder(null, "",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));
		head.setLayout(null);

		startButton.setIcon(Images.START_BEGIN);
        startButton.addActionListener(coreListener);
		Font font = new Font("Serif", Font.BOLD, 12);

		mineNumberLabel.setFont(font);
		time.setFont(font);
		mineNumberLabel.setHorizontalAlignment(JLabel.CENTER);
        time.setHorizontalAlignment(JLabel.CENTER);
        mineNumberLabel.setText("MineNum");
		time.setText("Time");

        showMineNum.setFont(font);
        showTime.setFont(font);
        showMineNum.setHorizontalAlignment(JLabel.CENTER);
        showTime.setHorizontalAlignment(JLabel.CENTER);
        showMineNum.setText("MineNum");
        showTime.setText("Time");

		head.add(mineNumberLabel);
		head.add(startButton);
		head.add(time);
		mainFrame.getContentPane().add(head);
		mainFrame.getContentPane().add(body);

		mainFrame.setTitle("扫雷 - KrayC");
		mainFrame.setIconImage(Images.FRAME_IMAGE);
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();

		head.setBounds(4, 5, body.getColumns() * buttonSize + bodyMarginOther * 2 - 4, 65);
		startButton.setBounds((head.getWidth() - 50) / 2,
				(head.getHeight() - 50) / 2, 50, 50);
		mineNumberLabel.setBounds(0, 0, head.getHeight(), head.getHeight());
		time.setBounds(head.getWidth() - head.getHeight(), 0, head.getHeight(),
				head.getHeight());

		body.setBounds(2, head.getHeight(), body.getColumns() * buttonSize + 2
				* bodyMarginOther, body.getRows() * buttonSize + bodyMarginNorth
				+ bodyMarginOther);

        if(GameModelImpl.isServer()){
            body.setBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.TitledBorder(""), "You are Server!",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION));
            startButton.setIcon(Images.START_RUN);
        }else if(GameModelImpl.isClient()){
            body.setBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.TitledBorder(""), "You are Client!",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION));
            startButton.setIcon(Images.START_RUN);
        }else {
            body.setBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.TitledBorder(""), "Have a try!",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION));
            startButton.setIcon(Images.START_RUN);
        }

		mainFrame.getContentPane().add(body);
		mainFrame.setSize(body.getWidth() + 10, body.getHeight()
				+ head.getHeight() + 60);
		mainFrame.validate();
		mainFrame.repaint();
		easy.setSelected(true);
		mainFrame
                .setLocation((screenSize.width - head.getWidth()) / 2,
						(screenSize.height - aJMenuBar.getHeight()
								- head.getHeight() - body.getHeight()) / 2);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public JMenuItem getMenuItem(String name) {
		if (menuItemMap == null)
			return null;
		return (JMenuItem) menuItemMap.get(name);
	}

	public MineBoardPanel getMineBoard(){
		return this.body;
	}

	public MineNumberLabel getMineNumberLabel(){
		return this.mineNumberLabel;
	}

	public JButton getStartButton(){
		return this.startButton;
	}

	/*
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * 游戏gameModel发生变化体现在这里
	 */
	public void update(Observable o, Object arg) {

		UpdateMessage notifyingObject = (UpdateMessage)arg;

		if(notifyingObject.getKey().equals("start")){
            GameVO newGame = (GameVO) notifyingObject.getValue();
			int gameWidth = newGame.getWidth();
			int gameHeight = newGame.getHeight();
			String level = newGame.getLevel();
			restart(gameHeight, gameWidth, level);
            if(GameModelImpl.isServer()){
                body.setBorder(new javax.swing.border.TitledBorder(
                        new javax.swing.border.TitledBorder(""), "You are Server!",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION));
                startButton.setIcon(Images.START_RUN);
            }else if(GameModelImpl.isClient()){
                body.setBorder(new javax.swing.border.TitledBorder(
                        new javax.swing.border.TitledBorder(""), "You are Client!",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION));
                startButton.setIcon(Images.START_RUN);
            }else {
                body.setBorder(new javax.swing.border.TitledBorder(
                        new javax.swing.border.TitledBorder(""), "Have a try!",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION));
                startButton.setIcon(Images.START_RUN);
            }
		}else if(notifyingObject.getKey().equals("end")){
            GameVO newGame = (GameVO) notifyingObject.getValue();
            if(newGame.getGameResultState() == GameResultState.FAIL){
                startButton.setIcon(Images.START_END);
                body.setBorder(new javax.swing.border.TitledBorder(
                        new javax.swing.border.TitledBorder(""), "You lose!",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION));
            }else if(newGame.getGameResultState() == GameResultState.SUCCESS){
                startButton.setIcon(Images.START_BEGIN);
                body.setBorder(new javax.swing.border.TitledBorder(
                        new javax.swing.border.TitledBorder(""), "Congratulations!",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION));;
            }else if(newGame.getGameResultState() == GameResultState.INTERRUPT){
                startButton.setIcon(Images.START_RUN);
                body.setBorder(new javax.swing.border.TitledBorder(
                        new javax.swing.border.TitledBorder(""), "Tie!",
                        javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION));;
            }
        }

        if(notifyingObject.getKey().equals("time")){
            time.setText(notifyingObject.getValue().toString());
        }

	}

	private void restart(int mineBoardHeight,int mineBoardWidth,String type) {

		mainFrame.getContentPane().remove(body);
		body = new MineBoardPanel(mineBoardHeight,mineBoardWidth);
		head.setBounds(4, 5, mineBoardWidth * buttonSize + bodyMarginOther * 2 - 4, 65);
		startButton.setBounds((head.getWidth() - 50) / 2,
				(head.getHeight() - 50) / 2, 50, 50);
		mineNumberLabel.setBounds(0, 0, head.getHeight(), head.getHeight());
		time.setBounds(head.getWidth() - head.getHeight(), 0, head.getHeight(),
				head.getHeight());
		body.setBounds(2, head.getHeight(), mineBoardWidth * buttonSize + 2
				* bodyMarginOther, mineBoardHeight * buttonSize + bodyMarginNorth
				+ bodyMarginOther);

        if(GameModelImpl.isServer()){
            body.setBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.TitledBorder(""), "You are Server!",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION));
            startButton.setIcon(Images.START_RUN);
        }else if(GameModelImpl.isClient()){
            body.setBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.TitledBorder(""), "You are Client!",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION));
            startButton.setIcon(Images.START_RUN);
        }else {
            body.setBorder(new javax.swing.border.TitledBorder(
                    new javax.swing.border.TitledBorder(""), "Have a try!",
                    javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.DEFAULT_POSITION));
            startButton.setIcon(Images.START_RUN);
        }

		mainFrame.getContentPane().add(body);
		mainFrame.setSize(body.getWidth() + 10, body.getHeight()
				+ head.getHeight() + 60);

		time.setText("0");

		if(type == null){
			easy.setSelected(true);
		}
		else if(type.equals("小")){
			easy.setSelected(true);
		}
		else if(type.equals("中")){
			hard.setSelected(true);
		}
		else if(type.equals("大")){
			hell.setSelected(true);
		}
		else if(type.equals("自")){
			custom.setSelected(true);
		}

		mainFrame.validate();
		mainFrame.repaint();
	}

}

