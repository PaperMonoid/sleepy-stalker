package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.os.Environment
import java.io.File
import java.lang.StringBuilder
import java.util.*

class WakeUpStore(val context: Context) {

    private fun fromCsv(line: String): List<WakeUpModel> {
        val data = line.split(",")
        if (data.size == 2) {
            return listOf(
                WakeUpModel(
                    Date(data[0].toLong()), WakeUpType.valueOf(data[1])
                )
            )
        }
        return listOf()
    }

    private fun toCsv(model: WakeUpModel): String {
        return StringBuilder()
            .append(model.datetime.time).append(",")
            .append(model.wakeUpType.name)
            .toString()
    }

    fun save(wakeUpModel: WakeUpModel) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "wakeup.csv")
        if (!file.exists()) {
            file.writeText(toCsv(wakeUpModel) + "\n")
        } else {
            file.appendText(toCsv(wakeUpModel) + "\n")
        }
    }

    fun saveAll(wakeUpModels: List<WakeUpModel>) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "wakeup.csv")
        val text = wakeUpModels.fold(StringBuilder()) { builder, wakeUpModel -> builder.append(toCsv(wakeUpModel)).append("\n")}.toString()
        file.writeText(text)
    }

    fun fetchAll(): List<WakeUpModel> {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "wakeup.csv")
        if (file.exists()) {
            return file.readLines().flatMap { line -> fromCsv(line) }
        }
        return listOf()
    }
}