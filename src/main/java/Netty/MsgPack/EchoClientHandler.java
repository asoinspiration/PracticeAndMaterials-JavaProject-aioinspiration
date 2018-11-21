package Netty.MsgPack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private int count=0;
    private final int sendNumber;
    public EchoClientHandler(int sendNumber) {
		// TODO Auto-generated constructor stub
    	this.sendNumber=sendNumber;
	}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	UserInfo[] infos =UserInfo();
    	for(UserInfo infoe:infos) {
    		ctx.write(infoe);
    	}
    	ctx.flush();
    }
    private UserInfo[] UserInfo() {
		UserInfo[] userInfos =new UserInfo[sendNumber];
		UserInfo userInfo =null;
		for(int i=0;i<sendNumber; i++) {
			userInfo=new UserInfo();
			userInfo.setUserID(i);
			userInfo.setUserName("ABCDEF-------->"+i);
			userInfos[i]=userInfo;
		}
    	return userInfos;
	}

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("this is client receive msg"+msg);
        ctx.write(msg);
//        ++count;
//        if(count<5){ //控制运行次数，因为不加这个控制直接调用下面代码的话，客户端和服务端会形成闭环循环，一直运行
//            ctx.write(msg);
//            }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


}