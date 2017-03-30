package com.retrommo.server.netty;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*********************************************************************************
 *
 * OWNER: Joseph Rugh
 * PROGRAMMER: Joseph Rugh
 * PROJECT: retrommo-server
 * DATE: 3/25/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 Robert Andrew Brown. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be
 * reproduced, distributed, or transmitted in any form or by any means,
 * including photocopying, recording, or other electronic or mechanical methods,
 * without the prior written permission of the owner.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectType {
    Class<? extends Serializable> getType();
}
