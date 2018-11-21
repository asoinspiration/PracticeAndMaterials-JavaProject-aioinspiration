package Netty.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class PerformTestUserInfo {
	public static void main(String[] args) throws Exception {
		UserInfo info =new UserInfo();
		info.buildUserID(100).buildUserName("aioi");
		int loop =1000000;
		ByteArrayOutputStream bos=null;
		ObjectOutputStream os =null;
		long startTime = System.currentTimeMillis();
		for(int i=0;i<loop;i++) {
			bos=new ByteArrayOutputStream();
			os=new ObjectOutputStream(bos);
			os.writeObject(info);
			os.flush();
			os.close();
			byte[] b = bos.toByteArray();
			bos.close();
		}
		long endTime =System.currentTimeMillis();
		System.out.println((endTime-startTime)+"ms");
		System.out.println("--------------------------");
		ByteBuffer buffer= ByteBuffer.allocate(1024);
		startTime= System.currentTimeMillis();
		for(int i =0; i<loop;i++) {
			byte[] b =info.codeC(buffer);
		}
		endTime = System.currentTimeMillis();
		System.out.println((endTime-startTime)+"ms");
		
		
	}

}
