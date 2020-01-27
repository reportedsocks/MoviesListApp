package com.reportedsocks.restaurantlistapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.reportedsocks.restaurantlistapp.R
import com.reportedsocks.restaurantlistapp.data.RestaurantPreview
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_restaurant_list.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.*


class RestaurantListFragment : Fragment() {

    companion object {
        fun newInstance() =RestaurantListFragment()
    }


    private lateinit var adapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val model: MainViewModel = ViewModelProvider(this).get<MainViewModel>(MainViewModel::class.java)

        model.getRestaurants()?.observe(viewLifecycleOwner, Observer<List<RestaurantPreview>>{ users ->
            // update UI
            adapter = UserAdapter(model)
            adapter.replaceItems(users)
            list.adapter = adapter
            progressBar.visibility = View.GONE
        })

        model.selectItemEvent.observe(viewLifecycleOwner, Observer{
            if (it != null) {
                val bundle = Bundle(0)
                bundle.putString("id", it.id)
                val fragment = RestaurantProfile.newInstance()
                fragment.setArguments(bundle)
                val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })
    }

}

class UserAdapter (val viewModel: MainViewModel) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var items = listOf<RestaurantPreview>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.nameTextView.text = item.name
        Picasso.get().load(item.img).fit().centerCrop()
            .placeholder(R.drawable.ic_image_grey_200dp)
            .error(R.drawable.ic_image_grey_200dp)
            .into(holder.itemView.restImageView)

        holder.itemView.setOnClickListener {
            // fire recyclerView click event
            viewModel.selectItemEvent.value = item
        }

    }

    fun replaceItems(items: List<RestaurantPreview>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}
