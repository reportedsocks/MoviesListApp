package com.reportedsocks.movieslistapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.reportedsocks.movieslistapp.ui.MainFragment
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        //set init toolbar parameters
        val toolbar = toolbar
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.title = "MoviesListApp"



    }

}
