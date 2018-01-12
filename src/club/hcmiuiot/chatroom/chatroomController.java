package club.hcmiuiot.chatroom;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class chatroomController {
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtNewRoom;
	@FXML
	private ListView<String> lstRooms;
	@FXML
    private TabPane chatsTab;

	@FXML
    private void onCreateNewRoom(ActionEvent event) {
		Main.db.loadRooms(lstRooms);
    }
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
    void onMouseClicked(MouseEvent  event) {
		if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
			if (txtName.getText() == "") {
				Alert al = new Alert(AlertType.ERROR);
				al.setContentText("Please type your nickname!");
			}
			else {
				int id = lstRooms.getSelectionModel().getSelectedIndex();
				loginRoom(id, lstRooms.getItems().get(id));	
				//DB.log(selectedRoomName +" -> #" + id);
			}		
		}
    }
	
	private void loginRoom(int roomId, String roomName) {
		try {
			AnchorPane newNode = FXMLLoader.load(getClass().getResource("chatbox.fxml"));
			Tab newTab = new Tab(roomName, newNode);
			newTab.setClosable(true);
			//chatboxController controller = ((FXMLLoader) newNode).getController();
			chatsTab.getTabs().add(newTab);
		} catch (IOException e) {
			DB.log(e.getMessage());
		}
	}

	
}
