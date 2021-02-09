package com.nostyling.proxy.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author shiliang
 * @Classname ProxyServerHandler
 * @Date 2021/1/7 15:42
 * @Description 代理handler
 */
public class ProxyServerHandler extends ChannelInboundHandlerAdapter {

    private Channel channel;

    public ProxyServerHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("代理服务器连接成功.....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channel.writeAndFlush(msg);
    }
}
