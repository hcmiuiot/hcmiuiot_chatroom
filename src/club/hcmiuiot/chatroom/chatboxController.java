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

	@FXML 
	private void initialize() {
		memoChat.clear();
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
			memoChat.setText(memoChat.getText() + "\n" + msg);
			txtMsg.clear();
			txtMsg.requestFocus();
		}
		
		
	}
}
