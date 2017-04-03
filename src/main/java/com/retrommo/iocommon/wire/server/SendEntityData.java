package com.retrommo.iocommon.wire.server;

import com.retrommo.iocommon.enums.EntityTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: retrommo-server
 * DATE: 3/31/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */
@AllArgsConstructor
@Getter
public class SendEntityData implements Serializable {
    private int serverEntityId;
    private EntityTypes entityType;
    private float x, y;
}
