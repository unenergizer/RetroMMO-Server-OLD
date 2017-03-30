package com.retrommo.server.netty.listeners;

import com.retrommo.iocommon.AuthSuccess;
import com.retrommo.iocommon.LoginInfo;
import com.retrommo.server.RetroMmoServer;
import com.retrommo.server.game.ServerPlayer;
import com.retrommo.server.netty.ObjectListener;
import com.retrommo.server.netty.ObjectType;
import io.netty.channel.ChannelHandlerContext;

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
public class LoginInfoListener implements ObjectListener {

    @ObjectType(getType = LoginInfo.class)
    public void onLogin(LoginInfo loginInfo, ChannelHandlerContext ctx) {
        System.out.println("[Client Login] Account: " + loginInfo.getAccount() + " IP: " + ctx.channel().remoteAddress());
        AuthSuccess authSuccess = new AuthSuccess();
        UUID uuid = UUID.randomUUID();
        authSuccess.setUuid(uuid);

        boolean loginSuccess = false, versionSuccess = false;

        if (authUser(loginInfo.getAccount(), loginInfo.getPassword()))
            authSuccess.setLoginSuccess(loginSuccess = true); // auth success
        if (loginInfo.getVersion().equals(RetroMmoServer.VERSION))
            authSuccess.setVersionCheckPassed(versionSuccess = true); // version success

        // if login success, create serverPlayer object
        if (loginSuccess && versionSuccess) {
            RetroMmoServer.getInstance().getGameManager().addServerPlayer(new ServerPlayer(ctx.channel(), loginInfo.getAccount(), uuid));
        }

        ctx.write(authSuccess);
    }

    public boolean authUser(String account, String password) {
        // do stuff;
        if (account.equalsIgnoreCase("andrew") && password.equals("Brown")) {
            return true;
        } else if (account.equalsIgnoreCase("a") && password.equals("a")) {
            return true;
        }
        return true;
    }
}
