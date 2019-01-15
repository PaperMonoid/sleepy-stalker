package com.coffeebeans.tritiummonoid.sleepystalker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class SyncActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Sync"
        val btnSync = findViewById(R.id.btnSync) as Button
        val progressBar = findViewById(R.id.barSync) as ProgressBar
        val txtSleeps = findViewById(R.id.txtSyncValueSleeps) as TextView;
        val txtWakeUps = findViewById(R.id.txtSyncValueWakeUps) as TextView;
        txtSleeps.text = getSleepsCount()
        txtWakeUps.text = getWakeUpsCount()
        progressBar.visibility = View.INVISIBLE;
        btnSync.setOnClickListener {
            SyncService(this).sync(
                Runnable {
                    progressBar.visibility = View.VISIBLE;
                },
                Runnable {
                    progressBar.visibility = View.INVISIBLE;
                    Toast.makeText(this, "SYNCED DATA", Toast.LENGTH_LONG).show()
                    finish()
                }
            )
        }
    }

    fun getSleepsCount(): String {
        try {
            return SleepStore(this).fetchAll().count().toString()
        } catch (e: Exception) {
            return "0"
        }
    }

    fun getWakeUpsCount(): String {
        try {
            return WakeUpStore(this).fetchAll().count().toString()
        } catch (e: Exception) {
            return "0"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return true;
    }
}
