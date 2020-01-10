package com.spring.socket;

import com.spring.socket.netty.client.NettyTcpClient;
import com.spring.socket.netty.server.NettyTcpServer;
import com.spring.socket.netty.vo.TestVO.TestRequest;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

/**
 * @author 赵小超
 */
@SpringBootApplication
public class NettyServerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

    @Autowired
    NettyTcpServer nettyTcpServer;
    @Autowired
    NettyTcpClient nettyTcpClient;

    @Override
    public void run(String... args) throws Exception {
        //启动服务端
        ChannelFuture start = nettyTcpServer.start();
//        //启动客户端，发送数据
//        nettyTcpClient.connect();
//        for (int i = 0; i < 10; i++) {
//            TestRequest testRequest = new TestRequest();
//            testRequest.setName("张三");
//            testRequest.setTime(new Date());
//            testRequest.setFlag(true);
//            testRequest.setAge(i);
//            nettyTcpClient.sendMsg(testRequest);
//        }

        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        start.channel().closeFuture().syncUninterruptibly();
        System.out.println("服务关闭");


    }
}