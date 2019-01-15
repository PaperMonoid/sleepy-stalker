package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SleepPagerAdapter(val context: Context, val fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return Fragment.instantiate(context, SleepFood::class.java.name)
            1 -> return Fragment.instantiate(context, SleepStress::class.java.name)
            2 -> return Fragment.instantiate(context, SleepExercise::class.java.name)
            3 -> return Fragment.instantiate(context, SleepMood::class.java.name)
            4 -> return Fragment.instantiate(context, SleepSave::class.java.name)
            else -> return Fragment.instantiate(context, SleepFood::class.java.name)
        }
    }

    override fun getCount(): Int {
        return 5
    }

}