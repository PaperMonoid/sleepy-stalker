package com.coffeebeans.tritiummonoid.sleepystalker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

class WakeUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wake up"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        return true;
    }
}
