package com.reportedsocks.movieslistapp.ui.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reportedsocks.movieslistapp.R
import com.reportedsocks.movieslistapp.data.MoviePreview
import com.reportedsocks.movieslistapp.ui.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.movies_list_item.view.*

class MoviesAdapter (val viewModel: MainViewModel) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private var items = listOf<MoviePreview>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movies_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.titleTextView.text = item.Title
        holder.itemView.yearTextView.text = item.Year
        Picasso.get().load(item.Poster).fit().centerInside()
            .placeholder(R.drawable.ic_image_grey_200dp)
            .error(R.drawable.ic_image_grey_200dp)
            .into(holder.itemView.posterImageView)

        holder.itemView.setOnClickListener {
            // fire recyclerView click event
            viewModel.selectItemEvent.value = item
        }

    }

    fun replaceItems(items: List<MoviePreview>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}