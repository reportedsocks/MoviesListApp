package com.reportedsocks.movieslistapp.utils

import android.widget.ListView

class Utils {

    companion object{

        // used to set ListView height
        fun setListViewHeightBasedOnChildren(listView: ListView) {
            val listAdapter = listView.adapter
                ?: // pre-condition
                return
            var totalHeight = 0
            for (i in 0 until listAdapter.count) {
                val listItem = listAdapter.getView(i, null, listView)
                listItem.measure(0, 0)
                totalHeight += listItem.measuredHeight
            }
            val params = listView.layoutParams
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
            listView.layoutParams = params
            listView.requestLayout()
        }
    }
}