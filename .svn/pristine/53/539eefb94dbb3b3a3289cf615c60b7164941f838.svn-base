package excalibur.game.presentation.ui;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import excalibur.game.logic.networkadapter.Client;
import excalibur.game.logic.networkadapter.GameRoom;
import excalibur.game.presentation.constant.Constant;
import excalibur.game.presentation.myuicomponent.RoomButton;
import excalibur.game.presentation.tools.FontLoader;

public class RoomsPanel extends JPanel {
	RoomButton rooms[];
	/**
	 * Create the panel.
	 */
	public RoomsPanel() {
		rooms = new RoomButton[Constant.NetWork.ROOMCOUNT];
		setBounds(0, 0, 600, 1200);
		setLayout(null);
//		setOpaque(false);
		setBackground(new Color(228,248,255));
		
		for (int i = 0 ; i < 8 ; i++){
			for (int j = 1 ; j <= 4 ; j++){
				RoomButton room = new RoomButton((GameRoom)Client.rooms[i*4+j]);
				room.setBounds(new Rectangle(20+(j-1)*(10+Constant.NetWork.ROOM_BUTTON_WIDTH), 20+i*(10+Constant.NetWork.ROOM_BUTTON_HEIGHT), Constant.NetWork.ROOM_BUTTON_WIDTH, Constant.NetWork.ROOM_BUTTON_HEIGHT));
				JLabel idLabel = new JLabel();
				idLabel.setForeground(Color.red);
				idLabel.setFont(FontLoader.loadFont("Duck Tape.ttf", 20));
				idLabel.setText(""+room.getRoomId());
				idLabel.setBounds(80+(j-1)*(10+Constant.NetWork.ROOM_BUTTON_WIDTH),120+i*(10+Constant.NetWork.ROOM_BUTTON_HEIGHT), 100, 50);
				add(idLabel);
				add(room);
				rooms[room.getRoomId()-1]=room;
			}
		}
	}
	
	public RoomButton[] getRooms() {
		return rooms;
	}

}
