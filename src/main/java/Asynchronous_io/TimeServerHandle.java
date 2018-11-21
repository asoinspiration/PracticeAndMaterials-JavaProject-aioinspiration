package Asynchronous_io;

public class TimeServerHandle {
	public static void main(String[] args) {
		int port = 8080;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(port);
			} catch (NumberFormatException e) {
				// 发生异常则采用默认值
			}
		}
		AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
		new Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start();
	}

}
