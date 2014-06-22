package excalibur.game.presentation.ui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.ChessPoint;
import excalibur.game.logic.gamelogic.Map.ACTION;
import excalibur.game.logic.gamelogic.ScoreCounter;
import excalibur.game.logic.networkadapter.GameRoom;
import excalibur.game.logic.networkadapter.ThreadController;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.DialogCreator;
import excalibur.game.presentation.myuicomponent.PlayButton;
/**
 * 
 * @author luck-mac
 * 继承Playboard
 * 联机或对战使用
 */
public class PlayBoardForNet extends PlayBoard {
	GameRoom gameRoom;
	JLabel teamAScoreLabel;
	JLabel teamBScoreLabel;

	public PlayBoardForNet(JLabel scoreLabel, ArrayList<Tools> tools,
			GameRoom gameRoom) {
		super(scoreLabel, tools);
		this.gameRoom = gameRoom;
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				buttonList[i][j].removeMouseMotionListener(myMotionListener);
			}
		}
		isDropDown=true;
		new Thread(new ScoreSync()).start();
	}
	
	public void addListener() {
		 for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			 for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				 /**
				 * =.= 逻辑层的i j 和这的反了。。。
				 */
				 buttonList[i][j].addMouseListener(myButtonListener);
//				 buttonList[i][j].addMouseMotionListener(myMotionListener);
				 buttonList[i][j].addKeyListener(myKeyListener);
			 }
		 }
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
		hint = new Hint_For_Net();
		hint.clearCount();
		new Thread(hint).start();
	}
	public void setTeamAScoreLabel(JLabel teamAScoreLabel) {
		this.teamAScoreLabel = teamAScoreLabel;
	}
	public void setTeamBScoreLabel(JLabel teamBScoreLabel) {
		this.teamBScoreLabel = teamBScoreLabel;
	}

