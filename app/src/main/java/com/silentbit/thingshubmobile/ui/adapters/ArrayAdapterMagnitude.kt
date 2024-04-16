package com.silentbit.thingshubmobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import com.silentbit.thingshubmobile.R
import com.silentbit.thingshubmobile.domain.objs.ObjMagnitude


class ArrayAdapterMagnitude(
    private val context: Context,
    private val magnitudes : List<ObjMagnitude>
) : ArrayAdapter<ObjMagnitude>(context, 0, magnitudes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_magnitudes, parent, false)
        }

        val textView : TextView = view!!.findViewById(R.id.textView8)
        val imageView : ImageView = view.findViewById(R.id.imageView)

        val item : ObjMagnitude = magnitudes[position]

        textView.text = item.name
        imageView.setImageResource(item.idImage)

        return view
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    private val listFilter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            return FilterResults()
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

        }

        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as ObjMagnitude).name
        }
    }

}
