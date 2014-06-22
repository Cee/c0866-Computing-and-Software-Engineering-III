package excalibur.game.presentation.constant;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author luck
 *         各种参数
 */
public class Constant {
    public static class NetWork {
        public static final int PORT = 23333;
        public static final int ROOMCOUNT = 32;
        public static String SERVERIP = getServerIp();
        public static final int MAX_PLAYER = 4;
        public static final int ROOM_BUTTON_WIDTH = 131;
        public static final int ROOM_BUTTON_HEIGHT = 130;

        static String getServerIp() {
            Pattern p = Pattern.compile("server_ip:(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
            File f = new File("settings.ini");
            try {
                BufferedReader br;
                br = new BufferedReader(new BufferedReader(new InputStreamReader(new FileInputStream(f))));
                Matcher m = null;
                try {
                    String s = br.readLine();
                    m = p.matcher(s);
                    m.find();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return m.group(1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("设置文件不存在");
                return null;
            }

        }
    }


    public static class Config {

        public static final String title = "天天爱消除";
        public static final int WIDTH = 800;
        public static final int HEIGHT = 600;

        public static final int BT_WIDTH = 45;
        public static final int BT_HEIGHT = 45;
        public static final int MAP_SIZE = 9;
        public static int MATCH_TYPE = 5;
        public void setMATCH_TYPE(int mATCH_TYPE) {
            MATCH_TYPE = mATCH_TYPE;
        }
    }

    public static class UILocation {
        //主界面
        public static final int paihangX = 189;
        public static final int paihangY = 290;


        public static final int startX = 269;
        public static final int startY = 171;

        public static final int exitX = 530;
        public static final int exitY = 322;

        public static final int settingsX = 438;
        public static final int settingsY = 220;

        public static final int returnX = 690;
        public static final int returnY = 500;

        public static final int levelX = 30;
        public static final int levelY = 450;


        //道具选择界面
        public static final int itemStartX = 260;
        public static final int itemStartY = 135;

        public static final int item1X = 210;
        public static final int item1Y = 145;

        public static final int item2X = 157;
        public static final int item2Y = 227;

        public static final int item3X = 210;
        public static final int item3Y = 337;

        public static final int item4X = 525;
        public static final int item4Y = 140;

        public static final int item5X = 550;
        public static final int item5Y = 225;

        public static final int item6X = 525;
        public static final int item6Y = 340;

    }

    public static class PictureSrc {
        public static final String IMGSRC = "img/";
        public static final String PNG = ".png";
        public static final String ENTERED = "_entered";
        public static final String MENU = IMGSRC + "menu/";
        public static final String GAME = IMGSRC + "game/";
        public static final String TEST = "_num";
        public static final String ITEM = IMGSRC + "item/";
        public static final String RANK = IMGSRC + "ranking/";
        public static final String PREFER = IMGSRC + "preference/";
        public static final String EFFECT = IMGSRC + "effect/";
        public static final String GIF = ".gif";
        public static final String GAMEROOM = IMGSRC + "gameroom/";
        public static final String WAITINGROOM = IMGSRC + "waitingroom/";
        public static final String ENDLESS = IMGSRC + "endless/";
        public static final String HINT = IMGSRC+"hint/";
        public static final String DIALOGUE = IMGSRC+"dialogue/";
        public static final String DECORATION = IMGSRC+"decoration/";
        //主界面
        public static final String mainBg = MENU + "mainBg" + PNG;
        public static final String paiHangBt = MENU + "paihangBt" + PNG;
        public static final String startBt = MENU + "startBt" + PNG;
        public static final String startBt_Entered = MENU + "startBt" + ENTERED + PNG;
        public static final String startBt_Ani = MENU + "startGame_ani/startGame_00";


        public static final String exitBt = MENU + "exitBt" + PNG;
        public static final String exit_Entered = MENU + "exitBt" + ENTERED + PNG;
        public static final String exit_Ani = MENU + "exit_ani/exit_00";

        public static final String settingsBt = MENU + "settingsBt" + PNG;
        public static final String settingsBt_Entered = MENU + "settingsBt" + ENTERED + PNG;
        public static final String settingsBt_Ani = MENU + "settings_ani/settings_00";

        public static final String paiHangBt_Entered = MENU + "paihangBt" + ENTERED + PNG;
        public static final String paiHangBt_Ani = MENU + "paihang_ani/paihang_00";

        public static final String levelBg = MENU + "level.gif";

        public static final String tool_B = EFFECT+"B.gif";

        //饰品界面
        public static final String cursor1 = DECORATION+"cursor1"+PNG;
        public static final String cursor2 = DECORATION+"cursor2"+PNG;
        public static final String cursor3 = DECORATION+"cursor3"+PNG;
        public static final String cursor4 = DECORATION+"cursor4"+PNG;

        //游戏房间
        public static final String gameroom_background = GAMEROOM + "background" + PNG;
        public static final String gameroom_chooseteam = GAMEROOM + "chooseteam" + PNG;
        public static final String gameroom_personalinfo_A = GAMEROOM + "personalinfo_red" + PNG;
        public static final String gameroom_personalinfo_B = GAMEROOM + "personalinfo_blue" + PNG;
        public static final String gameroom_prepared = GAMEROOM + "prepared" + PNG;
        public static final String gameroom_startgame = GAMEROOM + "startgame" + PNG;
        public static final String gameroom_prepare = GAMEROOM + "prepare" + PNG;
        public static final String gameroom_cancelprepare = GAMEROOM + "cancelprepare" + PNG;
        public static final String gameroom_teamA = GAMEROOM + "teamA" + PNG;
        public static final String gameroom_teamB = GAMEROOM + "teamB" + PNG;
        public static final String gameroom_back = GAMEROOM + "back" + PNG;
        public static final String playerPanelbackground_teamA = GAMEROOM + "teamred_bkg" + PNG;
        public static final String playerPanelbackground_teamB = GAMEROOM + "teamblue_bkg" + PNG;
        public static final String playerPanel_sculptureA = GAMEROOM + "redSculpture" + PNG;
        public static final String playerPanel_sculptureB = GAMEROOM + "blueSculpture" + PNG;
        public static final String playerPanel_roomMaster = GAMEROOM + "roommaster" + PNG;
        public static final String gameroom_selected = GAMEROOM + "choose" + PNG;

        //等候室
        public static final String canAddRoom = WAITINGROOM + "waiting_player";
        public static final String gamingRoom = WAITINGROOM+"gaming_player";
        public static final String gamingMoved = WAITINGROOM+"gaming_moved"+PNG;
        public static final String waitingMoved = WAITINGROOM+"waiting_moved"+PNG;
        public static final String listBackground = WAITINGROOM+"list_bg"+PNG;
        public static final String createRoom = WAITINGROOM+"create_room"+PNG;
        public static final String quitRoom = WAITINGROOM+"quitRoom"+PNG;

        //色块消失动画
        public static final String blue_clear = GAME + "blue_clear/_00";
        public static final String brown_clear = GAME + "brown_clear/_00";
        public static final String red_clear = GAME + "red_clear/_00";
        public static final String purple_clear = GAME + "purple_clear/_00";
        public static final String green_clear = GAME + "green_clear/_00";
        public static final String rock = GAME + "rock" + PNG;

        //游戏界面
        public static final String gameBg = GAME + "gameBg" + PNG;
        public static final String superBg = EFFECT + "superMode" + GIF;
        public static final String backBt = GAME + "backBt" + PNG;
        public static final String timeBar = GAME + "timeBar" + PNG;
        public static final String star = EFFECT + "star" + PNG;
        public static final String teamAFlag = GAMEROOM + "redFlag" + PNG;
        public static final String teamBFlag = GAMEROOM + "blueFlag" + PNG;

        //道具选择界面
        public static final String gameStart = ITEM + "StartGame" + PNG;
        public static final String item1 = ITEM + "item1" + PNG;
        public static final String item2 = ITEM + "item2" + PNG;
        public static final String item3 = ITEM + "item3" + PNG;
        public static final String item4 = ITEM + "item4" + PNG;
        public static final String item5 = ITEM + "item5" + PNG;
        public static final String item6 = ITEM + "item6" + PNG;
        public static final String item1_C = ITEM + "item1Chosen" + PNG;
        public static final String item2_C = ITEM + "item2Chosen" + PNG;
        public static final String item3_C = ITEM + "item3Chosen" + PNG;
        public static final String item4_C = ITEM + "item4Chosen" + PNG;
        public static final String item5_C = ITEM + "item5Chosen" + PNG;
        public static final String item6_C = ITEM + "item6Chosen" + PNG;
        public static final String item1_H = ITEM + "item1Hint" + PNG;
        public static final String item2_H = ITEM + "item2Hint" + PNG;
        public static final String item3_H = ITEM + "item3Hint" + PNG;
        public static final String item4_H = ITEM + "item4Hint" + PNG;
        public static final String item5_H = ITEM + "item5Hint" + PNG;
        public static final String item6_H = ITEM + "item6Hint" + PNG;

        //排行榜界面
        public static final String rankBg = RANK + "rankBg" + PNG;
        public static final String dailyGameCountIcon = RANK + "dailyGameCount" + PNG;
        public static final String scorePerGame = RANK + "scorePerGame" + PNG;
        public static final String averageScorePerDay = RANK + "averageScorePerday" + PNG;
        public static final String totalGameCount = RANK + "totalGameCount" + PNG;
        public static final String maxCombo = RANK + "maxCombo" + PNG;
        public static final String maxScore = RANK + "maxScore" + PNG;

        //设置界面
        public static final String preferenceBg = PREFER + "preferenceBg" + PNG;
        public static final String bmBt = PREFER + "bgm" + PNG;
        public static final String userNameIcon = PREFER + "userName" + PNG;
        public static final String userSculptureIcon = PREFER + "userSculpture" + PNG;
        public static final String redSculptureIcon = PREFER + "redSculpture" + PNG;
        public static final String blueSculptureIcon = PREFER + "blueSculpture" + PNG;
        public static final String seIcon = PREFER + "se" + PNG;
        public static final String dropIcon = PREFER + "drop" + PNG;
        public static final String dropTopIcon = PREFER + "dropTop" + PNG;
        public static final String dropSideIcon = PREFER + "dropSide" + PNG;
        public static final String offIcon = PREFER + "off" + PNG;
        public static final String onIcon = PREFER + "on" + PNG;
        public static final String returnIcon = PREFER + "return" + PNG;
        public static final String saveIcon = PREFER + "save" + PNG;
        public static final String chooseIcon = PREFER + "choose" + PNG;

        //弹出框
        public static final String dialogueBg = DIALOGUE + "dialogueBg" + PNG;
        public static final String okIcon = DIALOGUE + "okIcon" + PNG;
        public static final String levelUpIcon = DIALOGUE + "levelUpIcon" + PNG;

        //无尽模式
        public static final String endless_background = ENDLESS + "background" + PNG;
        public static final String endless_continue = ENDLESS + "continue" + PNG;
        public static final String endless_mydecoration = ENDLESS + "mydecoration" + PNG;
        public static final String endless_restart = ENDLESS + "restart" + PNG;

        public static String[] loadGameIcon(String folder, String suffix) {
            String[] iconStrings = new String[Config.MATCH_TYPE];
            //色块
            String blue = GAME+folder + "blue" + suffix;
            String brown = GAME+folder + "brown" + suffix;
            String red = GAME+folder + "red" + suffix;
            String purple = GAME+folder + "purple" + suffix;
            String green = GAME+folder + "green" + suffix;
//            String blue =  "img/decoration/jx3/button1" + PNG;
//            String brown = "img/decoration/jx3/button2" + PNG;
//            String red = "img/decoration/jx3/button3" + PNG;
//            String purple = "img/decoration/jx3/button4" + PNG;
//            String green = "img/decoration/jx3/button5" + PNG;
            iconStrings[0] = blue;
            iconStrings[1] = brown;
            iconStrings[2] = red;
            iconStrings[3] = purple;
            iconStrings[4] = green;
            return iconStrings;
        }
        public static String loadGameBg(String folder){
        	return GAME+folder+"/gameBg"+PNG;
        }
        public static String[] getSculpture(){
            String[] iconStrings = new String[6];
        	for (int i = 1 ; i <= 6; i++){
        		iconStrings[i-1]=IMGSRC+"myotee/"+i+"_small"+PNG;
        	}
        	return iconStrings;
        }
        public static String getItemB(String folder){
        	return GAME+folder+"/effect/B.gif";
        }
        public static String[] getBigScuplture(){
            String[] iconStrings = new String[6];
        	for (int i = 1 ; i <= 6; i++){
        		iconStrings[i-1]=IMGSRC+"myotee/"+i+PNG;
        	}
        	return iconStrings;
        }
    }


    public static class RankingPanel {

        public static final int maxComboColor = 0xe3bc7e;
        public static final int gameCountColor = 0xc4b291;
        public static final int maxScoreColor = 0x73daa3;


        public static final int animateTime = 200;

        public static final int gameCountPosX = 8;
        public static final int gameCountPosY = 17;
        public static final int gameCountRound = 37;
        public static final int gameCountFontSize = 22;
        public static final int gameCountFontSizeBack = 29;

        public static final int maxScoreX = 58;
        public static final int maxScoreY = 468;
        public static final int maxScoreRound = 42;
        public static final int maxScoreFontSize = 26;
        public static final int maxScoreFontSizeBack = 24;

        public static final int maxComboX = 46;
        public static final int maxComboY = 272;
        public static final int maxComboRound = 32;
        public static final int maxComboFontSize = 20;
        public static final int maxComboFontSizeBack = 26;

        public static final int dailyGameCountX = 10;
        public static final int dailyGameCountY = 128;

        public static final int scorePerGameX = 104;
        public static final int scorePerGameY = 201;

        public static final int averageScoreX = 2;
        public static final int averageScoreY = 359;


        public static final int lineX = 220;
        public static final int lineY = 50;
        public static final int lineHeight = 450;
        public static final int lineWidth = 550;
        public static final Rectangle xyRect = new Rectangle(lineX, lineY, lineWidth, lineHeight);

    }

    public static class FontSrc {
        public static String DUCK_TAPE = "Duck Tape.ttf";
        public static String YOUYUAN = "youyuan.ttf";
        public static String YUANTI = "yuanti.ttf";
        public static String ZHUNYUAN = "zhunyuan.ttf";
    }
}
