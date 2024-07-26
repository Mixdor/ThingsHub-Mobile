package com.silentbit.thingshubmobile.domain.objs

import java.io.Serializable

data class ObjActuator(
    val id : String,
    val name : String,
    val type : String,
    val value : Any?,
    val idDevice : String,
    val nameDevice : String
) : Serializable