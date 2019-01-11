package com.coffeebeans.tritiummonoid.sleepystalker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import java.lang.Exception
import java.util.*

class WakeUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wake up"
        val spnWakeUpType = findViewById(R.id.spnWakeUpType) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayOf("natural", "alarm", "other"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnWakeUpType.adapter = adapter
        val btnSave = findViewById(R.id.btnWakeUpSave) as Button
        btnSave.setOnClickListener {
            val value = spnWakeUpType.selectedItem as String
            Log.v("WAKEUP_SAVE", value)
            try {
                WakeUpStore(it.context).save(WakeUpModel(Date(), WakeUpType.valueOf(value)))
                Log.v("WAKEUP_SAVE", "SAVED")
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                finish()
            } catch (e: Exception) {
                Log.e("WAKEUP_SAVE", e.message)
                Toast.makeText(this, "Save error", Toast.LENGTH_LONG).show()
            }
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
