package NonBlock_io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable{
	
	private Selector selector;
	private ServerSocketChannel servChannel;
	private volatile boolean stop;

	public MultiplexerTimeServer(int port) {
		try {
			selector=Selector.open();
			servChannel =ServerSocketChannel.open(); //创建多路复用器Selector,ServerSocketChannel
			servChannel.configureBlocking(false); //将ServerSocketChannel设置为异步非阻塞模式
			servChannel.socket().bind(new InetSocketAddress(port),1024);//设blacklog为1024
			servChannel.register(selector, SelectionKey.OP_ACCEPT);//系统资源初始化成功后，将ServerSocketChannel注册到Select，监听ACCEPT操作位。
			System.out.println("The time server is start in port : "+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.exit(1);//如果资源初始化失败(例如端口被占用)，则退出。
			e.printStackTrace();
		}
	}
	public void stop() {
		this.stop=true;
	}

	@Override
	public void run() {
		//循环遍历selector
		while(!stop) {
			try {
				selector.select(1000);//休眠时间为1秒，无论是否有读写事件发生，selector每隔1s被唤醒一次。
				Set<SelectionKey> selectedKeys=selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				SelectionKey key=null;
				while(it.hasNext()) {
					key=it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if(key!=null) {
							key.cancel();
							if(key.channel()!=null)
							key.channel().close();
						}
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	private void handleInput(SelectionKey key) throws IOException {
		if(key.isValid()) {
			if(key.isAcceptable()) {
				ServerSocketChannel ssc=(ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				sc.register(selector, SelectionKey.OP_READ);
			}
			if(key.isReadable()) {
				SocketChannel sc=(SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes >0) {
					readBuffer.flip();
					byte[] bytes=new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes,"UTF-8");
					System.out.println("The time server receive order : "+ body);
					String currentTime = "QUERY TIME ORDER"
							.equalsIgnoreCase(body)? new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
					doWrite(sc,currentTime );
				}else if(readBytes < 0){
					key.cancel();
					sc.close();
				}else 
				;
			}
		}
		
	}
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if(response!=null && response.trim().length()>0) {
			byte[] bytes =response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
		
	}

}
