package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.os.Environment
import java.io.File
import java.lang.StringBuilder
import java.util.*

class WakeUpStore(val context: Context) {

    private fun String.fromCsv(): List<WakeUpModel> {
        val data = this.split(",")
        if (data.size == 2) {
            return listOf(
                WakeUpModel(
                    Date(data[0].toLong()), WakeUpReason.valueOf(data[1])
                )
            )
        }
        return listOf()
    }

    private fun WakeUpModel.toCsv(): String {
        return StringBuilder()
            .append(datetime.time).append(",")
            .append(reason.name)
            .toString()
    }

    fun save(wakeUpModel: WakeUpModel) {
        val file = File(context.getExternalFilesDir("Documents"), "wakeup.csv")
        if (!file.exists()) {
            file.writeText(wakeUpModel.toCsv() + "\n")
        } else {
            file.appendText(wakeUpModel.toCsv() + "\n")
        }
    }

    fun saveAll(wakeUpModels: List<WakeUpModel>) {
        val file = File(context.getExternalFilesDir("Documents"), "wakeup.csv")
        val text = wakeUpModels.fold(StringBuilder()) { builder, wakeUpModel -> builder.append(wakeUpModel.toCsv()).append("\n")}.toString()
        file.writeText(text)
    }

    fun fetchAll(): List<WakeUpModel> {
        val file = File(context.getExternalFilesDir("Documents"), "wakeup.csv")
        if (file.exists()) {
            return file.readLines().flatMap { line -> line.fromCsv() }
        }
        return listOf()
    }
}