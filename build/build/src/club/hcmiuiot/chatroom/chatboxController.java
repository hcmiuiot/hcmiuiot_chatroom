package club.hcmiuiot.chatroom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.scene.control.TextArea;

import javafx.scene.input.KeyEvent;

public class chatboxController {
	@FXML
	private TextArea memoChat;
	@FXML
	private TextField txtMsg;
	
	private RoomListening listener;

	@FXML 
	private void initialize() {
		memoChat.clear();
		listener = new RoomListening(memoChat, "Room #1");
	}
	
	@FXML
	private void shutdown() {
		listener.pause();
		listener = null;
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
			//memoChat.setText(memoChat.getText() + "\n" + msg);
			txtMsg.clear();
			txtMsg.requestFocus();
			
			Main.db.sendMsg("hungthuanmk", "Room #1", msg);
		}
		
		
	}
}
