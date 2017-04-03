package com.retrommo.server.commands.listeners;

import com.retrommo.server.commands.Command;
import com.retrommo.server.commands.CommandListener;
import io.netty.channel.Channel;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: retrommo-server
 * DATE: 3/29/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class AdminCommand implements CommandListener {

    @Command(getCommands = {"/admin", "/administrator"})
    public void onAdminCmd(Channel playerChannel) {
        System.out.println("this is a fucking admin command");
    }
}
