package com.retrommo.server.game;

import io.netty.channel.Channel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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
public class GameManager {

    private Map<Channel, ServerPlayer> serverPlayers = new HashMap<>();

    public void addServerPlayer(ServerPlayer player) {
        Channel channel = player.getChannel();
        if (!serverPlayers.containsKey(channel)) {
            serverPlayers.put(channel, player);
        } else {
            //   throw new RuntimeException("Attempted to ");
        }
    }

    public void removeServerPlayer(Channel channel) {
        serverPlayers.remove(channel);
    }

    public ServerPlayer getPlayer(Channel channel) {
        return serverPlayers.get(channel);
    }
}
