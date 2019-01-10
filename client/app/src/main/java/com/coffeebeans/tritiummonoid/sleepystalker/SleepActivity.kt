package com.coffeebeans.tritiummonoid.sleepystalker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem


class SleepActivity : AppCompatActivity(), SleepExercise.OnFragmentInteractionListener,
    SleepFood.OnFragmentInteractionListener, SleepMood.OnFragmentInteractionListener,
    SleepStress.OnFragmentInteractionListener {

    override fun onFoodChange(food: Int) {
        Log.v("FOOD", food.toString())
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Go to sleep"
        val pager = findViewById(R.id.pagerSleep) as ViewPager
        pager.adapter = SleepPagerAdapter(applicationContext, supportFragmentManager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return true;
    }
}
