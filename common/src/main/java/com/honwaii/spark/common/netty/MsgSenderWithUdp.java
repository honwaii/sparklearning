package com.honwaii.spark.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class MsgSenderWithUdp {
    private final Integer port = 9090;
    private NioEventLoopGroup group;
    private Bootstrap bootstrap;

    public MsgSenderWithUdp() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
//                .localAddress(new InetSocketAddress(port))
                .handler(new MsgSenderWithUdpHandler());
    }

    public void sendMsgWithUdp(String msg) throws InterruptedException {
        Channel channel = bootstrap.bind(0).sync().channel();
        DatagramPacket datagramPacket = datagramPacket(msg, new InetSocketAddress("127.0.0.1", port));
        channel.writeAndFlush(datagramPacket).addListener((GenericFutureListener<ChannelFuture>) future -> {
            boolean success = future.isSuccess();
            System.out.println("message send with udp " + success);
        });
    }

    public DatagramPacket datagramPacket(String msg, InetSocketAddress inetSocketAddress) {
        ByteBuf dataBuf = Unpooled.copiedBuffer(msg, Charset.forName("UTF-8"));
        DatagramPacket datagramPacket = new DatagramPacket(dataBuf, inetSocketAddress);
        return datagramPacket;
    }

    public void stop() {
        group.shutdownGracefully();
    }


    public static void main(String[] args) throws InterruptedException {
        MsgSenderWithUdp msgsender = new MsgSenderWithUdp();
        try {
            msgsender.sendMsgWithUdp("netty udp test!");
        } finally {
            msgsender.stop();
        }
    }

}
