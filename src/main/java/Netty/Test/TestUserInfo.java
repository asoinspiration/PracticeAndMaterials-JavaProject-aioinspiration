package Netty.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUserInfo {
	public static void main(String[] args) throws Exception {
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("aioi");
		ByteArrayOutputStream bos =new ByteArrayOutputStream();
		ObjectOutputStream os =new ObjectOutputStream(bos);
		os.writeObject(info);
		os.flush();
		os.close();
		byte [] b= bos.toByteArray();
		System.out.println(b.length);
		bos.close();
		System.out.println("-------------------------");
		System.out.println(info.codeC().length);
	}

}
