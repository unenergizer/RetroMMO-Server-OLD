package com.retrommo.server.netty.listeners;

import com.retrommo.iocommon.wire.client.LoginInfo;
import com.retrommo.iocommon.wire.server.AuthSuccess;
import com.retrommo.server.RetroMmoServer;
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
public class LoginInfoListener implements ObjectListener {

    @ObjectType(getType = LoginInfo.class)
    public void onLogin(LoginInfo loginInfo, ChannelHandlerContext ctx) {
        String authInfo = "[Client Login] Login {RESULT}! Account: " + loginInfo.getAccount() + " IP: " + ctx.channel().remoteAddress();
        AuthSuccess authSuccess = new AuthSuccess(false, false); //by default make sure these values are false
        boolean loginSuccess = false, versionSuccess = false;

        if (authUser(loginInfo.getAccount(), loginInfo.getPassword()))
            authSuccess.setLoginSuccess(loginSuccess = true); // auth success
        if (loginInfo.getVersion().equals(RetroMmoServer.VERSION))
            authSuccess.setVersionCheckPassed(versionSuccess = true); // version success

        // if login success, create serverPlayer object
        if (loginSuccess && versionSuccess) {
            System.out.println(authInfo.replace("{RESULT}", "Success"));
            RetroMmoServer.getClientManager().addClientPlayer(loginInfo.getAccount(), ctx);
        } else {
            System.out.println(authInfo.replace("{RESULT}", "Failure"));
        }

        // Send authSuccess information back to the client.
        // This information contains Version checking and Login authentication success.
        // If both booleans are true, then client will login. If one or both are false,
        // then the client will not be allowed to login.
        ctx.writeAndFlush(authSuccess);
    }

    /**
     * TODO: Add legit authentication and fix this description.
     * This will eventually be used to check the users credentials against a server database.
     *
     * @param account  The players account name.
     * @param password The players account password.
     * @return True if a successful account/password match. False otherwise.
     */
    private boolean authUser(String account, String password) {
        // do stuff;
        if (account.equalsIgnoreCase("andrew") && password.equals("Brown")) {
            return true;
        } else if (account.equalsIgnoreCase("a") && password.equals("a")) {
            return true;
        }
        return true; //TODO: Return true for private testing. Return false for LIVE server.
    }
}
