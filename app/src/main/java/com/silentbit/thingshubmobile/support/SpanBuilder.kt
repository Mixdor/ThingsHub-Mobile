package com.silentbit.thingshubmobile.support

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.text.toSpannable
import javax.inject.Inject

class SpanBuilder @Inject constructor() {

    fun setSpanBold(textView: TextView, target:String){

        val text = textView.text
        val sequence = Regex(target).findAll(text)

        val spannableFactory = object : Spannable.Factory() {
            override fun newSpannable(source: CharSequence?): Spannable {
                val spannable = source!!.toSpannable()

                for (result in sequence){

                    val start = result.range.first
                    val end = result.range.last + 1

                    spannable.setSpan(
                        StyleSpan(Typeface.BOLD),
                        start,
                        end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                return spannable
            }
        }
        textView.setSpannableFactory(spannableFactory)
        textView.setText(text, TextView.BufferType.SPANNABLE)
    }

    fun setSpanBold(textView: TextView, targets:List<String>){

        val text = textView.text

        val spannableFactory = object : Spannable.Factory() {
            override fun newSpannable(source: CharSequence?): Spannable {
                val spannable = source!!.toSpannable()

                for(target in targets){
                    val sequence = Regex(target).findAll(text)

                    for (result in sequence){

                        val start = result.range.first
                        val end = result.range.last + 1

                        spannable.setSpan(
                            StyleSpan(Typeface.BOLD),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
                return spannable
            }
        }
        textView.setSpannableFactory(spannableFactory)
        textView.setText(text, TextView.BufferType.SPANNABLE)
    }

    fun getSpannable(message: String, target: String) :Spannable{

        val sequence = Regex(target).findAll(message)
        val spanString = SpannableString(message)

        for (result in sequence){
            val start = result.range.first
            val end = result.range.last + 1

            spanString.setSpan(
                StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spanString
    }

    fun getSpannable(message: String, targets: List<String>) :Spannable{

        val spanString = SpannableString(message)

        for (target in targets){
            val sequence = Regex(target).findAll(message)

            for (result in sequence){
                val start = result.range.first
                val end = result.range.last + 1

                spanString.setSpan(
                    StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spanString
    }

}