package com.retrommo.server.ecs.systems;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import com.retrommo.server.ecs.components.EntityType;
import com.retrommo.server.ecs.components.Position;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: retrommo-server
 * DATE: 3/30/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class PositionUpdateSystem extends IteratingSystem {

    public PositionUpdateSystem() {
        super(Aspect.all(Position.class, EntityType.class));
    }

    @Override
    protected void process(int entityId) {

//        ECS ecs = RetroMmoServer.getECS();
//
//        Position position = ecs.getPositionMapper().get(entityId);
//        EntityType entityType = ecs.getEntityTypeMapper().get(entityId);
//
//        switch (entityType.getEntityType()) {
//            case PLAYER:
//
//                int mapId = position.getMapId();
//
//                EntitySubscription subscription = world.getAspectSubscriptionManager()
//                        .get(Aspect.all(Player.class));
//
//                IntBag entityIds = subscription.getEntities();
//                for (int i = 0; i < entityIds.size(); i++) {
//
//                    PlayerData somePlayer = ecs.getPlayerMapper().get(entityId);
//
//                    if (!somePlayer.getStates().equals(ClientStates.GAME_READY)) return;
//
//                    int theirMap = ecs.getPositionMapper().get(entityId).getMapId();
//                    // Send out to the player since we are on the same map
//                    if (theirMap == mapId) {
//
//                        EntityMove newPosition = new EntityMove();
//                        newPosition.setX(position.getX() + 1);
//                        newPosition.setY(position.getY() + 1);
//                        newPosition.setEntityId(entityId);
//                        newPosition.setMapId(mapId);
//
//                        somePlayer.getChannel().writeAndFlush(newPosition);
//                    }
//                }
//
//                break;
//        }
    }
}
