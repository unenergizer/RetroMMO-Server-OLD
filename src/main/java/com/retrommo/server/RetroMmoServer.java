package com.retrommo.server;

import com.retrommo.server.commands.CommandProcessor;
import com.retrommo.server.commands.ConsoleCommandManager;
import com.retrommo.server.commands.listeners.AdminCommand;
import com.retrommo.server.commands.listeners.OnlineCommand;
import com.retrommo.server.ecs.ClientManager;
import com.retrommo.server.ecs.ECS;
import com.retrommo.server.netty.NettySetup;
import com.retrommo.server.netty.listeners.ChatListener;
import com.retrommo.server.netty.listeners.ClientStateListener;
import com.retrommo.server.netty.listeners.EntityMoveListener;
import com.retrommo.server.netty.listeners.LoginInfoListener;
import com.retrommo.server.netty.listeners.NetworkListenerManager;
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

    // GENERAL
    public static final String VERSION = "0.1.0";
    private static RetroMmoServer instance = null;
    private ServerLoop loop;
    private CommandProcessor commandProcessor;

    // NETWORK
    private NettySetup netty;
    private NetworkListenerManager networkListenerManager;
    private volatile boolean isOnline = false;

    // ENTITIES
    private ECS ecs;
    private ClientManager clientManager;

    private RetroMmoServer() {
    }

    public static void main(String[] args) {
        System.out.println("[Server] Initializing RetroMMO Server!");
        RetroMmoServer.getInstance().start(); // using singleton
    }

    /**
     * Singleton of the RetroMMO-Server main class.
     *
     * @return Instance of the main RetroMMO-Server class.
     */
    public static RetroMmoServer getInstance() {
        if (instance == null) instance = new RetroMmoServer();
        return instance;
    }

    /**
     * A helper method for short access to the Entity Component System.
     *
     * @return A instance of the Entity Component System manager.
     */
    public static ECS getECS() {
        return getInstance().ecs;
    }

    /**
     * A helper method for short access to the Client Manager.
     * The client manager is used to store ECS and Netty data together.
     * This is a small helper class to make lookup, creation, and
     * deletion of client information a lot easier.
     *
     * @return A instance of the Client Manager.
     */
    public static ClientManager getClientManager() {
        return getInstance().clientManager;
    }

    /**
     * Starts everything needed to begin listening to client connections.
     */
    private void start() {
        isOnline = true;
        ecs = new ECS();
        clientManager = new ClientManager();

        // register listeners
        registerNetworkListeners();
        registerCommands();

        // start server loop
        loop = new ServerLoop();
        loop.start();

        // start netty netty
        netty = new NettySetup();
        netty.start();
    }

    /**
     * Closes the server and disconnects all clients.
     */
    public void stop() {
        System.out.println("[Server] Server shutdown initialized!");
        isOnline = false; // this will stop the loop
        netty.stop();
        System.out.println("[Server] Server shutdown complete!");
    }

    /**
     * This is the Network Listener. It is responsible for processing
     * incoming server object's. To process an object, a listener
     * must be defined for it. Otherwise, the object will be ignored.
     */
    private void registerNetworkListeners() {
        System.out.println("[NetworkListener] Adding Netty object listeners!");
        networkListenerManager = new NetworkListenerManager();

        networkListenerManager.addListener(new ChatListener());
        networkListenerManager.addListener(new ClientStateListener());
        networkListenerManager.addListener(new EntityMoveListener());
        networkListenerManager.addListener(new LoginInfoListener());
    }

    /**
     * This is the command listeners. It is responsible for parsing
     * and running commands. To process a command, a listener must
     * be provided for it. Otherwise, the command will throw an
     * error.
     * <p>
     * TODO: Get commands working for the console!
     */
    private void registerCommands() {
        System.out.println("[CommandProcessor] Adding command listeners!");
        commandProcessor = new CommandProcessor();

        commandProcessor.addListener(new AdminCommand());
        commandProcessor.addListener(new OnlineCommand());

        // console command manager
        // TODO: Get commands working for the console!
        new ConsoleCommandManager().start();
    }
}
