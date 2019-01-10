package com.coffeebeans.tritiummonoid.sleepystalker

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class LevelMeter : Fragment() {

    companion object {
        fun newInstance() = LevelMeter()
    }

    private lateinit var viewModel: LevelMeterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.level_meter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LevelMeterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
