package com.retrommo.server.netty.listeners;

import com.retrommo.server.netty.ObjectType;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class NetworkListenerManager {

    private final Map<EventMethodPair, com.retrommo.server.netty.ObjectListener> listeners = new HashMap<>();

    public void addListener(com.retrommo.server.netty.ObjectListener listener) {

        boolean annotationFound = false;
        Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            ObjectType[] eventTypes = method.getAnnotationsByType(ObjectType.class);
            if (eventTypes.length == 0) continue;

            if (eventTypes.length > 1) throw new RuntimeException("Cannot contain more than one eventType annotation.");

            annotationFound = true;

            Class<?>[] params = method.getParameterTypes();

            if (params.length > 2) throw new RuntimeException("Cannot contain more than one parameter in: " + listener);
            if (params.length < 2) throw new RuntimeException("Must have two parameter for: " + listener);

            if (!eventTypes[0].getType().equals(params[0]))
                throw new RuntimeException("Annotation to parameter mismatch.");

            if (!params[1].equals(ChannelHandlerContext.class))
                throw new RuntimeException("The second parameter must be of type: " + ChannelHandlerContext.class);

            listeners.put(new EventMethodPair(eventTypes[0], method), listener);
        }

        if (!annotationFound) throw new RuntimeException("Failed to find annotation for: " + listener);
    }

    public void runListeners(Object object, ChannelHandlerContext ctx) {

        boolean packetFound = false;

        for (EventMethodPair it : listeners.keySet()) {
            if (object.getClass().equals(it.getObjectType().getType())) {
                packetFound = true;
                try {
                    it.getMethod().invoke(listeners.get(it), object, ctx);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!packetFound) {
            throw new RuntimeException("Could not find the object type: " + object);
        }
    }

    @AllArgsConstructor
    @Getter
    private class EventMethodPair {
        private final ObjectType objectType;
        private final Method method;
    }
}
