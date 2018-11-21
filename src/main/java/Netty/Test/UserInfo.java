package Netty.Test;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo implements Serializable{
	private static final long serialVersionUID=1L;
	private String username;
	private int userID;
	public UserInfo buildUserName(String userName) {
		this.username=userName;
		return this;
	}
	public UserInfo buildUserID(int userID) {
		this.userID=userID;
		return this;
	}
	public final void setUserName(String userName) {
		this.username= userName;
	}
	public final int getUserID() {
		return userID;
	}
	public final void serUserID(int userID) {
		this.userID=userID;
	}
	public byte[] codeC() {
		ByteBuffer buffer =ByteBuffer.allocate(1024);
		byte[] value =this.username.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.userID);
		buffer.flip();
		value=null;
		byte[] result =new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}
	public byte[] codeC(ByteBuffer buffer) {
		buffer.clear();
		byte[] value = this.username.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.userID);
		buffer.flip();
		value=null;
		byte[] result =new byte[buffer.remaining()];
		buffer.get(result);
		return result;
	}

}
