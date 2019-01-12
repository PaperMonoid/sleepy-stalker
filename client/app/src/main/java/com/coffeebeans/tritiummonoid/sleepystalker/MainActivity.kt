package com.coffeebeans.tritiummonoid.sleepystalker

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.coffeebeans.tritiummonoid.sleepystalker.ui.main.MainFragment
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onResume() {
        super.onResume()
        SyncService(this).sync(
            Runnable {
                Toast.makeText(this, "SYNCING DATA", Toast.LENGTH_SHORT).show()
            },
            Runnable {
                Toast.makeText(this, "SYNCED DATA", Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id === R.id.settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return true;
    }

}
