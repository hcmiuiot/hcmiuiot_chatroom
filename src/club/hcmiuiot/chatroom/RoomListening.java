package club.hcmiuiot.chatroom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class RoomListening implements Runnable {

	//private DB db = Main.d;
	private Thread thread;
	private String roomName;
	private int sleepTime = 100;
	private boolean running = false;
	private int lastestId = -1;
	private javafx.scene.control.TextArea memo;
	
	public RoomListening(javafx.scene.control.TextArea memoChat, String roomName) {
		this.roomName = roomName;
		this.memo = memoChat;
		thread = new Thread(this, "roomName: "+roomName);
		running = true;
		thread.start();
	}
	
	public void pause() {
		running = false;
	}
	
	public void resume() {
		running = true;
	}
	
	@Override
	public void run() {
		while (running) {
			ResultSet rs = Main.db.loadMsgs(roomName);
			if (rs != null) {
				try {
					while (rs.next()) {
						int id = rs.getInt("id");
						if (id > lastestId) {
							lastestId = id;
							DB.log("New msg > " + rs.getString("msg"));
							memo.setText(memo.getText() + "\n" + rs.getString("nickname")+": " + rs.getString("msg"));
							memo.appendText("");
						}
					}
				} catch (SQLException e) {
					DB.log(e.getMessage());
				}
			}
					
			try {
				TimeUnit.MILLISECONDS.sleep(sleepTime);
			} catch (InterruptedException e) {
				DB.log(e.getMessage());
			} //delay a little bit <3
		}
		
	}

}
