package excalibur.game.presentation.ui;

import java.util.ArrayList;

import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.EndLessJudge;
import excalibur.game.logic.syslogic.DataUtils;
import excalibur.game.logic.syslogic.DataUtils.Shipin;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.myuicomponent.DialogCreator;
import excalibur.game.presentation.tools.StageController;
 class EndlessGameBoard extends SingleGameBoard implements EndLessJudge{
//	private static final int[] SCORES_LEVEL={1000,10000,30000,150000,200000,250000,320000,400000,480000,520000};
	EndlessPlayBoard playBoard;
	TimeThread timeThread;
	boolean isTimeUp;
	int level=0;
	int ini_time;
	int[] left_time={0,0,0,0,0};	
	boolean isRandom;
	Shipin[] shipins = DataUtils.getInstance().getSelectShipin();
	public EndlessGameBoard(){
		super(new ArrayList<Tools>());
		initialize_param();
		initPlayBoard();
		isTimeUp = false;
	}
	public EndlessGameBoard(int level){
		super(new ArrayList<Tools>());
		initialize_param();
		initPlayBoard();
		isTimeUp = false;
		this.level = level-1;
		playBoard.setScoreByContinue(level*level*10000);
		startNext(DataUtils.getInstance().getLastTime()*200/60);
	}
	
	public void initPlayBoard(){
		playBoard =  new EndlessPlayBoard(scoreLabel,toolList,this);
		playBoard.setLocation(150,75);
		playBoard.setDelegate(this);
		add(playBoard,0);
		
		if (level==0) {
			startTiming();
		}
		
	}

		
	
	private ArrayList<Tools> getTools(){
		return new ArrayList<Tools>();
	}
	
	private void initialize_param(){
		ini_time=200;
//		ask if load the highest record or restart from level 0
//		initialize the level and time
	}
	
	
	private void startNext(int remindtime){		
		level++;
		isRandom=false;
		if (level>=3) {
			switch (level%3) {
			case 0:
				playBoard.isDropDown=false;
				break;
			case 1:
				playBoard.isDropDown=true;
				break;
			case 2:
				isRandom=true;
			default:
				break;
			}
		}
		if (level==4||level==6||level==8) {
			playBoard.setCannotMove((int)(Math.random()*5)+1);
		} else {
			playBoard.recover();
		}
		if (timeThread!=null) {
			timeThread.setPass(true);
		}
		timeThread = new TimeThread(remindtime+ini_time);
		new Thread(timeThread).start();
	}
	
	private void savedata(){
		if (isTimeUp) {
			if (DataUtils.getInstance().getBestRecord()<level) {
				DataUtils.getInstance().setBestRecord(level);
				DataUtils.getInstance().setLastTime(left_time[0]*30/1000);
				DataUtils.getInstance().setLastRecord(level/5*5);
			}
		} else {
			DataUtils.getInstance().setLastRecord(level);
			DataUtils.getInstance().setRemainTime(timeThread.getLeft_time()*300/1000);
			DataUtils.getInstance().setLastTime(0);
		}
		
//		save the data (only when the player breaked the record)
	}
	@Override
	public void startTiming()
	{
		System.out.println("Start");
		timeThread = new TimeThread(ini_time);
		new Thread(timeThread).start();
	}
	
	
	
	class TimeThread implements Runnable{
		private int limit_time;
		private int left_time;
		private boolean isPass=false;
		
		public TimeThread(int limit){
			for (Shipin shipin : shipins) {
				if (shipin!=null&&shipin.type== Shipin.Effect.timeUp) {
					limit = (int) (limit*(1+shipin.current_effect/100.0));
				}
			}
			
			this.limit_time=limit;
			
			System.out.println("剩余时间"+limit);
		}
		
		public int getLeft_time() {
			return left_time;
		}
		public void setPass(boolean isPass) {
			this.isPass = isPass;
		}
		@Override
		public void run() {
			double shift = 0.0;
			timeBarLabel.setLocation(0, timeBarLabel.getY());			
			for (left_time=limit_time;left_time>0;left_time--){
				if (isPass) {
					System.err.println("Pass");
					break;
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				shift = Constant.Config.WIDTH/(limit_time)*(limit_time-left_time);
				timeBarLabel.setLocation(-(int)shift, timeBarLabel.getY());			
				if (isStop){
					savedata();
					System.out.println("thread end");
					return;	
				}
			}
			if (!isPass) {
				isTimeUp = true;
				playBoard.timeUp(false);
				savedata();
				Media.stopBGM(Media.GAME);
				Media.playSound(Media.FAIL);
				DialogCreator.oneButtonDialog("抱歉", "时间到！");	
				StageController.returnToMain(EndlessGameBoard.this);
			}
		
		}
	}



	@Override
	public boolean judge(int score) {
		for (Shipin shipin : shipins) {
			if (shipin!=null&&shipin.type== Shipin.Effect.socreUp) {
				score = (int) (score*(1+shipin.current_effect/100.0));
			}
		}
		if (isRandom) {
			playBoard.isDropDown=!playBoard.isDropDown;
		}
	
		if(score>=(level+1)*(level+1)*10000&&!isStop){
//			change to next
			left_time[level%5]=timeThread.getLeft_time();
			timeThread.setPass(true);
			Object[] options ={ "继续", "退出" };	
			Media.playBGM(Media.GAME);
			int m = DialogCreator.twoButtonDialog("恭喜您", "闯关成功，您可以进行下一关，或者选择退出，闯关记录将为您保存", "继续", "返回", 0);
//			int m = JOptionPane.showOptionDialog(null, "第"+(level+1)+"关，恭喜你成功过关", "",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(m==1){
				Media.playBGM(Media.GAME);
				startNext(left_time[level%5]);
			}else{
				isStop=true;
				savedata();
				backtomainMethod();
			}
			return true;
		}
		return false;
	}
}
