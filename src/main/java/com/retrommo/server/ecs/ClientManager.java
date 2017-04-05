package com.retrommo.server.ecs;

import com.artemis.Entity;
import com.artemis.World;
import com.retrommo.iocommon.enums.EntityTypes;
import com.retrommo.server.RetroMmoServer;
import com.retrommo.server.ecs.components.EntityType;
import com.retrommo.server.ecs.components.Position;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: retrommo-server
 * DATE: 4/2/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class ClientManager {

    private final ECS ecs;
    private final World world;

    private final Map<Channel, PlayerInfo> channelToPlayerInfo = new HashMap<>();
    private final Map<Integer, PlayerInfo> entityIdToPlayerInfo = new HashMap<>();

    public ClientManager() {
        ecs = RetroMmoServer.getECS();
        world = ecs.getWorld();
    }

    /**
     * This will send Objects to all the players currently logged in.
     * Generally this really should not be used.
     *
     * @param object The object we want to send to the client.
     */
    public void writeDataToAllClients(Object object) {
        for (Channel channel : channelToPlayerInfo.keySet()) {
            channel.writeAndFlush(object);
        }
    }

    /**
     * This is meant to be the only way to add a client to the server
     * and to the ECS (entity component system).  The setup that happens
     * in this method will make it easier for us later to access this
     * information.  Netty and Artemis-odb (ECS) was not originally
     * meant to work together.  This will make our life's easier.
     *
     * @param accountName The name of the playerAccount who logged in.
     * @param ctx The channel that the client has connected to.
     */
    public void addClientPlayer(String accountName, ChannelHandlerContext ctx) {
        // create entity
        int entityId = world.create();
        Entity entity = world.getEntity(entityId);

        // set entity to player
        ecs.createComponent(entity, ecs.getPlayerMapper());

        EntityType entityType = ecs.createComponent(entity, ecs.getEntityTypeMapper());
        entityType.setEntityType(EntityTypes.PLAYER);

        Position position = ecs.createComponent(entity, ecs.getPositionMapper());
        float x = ThreadLocalRandom.current().nextInt(300, 600 + 1);
        float y = ThreadLocalRandom.current().nextInt(300, 600 + 1);
        position.setX(x);
        position.setY(y);

        // save entity data to HashMap and List
        PlayerInfo playerInfo = new PlayerInfo(entityId, ctx.channel(), accountName);
        channelToPlayerInfo.put(ctx.channel(), playerInfo); // Save to HashMap
        entityIdToPlayerInfo.put(entityId, playerInfo); // Save to HashMap

        //TODO: Notify other players of player new client login. (UNTESTED: Might be automatic thanks to ECS)
    }

    /**
     * This will remove a player from the server and remove them from the ECS (entity component system).
     *
     * @param channel Retrieves the player to remove based on the channel they are connected to.
     */
    public void removeClientPlayer(Channel channel) {
        removeClientPlayer(channelToPlayerInfo.get(channel).getEntityID(), channel);
    }

    /**
     * This will remove a player from the server and remove them from the ECS (entity component system).
     *
     * @param entityID Retrieves the player to remove based on the entity id provided by the ECS.
     */
    public void removeClientPlayer(int entityID) {
        removeClientPlayer(entityID, entityIdToPlayerInfo.get(entityID).getChannel());
    }

    /**
     * This is the main method responsible for removing a player entity from both Netty and ECS.
     *
     * @param entityID The entity we want to remove from ECS.
     * @param channel  The Netty channel we want to close.
     */
    private void removeClientPlayer(int entityID, Channel channel) {
        // Delete the entity from the world
        world.delete(entityID);

        // Close the Netty Channel
        channel.close();

        //TODO: Save account info to database.
        //TODO: Notify other players of player logout. (UNTESTED: Might be automatic thanks to ECS)

        // Remove from HashMap and List
        channelToPlayerInfo.remove(channel); // Remove from HashMap
        entityIdToPlayerInfo.remove(entityID); // Remove from HashMap
    }

    /**
     * This will retrieve a ECS entity ID based on the player's channel context.
     *
     * @param channel The channel we will use to grab the player's Entity ID.
     * @return A entity ID used to manipulate the player in the ECS (world).
     */
    public int getClientPlayerEntityID(Channel channel) {
        return channelToPlayerInfo.get(channel).getEntityID();
    }

    /**
     * This will retrieve a Netty channel based on the player's entity ID.
     *
     * @param entityID The entity ID we will use to grab the players channel.
     * @return A Netty channel used to send and receive data from the client to the server.
     */
    public Channel getClientPlayerChannel(int entityID) {
        return entityIdToPlayerInfo.get(entityID).getChannel();
    }

    /**
     * Gets basic data about a user.  This does not get data from ECS or Netty.
     *
     * @param channel Look up the player data by the Netty channel.
     * @return Various player data.
     */
    public PlayerInfo getClientPlayerInfo(Channel channel) {
        return channelToPlayerInfo.get(channel);
    }

    /**
     * Gets basic data about a user.  This does not get data from ECS or Netty.
     *
     * @param entityID Look up the player data by the ECS Entity ID.
     * @return Various player data.
     */
    public PlayerInfo getClientPlayerInfo(int entityID) {
        return entityIdToPlayerInfo.get(entityID);
    }
}
