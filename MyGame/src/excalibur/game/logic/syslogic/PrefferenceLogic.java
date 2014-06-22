package excalibur.game.logic.syslogic;

import excalibur.game.data.gamedata.setPO;
import excalibur.game.data.gamedata.setpreference;
import excalibur.game.presentation.media.Media;

public class PrefferenceLogic {
	private static setVO setVO=null;
	private static boolean isnew=false;
	
	/**
	 * 
	 * @param sv
	 * @return 0代表成功，1代表io错误
	 */
	public static int setPreference(setVO sv){
		setPO sp=new setPO(sv.getPictureName(),sv.getIsBackgroundMusicOpen(),sv.getIsSoundEffectOpen(),sv.getIsVertical(),sv.getUsrName());
		isnew=false;
//		Media.IsSoundOpen(sv.getIsSoundEffectOpen());
		return setpreference.setpreferrence(sp);
	}
	
	public static setVO getSettings(){
		if(setVO!=null&&isnew){
			return new setVO(setVO.getPictureName(),setVO.getIsBackgroundMusicOpen()
					,setVO.getIsSoundEffectOpen(),setVO.getIsVertical(),setVO.getUsrName());
		}
		setPO sp=setpreference.getSettings();
		if(sp==null){
			return null;
		}
		setVO sv=new setVO(sp.getPictureName(),sp.getIsBackgroundMusicOpen()
				,sp.getIsSoundEffectOpen(),sp.getIsVertical(),sp.getUsrName());
		setVO=new setVO(sp.getPictureName(),sp.getIsBackgroundMusicOpen()
				,sp.getIsSoundEffectOpen(),sp.getIsVertical(),sp.getUsrName());
		isnew=true;
		return sv;
	}
	
	public static String getPictureName(){
		if(setVO!=null&&isnew){
			return setVO.getPictureName();
		}
		return getSettings().getPictureName();
	}
	
	
	public static String getUserName(){
		if(setVO!=null&&isnew){
			return setVO.getUsrName();
		}
		return getSettings().getUsrName();
	}
	
	public static boolean getIsBackMusicOpen(){
		if(setVO!=null&&isnew){
			return setVO.getIsBackgroundMusicOpen();
		}
		return getSettings().getIsBackgroundMusicOpen();
	}
	
	public static boolean getIsVertical(){
		if(setVO!=null&&isnew){
			return setVO.getIsVertical();
		}
		return getSettings().getIsVertical();
	}
	
	public static boolean getIsSoundEffectOpen(){
		if(setVO!=null&&isnew){
			return setVO.getIsSoundEffectOpen();
		}
		return getSettings().getIsSoundEffectOpen();
	}
	
}
