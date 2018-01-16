package club.hcmiuiot.chatroom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class chatboxController {
 	@FXML
    private TextField txtMsg;
    @FXML
    private TextArea memoChat;
	
	private RoomListening listener;
	
	private String roomName;
	private String nickname;

	@FXML 
	private void initialize() {
		memoChat.clear();
	}
	
	@FXML
	public void shutdown() {
		listener.pause();
		listener = null;
	}
	
	public void setInfo(String roomName, String nickName) {
		this.roomName = roomName;
		this.nickname = nickName;
		listener = new RoomListening(memoChat, roomName);
	}
	
	// Event Listener on TextField[#txtMsg].onKeyPressed
	@FXML
	public void onMsgKeyEntered(ActionEvent event) {
			onSend(null);
	}
	// Event Listener on Button.onKeyPressed
	@FXML
	public void onSend(ActionEvent event) {
		String msg = txtMsg.getText();
		if (! msg.isEmpty()) {
			txtMsg.clear();
			txtMsg.requestFocus();
			
			Main.db.sendMsg(nickname, roomName, msg);
		}
		
		
	}
}
