package com.spring.socket.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zc
 * @Date: 2019-06-20 17:48
 * @Description: 通道初始化，主要用于设置各种Handler
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    ServerChannelHandler serverChannelHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("初始化 initChannel");
        ChannelPipeline pipeline = socketChannel.pipeline();
        //IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
        pipeline.addLast("idleStateHandler",
                new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
        //编解码器-客户端与服务端保持一致
        pipeline.addLast(
                //对象编码解码
//                new ObjectDecoder(1024 * 1024, ClassResolvers
//                        .weakCachingConcurrentResolver(this.getClass()
//                                .getClassLoader())),
//                new ObjectEncoder(),
                //字符串编码解码
                new StringDecoder(),
                new StringEncoder()
        );
        //自定义Handler
        pipeline.addLast("serverChannelHandler", serverChannelHandler);
    }
}
