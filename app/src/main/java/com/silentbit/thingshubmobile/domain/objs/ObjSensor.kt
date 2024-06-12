package com.silentbit.thingshubmobile.domain.objs

import java.io.Serializable

data class ObjSensor(
    val id : String,
    var name : String,
    var value : Long,
    var magnitude : Long,
    var isPercentage : Boolean,
    var isEnableRanges : Boolean,
    var rangeRegular : String,
    var rangeWarning : String,
    val idDevice : String,
    val nameDevice : String
): Serializable