//
//	public void setClearList(ArrayList<Point> clearList) {
//		this.clearList = clearList;
//	}

	/**
	 * 交换
	 * 
	 * @param action
	 * 鼠标监听 调用swap方法
	 */
	@Override
	public void swap(ACTION action, PlayButton selectedButton) {
		if (!isSwap) {
//			lock();
			waiting=true;
			gameRoom.swap(action, searchButton(selectedButton));
		}
	}

	/**
	 * 此方法仅提供给TeamMaster使用
	 * @param action
	 * @param point
	 * @return
	 */
	public ArrayList<Point> trySwap(ACTION action,Point point){
		waiting=false;
		if (isSwap) {
			return new ArrayList<>();
		}
		if (buttonList[point.y][point.x].type==cannotMoveColor){
			return new ArrayList<>();
		}
		PlayButton anotherButton =  null;
		switch (action) {
		case UP:
			System.out.println("UP");
			anotherButton = buttonList[point.y][point.x-1];
			break;
		case DOWN:
			System.out.println("DOWN");

			anotherButton = buttonList[point.y][point.x+1];
			break;
		case RIGHT:
			System.out.println("RIGHT");

			anotherButton = buttonList[point.y+1][point.x];
			break;
		case LEFT:
			System.out.println("LEFT");

			anotherButton = buttonList[point.y-1][point.x];
			break;
		default:
			break;
		}
		if (anotherButton!=null&&anotherButton.type==cannotMoveColor) {
			return new ArrayList<>();
		}
		lock();
		final ArrayList<Point> clearList = map.clear(point.x, point.y, action);
		if (action == ACTION.TRIGGER_A || action == ACTION.TRIGGER_B){
			if (action==ACTION.TRIGGER_B){
				gameRoom.triggerB();
			}
			if (getBonus("time")!=0)
				timer.addTime(getBonus("time"));
			ThreadController.addThread(new Thread() {
				public void run() {
					clearButtons(clearList);
					while (true) {
						ArrayList<Point> clearList = map.clear(-1, -1, ACTION.NOACTION);
						if (clearList.isEmpty()) {
							break;
						} else {
//							gameRoom.swap(ACTION.NOACTION, new Point(
//										-1, -1));
							gameRoom.writeClear(clearList, new Point(-1,-1),ACTION.NOACTION);
							clearButtons(clearList);
						}
					}
					if (!ThreadController.nextThread()) {
						unLock();
					}
				}
				});
			
		} else if(action!=action.NOACTION) {
			Thread thread = new Thread(new SwapThreadForNet(action, buttonList[point.y][point.x],clearList));
			ThreadController.addThread(thread);
		}
		
		return clearList;
	}
	public void swap(final ArrayList<Point> clearList2, ACTION action, Point point) {
		waiting=false;
		if (action == ACTION.NOACTION||action == ACTION.TRIGGER_A || action == ACTION.TRIGGER_B) {
			if (action == ACTION.TRIGGER_A || action == ACTION.TRIGGER_B) {
				if (getBonus("time")!=0)
					timer.addTime(getBonus("time"));
			}
			ThreadController.addThread(new Thread() {
				public void run() {
					ArrayList<Point> clearListForThread = new ArrayList<>();
					for (Point point:clearList2){
						clearListForThread.add(point);
					}
					clearButtons(clearListForThread);
					
					if (!ThreadController.nextThread()) {
						unLock();
					}
				}
			});
		} else {
			Thread thread = new Thread(new SwapThreadForNet(action,
						buttonList[point.y][point.x],clearList2));
			ThreadController.addThread(thread);
		}
	}

	public void setMap(int[][] map) {
		this.map.setMap(map);
		for (int i = 0; i < Constant.Config.MAP_SIZE; i++) {
			for (int j = 0; j < Constant.Config.MAP_SIZE; j++) {
				int type = map[j][i];
				ImageIcon icon = new ImageIcon(buttonBgs[type - 1]);
				buttonList[i][j].type = type;
				buttonList[i][j].setIcon(icon);
			}
		}

	}

	public PlayButton getButton(Point point) {
		return buttonList[point.y][point.x];
	}
	
	

	/**
	 * 交换的动画线程
	 * 
	 * @author luck
	 * 
	 */
	class SwapThreadForNet implements Runnable {
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
		public SwapThreadForNet(ACTION action, PlayButton selectedButton,ArrayList<Point> clearList) {
			this.action = action;
			this.selectedButton = selectedButton;
			this.clearList = clearList;
			Point point = searchButton(selectedButton);
			x = point.y;
			y = point.x;
			changeButton();
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
			lock();
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
					unLock();
				} else {
					clearButtons(clearList);
					while (gameRoom.isTeamMaster()) {
						clearList = map.clear(-1, -1, ACTION.NOACTION);
						if (clearList.isEmpty()) {
							unLock();
							break;
						} else {
//							gameRoom.swap(ACTION.NOACTION, new Point(-1, -1));
							gameRoom.writeClear(clearList, new Point(-1, -1), ACTION.NOACTION);
							clearButtons(clearList);
						}

					}
					myMotionListener.mutex=false;
				}
				anotherButton = null;
			}
			setSelectedButtonForMouse(null);
//			if (!gameRoom.isTeamMaster()) {
				ThreadController.nextThread();
