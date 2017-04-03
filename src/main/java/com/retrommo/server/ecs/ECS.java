package com.retrommo.server.ecs;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.utils.IntBag;
import com.retrommo.server.ecs.components.EntityType;
import com.retrommo.server.ecs.components.Player;
import com.retrommo.server.ecs.components.Position;
import com.retrommo.server.ecs.systems.PositionUpdateSystem;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class ECS {

    private WorldConfiguration config;
    private World world;

    private ComponentMapper<Position> positionMapper;
    private ComponentMapper<EntityType> entityTypeMapper;
    private ComponentMapper<Player> playerMapper;

    public ECS() {
        initWorld();
        setupComponentMappers();
    }

    /**
     * Setup the entity component system world.
     * ECS will not function without it.
     */
    private void initWorld() {
        config = new WorldConfigurationBuilder()
                .with(
                        new PositionUpdateSystem()
                ).build();

        world = new World(config);
    }

    /**
     * Setup entity component mappers.
     * These are basically data tags for entities.
     */
    private void setupComponentMappers() {
        positionMapper = new ComponentMapper<>(Position.class, world);
        entityTypeMapper = new ComponentMapper<>(EntityType.class, world);
        playerMapper = new ComponentMapper<>(Player.class, world);
    }

    /**
     * Gets all entities in our ECS-World besides player entities.
     *
     * @return A IntBag of non-player entities.
     */
    public IntBag getAllNonPlayerEntities() {
        return world.getAspectSubscriptionManager().get(Aspect.all(EntityType.class).exclude(Player.class)).getEntities();
    }

    /**
     * Gets all player entities in our ECS-World.
     *
     * @return A IntBag of all player entities.
     */
    public IntBag getAllPlayersEntities() {
        return world.getAspectSubscriptionManager().get(Aspect.all(Player.class)).getEntities();
    }

    public <T extends Component> T createComponent(Entity entity, ComponentMapper<T> componentMapper) {
        T component = componentMapper.create(entity.getId());
        entity.edit().add(component);
        return component;
    }
}
