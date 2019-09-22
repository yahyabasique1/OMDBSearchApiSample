package com.example.omdbsearchapisample

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import com.example.omdbsearchapisample.fragment.MovieListFragment
import com.example.omdbsearchapisample.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.fragmentcontainer, MovieListFragment())
        transaction.addToBackStack("MovieFragment")
        transaction.commit()
    }

    override fun onBackPressed() {

        if (supportFragmentManager.getBackStackEntryCount() <= 1) {
            super.onBackPressed()
            finishAffinity()
        } else {
            supportFragmentManager.popBackStack();
        }


    }
}
