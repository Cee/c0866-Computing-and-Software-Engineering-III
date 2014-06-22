package excalibur.game.data.gamedata;
import java.io.Serializable;


public class setPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7140592702801336812L;
	private String picturename;
	private boolean isBackgroundMusicOpen;
	private boolean isSoundEffectOpen;
	private boolean isVertical;
	private String usrname;
	public setPO(){
		picturename="default.png";
		isBackgroundMusicOpen=true;
		isSoundEffectOpen=true;
		usrname="user";
	}
	
	public setPO(String picturename,boolean isback,boolean issound,boolean isvert,String username){
		this.picturename=picturename;
		this.isBackgroundMusicOpen=isback;
		this.isSoundEffectOpen=issound;
		this.usrname=username;
		this.isVertical=isvert;
	}
	
	public String getPictureName(){
		return picturename;
	}
	
	public String getUsrName(){
		return usrname;
	}
	
	public boolean getIsBackgroundMusicOpen(){
		return isBackgroundMusicOpen;
	}
	
	public boolean getIsSoundEffectOpen(){
		return isSoundEffectOpen;
	}
	
	public boolean getIsVertical(){
		return isVertical;
	}
	
	public String toString(){
		return usrname+" "+picturename+" "+isBackgroundMusicOpen+" "+isSoundEffectOpen;
	}
}
