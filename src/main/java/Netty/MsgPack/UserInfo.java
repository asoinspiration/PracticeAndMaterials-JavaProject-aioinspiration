package Netty.MsgPack;

import org.msgpack.annotation.Message;

@Message
public class UserInfo {
	
	private String userName;
	private int userID;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", userID=" + userID + "]";
	}
	

}
