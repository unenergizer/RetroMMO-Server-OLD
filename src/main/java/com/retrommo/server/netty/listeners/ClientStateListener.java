package com.retrommo.server.netty.listeners;

import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.retrommo.iocommon.enums.EntityTypes;
import com.retrommo.iocommon.wire.client.ClientState;
import com.retrommo.iocommon.wire.server.SendEntityData;
import com.retrommo.server.RetroMmoServer;
import com.retrommo.server.ecs.ClientManager;
import com.retrommo.server.ecs.ECS;
import com.retrommo.server.ecs.components.EntityType;
import com.retrommo.server.ecs.components.Position;
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
public class ClientStateListener implements ObjectListener {

    private final ECS ecs = RetroMmoServer.getECS();
    private final ClientManager clientManager = RetroMmoServer.getClientManager();

    @ObjectType(getType = ClientState.class)
    public void onClientStateReceived(ClientState clientState, ChannelHandlerContext ctx) {

        Channel channel = ctx.channel();

        switch (clientState.getState()) {

            case GAME_READY:
                int newPlayerID = clientManager.getClientPlayerEntityID(channel);
                sendInitialClientPlayerInfo(newPlayerID, channel);
                sendPlayerEntities(newPlayerID, channel);
                sendNonPlayerEntities(newPlayerID, channel);
                break;
            case SHUT_DOWN:
                break;
        }
    }

    /**
     * This will setup the clients player character.
     *
     * @param newPlayerID The server assigned ID.
     * @param channel The Netty Channel that we will be sending data to.
     */
    private void sendInitialClientPlayerInfo(int newPlayerID, Channel channel) {
        Position newLoginPosition = ecs.getPositionMapper().get(newPlayerID);
        float newPlayerX = newLoginPosition.getX();
        float newPlayerY = newLoginPosition.getY();
        int newPlayerMapId = newLoginPosition.getMapId();

        // create entity location data to send to other player clients.
        SendEntityData newLoginData = new SendEntityData(newPlayerID, EntityTypes.CLIENT_PLAYER, newPlayerX, newPlayerY, newPlayerMapId);
        channel.writeAndFlush(newLoginData);
    }

    /**
     * This will send a newly logged in player all online player data if they are all on the
     * same map.
     *
     * @param newPlayerID The ECS ID of the player who will be getting entity updates.
     * @param channel     The Netty Channel that we will be sending data to.
     */
    private void sendPlayerEntities(int newPlayerID, Channel channel) {
        ComponentMapper<EntityType> entityTypes = ecs.getEntityTypeMapper();
        EntityTypes newLoginType = entityTypes.get(newPlayerID).getEntityType();

        Position newLoginPosition = ecs.getPositionMapper().get(newPlayerID);
        float newPlayerX = newLoginPosition.getX();
        float newPlayerY = newLoginPosition.getY();
        int newPlayerMapId = newLoginPosition.getMapId();

        // create entity location data to send to other player clients.
        SendEntityData newLoginData = new SendEntityData(newPlayerID, newLoginType, newPlayerX, newPlayerY, newPlayerMapId);

        IntBag entities = ecs.getAllPlayersEntities();
        for (int entityId = 0; entityId < entities.size(); entityId++) {

            EntityTypes entityType = entityTypes.get(entityId).getEntityType();
            Position entity = ecs.getPositionMapper().get(entityId);
            float entityX = entity.getX();
            float entityY = entity.getY();
            int entityMapId = entity.getMapId();

            // Only send data to entities on the same Tiled map.
            if (newPlayerMapId != entityMapId) continue;

            // Prevent sending information about the client, to the client.
            if (entityId == newPlayerID) continue;

            // Send the newPlayer info about the currently online players.
            SendEntityData onlinePlayerData = new SendEntityData(entityId, entityType, entityX, entityY, entityMapId);
            channel.writeAndFlush(onlinePlayerData);

            // Send data about the new Player to all online players.
            clientManager.getClientPlayerChannel(entityId).writeAndFlush(newLoginData);
        }
    }

    /**
     * This will send a new login player all non-entity data if the player
     * and the entities are on the same map.
     *
     * @param newPlayerID The ECS ID of the player who will be getting entity updates.
     * @param channel     The Netty Channel that we will be sending data to.
     */
    private void sendNonPlayerEntities(int newPlayerID, Channel channel) {
        ComponentMapper<EntityType> entityTypes = ecs.getEntityTypeMapper();
        Position newLoginPosition = ecs.getPositionMapper().get(newPlayerID);
        int newLoginMapId = newLoginPosition.getMapId();

        IntBag entities = ecs.getAllNonPlayerEntities();
        for (int entityId = 0; entityId < entities.size(); entityId++) {

            Position entity = ecs.getPositionMapper().get(entityId);
            int entityMapId = entity.getMapId();

            // Only send data to entities on the same Tiled map.
            if (newLoginMapId != entityMapId) continue;

            EntityTypes entityType = entityTypes.get(entityId).getEntityType();
            float entityX = entity.getX();
            float entityY = entity.getY();

            // Finally send the onlineEntityData for this entity to the newly logged in player.
            channel.writeAndFlush(new SendEntityData(entityId, entityType, entityX, entityY, entityMapId));
        }
    }
}
