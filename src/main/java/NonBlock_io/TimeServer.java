package NonBlock_io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	public static void main(String[] args) throws IOException {
		int port =8080;
		if(args !=null && args.length>0) {
			try {
				port=Integer.valueOf(args[0]);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port); 
		new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
	
	}
}
