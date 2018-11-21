package Netty.DBFDandFLFD;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {
	public void connection(int port, String host) throws InterruptedException {
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							ByteBuf delimiter = Unpooled.copiedBuffer("#".getBytes());
							socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
									.addLast(new StringDecoder()).addLast(new EchoClientHandler());
							//
						}
					});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} finally {
			workGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int port = 8080;
		if (args.length > 0 && args != null) {
			System.out.println(args[0]);
			port = Integer.parseInt(args[0]);
		}
		new EchoClient().connection(port, "127.0.0.1");
	}
}
