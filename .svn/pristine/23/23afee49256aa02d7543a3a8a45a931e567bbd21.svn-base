package excalibur.game.presentation.myuicomponent;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import excalibur.game.logic.networkadapter.Client;
import excalibur.game.logic.networkadapter.GameRoom;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.media.Media;
import excalibur.game.presentation.tools.FontLoader;

public class RoomButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentPlayer=0;
	private boolean isPlaying = false;
	GameRoom gameRoom;
	boolean isSelect = false;
	
	public RoomButton(final GameRoom gameRoom){
		this.gameRoom = gameRoom;
		setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
		setSize(Constant.NetWork.ROOM_BUTTON_WIDTH, Constant.NetWork.ROOM_BUTTON_HEIGHT);
		addMouseListener(new RoomMouseListener());
		setIcon(new ImageIcon(Constant.PictureSrc.canAddRoom+currentPlayer+".png"));
		System.out.println(Constant.PictureSrc.canAddRoom+currentPlayer+".png");
		System.out.println(getIcon().getIconHeight());
//		if (currentPlayer==0) {
//			setVisible(false);
//		} else {
//			setVisible(true);
//		}
		setForeground(Color.white);
		setFont(FontLoader.loadFont("zhunyuan.ttf"	, 20));

	}
	public void setInfo(String[] infos){
		currentPlayer = Integer.parseInt(infos[0]);
		isPlaying = infos[1].toUpperCase().equals("TRUE");
		if (isPlaying) {
			setIcon(new ImageIcon(Constant.PictureSrc.gamingRoom+currentPlayer+".png"));
		} else {
			setIcon(new ImageIcon(Constant.PictureSrc.canAddRoom+currentPlayer+".png"));
		}
//		if (currentPlayer==0) {
//			setVisible(false);
//		} else {
//			setVisible(true);
//		}
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public String toString(){
		String state = isPlaying?"游戏进行中":"等待加入";
		return "<html>房间号："+gameRoom.getRoomId()+"<br></br>房间人数"+currentPlayer+"/"+Constant.NetWork.MAX_PLAYER+"<br></br>"+state+"</html>";
	}
	
	class RoomMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()==2){
				if (currentPlayer<Constant.NetWork.MAX_PLAYER&&!isPlaying) {
					Media.playBGM(Media.WAITING);
					Client.waitingRoom.enterRoom(gameRoom.getRoomId());
				}
			} else {
				isSelect=true;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (!gameRoom.isGame){
				setIcon(new ImageIcon(Constant.PictureSrc.waitingMoved));
			} else {
				setIcon(new ImageIcon(Constant.PictureSrc.gamingMoved));
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (gameRoom.isGame) {
				setIcon(new ImageIcon(Constant.PictureSrc.gamingRoom+currentPlayer+".png"));
			} else {
				setIcon(new ImageIcon(Constant.PictureSrc.canAddRoom+currentPlayer+".png"));
			}
//			setBounds(getBounds());
		}
		
		
		
	}
	
	public int getRoomId(){
		return gameRoom.getRoomId();
	}
	
}

