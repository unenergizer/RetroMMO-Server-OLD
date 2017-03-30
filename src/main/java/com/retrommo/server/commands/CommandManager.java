package com.retrommo.server.commands;

import com.retrommo.server.RetroMmoServer;

import java.util.Scanner;

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
public class CommandManager {

    /**
     * This will start listening to console commands.
     */
    public void start() {
        System.out.println("[Commands] Initializing server commands...");
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equalsIgnoreCase("stop")) {
            input = scanner.next();
        }

        try {
            RetroMmoServer.getInstance().stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
