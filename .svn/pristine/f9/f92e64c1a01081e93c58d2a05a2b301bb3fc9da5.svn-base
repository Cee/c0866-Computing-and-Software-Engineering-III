package excalibur.game.presentation.ui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import excalibur.game.logic.Tools;
import excalibur.game.logic.gamelogic.EndLessJudge;
import excalibur.game.logic.gamelogic.Map;

public class EndlessPlayBoard extends PlayBoard{
	EndLessJudge judge;
	public EndlessPlayBoard(JLabel scoreLabel,ArrayList<Tools> tools,EndLessJudge judge){
		super(scoreLabel, tools);
		this.judge = judge;
	}
	protected void initMap(){
		map = new Map();
	}
	
	@Override
	public void timeUp(boolean isFalse){
//		removeListener();
//		isStop = true;
	}
	
	public int getscore(){
		return score;
	}
	@Override
	protected void setScore(int score){
		scoreLabel.setText(score+"");
		judge.judge(score);
	}
	protected void setScoreByContinue(int score){
		scoreLabel.setText(score+"");
		this.score= score;
	}
	public void setInitialScore(int s){
		score=s;
	}
}
