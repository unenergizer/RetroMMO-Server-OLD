package com.retrommo.iocommon.wire.global;

import com.retrommo.iocommon.enums.ChatChannel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
@Getter
@Setter
public class ChatMessage implements Serializable {
    private ChatChannel target;
    private String message;
}
