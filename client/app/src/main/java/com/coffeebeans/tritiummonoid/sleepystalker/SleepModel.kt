package com.coffeebeans.tritiummonoid.sleepystalker

import java.lang.StringBuilder
import java.text.DateFormat
import java.util.Date

class SleepModel(var datetime: Date = Date(), var food: Level = Level.low, var stress: Level = Level.low,
                 var exercise: Level = Level.low, var mood: Mood = Mood.happy) {

    private fun datetimeOf(datetime: Date): String {
        return DateFormat.getInstance().format(datetime)
    }

    fun toJson(): String {
        return StringBuilder("{")
            .append("\"time\":\"").append(datetimeOf(datetime)).append("\",")
            .append("\"food\":\"").append(food.name).append("\",")
            .append("\"stress\":\"").append(stress.name).append("\",")
            .append("\"exercise\":\"").append(exercise.name).append("\",")
            .append("\"mood\":\"").append(mood.name).append("\"")
            .append("}")
            .toString()
    }
}