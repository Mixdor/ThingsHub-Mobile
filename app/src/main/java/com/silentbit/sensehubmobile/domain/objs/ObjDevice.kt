package com.silentbit.sensehubmobile.domain.objs

import java.io.Serializable

data class ObjDevice(
    val id : String,
    val name : String,
    val description : String,
    val sensors : Int,
    val actuators : Int
) : Serializable
