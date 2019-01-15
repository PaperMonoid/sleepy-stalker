package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.os.Environment
import java.io.File
import java.lang.StringBuilder
import java.util.*

class SleepStore(val context: Context) {

    private fun String.fromCsv(): List<SleepModel> {
        val data = this.split(",")
        if (data.size == 5) {
            return listOf(
                SleepModel(
                    Date(data[0].toLong()), Level.valueOf(data[1]), Level.valueOf(data[2]),
                    Level.valueOf(data[3]), Mood.valueOf(data[4])
                )
            )
        }
        return listOf()
    }

    private fun SleepModel.toCsv(): String {
        return StringBuilder()
            .append(datetime.time).append(",")
            .append(food.name).append(",")
            .append(stress.name).append(",")
            .append(exercise.name).append(",")
            .append(mood.name)
            .toString()
    }

    fun save(sleepModel: SleepModel) {
        val file = File(context.getExternalFilesDir("Documents"), "sleep.csv")
        if (!file.exists()) {
            file.writeText(sleepModel.toCsv() + "\n")
        } else {
            file.appendText(sleepModel.toCsv() + "\n")
        }
    }

    fun saveAll(sleepModels: List<SleepModel>) {
        val file = File(context.getExternalFilesDir("Documents"), "sleep.csv")
        val text = sleepModels.fold(StringBuilder()) { builder, sleepModel -> builder.append(sleepModel.toCsv()).append("\n")}.toString()
        file.writeText(text)
    }

    fun fetchAll(): List<SleepModel> {
        val file = File(context.getExternalFilesDir("Documents"), "sleep.csv")
        if (file.exists()) {
            return file.readLines().flatMap { line -> line.fromCsv() }
        }
        return listOf()
    }
}