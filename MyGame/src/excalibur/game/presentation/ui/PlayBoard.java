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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.ChessPoint;
import excalibur.game.logic.gamelogic.Map;
import excalibur.game.logic.gamelogic.Map.ACTION;
import excalibur.game.logic.gamelogic.ScoreCounter;
import excalibur.game.logic.gamelogic.SuperModelAdapter;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.PrefferenceLogic;
import excalibur.game.logic.syslogic.DataUtils.Shipin;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.DialogCreator;
import excalibur.game.presentation.myuicomponent.PlayButton;
import excalibur.game.presentation.ui.SingleGameBoard.TimeThread;

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
	/**
	 * - 逻辑地图
	 */
	protected Map map;
	/**
	 * 是否正在交换
	 */
	public boolean isSwap = false;
	/**
	 * 所有的按钮
	 */
	protected PlayButton buttonList[][] = new PlayButton[Constant.Config.MAP_SIZE][Constant.Config.MAP_SIZE];
	/**
	 * 被选中的按钮
	 */
	private PlayButton selectedButton;
	private PlayButton selectedButtonForKeyBoard;
	/**
	 * 得分
	 */
	protected int score;
	/**
	 * 按钮的图片
	 */
	
	protected String[] buttonBgs;
	protected String[] effectButtonBgs;
	protected String[] hintButtons ;
	protected String cannotMoveImg = Constant.PictureSrc.rock;
	/**
	 * 按钮消失的动画文件夹
	 */
	
	protected String[] buttonBgClear;
	protected String toolB ;
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
	// 道具列表
	ArrayList<Tools> tools;
	/*
	 * playBoard背景
	 */
	JLabel playBoardBg;
	protected ImageIcon[][] iconList = new ImageIcon[5][15];
	int initialX = 0;
	int initialY = 0;
	
	HashMap<String, Double> bonusMap;

	protected boolean isDropDown = false;
	// 分数
	JLabel scoreLabel;

	// 是否结束
	boolean isStop = false;

	int superPower = 1;

	SuperModel superModel;

	protected int maxCombo = 0;

	protected int combo = 0;
	Hint hint;
	SuperModelAdapter delegate;
	TimeThread timer;
	
	PlayButton hintButton1;
	PlayButton hintButton2;
	protected int cannotMoveColor ; 
	public boolean waiting;
	/**
	 * 初始化地图
	 * 
	 * @param scoreLabel
	 */
	public PlayBoard(JLabel scoreLabel, ArrayList<Tools> tools) {
		initMap();
		Shipin[] shipins = DataUtils.getInstance().getSelectShipin();
		boolean hasShipinBlock=false;
		for (int i = 0; i < shipins.length; i++) {
			if (shipins[i]!=null&&shipins[i].type==1) {
				hasShipinBlock=true;
				buttonBgs = Constant.PictureSrc.loadGameIcon(shipins[i].name+"/normal/", Constant.PictureSrc.PNG);
				buttonBgClear = Constant.PictureSrc.loadGameIcon(shipins[i].name+"/clear/", "_clear/_00");
				effectButtonBgs = Constant.PictureSrc.loadGameIcon(shipins[i].name+"/effect/", Constant.PictureSrc.GIF);
				hintButtons = Constant.PictureSrc.loadGameIcon(shipins[i].name+"/hint/", Constant.PictureSrc.GIF);
				toolB = Constant.PictureSrc.getItemB(shipins[i].name);
			} 
		}
		if (!hasShipinBlock) {
			buttonBgs = Constant.PictureSrc.loadGameIcon("default/normal/", Constant.PictureSrc.PNG);
			buttonBgClear = Constant.PictureSrc.loadGameIcon("default/clear/", "_clear/_00");
			effectButtonBgs = Constant.PictureSrc.loadGameIcon("default/effect/", Constant.PictureSrc.GIF);
			hintButtons = Constant.PictureSrc.loadGameIcon("default/hint/", Constant.PictureSrc.GIF);
			toolB = Constant.PictureSrc.getItemB("default");

		}
		
		this.scoreLabel = scoreLabel;
		this.tools = tools;
		useTools();
		setOpaque(false);
		setLayout(null);
		setSize(Constant.Config.BT_WIDTH * 9, Constant.Config.BT_WIDTH * 9);
		superModel = new SuperModel();
		setSelectedButtonForKeyBoard(buttonList[0][0]);
		isDropDown = PrefferenceLogic.getIsVertical();
		initGame();
		cannotMoveColor = 0;
		waiting = false;
	}
	protected void initMap(){
		map = new Map();
	}
	public void setTimer(TimeThread timer) {
		this.timer = timer;
	}

	public void useTools(){
		initialBonus();
		for (Tools each_tool:this.tools){
			each_tool.setPlayBoard(this);
			each_tool.affect();
		}
	}
	public int[][] getMap() {
		return map.getMap();
	}

	public void initGame() {
		myButtonListener = new ButtonListener();
		myMotionListener = new MotionListener();
		myKeyListener = new MyKeyListener();
		makeButtons();
		readImages();
		int count = getComponentCount();
		for (int i = 0; i < count; i++) {
			getComponent(i).addKeyListener(myKeyListener);
		}
		hint = new Hint();
		hint.clearCount();
		new Thread(hint).start();
	}
	
	public void restart()
	{
		makeButtons();
		setScore(0);
		int count = getComponentCount();
		for (int i = 0; i < count; i++) {
			getComponent(i).addKeyListener(myKeyListener);
		}
		hint.clearCount();
		delegate.hideSuperModel();
		isStop=false;
		unLock();
		addListener();
	}


	public void setSelectedButtonForKeyBoard(
			PlayButton selectedButtonForKeyBoard) {
		if (selectedButtonForKeyBoard != null) {
			if (this.selectedButtonForKeyBoard != null) {
				this.selectedButtonForKeyBoard.setBorder(null);
				this.selectedButtonForKeyBoard.setBorderPainted(false);
				this.selectedButtonForKeyBoard.setSure(false);
			}
			this.selectedButtonForKeyBoard = selectedButtonForKeyBoard;
			this.selectedButtonForKeyBoard.setBorderPainted(true);
			this.selectedButtonForKeyBoard.setBorder(BorderFactory
					.createRaisedBevelBorder());
		}
	}

	public void initialBonus() {
		bonusMap = new HashMap<>();
		bonusMap.put("score", 1.0);
		bonusMap.put("super", 1.0);//这里偷偷改一下可以测试superMode
		bonusMap.put("remind", 3.0);
		bonusMap.put("time", 0.0);
		bonusMap.put("bomb", 0.0);
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
				if (buttonList[i][j]!=null) {
					buttonList[i][j].type = type;
					buttonList[i][j].setIcon(icon);
				} else {
					PlayButton button = new PlayButton(icon);
					button.type = type;
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
	}

	
	 public void addListener() {
		 for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			 for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				 /**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				 buttonList[i][j].addMouseListener(myButtonListener);
				 buttonList[i][j].addMouseMotionListener(myMotionListener);
				 buttonList[i][j].addKeyListener(myKeyListener);
			 }
		 }
	
	 }

	class MyKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (waiting)
				return;
			Point point = searchButton(selectedButtonForKeyBoard);
			if (point == null) {
				setSelectedButtonForKeyBoard(buttonList[4][4]);
				return;
			}
			if (selectedButtonForKeyBoard.isSure()) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (point.getX() > 0) {
						swap(ACTION.LEFT, selectedButtonForKeyBoard);
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (point.getX() < 8) {
						swap(ACTION.RIGHT, selectedButtonForKeyBoard);
					}
					break;
				case KeyEvent.VK_UP:
					if (point.getY() > 0) {
						swap(ACTION.UP, selectedButtonForKeyBoard);
					}
					break;
				case KeyEvent.VK_DOWN:
					if (point.getY() < 8) {
						swap(ACTION.DOWN, selectedButtonForKeyBoard);
					}
					break;
				case KeyEvent.VK_SPACE:
					if (point != null) {
						selectedButtonForKeyBoard.setSure(false);
					}

				default:
					break;
				}
			} else
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (point != null && point.getY() > 0) {
						setSelectedButtonForKeyBoard(buttonList[(int) point
								.getY() - 1][(int) point.getX()]);
					} else {
						setSelectedButtonForKeyBoard(buttonList[8][(int) point
								.getX()]);
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (point != null && point.getY() < 8) {
						setSelectedButtonForKeyBoard(buttonList[(int) point
								.getY() + 1][(int) point.getX()]);
					} else {
						setSelectedButtonForKeyBoard(buttonList[0][(int) point
								.getX()]);
					}
					break;
				case KeyEvent.VK_UP:
					if (point != null && point.getX() > 0) {
						setSelectedButtonForKeyBoard(buttonList[(int) point
								.getY()][(int) point.getX() - 1]);
					} else {
						setSelectedButtonForKeyBoard(buttonList[(int) point
								.getY()][8]);
					}
					break;
				case KeyEvent.VK_DOWN:
					if (point != null && point.getX() < 8) {
						setSelectedButtonForKeyBoard(buttonList[(int) point
								.getY()][(int) point.getX() + 1]);
					} else {
						setSelectedButtonForKeyBoard(buttonList[(int) point
								.getY()][0]);
					}
					break;
				case KeyEvent.VK_SPACE:
					if (point != null) {
						selectedButtonForKeyBoard.setSure(true);
					}
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
		boolean mutex=false;
		@Override
		public void mouseDragged(MouseEvent e) {
			if (mutex) {
				return;
			}
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
					mutex=true;
					if (x > 0 && y > 0) {
						if (x > y) {
							swap(ACTION.RIGHT, selectedButton);
						} else {
							swap(ACTION.DOWN, selectedButton);
						}
					} else {
						if (x > 0) {
							if (x > -y) {
								swap(ACTION.RIGHT, selectedButton);
							} else {
								swap(ACTION.UP, selectedButton);
							}
						} else if (y > 0) {
							if (y > -x) {
								swap(ACTION.DOWN, selectedButton);
							} else {
								swap(ACTION.LEFT, selectedButton);
							}
						} else {
							if (x > y) {
								swap(ACTION.UP, selectedButton);
							} else {
								swap(ACTION.LEFT, selectedButton);
							}
						}
					}
				}

			}
		}
	}

	protected void handleTrigger(Point point, ACTION action) {
		if (point==null) {
			lock();
			ArrayList<Point> clearList = map.clear(0,
					0, action);
			;
			if (clearList.isEmpty()) {
			} else {
				clearButtons(clearList);
				while (true) {
					clearList = map.clear(-1, -1, ACTION.NOACTION);
					if (clearList.isEmpty()) {
						break;
					} else {
						clearButtons(clearList);
					}
				}
			}
			unLock();
		} else 
		if (!isSwap&&buttonList[point.y][point.x].type==6||buttonList[point.y][point.x].type<0) {
			if (getBonus("time")!=0)
				timer.addTime(getBonus("time"));
			lock();
			ArrayList<Point> clearList = map.clear((int) point.getX(),
					(int) point.getY(), action);
			;
			if (clearList.isEmpty()) {
			} else {
				clearButtons(clearList);
				while (true) {
					clearList = map.clear(-1, -1, ACTION.NOACTION);
					if (clearList.isEmpty()) {
						break;
					} else {
						clearButtons(clearList);
					}
				}
			}
			unLock();
			
		} else {
			System.out.println("判断返回");
			return;
		}

	}

	public Point searchButton(PlayButton button) {
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				if (buttonList[i][j] == button) {
					Point point = new Point(j, i);
					return point;
				}
			}
		}
		return null;
	}

	void setSelectedButtonForMouse(PlayButton selectedButton) {

		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				if (buttonList[i][j] != null
						&& buttonList[i][j] != selectedButtonForKeyBoard)
					buttonList[i][j].setBorderPainted(false);
			}
		}
		this.selectedButton = selectedButton;
		if (selectedButton != null)
			selectedButton.setBorderPainted(true);
	}

	/**
	 * 
	 * @author luck 按钮的按键监听
	 */
	class ButtonListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (waiting) {
				return;
			}
			if (!isSwap) {
			
				PlayButton button = (PlayButton) e.getSource();
				if (button.type < 0) {
					final Point point = searchButton(button);
					if (point != null) {
						new Thread() {
							public void run() {
								handleTrigger(point, ACTION.TRIGGER_A);
							}
						}.start();
					}
				} else if (button.type == 6) {
					final Point point = searchButton(button);
					if (point != null) {
						new Thread() {
							public void run() {
								handleTrigger(point, ACTION.TRIGGER_B);
							}
						}.start();
					}
				}

				else if (selectedButton != null
						&& selectedButton != (PlayButton) e.getSource()) {
					ACTION action = selectedButton.isNear((PlayButton) e
							.getSource());
					if (action != null) {
						swap(action, selectedButton);
					} else {
						selectedButton = (PlayButton) e.getSource();
						setSelectedButtonForMouse(selectedButton);
					}
				} else {
					selectedButton = (PlayButton) e.getSource();
					setSelectedButtonForMouse(selectedButton);
				}
			} else {
				System.err.println("Is Swap");
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
	public void swap(ACTION action, PlayButton selectedButton) {
		if (!isSwap) {	
			lock();
			new Thread(new SwapThread(action, selectedButton)).start();
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
		PlayButton selectedButton;
		ArrayList<Point> clearList;
		ACTION action;
		int x;
		int y;

		/**
		 * 根据Action交换按钮
		 */
		public void changeButton() {
			if (selectedButton.type==cannotMoveColor) {
				return;
			} else {
				switch (action) {
				case UP:
					if (y >= 1) {
						if (buttonList[x][y-1].type==cannotMoveColor) {
							return;
						}
						anotherButton = buttonList[x][y - 1];
						buttonList[x][y - 1] = selectedButton;
					}
					break;
				case DOWN:
					if (y <= Constant.Config.MAP_SIZE - 2) {
						if (buttonList[x][y+1].type==cannotMoveColor) {
							return;
						}
						anotherButton = buttonList[x][y + 1];
						buttonList[x][y + 1] = selectedButton;
					}
					break;
				case RIGHT:
					if (x <= Constant.Config.MAP_SIZE - 2) {
						if (buttonList[x+1][y].type==cannotMoveColor) {
							return;
						}
						anotherButton = buttonList[x + 1][y];
						buttonList[x + 1][y] = selectedButton;
					}
					break;
				case LEFT:
					if (x >= 1) {
						if (buttonList[x-1][y].type==cannotMoveColor) {
							return;
						}
						anotherButton = buttonList[x - 1][y];
						buttonList[x - 1][y] = selectedButton;
					}
					break;
				default:
					break;
				}				
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
		public SwapThread(ACTION action, PlayButton selectedButton) {
			this.action = action;
			this.selectedButton = selectedButton;
			Point point = searchButton(selectedButton);
			x = point.y;
			y = point.x;
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
					clearButtons(clearList);
					while (true) {
						clearList = map.clear(-1, -1, ACTION.NOACTION);
						if (clearList.isEmpty()) {
							break;
						} else {
							clearButtons(clearList);
						}

					}
				}
				anotherButton = null;
			}
			setSelectedButtonForMouse(null);
			unLock();
			
		}
	}

	protected void setScore(int score){
		this.score =score ;
		scoreLabel.setText(score+"");
	}
	/**
	 * 清除消除的按钮
	 * 
	 * @param points
	 */
	public void clearButtons(final ArrayList<Point> points) {
		
		if (hintButton1!=null) {
			hintButton1.setIcon(new ImageIcon(buttonBgs[Math.abs(hintButton1.type) - 1]));
			hintButton1=null;
		}
		if (hintButton2!=null) {
			hintButton2.setIcon(new ImageIcon(buttonBgs[Math.abs(hintButton2.type) - 1]));
			hintButton2=null;
		}
		
		
		superModel.count();
		ArrayList<ChessPoint> chessPoints = new ArrayList<>();
		for (Point point : points) {
			System.out.println("Type"+buttonList[point.x][point.y].type);
			ChessPoint chessPoint = new ChessPoint(
					buttonList[point.x][point.y].type, 0, point);
			chessPoints.add(chessPoint);
		}
		score += ScoreCounter.getScore(chessPoints) * superPower;
		setScore(score);
		for (int i = 0; i <= 14; i++) {
			for (Point point : points) {
				int y = (int) point.getX();
				int x = (int) point.getY();
				PlayButton playButton = buttonList[x][y];
				if (playButton.type != 6)
					playButton
							.setIcon(iconList[Math.abs(playButton.type) - 1][i]);
				else
					playButton.setIcon(iconList[0][i]);
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
		int[][] fillMap = map.getFillMap(!isDropDown);
		dropDown(points, fillMap);
	}

	// public void printAll() {
	// for (int i = 0; i < 9; i++) {
	// for (int j = 0; j < 9; j++) {
	// if (buttonList[j][i] != null) {
	// System.out.print(buttonList[j][i].type + " ");
	// } else {
	// System.out.print("0 ");
	// }
	// }
	// System.out.println();
	// }
	// }

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
					pairs[1] = pairs[1] + 1;
					if (pairs[0] < y) {
						pairs[0] = y;
						map.put(x, pairs);
					}
				} else {
					map.put(x, new Integer[] { y, 1 });
				}
			} else {
				if (map.containsKey(y)) {
					Integer[] pairs = map.get(y);
					pairs[1] = pairs[1] + 1;

					if (pairs[0] < x) {
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
					// 如果从上往下 向下平移
					if (isDropDown) {
						if (buttonList[key][i - jump] != null) {
							buttonList[key][i] = buttonList[key][i - jump];
							buttonList[key][i - jump] = null;
							jumpList[i] = jump;
							break;
						}
					} else {
						if (buttonList[i - jump][key] != null) {
							buttonList[i][key] = buttonList[i - jump][key];
							buttonList[i - jump][key] = null;
							jumpList[i] = jump;
							break;
						}
						// 如果从左往右，向右平移
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
				// ToDo
				ImageIcon icon;
				if (type < 0)
					icon = new ImageIcon(effectButtonBgs[-type - 1]);
				else if (type == 6)
					icon = new ImageIcon(toolB);
				else
					icon = new ImageIcon(buttonBgs[type - 1]);
				button = new PlayButton(icon);
				button.type = type;
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

	// private void printArray(Integer[] datas) {
	// for (Integer data : datas) {
	// System.out.print(data + " ");
	// }
	// }

	/**
	 * 落下
	 * 
	 * @param points
	 * @param newMap
	 */
	public void dropDown(ArrayList<Point> points, int[][] newMap) {
		HashMap<Integer, Integer[]> map = getDepth(points);

		// for (Integer key : map.keySet()){
		// System.out.print(key+":");
		// printArray(map.get(key));
		// System.out.println();
		// }
		HashMap<Integer, Integer[]> jumpMap = shiftButtons(map, newMap);
		// for (Integer key : jumpMap.keySet()){
		// System.out.print(key+":");
		// printArray(jumpMap.get(key));
		// System.out.println();
		// }

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
	public void handleRemainedTrigger(){
			System.out.println("循环");
//			boolean hasFound = false;
			for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
				for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
					if (buttonList[j][i].type==6||buttonList[j][i].type<0) {
							final ACTION action=buttonList[j][i].type<0?ACTION.TRIGGER_A:ACTION.TRIGGER_B;
							final Point point = new Point(i,j);
							new Thread(){
								public void run(){
										System.out.println("进入队列");
										handleTrigger(point, action);
										handleRemainedTrigger();
									}
							}.start();		
						return;
					}
				}
			}
			removeListener();
			while (isSwap){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//施工...
			
/*			
			JOptionPane.showMessageDialog(this, "时间到！您的分数是："
					+ (int) (score * bonusMap.get("score")));
*/
//			
			//redrock...
						
			DataUtils dataUtils=DataUtils.getInstance();
			int temp=dataUtils.getLevel();
			dataUtils.addRecord(score, maxCombo);
			int choise=0;
			boolean islevelup=dataUtils.getLevel()-temp>=1;
			Media.stopBGM(Media.GAME);		
			if(islevelup){
//				DialogCreator.oneButtonDialog("", "恭喜你升级了！时间到！您的分数是："
//						+ (int) (score * bonusMap.get("score")));	
				Media.playSound(Media.LEVELUP);
//				choise=DialogCreator.twoButtonDialog("", "恭喜你升级了！时间到！您的分数是："
//						+ (int) (score * bonusMap.get("score"))+";获得金钱"+(int) (score * bonusMap.get("score"))/10, "回到主界面", "重新开始",0);
				choise = DialogCreator.gameResultDialog("", (int) (score * bonusMap.get("score")/10), (int) (score * bonusMap.get("score")), true);
			}else{
				Media.playSound(Media.ENDGAME);	
				choise = DialogCreator.gameResultDialog("", (int) (score * bonusMap.get("score")/10), (int) (score * bonusMap.get("score")), false);

//				choise=DialogCreator.twoButtonDialog("", "时间到！您的分数是："
//						+ (int) (score * bonusMap.get("score"))+";获得金钱"+(int) (score * bonusMap.get("score"))/10, "回到主界面", "重新开始",0);
			}
			
			if(choise==1){
				delegate.backtoMain();
				
			}else if(choise==2){
				Media.playBGM(Media.GAME);
				delegate.restart();
			}
			
	}
	public void timeUp(boolean isFalse) {
		
		if (!isFalse) {
			while (isSwap){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			removeListener();
			isStop = true;
			while (getBonus("bomb")>0) {
				setBonus("bomb", getBonus("bomb")-1);
				handleTrigger(null, ACTION.TRIGGER_COLOR);
			};
			handleRemainedTrigger();
		}
	}

	protected void removeListener() {
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				/**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				if (buttonList[i][j] != null) {
					buttonList[i][j].removeMouseListener(myButtonListener);
					buttonList[i][j]
							.removeMouseMotionListener(myMotionListener);
					buttonList[i][j].removeKeyListener(myKeyListener);
				}

			}
		}
	}

	/**
	 * 超级模式
	 * 
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
					if (superPower==1) {
						delegate.hideSuperModel();
					}
					break;
				}
			}
		}

		/**
		 * 判断是否超级模式
		 */
		public void count() {
			long now = new Date().getTime();
			if (now - lastTime <= bonusMap.get("super") * 1000 || lastTime == 0) {
				queue.offer(now);
				combo++;
				if (combo>=3) {
					delegate.showCombos(combo);
				}
			} else {
				delegate.showCombos(0);
				queue.clear();
				queue.offer(now);
				if (combo > maxCombo) {
					maxCombo = combo;
					System.out.println("当前最大连击" + maxCombo);
				}
				combo = 1;
			}
			if (queue.size() == 4) {
				delegate.showSuperModel();
				superPower = superPower * 2;
				System.err.println(superPower);
				queue.clear();
				queue.offer(now);
				new Thread(new SuperModel()).start();
			}
			lastTime = now;
		}
	}

	public void setBonus(String key, double d) {
		bonusMap.put(key, d);
	}
	public double getBonus(String key){
		return bonusMap.get(key);
	}
	
	class Hint implements Runnable {
		int count = 0;

		public void clearCount() {
			count = 0;
		}
		
		@Override
		public void run() {
			while (!isStop) {
				if (!isSwap&&hintButton1==null&&hintButton2==null) {
					count++;
				}
				
				if (count >= bonusMap.get("remind")*10&&hintButton1==null&&hintButton2==null) {
					try {
						int[] hint = map.getHint();
						if (!isSwap) {
							System.out.println("HintSize"+hint.length);
						}
						 ACTION action = ACTION.NOACTION;
						 hintButton1 = buttonList[hint[1]][hint[0]];
						 switch (hint[2]) {
						 case 2:
						 action = ACTION.UP;
						 hintButton2 = buttonList[hint[1]][hint[0]-1];

						 break;
						 case -2:
	                     action = ACTION.DOWN;
						 hintButton2 = buttonList[hint[1]][hint[0]+1];

						 break;
						 case -1:
						 action= ACTION.LEFT;
						 hintButton2 = buttonList[hint[1]-1][hint[0]];

						 break;
						 case 1:
						 action = ACTION.RIGHT;
						 hintButton2 = buttonList[hint[1]+1][hint[0]];

						 break;
						 default:
						 break;
						 }
						 hintButton1.setIcon(new ImageIcon(hintButtons[Math.abs(hintButton1.type)-1]));
						 hintButton2.setIcon(new ImageIcon(hintButtons[Math.abs(hintButton2.type)-1]));
						 count = 0;
					} catch (Exception e) {
						hintButton1=null;
						hintButton2=null;
						count=0;
					}
				
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setDelegate(SuperModelAdapter singleGameBoard) {
		this.delegate = singleGameBoard;
	}
	public void unLock(){
		System.err.println("unlock");
		isSwap=false;
		myMotionListener.mutex=false;
	}
	public void lock(){
		System.out.println("lock");
		isSwap=true;
		myMotionListener.mutex=true;
	}
	public void setCannotMove(int cannotMove){
		cannotMoveColor =cannotMove ;
		setCannotMove();
	}
	protected void setCannotMove(){
		 for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			 for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				 /**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				 if (buttonList[i][j]!=null&&buttonList[i][j].type==cannotMoveColor) {
//					buttonList[i][j].setIcon(new ImageIcon(cannotMoveImg));
						buttonList[i][j].setIcon(new ImageIcon(cannotMoveImg));

				}
				
			 }
		 }
	}
	
	protected void recover(){
		 for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			 for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				 /**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				 if (buttonList[i][j]!=null&&buttonList[i][j].type==cannotMoveColor) {
					 int type = buttonList[i][j].type;
					 ImageIcon icon;
						if (type < 0)
							icon = new ImageIcon(effectButtonBgs[-type - 1]);
						else if (type == 6)
							icon = new ImageIcon(toolB);
						else
							icon = new ImageIcon(buttonBgs[type - 1]);
					 buttonList[i][j].setIcon(icon);

					 
				}
				
			 }
		 }
		 cannotMoveColor = 0;
	}
	protected void setScore(int score, boolean b) {
		// TODO Auto-generated method stub
		
	}
	
}
