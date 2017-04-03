package com.retrommo.server.netty.listeners;

import com.retrommo.iocommon.wire.client.EntityMove;
import com.retrommo.server.RetroMmoServer;
import com.retrommo.server.ecs.components.Position;
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
public class EntityMoveListener implements ObjectListener {

    @ObjectType(getType = EntityMove.class)
    public void onEntityMove(EntityMove entityMove, ChannelHandlerContext ctx) {

        Position position = RetroMmoServer.getECS().getPositionMapper().get(entityMove.getEntityId());

        position.setX(entityMove.getX());
        position.setY(entityMove.getY());
    }
}
