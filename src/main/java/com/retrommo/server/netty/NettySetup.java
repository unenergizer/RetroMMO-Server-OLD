package com.retrommo.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: retrommo-server
 * DATE: 3/26/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be
 * reproduced, distributed, or transmitted in any form or by any means,
 * including photocopying, recording, or other electronic or mechanical methods,
 * without the prior written permission of the owner.
 */
public class NettySetup implements Runnable {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private int port;

    public NettySetup(int port) throws Exception {
        this.port = port;
    }

    /**
     * This will start Netty in a new thread.
     */
    public void start() {
        System.out.println("[Netty] Netty is starting up...");
        new Thread(this, "Netty").start();
    }

    /**
     * This will safely shut Netty down and close all connections.
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        System.out.println("[Netty] Shutting down Netty!");
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    @Override
    public void run() {
        initNettyServer();
    }

    /**
     * Setup netty server
     */
    private void initNettyServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("[Netty] Listening for connections on port " + port + ".");

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } finally {
            System.out.println("[Netty] Netty shutdown complete!");
        }
    }
}