//			}
		}

	}

	/**
	 * 清除消除的按钮
	 * 
	 * @param points
	 */
	@Override
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
				if (playButton.type!=6)
					playButton.setIcon(iconList[Math.abs(playButton.type) - 1][i]);
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
		if (gameRoom.isTeamMaster()) {
			int[][] fillMap = map.getFillMap(!isDropDown);
			dropDown(points, fillMap);
			gameRoom.sendDropDown(fillMap,points);
		}
	}

	@Override
	public void dropDown(ArrayList<Point> points, int[][] newMap) {
		super.dropDown(points, newMap);
	}



	protected void handleTrigger(Point point, ACTION action) {
		if (!isSwap) {
//			lock();
			waiting=true;
			gameRoom.swap(action, point);
		}
	}

	public void timeUp(boolean isFalse) {
		removeListener();
		isStop = true;
		while (isSwap){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("等待");
		}
		if (!isFalse) {
//			JOptionPane.showMessageDialog(this, "时间到！您的分数是："
//					+ (int) (score * bonusMap.get("score")));
//			redrock
			Media.stopBGM(Media.MAIN);
			Media.playSound(Media.ENDGAME);
			DialogCreator.oneButtonDialog("游戏结束", "时间到！您的分数是："
					+ (int) (getScore(gameRoom.myTeam) * bonusMap.get("score")));
		}
		gameRoom.end(isFalse);
	}

	public void triggerB() {
		cannotMoveColor = (int)(Math.random()*5)+1;
		setCannotMove();
		new Thread(){
			public void run(){
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				recover();
			}
		
		}.start();

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
				}
				combo = 1;
			}
			if (queue.size() == 4) {
				delegate.showSuperModel();
				superPower = superPower * 2;
				System.err.println(superPower);
				queue.clear();
				queue.offer(now);
				gameRoom.superModel();
				new Thread(new SuperModel()).start();
			}
			lastTime = now;
		}
	}
	
	
	public void setScore(int score){
		this.score =score ;
		scoreLabel.setText(score+"");
		if (gameRoom.myTeam.equals("False")) {
			teamAScoreLabel.setText(score+"");
		} else {
			teamBScoreLabel.setText(score+"");

		}
		
//		if (gameRoom.isTeamMaster()) {
//			gameRoom.sendScore(score);
//		}
	}
	public void triggerSuper() {
		if (getBonus("super")>2.0) {
		}
		new Thread(){
			public void run(){
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setBonus("super", getBonus("super")+2.0);

			}
		}.start();
	}

	public void updateScore(int score) {
		if (gameRoom.myTeam.equals("False")) {
			teamBScoreLabel.setText(score+"");
		} else {
			teamAScoreLabel.setText(score+"");
		}
	}
	public int getScore(String teamName){
		if (teamName.equals("False")) {
			if (teamAScoreLabel.getText().equals("")) {
				return 0;
			}
			
			return Integer.parseInt(teamAScoreLabel.getText());
		} else {
			if (teamBScoreLabel.getText().equals("")) {
				return 0;
			}
			return Integer.parseInt(teamBScoreLabel.getText());

		}
	}
	
	
	class Hint_For_Net extends Hint {
		
		@Override
		public void run() {
			while (!isStop&&gameRoom.isTeamMaster()) {
				if (!isSwap&&hintButton1==null&&hintButton2==null) {
					count++;
				}

				if (count >= bonusMap.get("remind")*10&&hintButton1==null&&hintButton2==null) {
					int[] hint = map.getHint();
//					gameRoom.sendHint(hint);
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
                    try {
                        hintButton1.setIcon(new ImageIcon(hintButtons[Math.abs(hintButton1.type)-1]));
                        hintButton2.setIcon(new ImageIcon(hintButtons[Math.abs(hintButton2.type)-1]));

                    } catch (Exception e){
                        hintButton1 = null;
                        hintButton2 = null;
                    }
                   count = 0;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected void handleHint(){
		
	}
	public void hint(String[] args) {
		if (isSwap) {
			return;
		}
		try {
			 int hint[] = new int[3];
			 for(int i= 0; i < 3 ; i++){
				 hint[i]=Integer.parseInt(args[i]);
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
		} catch (Exception e) {
			hintButton1=null;
			hintButton2=null;
		}
		
	}
	class ScoreSync implements Runnable{
		@Override
		public void run() {
			while (!isStop&&gameRoom.isTeamMaster()&&gameRoom.isLink()) {
				try
				{
					Thread.sleep(5000);
				}
				catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
				if (!isSwap) {
					gameRoom.sendScore(score);
				}	
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	
}
