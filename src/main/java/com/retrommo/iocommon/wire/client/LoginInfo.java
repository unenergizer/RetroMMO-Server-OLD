package com.retrommo.iocommon.wire.client;

import lombok.Getter;

import java.io.Serializable;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown
 * PROGRAMMER: Robert Andrew Brown
 * PROJECT: retrommo-server
 * DATE: 3/26/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 Robert Andrew Brown. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */
@Getter
public class LoginInfo implements Serializable {

    private final String account;
    private final String password;
    private final String version;

    public LoginInfo(String account, String password, String version) {
        this.account = account;
        this.password = password;
        this.version = version;
    }
}
