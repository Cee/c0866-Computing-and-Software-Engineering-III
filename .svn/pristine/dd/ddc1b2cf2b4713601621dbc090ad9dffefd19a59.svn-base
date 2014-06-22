package excalibur.game.presentation.ui;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.Map;
import excalibur.game.logic.gamelogic.Map.ACTION;
import excalibur.game.logic.gamelogic.ScoreCounter;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.PlayButton;
/**
 * 
 * @author luck
 * 
 */
public class PlayBoard extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**-
	 * 逻辑地图
	 */
	protected Map map;
	/**
	 * 是否正在交换
	 */
	protected boolean isSwap = false;
	/**
	 * 所有的按钮
	 */
	protected PlayButton buttonList[][] = new PlayButton[Constant.Config.MAP_SIZE][Constant.Config.MAP_SIZE];
	/**
	 * 被选中的按钮
	 */
	private PlayButton selectedButton;
	/**
	 * 得分
	 */
	protected int score;
	/**
	 * 按钮的图片
	 */
	protected String[] buttonBgs = new String[] { Constant.PictureSrc.blue,
			Constant.PictureSrc.brown, Constant.PictureSrc.red,
			Constant.PictureSrc.green, Constant.PictureSrc.purple };
	/**
	 * 按钮消失的动画文件夹
	 */
	protected String[] buttonBgClear = new String[] {
			Constant.PictureSrc.blue_clear, Constant.PictureSrc.brown_clear,
			Constant.PictureSrc.red_clear, Constant.PictureSrc.green_clear,
			Constant.PictureSrc.purple_clear };
	/**
	 * 按钮的监听
	 */
	ButtonListener myButtonListener;
	/**
	 * 拖动的监听
	 */
	MotionListener myMotionListener;
	/**
	 * 键盘的监听
	 */
	MyKeyListener myKeyListener;
	//道具列表
	ArrayList<Tools> tools;
	protected ImageIcon[][] iconList = new ImageIcon[5][15];
	int initialX = 0;
	int initialY = 0;
	
	HashMap<String, Double> bonusMap;
	
   protected boolean isDropDown = true;
	// 分数
	JLabel scoreLabel;

	// 是否结束
	boolean isStop = false;

	int superPower = 1;

	SuperModel superModel;
	
	protected int maxCombo=0;
	
	protected int combo=0;

	/**
	 * 初始化地图
	 * 
	 * @param scoreLabel
	 */
	public PlayBoard(JLabel scoreLabel,ArrayList<Tools> tools) {
		map = new Map();
		this.scoreLabel = scoreLabel;
		this.tools = tools;
		setOpaque(false);
		setLayout(null);
		setSize(Constant.Config.BT_WIDTH * 9, Constant.Config.BT_WIDTH * 9);
		myButtonListener = new ButtonListener();
		myMotionListener = new MotionListener();
		myKeyListener = new MyKeyListener();
		makeButtons();
		superModel = new SuperModel();
		readImages();
		initialBonus();
	}
	
	public void initialBonus(){
		bonusMap = new HashMap<>();
		bonusMap.put("score", 1.0);
		bonusMap.put("super", 1.0);
		bonusMap.put("remind", 3.0);
	}
	
	public void readImages() {
		String iconSrc = null;
		for (int k = 0; k < 5; k++) {
			for (int i = 0; i <= 14; i++) {
				if (i < 10) {
					iconSrc = buttonBgClear[k] + "0" + i + "_0.png";
				} else {
					iconSrc = buttonBgClear[k] + i + "_0.png";
				}
				ImageIcon icon = new ImageIcon(iconSrc);
				iconList[k][i] = icon;
			}
		}
	}

	/**
	 * 生成所有初始按钮
	 */
	public void makeButtons() {
		int[][] typeMap = map.initialMap();
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				/**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				int type = typeMap[j][i];
				ImageIcon icon = new ImageIcon(buttonBgs[type - 1]);
				PlayButton button = new PlayButton(icon);
				button.type = type - 1;
				button.setBounds(i * Constant.Config.BT_WIDTH, j
						* Constant.Config.BT_HEIGHT, Constant.Config.BT_WIDTH,
						Constant.Config.BT_HEIGHT);
				buttonList[i][j] = button;
				button.addMouseListener(myButtonListener);
				button.addMouseMotionListener(myMotionListener);
				add(button);
			}
		}
	}

	class MyKeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				break;
			default:
				break;
			}
		}
		
	}
	/**
	 * 
	 * @author luck 拖动监听
	 */
	class MotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			if (!isSwap && selectedButton != null) {
				int x = e.getX() - initialX;
				int y = e.getY() - initialY;
				/**
				 * 此处判断拖动距离，以及比较偏移的x与y来判定移动方向
				 */
				if (e.getX() / Constant.Config.BT_WIDTH != initialX
						/ Constant.Config.BT_WIDTH
						|| e.getY() / Constant.Config.BT_WIDTH != initialY
								/ Constant.Config.BT_WIDTH && !isSwap) {
					if (x > 0 && y > 0) {
						if (x > y) {
							swap(ACTION.RIGHT);
						} else {
							swap(ACTION.DOWN);
						}
					} else {
						if (x > 0) {
							if (x > -y) {
								swap(ACTION.RIGHT);
							} else {
								swap(ACTION.UP);
							}
						} else if (y > 0) {
							if (y > -x) {
								swap(ACTION.DOWN);
							} else {
								swap(ACTION.LEFT);
							}
						} else {
							if (x > y) {
								swap(ACTION.UP);
							} else {
								swap(ACTION.LEFT);
							}
						}
					}
				}

			}
		}
	}
	
	private void setSelectedButtonForMouse(PlayButton selectedButton){
		
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				buttonList[i][j].setBorderPainted(false);
			}
		}
		this.selectedButton = selectedButton;
		if (selectedButton!=null)
			selectedButton.setBorderPainted(true);
	}
	/**
	 * 
	 * @author luck 按钮的按键监听
	 */
	class ButtonListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (!isSwap) {
				if (selectedButton!=null&&selectedButton !=(PlayButton)e.getSource()){
					ACTION action = selectedButton.isNear((PlayButton)e.getSource());
					if (action!=null){
						swap(action);
					} else {
						selectedButton = (PlayButton) e.getSource();	
						setSelectedButtonForMouse(selectedButton);
					}
				} else {
					selectedButton = (PlayButton) e.getSource();
					setSelectedButtonForMouse(selectedButton);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
		}

	}

	/**
	 * 交换
	 * 
	 * @param action
	 */
	public void swap(ACTION action) {
		if (!isSwap) {
			isSwap = true;
			new Thread(new SwapThread(action)).start();
		}
	}

	/**
	 * 交换的动画线程
	 * 
	 * @author luck
	 * 
	 */
	class SwapThread implements Runnable {
		PlayButton anotherButton;
		ArrayList<Point> clearList;
		ACTION action;
		int x;
		int y;

		/**
		 * 根据Action交换按钮
		 */
		public void changeButton() {
			switch (action) {
			case UP:
				if (y >= 1) {
					anotherButton = buttonList[x][y - 1];
					buttonList[x][y - 1] = selectedButton;
				}
				break;
			case DOWN:
				if (y <= Constant.Config.MAP_SIZE - 2) {
					anotherButton = buttonList[x][y + 1];
					buttonList[x][y + 1] = selectedButton;
				}
				break;
			case RIGHT:
				if (x <= Constant.Config.MAP_SIZE - 2) {
					anotherButton = buttonList[x + 1][y];
					buttonList[x + 1][y] = selectedButton;
				}
				break;
			case LEFT:
				if (x >= 1) {
					anotherButton = buttonList[x - 1][y];
					buttonList[x - 1][y] = selectedButton;
				}
				break;
			default:
				break;
			}
			if (anotherButton != null) {
				buttonList[x][y] = anotherButton;
			}
		}

		/**
		 * 构造函数
		 * 
		 * @param action
		 */
		public SwapThread(ACTION action) {
			this.action = action;
			x = selectedButton.getX() / Constant.Config.BT_WIDTH;
			y = selectedButton.getY() / Constant.Config.BT_HEIGHT;
			changeButton();
			clearList = map.clear(y, x, action);
		}

		public void trySwap(int speed) {
			switch (action) {
			case UP:
				selectedButton.setLocation(selectedButton.getX(),
						selectedButton.getY() - speed);
				anotherButton.setLocation(anotherButton.getX(),
						anotherButton.getY() + speed);
				break;
			case DOWN:
				selectedButton.setLocation(selectedButton.getX(),
						selectedButton.getY() + speed);
				anotherButton.setLocation(anotherButton.getX(),
						anotherButton.getY() - speed);
				break;
			case LEFT:
				selectedButton.setLocation(selectedButton.getX() - speed,
						selectedButton.getY());
				anotherButton.setLocation(anotherButton.getX() + speed,
						anotherButton.getY());
				break;
			case RIGHT:
				selectedButton.setLocation(selectedButton.getX() + speed,
						selectedButton.getY());
				anotherButton.setLocation(anotherButton.getX() - speed,
						anotherButton.getY());
				break;
			default:
				break;
			}
		}

		@Override
		public void run() {
			if (anotherButton != null) {
				for (int i = 0; i < Constant.Config.BT_HEIGHT; i = i + 5) {
					trySwap(5);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/**
				 * 若返回的可消除的坐标列表为空，回归原来画面
				 */
				if (clearList.isEmpty()) {
					selectedButton = anotherButton;
					changeButton();
					for (int i = 0; i < Constant.Config.BT_HEIGHT; i = i + 5) {
						trySwap(5);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					superModel.count();
					score += ScoreCounter.getScore(clearList) * superPower;
					clearButtons(clearList);
					while (true) {
						clearList = map.clear(-1, -1, ACTION.NOACTION);
						if (clearList.isEmpty()) {
							break;
						} else {
							superModel.count();
							score += ScoreCounter.getScore(clearList) * superPower;
							clearButtons(clearList);
						}
					}
				}
				anotherButton = null;
			}
			setSelectedButtonForMouse(null);
			isSwap = false;
		}

	}


	/**
	 * 清除消除的按钮
	 * 
	 * @param points
	 */
	public void clearButtons(final ArrayList<Point> points) {
		scoreLabel.setText(score + "");
		for (int i = 0; i <= 14; i++) {
			for (Point point : points) {
				int y = (int) point.getX();
				int x = (int) point.getY();
				PlayButton playButton = buttonList[x][y];
				playButton.setIcon(iconList[playButton.type][i]);
				if (i == 14) {
					remove(buttonList[x][y]);
					buttonList[x][y] = null;
				}
			}
			try {
				Thread.sleep(30 - 2 * i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dropDown(points, map.getFillMap());
	}


	public HashMap<Integer, Integer[]> getDepth(ArrayList<Point> points) {
		// 若从上到下 key指代列数，Integer中第一个代表每列消除的最深处，第二个代表每列个数
		// 若从左到右 key代表行数，Integer中第一个代表每行消除的最深处，第二个代表每行个数
		HashMap<Integer, Integer[]> map = new HashMap<>();
		for (Point point : points) {
			int y = (int) point.getX();
			int x = (int) point.getY();
			if (isDropDown) {
				if (map.containsKey(x)) {
					Integer[] pairs = map.get(x);
					if (pairs[0] < y) {
						pairs[1] = pairs[1] + 1;
						pairs[0] = y;
						map.put(x, pairs);
					}
				} else {
					map.put(x, new Integer[] { y, 1 });
				}
			} else {
				if (map.containsKey(y)) {
					Integer[] pairs = map.get(y);
					if (pairs[0] < x) {
						pairs[1] = pairs[1] + 1;
						pairs[0] = x;
						map.put(y, pairs);
					}
				} else {
					map.put(y, new Integer[] { x, 1 });
				}
			}

		}
		return map;
	}

	public HashMap<Integer, Integer[]> shiftButtons(
			HashMap<Integer, Integer[]> map, int[][] newMap) {
		HashMap<Integer, Integer[]> jumpMap = new HashMap<>();
		PlayButton button;
		// 地图上已有的按钮全部平移
		for (Integer key : map.keySet()) {
			Integer[] pairs = map.get(key);
			Integer[] jumpList = new Integer[pairs[0] + 1];
			for (int i = pairs[0]; i >= pairs[1]; i--) {
				for (int jump = 1; jump <= pairs[1]; jump++) {
					if (buttonList[key][i - jump] != null) {
						// 如果从上往下 向下平移
						if (isDropDown) {
							buttonList[key][i] = buttonList[key][i - jump];
							buttonList[key][i - jump] = null;
						} else {
							// 如果从左往右，向右平移
							buttonList[i][key] = buttonList[i - jump][key];
							buttonList[i - jump][key] = null;
						}
						jumpList[i] = jump;
						break;
					}
				}
			}
			// 新出现的Button根据newMap摆在相应位置
			for (int i = pairs[1] - 1; i >= 0; i--) {
				int type = -1;
				if (isDropDown) {
					type = newMap[Constant.Config.MAP_SIZE - 1 - i][key];
				} else {
					type = newMap[key][Constant.Config.MAP_SIZE - 1 - i];
				}
				ImageIcon icon = new ImageIcon(buttonBgs[type - 1]);
				button = new PlayButton(icon);
				button.type = type - 1;
				if (isDropDown) {
					button.setBounds(key * Constant.Config.BT_WIDTH,
							-((i + 1) * Constant.Config.BT_HEIGHT),
							Constant.Config.BT_WIDTH, Constant.Config.BT_HEIGHT);
					buttonList[key][pairs[1] - i - 1] = button;
				} else {
					button.setBounds(-((i + 1) * Constant.Config.BT_HEIGHT),
							key * Constant.Config.BT_WIDTH,
							Constant.Config.BT_WIDTH, Constant.Config.BT_HEIGHT);
					buttonList[pairs[1] - i - 1][key] = button;
				}
				add(button);
				button.addMouseListener(myButtonListener);
				button.addMouseMotionListener(myMotionListener);
				jumpList[i] = pairs[1];
			}
			jumpMap.put(key, jumpList);
		}
		return jumpMap;
	}

	/**
	 * 落下
	 * 
	 * @param points
	 * @param newMap
	 */
	public void dropDown(ArrayList<Point> points, int[][] newMap) {

		HashMap<Integer, Integer[]> map = getDepth(points);
		HashMap<Integer, Integer[]> jumpMap = shiftButtons(map, newMap);
		PlayButton playButton;
		for (int i = 0; i < 5; i++) {
			for (Integer key : map.keySet()) {
				Integer[] jumpList = jumpMap.get(key);
				Integer[] pairs = map.get(key);
				for (int j = pairs[0]; j >= 0; j--) {
					if (isDropDown) {
						playButton = buttonList[key][j];
					} else {
						playButton = buttonList[j][key];
					}
					if (playButton != null) {
						int x = playButton.getX();
						int y = playButton.getY();
						if (isDropDown) {
							playButton.setLocation(x, y
									+ Constant.Config.BT_HEIGHT / 5
									* jumpList[j]);
						} else {
							playButton.setLocation(x
									+ Constant.Config.BT_HEIGHT / 5
									* jumpList[j], y);
						}

					}

				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void timeUp(boolean isFalse) {
		removeListener();
		isStop = true;
		if (!isFalse){
			JOptionPane.showMessageDialog(this, "时间到！您的分数是：" +(int)(score*bonusMap.get("score")));
			DataUtils.getInstance().addRecord(score, maxCombo);		
		}
	}

	
	private void removeListener() {
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				/**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				buttonList[i][j].removeMouseListener(myButtonListener);
				buttonList[i][j].removeMouseMotionListener(myMotionListener);
			}
		}
	}	
	/**
	 * 超级模式
	 * @author luck
	 *
	 */
	class SuperModel implements Runnable {
		int time = 5;
		Queue<Long> queue = new LinkedList<Long>();
		long lastTime = 0;

		@Override
		public void run() {
			while (!isStop) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				time--;
				if (time == 0) {
					superPower /= 2;
					break;
				}
			}
		}
		/**
		 * 判断是否超级模式
		 */
		public void count() {
			long now = new Date().getTime();
			if (now - lastTime <= bonusMap.get("super")*1000 || lastTime == 0) {
				queue.offer(now);
				combo++;
			} else {
				queue.clear();
				queue.offer(now);
				if (combo>maxCombo){
					maxCombo=combo;
					System.out.println("当前最大连击"+maxCombo);
				}
				combo=1;
			}
			if (queue.size() == 4) {
				superPower = superPower * 2;
				System.err.println(superPower);
				queue.clear();
				queue.offer(now);
				new Thread(new SuperModel()).start();
			}
			lastTime = now;
		}
	}
	
	
	public void setBonus(String key,double d){
		bonusMap.put(key, d);
	}
	

}
