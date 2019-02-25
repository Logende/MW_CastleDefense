package org.neubauerfelix.manawars.manawars.enums


enum class MWArmorHolder(val collisionTypes: Iterable<MWCollisionType>) { //Keep those as simple as possible!

    HUMAN_HEAD(arrayListOf(MWCollisionType.HUMAN_HEAD)),
    HUMAN_BODY(arrayListOf(MWCollisionType.HUMAN_ARM, MWCollisionType.HUMAN_BODY, MWCollisionType.HUMAN_FOOT)),
    MOUNT(arrayListOf(MWCollisionType.MOUNT_BODY, MWCollisionType.MOUNT_HEAD, MWCollisionType.MOUNT_FOOT));



}