package Blocking_io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 
 *
 */

public class TimeServer {
	public static void main(String[] args) throws IOException {
		int port = 8080; 
		//根据传入的参数设置监听端口，如果没有入参，使用默认值8080。
		if (args != null && args.length > 0) {
			try {
				port=Integer.valueOf(args[0]);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		ServerSocket server= null;
		try {
			server =new ServerSocket(port);  //通过构造函数创建ServerSocket,如果端口合法且没有被占用，服务端监听成功。
			System.out.println("The time server is start in port :" + port);
			Socket socket =null;
			
			//通过无限循环来监听客户端的连接
			while(true) { 
				socket = server.accept(); //如果没有客户端接入，则主线程阻塞在ServerSocket的accept操作上
				new Thread(new TimeServerHandler(socket)).start();//当有新的客户端接入的时候，以Socket为参数构造TimeServerHandler对象
				//TimeServerHandler 是一个Runnable，使用它为构造函数的参数穿件一个新的客户端线程处理这条Socket链路
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// : handle finally clause
			if(server!=null) {
				System.out.println("The time server close");
				server.close();
				server=null;
			}
		}
	}

}
