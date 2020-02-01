package com.reportedsocks.movieslistapp.ui.moviedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.reportedsocks.movieslistapp.R
import com.reportedsocks.movieslistapp.data.Rating

class ReviewListAdapter(
    context: Context,
    objects: List<Rating?>
) :
    ArrayAdapter<Rating?>(context, 0, objects) {
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val movieReview: Rating? = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.movie_ratings_list_item, parent, false)
        }
        val reviewSourceTextView =
            convertView!!.findViewById<TextView>(R.id.reviewSourceTextView)
        val reviewMarkTextView =
            convertView.findViewById<TextView>(R.id.reviewMarkTextView)
        reviewSourceTextView.setText(movieReview?.Source)
        reviewMarkTextView.setText(movieReview?.Value)
        return convertView
    }
}