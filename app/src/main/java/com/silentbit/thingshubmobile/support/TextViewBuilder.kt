package com.silentbit.thingshubmobile.support

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import javax.inject.Inject

class TextViewBuilder @Inject constructor(){

    fun getTitle(context: Context, text:String, bold:Boolean) : TextView {
        val textView = TextView(context)
        textView.text = text
        textView.textSize = 16f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        if (bold) textView.setTypeface(null, Typeface.BOLD)
        return textView
    }

    fun getSubTitle(context: Context, text:String, bold:Boolean) : TextView {
        val textView = TextView(context)
        textView.text = text
        if (bold) textView.setTypeface(null, Typeface.BOLD)
        return textView
    }

    fun getText(context: Context, text: String, selectable:Boolean) : TextView {
        val textView = TextView(context)
        textView.setTextIsSelectable(selectable)
        textView.text = text
        return textView
    }

}