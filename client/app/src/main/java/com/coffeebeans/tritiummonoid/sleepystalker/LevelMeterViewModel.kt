package com.coffeebeans.tritiummonoid.sleepystalker

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;

class LevelMeterViewModel() : ViewModel() {
    private lateinit var level: MutableLiveData<Int>
}
