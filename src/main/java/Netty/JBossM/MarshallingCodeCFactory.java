package Netty.JBossM;

import io.netty.handler.codec.marshalling.*;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

public final class MarshallingCodeCFactory {
    public static MarshallingDecoder buildMarshallingDecoder(){
//      通过Marshalling工具类的getProvidedMarshallerFactory获取静态MarshallerFactory工厂实例，参数serial表示创建的是
//        java序列化工厂对象。
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
//      创建MarshallingConfiguration对象，将其版本号设置为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
//      根据工厂及配置创建UnmarshallerProvider实例
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
//      通过构造函数创建netty的MarshallingDecoder
        MarshallingDecoder decoder = new MarshallingDecoder(provider,1024);
        return decoder;
    }
    public static MarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory,configuration);
//      创建的下列解码器用于实现序列化接口的POJO对象序列化为二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }

}