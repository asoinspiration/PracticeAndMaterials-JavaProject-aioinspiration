package Netty.Protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubScribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }
    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }
    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("醉逍遥");
        builder.setProductName("Netty buffer");
        List<String> address = new ArrayList<>();
        address.add("beijing tiananmen");
        address.add("shanghai huangpujiang");
        address.add("tianjin binhaixinqu");
        address.add("shenzhen houhai");
        builder.addAllAddress(address);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("解码之前："+req.toString());
        byte[] b = encode(req);
        System.out.println("编码后："+b.toString());
        SubscribeReqProto.SubscribeReq req1 = decode(b);
        System.out.println("解码后："+req1);
        System.out.println("解码前后是否相等："+req.equals(req1));
    }
}