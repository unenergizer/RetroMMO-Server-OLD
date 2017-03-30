package com.retrommo.server;

import com.retrommo.server.commands.CommandManager;
import com.retrommo.server.game.GameManager;
import com.retrommo.server.netty.NettySetup;
import com.retrommo.server.netty.listeners.ChatListener;
import com.retrommo.server.netty.listeners.EntityMoveListener;
import com.retrommo.server.netty.listeners.ObjectProcessor;
import com.retrommo.server.netty.listeners.LoginInfoListener;
import lombok.Getter;

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
@Getter
public class RetroMmoServer {

    public static final String VERSION = "0.1.0";
    private static final int port = 1337;
    private static RetroMmoServer instance = null;
    private GameManager gameManager;
    private NettySetup netty;
    private ObjectProcessor listenerManager;


    protected RetroMmoServer() {
    }

    public static void main(String[] args) throws Exception {
        System.out.println("[Server] Initializing RetroMMO Server!");
        RetroMmoServer.getInstance().start(); // using singleton
    }

    /**
     * Singleton of the RetroMMO-Server main class.
     *
     * @return Instance of the main RetroMMO-Server class.
     */
    public static RetroMmoServer getInstance() {
        if (instance == null) {
            instance = new RetroMmoServer();
        }
        return instance;
    }

    /**
     * Starts everything needed to begin listening to client connections.
     *
     * @throws Exception
     */
    private void start() throws Exception {
        // setup managers
        gameManager = new GameManager();

        // register listeners
        registerListeners();

        // start netty netty
        netty = new NettySetup(port);
        netty.start();

        // start console command listener. initialize this last.
        new CommandManager().start();
    }

    /**
     * Closes the server and disconnects all clients.
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException {
        System.out.println("[Server] Server shutdown initialized!");
        netty.stop();
        System.out.println("[Server] Server shutdown complete!");
    }

    private void registerListeners() {
        System.out.println("[Listeners] Adding listeners!");
        listenerManager = new ObjectProcessor();

        listenerManager.addListener(new ChatListener());
        listenerManager.addListener(new EntityMoveListener());
        listenerManager.addListener(new LoginInfoListener());
    }
}
