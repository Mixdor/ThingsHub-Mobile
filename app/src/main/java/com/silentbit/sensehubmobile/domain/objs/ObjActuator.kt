package com.silentbit.sensehubmobile.domain.objs

import java.io.Serializable

data class ObjActuator(
    val id : String,
    val name : String,
    val type : Int,
    val value : Any?,
    val idDevice : String,
    val nameDevice : String
) : Serializable