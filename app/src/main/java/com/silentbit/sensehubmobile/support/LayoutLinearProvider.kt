package com.silentbit.sensehubmobile.support

import android.widget.LinearLayout
import javax.inject.Inject

class LayoutLinearProvider @Inject constructor(
    private val units: Units
){

    fun getMatchWrap(left:Int,top:Int,right:Int,bottom:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(
            units.getDp(left),
            units.getDp(top),
            units.getDp(right),
            units.getDp(bottom)
        )
        return layout
    }

    fun getMatchWrap(horizontal:Int, vertical:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(
            units.getDp(horizontal),
            units.getDp(vertical),
            units.getDp(horizontal),
            units.getDp(vertical)
        )

        return layout
    }

    fun getMatchWrap(margins:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins)
        )
        return layout
    }

    fun getWrapMatch(left:Int,top:Int,right:Int,bottom:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layout.setMargins(
            units.getDp(left),
            units.getDp(top),
            units.getDp(right),
            units.getDp(bottom)
        )
        return layout
    }

    fun getWrapMatch(horizontal:Int, vertical:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layout.setMargins(
            units.getDp(horizontal),
            units.getDp(vertical),
            units.getDp(horizontal),
            units.getDp(vertical)
        )
        return layout
    }

    fun getWrapMatch(margins:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layout.setMargins(
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins)
        )
        return layout
    }

    fun getMatches(left:Int,top:Int,right:Int,bottom:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layout.setMargins(
            units.getDp(left),
            units.getDp(top),
            units.getDp(right),
            units.getDp(bottom)
        )
        return layout
    }

    fun getMatches(horizontal:Int, vertical:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layout.setMargins(
            units.getDp(horizontal),
            units.getDp(vertical),
            units.getDp(horizontal),
            units.getDp(vertical)
        )
        return layout
    }

    fun getMatches(margins:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layout.setMargins(
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins)
        )
        return layout
    }

    fun getWraps(left:Int,top:Int,right:Int,bottom:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(
            units.getDp(left),
            units.getDp(top),
            units.getDp(right),
            units.getDp(bottom)
        )

        return layout
    }

    fun getWraps(horizontal:Int, vertical:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(
            units.getDp(horizontal),
            units.getDp(vertical),
            units.getDp(horizontal),
            units.getDp(vertical)
        )
        return layout
    }

    fun getWraps(margins:Int) : LinearLayout.LayoutParams{

        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins),
            units.getDp(margins)
        )
        return layout
    }

}