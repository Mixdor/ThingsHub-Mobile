package com.silentbit.sensehubmobile.domain.objs

import java.io.Serializable

data class ObjSensor(
    val id : String,
    var name : String,
    var value : Any?,
    var magnitude : Long,
    var isPercentage : Boolean,
    var isEnableRanges : Boolean,
    var rangeRegular : String,
    var rangeWarning : String,
    val idDevice : String,
    val nameDevice : String
): Serializable
