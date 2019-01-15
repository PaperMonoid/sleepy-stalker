package com.coffeebeans.tritiummonoid.sleepystalker.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.coffeebeans.tritiummonoid.sleepystalker.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        val btnSleepF = view.findViewById(R.id.btnSleepF) as Button
        btnSleepF.setOnClickListener {
            startActivity(Intent(view.context, SleepActivity::class.java))
        }
        var btnWakeUpF = view.findViewById(R.id.btnWakeUpF) as Button
        btnWakeUpF.setOnClickListener {
            startActivity(Intent(view.context, WakeUpActivity::class.java))
        }
        var btnSyncF = view.findViewById(R.id.btnSyncF) as Button
        btnSyncF.setOnClickListener {
            startActivity(Intent(view.context, SyncActivity::class.java))
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
