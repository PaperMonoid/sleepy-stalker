package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SyncService(context: Context) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val sleepStore: SleepStore = SleepStore(context)
    private val wakeUpStore: WakeUpStore = WakeUpStore(context)
    private val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    fun sync(onStart: Runnable, onFinish: Runnable) {
        val name: String = sharedPreferences.getString(SettingsActivity.USERNAME, "")!!
        Log.v("PREFERENCE", "sync on => " + name.equals("").not().toString())
        if (name.equals("").not()) {
            Log.v("SYNC", "sync on => starting...")
            val host = sharedPreferences.getString(SettingsActivity.HOST, "http://localhost:5000")!!
            Log.v("SYNC", "sync on => host: " + host)
            val executor = Executors.newFixedThreadPool(2)
            if (executor.submit<Boolean> { isUp(host) }.get(10, TimeUnit.SECONDS)) {
                onStart.run()
                Log.v("SYNC", "host up => syncing...")
                val sleepModelsReminders: List<SleepModel> = sleepStore.fetchAll()
                    .map { executor.submit<List<SleepModel>> { it.sync(host, name) } }
                    .fold(listOf()) { list, future -> list.plus(future.get()) }
                try {
                    sleepStore.saveAll(sleepModelsReminders)
                    Log.v("SYNC", "sync sleep => done with " + sleepModelsReminders.size.toString() + "reminders")
                } catch (e: Exception) {
                    Log.e("SYNC", "sync sleep => ERROR")
                }
                val wakeUpModelsReminders: List<WakeUpModel> = wakeUpStore.fetchAll()
                    .map { executor.submit<List<WakeUpModel>> { it.sync(host, name) } }
                    .fold(listOf()) { list, future -> list.plus(future.get()) }
                try {
                    wakeUpStore.saveAll(wakeUpModelsReminders)
                    Log.v("SYNC", "sync wakeup => done with " + wakeUpModelsReminders.size.toString() + "reminders")
                } catch (e: Exception) {
                    Log.e("SYNC", "sync wakeup => ERROR")
                }
                onFinish.run()
            }
            executor?.shutdown()
        }
    }

    fun isUp(host: String): Boolean {
        val url: String = StringBuilder().append(host).append("/up").toString()
        val error: FuelError? = url.httpGet().response().third.component2()
        return error == null
    }

    fun SleepModel.sync(host: String, name: String): List<SleepModel> {
        val url: String = StringBuilder().append(host).append("/sleep").toString()
        val error: FuelError? = url.httpPost().jsonBody(toJson(name)).response().third.component2()
        if (error == null) {
            return listOf()
        } else {
            return listOf(this)
        }
    }

    fun WakeUpModel.sync(host: String, name: String): List<WakeUpModel> {
        val url: String = StringBuilder().append(host).append("/wakeup").toString()
        val error: FuelError? = url.httpPost().jsonBody(toJson(name)).response().third.component2()
        if (error == null) {
            return listOf()
        } else {
            return listOf(this)
        }
    }

    private fun Date.format(): String {
        return formatter.format(this)
    }

    private fun SleepModel.toJson(name: String): String {
        return StringBuilder("{")
            .append("\"name\":\"").append(name).append("\",")
            .append("\"time\":\"").append(datetime.format()).append("\",")
            .append("\"food\":\"").append(food.name).append("\",")
            .append("\"stress\":\"").append(stress.name).append("\",")
            .append("\"exercise\":\"").append(exercise.name).append("\",")
            .append("\"mood\":\"").append(mood.name).append("\"")
            .append("}")
            .toString()
    }

    private fun WakeUpModel.toJson(name: String): String {
        return StringBuilder("{")
            .append("\"name\":\"").append(name).append("\",")
            .append("\"time\":\"").append(datetime.format()).append("\",")
            .append("\"reason\":\"").append(reason.name).append("\"")
            .append("}")
            .toString()
    }
}
