package Netty.TcpstickPackage;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());

	private int counter;

	private byte[] req;

	public TimeClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 与服务端建立连接后
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 服务端返回消息后
		 ByteBuf buf = (ByteBuf) msg;
		 byte[] req = new byte[buf.readableBytes()];
		 buf.readBytes(req);
		 String body = new String(req, "UTF-8");
	
		System.out.println("Now is :" + body + "; the counter is :" + ++counter);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught..");
		// 释放资源
		logger.warning("Unexpected exception from downstream:" + cause.getMessage());
		ctx.close();
	}

}
