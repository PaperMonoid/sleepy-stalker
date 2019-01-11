package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.os.Environment
import java.io.File
import java.lang.StringBuilder
import java.util.*

class SleepStore(val context: Context) {

    private fun fromCsv(line: String): List<SleepModel> {
        val data = line.split(",")
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

    private fun toCsv(model: SleepModel): String {
        return StringBuilder()
            .append(model.datetime.time).append(",")
            .append(model.food.name).append(",")
            .append(model.stress.name).append(",")
            .append(model.exercise.name).append(",")
            .append(model.mood.name)
            .toString()
    }

    fun save(sleepModel: SleepModel) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sleep.csv")
        if (!file.exists()) {
            file.writeText(toCsv(sleepModel) + "\n")
        } else {
            file.appendText(toCsv(sleepModel) + "\n")
        }
    }

    fun saveAll(sleepModels: List<SleepModel>) {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sleep.csv")
        val text = sleepModels.fold(StringBuilder()) { builder, sleepModel -> builder.append(toCsv(sleepModel)).append("\n")}.toString()
        file.writeText(text)
    }

    fun fetchAll(): List<SleepModel> {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "sleep.csv")
        if (file.exists()) {
            return file.readLines().flatMap { line -> fromCsv(line) }
        }
        return listOf()
    }
}