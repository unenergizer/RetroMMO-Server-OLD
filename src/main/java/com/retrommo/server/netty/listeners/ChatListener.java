package com.retrommo.server.netty.listeners;

import com.retrommo.iocommon.wire.global.ChatMessage;
import com.retrommo.server.netty.ObjectListener;
import com.retrommo.server.netty.ObjectType;
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
public class ChatListener implements ObjectListener {

    @ObjectType(getType = ChatMessage.class)
    public void onChatReceived(ChatMessage chatMessage, ChannelHandlerContext ctx) {
//        GameManager gameManager = RetroMmoServer.getInstance().getGameManager();
//        ServerPlayer serverPlayer = gameManager.getPlayer(ctx.channel());
//
//        String chatMsg = chatMessage.getMessage();
//
//        String msg = serverPlayer.getAccountName() + ": " + chatMsg;
//        System.out.println(msg); // print user chat messages to console
//
//        if (chatMsg.startsWith("/")) {
//            boolean commandFound = RetroMmoServer.getInstance().getCommandProcessor().runListeners(chatMsg, ctx.channel());
//
//            if (!commandFound) {
//                chatMessage.setMessage("[Error] That command does not exist! \n");
//                ctx.writeAndFlush(chatMessage);
//            }
//
//        } else {
//
//            // clean up and reuse chat message. Send new line marker back to client.
//            chatMessage.setMessage(msg + "\n");
//
//            //Send messages out to all players
//            for (Channel channel : gameManager.getServerPlayers().keySet()) {
//                channel.writeAndFlush(chatMessage);
//            }
//        }
    }
}
