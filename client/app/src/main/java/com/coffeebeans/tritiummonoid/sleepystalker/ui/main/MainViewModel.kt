package com.coffeebeans.tritiummonoid.sleepystalker.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val selected = MutableLiveData<String>()
}
