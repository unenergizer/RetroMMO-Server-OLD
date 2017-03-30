package com.retrommo.server.game;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

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
public class ServerPlayer {

    private Channel channel;
    private String accountName;
    private UUID uuid;
    private float x, y;

    public ServerPlayer(Channel channel, String accountName, UUID uuid) {
        this.channel = channel;
        this.accountName = accountName;
        this.uuid = uuid;
    }

    public void updateCords(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
