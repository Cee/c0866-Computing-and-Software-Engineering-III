package excalibur.game.presentation.media;
import java.io.File;

import saint.media.MidiPlayer;
import saint.media.SimplePlayer;
import excalibur.game.logic.syslogic.PrefferenceLogic;

public class Media{
	public static final String ENDGAME="EndGame"+"_Result";
	public static final String FAIL="Fail"+"_Result";
	public static final String GAME="Game"+"_Result";
	public static final String LEVELUP="LevelUp"+"_Result";
	public static final String LOBBY="Lobby"+"_Result";
	public static final String MAIN="Main"+"_Result";
	public static final String WAITING="WaitingRoom"+"_Result";
	
	public static final String ENTER="Enter";
	public static final String MOVE="move";
	public static final String OPTION="option";
	public static final String RETURN="return";
	public static final String SAVE="save";
	public static final String ENABLE="enable";
	
	private static SimplePlayer bgmPlayer = null;
	private static SimplePlayer soundPlayer = null;
	private static MidiPlayer midiPlayer = null;
	
	public static void playBGM(String name) {
		if(!PrefferenceLogic.getIsBackMusicOpen()){
			return;
		}
		if(bgmPlayer!=null){
			bgmPlayer.stop();			
		}
		bgmPlayer=new SimplePlayer();

		try{
			bgmPlayer.open(new File("sound/"+name+".mp3"));
			bgmPlayer.setLoop(true);
			bgmPlayer.setLoopCount(1000);
		}catch (Exception e) {
			System.err.println("没有找到文件");
			return;
		}

		try{
			bgmPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void playSound(String name){
		if(!PrefferenceLogic.getIsSoundEffectOpen()){
			return;
		}
		if(soundPlayer!=null){
			soundPlayer.stop();
		}
		soundPlayer=new SimplePlayer();
		
		try{
			soundPlayer.open(new File("sound/"+name+".mp3"));
			soundPlayer.setLoop(false);
		}catch (Exception e) {
			System.err.println("没有找到文件");
			return;
		}

		try{
			soundPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void playMidi(String name){
		if(!PrefferenceLogic.getIsSoundEffectOpen()){
			return;
		}
		if(midiPlayer!=null){
			midiPlayer.stop();
		}
		midiPlayer=new MidiPlayer();
		
		try{
			midiPlayer.open(new File("sound/"+name+".mid"));
			midiPlayer.setLoop(false);
		}catch (Exception e) {
			System.err.println("没有找到文件");
			return;
		}

		try{
			midiPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void stopBGM(String name){
		if(!PrefferenceLogic.getIsSoundEffectOpen()||bgmPlayer==null){
			return;
		}
		bgmPlayer.setVolume(0);
	}
	
	public static void stopSound(String name){
		if(!PrefferenceLogic.getIsSoundEffectOpen()||soundPlayer==null){
			return;
		}
		soundPlayer.setVolume(0);
	}
	
	public static void stopMidi(String name){
		if(!PrefferenceLogic.getIsSoundEffectOpen()||midiPlayer==null){
			return;
		}
		midiPlayer.setVolume(0);
	}
	
}