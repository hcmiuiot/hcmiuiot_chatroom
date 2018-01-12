package club.hcmiuiot.chatroom;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class DB {
	
	private Connection connection;
	private Statement st;
	private PreparedStatement preSt;
	
	public DB() {
		
		try {		// Init MySQL Driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			log(e.getMessage());
		}
			
		Properties login = new Properties();
		FileReader in;
		try {
			in = new FileReader(Paths.get(".").toAbsolutePath().normalize() + "/login.properties");
			login.load(in);
		} catch (FileNotFoundException e1) {
			log(e1.getMessage());
		} catch (IOException e) {
			log(e.getMessage());;
		}
		
		String db		= login.getProperty("db");
		String username = login.getProperty("username");
		String password = login.getProperty("password");
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+db + "/" + username + "?useSSL=false", username, password);
		} catch (SQLException e) {
			log(e.getMessage());
		}
	}
	
	public void closeDB() {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				log(e.getMessage());
			}
	}
	
	private ArrayList<Integer> roomIds = new ArrayList<Integer>();;
	public ResultSet loadRooms(ListView lstRoom) {
		try {
			ResultSet rs =  connection.createStatement().executeQuery("SELECT * FROM room");
			ObservableList<String> items = FXCollections.observableArrayList();
			roomIds.clear();
			while (rs.next()) {
				roomIds.add(rs.getInt("id"));
				items.add(rs.getString("name"));
			}
			lstRoom.setItems(items);	
		} catch (SQLException e) {
			log(e.getMessage());
		}
		
		return null;
	}
	
	public void sendMsg(String nickname, String roomName, String msg) {
		try {
			PreparedStatement pr = connection.prepareStatement("INSERT INTO Messages (nickname,msg,roomName) VALUES (?, ?, ?);");
			pr.setString(1, nickname);;
			pr.setString(2, msg);
			pr.setString(3, roomName);
			pr.executeUpdate();
		} catch (SQLException e) {
			log(e.getMessage());
		}
	}
	
	public void createNewRoom(String roomName) {
		try {
			PreparedStatement pr = connection.prepareStatement("INSERT INTO room (name) VALUES (?);");
			pr.setString(1, roomName);;
			pr.executeUpdate();
		} catch (SQLException e) {
			log(e.getMessage());
		}
	}
	
	public static void log(String msg) {
		System.out.println(msg);
	}
	
}
