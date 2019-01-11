package com.coffeebeans.tritiummonoid.sleepystalker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.util.Consumer
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast

class SleepActivity : AppCompatActivity(), SleepExercise.OnFragmentInteractionListener,
    SleepFood.OnFragmentInteractionListener, SleepMood.OnFragmentInteractionListener,
    SleepStress.OnFragmentInteractionListener, SleepSave.OnFragmentInteractionListener {

    private lateinit var viewPager: ViewPager
    private lateinit var sleepModel: SleepModel
    private var sleepModelConsumer: Consumer<SleepModel>? = null

    override fun onFoodChange(food: Level) {
        Log.v("FOOD", food.name)
        sleepModel.food = food
        sleepModelConsumer?.accept(sleepModel)
        viewPager.currentItem = 1
    }

    override fun onStressChange(stress: Level) {
        Log.v("STRESS", stress.name)
        sleepModel.stress = stress
        sleepModelConsumer?.accept(sleepModel)
        viewPager.currentItem = 2
    }

    override fun onExerciseChange(exercise: Level) {
        Log.v("EXERCISE", exercise.name)
        sleepModel.exercise = exercise
        sleepModelConsumer?.accept(sleepModel)
        viewPager.currentItem = 3
    }

    override fun onMoodChange(mood: Mood) {
        Log.v("MOOD", mood.name)
        sleepModel.mood = mood
        sleepModelConsumer?.accept(sleepModel)
        viewPager.currentItem = 4
    }

    override fun setSleepModelConsumer(consumer: Consumer<SleepModel>?) {
        Log.v("SLEEP_SAVE", "SET CONSUMER")
        this.sleepModelConsumer = consumer
        sleepModelConsumer?.accept(sleepModel)
    }

    override fun onSaveSuccess() {
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onSaveError() {
        Toast.makeText(this, "Save error", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Go to sleep"
        viewPager = findViewById(R.id.pagerSleep) as ViewPager
        viewPager.adapter = SleepPagerAdapter(applicationContext, supportFragmentManager)
        val seekBar = findViewById(R.id.sbSleepBottom) as SeekBar
        val textView = findViewById(R.id.txtSleepBottom) as TextView
        val listener = SeekBarViewPagerListener(seekBar, viewPager, textView)
        seekBar.setOnSeekBarChangeListener(listener)
        viewPager.addOnPageChangeListener(listener)
        sleepModel = SleepModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            when (viewPager.currentItem) {
                0 -> finish()
                1 -> viewPager.currentItem = 0
                2 -> viewPager.currentItem = 1
                3 -> viewPager.currentItem = 2
                4 -> viewPager.currentItem = 3
            }
        }
        return true;
    }
}

class SeekBarViewPagerListener(val seekBar: SeekBar, val viewPager: ViewPager, val textView: TextView): ViewPager.OnPageChangeListener, SeekBar.OnSeekBarChangeListener {

    override fun onPageSelected(page: Int) {
        if (seekBar.progress != page) {
            seekBar.progress = page
            textView.text = "Step " + (page + 1).toString()
        }
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (viewPager.currentItem != progress) {
            viewPager.currentItem = progress
            textView.text = "Step " + (progress + 1).toString()
        }
    }

}