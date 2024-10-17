package com.silentbit.sensehubmobile.support

import android.app.Activity
import android.util.TypedValue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Units @Inject constructor() {

    private lateinit var activityReference : Activity

    fun setActivity(activity: Activity){
        activityReference = activity
    }

    fun getDp(dp:Int) : Int{

        val unit = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            activityReference.resources.displayMetrics
        )
        return unit.toInt()
    }

}