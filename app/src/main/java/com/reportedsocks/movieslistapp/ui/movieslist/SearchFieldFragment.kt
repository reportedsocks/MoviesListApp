package com.reportedsocks.movieslistapp.ui.movieslist

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.reportedsocks.movieslistapp.R
import com.reportedsocks.movieslistapp.ui.MainViewModel
import com.reportedsocks.movieslistapp.ui.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_search_field.*


class SearchFieldFragment : Fragment(){

    companion object {
        fun newInstance() =
            SearchFieldFragment()
    }
    private lateinit var model: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_field, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProvider(this, ViewModelFactory()).get(MainViewModel::class.java)

        // Perform search request on button click or enter
        searchButton.setOnClickListener( View.OnClickListener {
            val s = searchEditText.text.toString().trim()
            searchMovie( s )
        })

        searchEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val s = searchEditText.text.toString().trim()
                searchMovie( s )
                return@OnKeyListener true
            }
            false
        })
    }

    private fun searchMovie( s: String ){
        if(s.isNotEmpty()){

            //starts API request
            model.loadMovies(s)

            //hides the kbd
            val inputMethodManager =
                activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
    }

}

