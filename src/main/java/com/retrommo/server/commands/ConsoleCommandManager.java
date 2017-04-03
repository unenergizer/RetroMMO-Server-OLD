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
public class ConsoleCommandManager implements Runnable {

    /**
     * This will start listening to console commands.
     */
    public void start() {
        System.out.println("[ConsoleCommands] Initializing server commands...");
        new Thread(this, "ConsoleCommandManager").start();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equalsIgnoreCase("stop")) {
            input = scanner.next();
            RetroMmoServer.getInstance().getCommandProcessor().runListeners(input, null);
        }

        RetroMmoServer.getInstance().stop();
    }
}
