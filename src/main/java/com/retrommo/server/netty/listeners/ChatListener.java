package com.retrommo.server.netty.listeners;

import com.retrommo.iocommon.ChatMessage;
import com.retrommo.server.RetroMmoServer;
import com.retrommo.server.commands.Command;
import com.retrommo.server.commands.CommandListener;
import com.retrommo.server.commands.CommandProcessor;
import com.retrommo.server.game.GameManager;
import com.retrommo.server.game.ServerPlayer;
import com.retrommo.server.netty.ObjectListener;
import com.retrommo.server.netty.ObjectType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

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
public class ChatListener implements ObjectListener, CommandListener {

    private CommandProcessor commandProcessor = new CommandProcessor();

    public ChatListener() {
        commandProcessor.addListener(this);
    }

    @ObjectType(getType = ChatMessage.class)
    public void onChatReceived(ChatMessage chatMessage, ChannelHandlerContext ctx) {
        GameManager gameManager = RetroMmoServer.getInstance().getGameManager();
        ServerPlayer serverPlayer = gameManager.getPlayer(ctx.channel());

        String chatMsg = chatMessage.getMessage();

        String msg = serverPlayer.getAccountName() + ": " + chatMsg;
        System.out.println(msg); // print user chat messages to console

        if (chatMsg.startsWith("/")) {
            boolean commandFound = handleCommands(chatMsg, ctx.channel());

            if (!commandFound) {
                chatMessage.setMessage("[Server] That command does not exist! \n");
                ctx.writeAndFlush(chatMessage);
            }

        } else {

            // clean up and reuse chat message. Send new line marker back to client.
            chatMessage.setMessage(msg + "\n");

            //Send messages out to all players
            for (Channel channel : gameManager.getServerPlayers().keySet()) {
                channel.writeAndFlush(chatMessage);
            }
        }
    }

    private boolean handleCommands(String command, Channel playerChannel) {
        return commandProcessor.runListeners(command, playerChannel);
    }

    @Command(getCommands = { "/admin", "/administrator" })
    public void exampleCmdExecutor(Channel playerChannel) {
        System.out.println("They typed in a command!");
    }

    @Command(getCommands = "/second_command")
    public void exampleTwo(Channel playerChannel) {
        System.out.println("testing two commands");
    }
}
