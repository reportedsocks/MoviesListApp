package com.reportedsocks.movieslistapp.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reportedsocks.movieslistapp.R

class WrapperFragment : Fragment() {

    companion object {
        fun newInstance() =
            WrapperFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // update toolbar parameters to init when user leaves MovieDetailsFragment
        val toolbar = activity!!.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(null)
        toolbar.title = "MoviesListApp"

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wrapper, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}