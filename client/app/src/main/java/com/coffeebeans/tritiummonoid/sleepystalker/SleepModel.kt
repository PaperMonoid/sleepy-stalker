package com.coffeebeans.tritiummonoid.sleepystalker

import java.util.Date

class SleepModel(var datetime: Date = Date(), var food: Level = Level.low, var stress: Level = Level.low,
                 var exercise: Level = Level.low, var mood: Mood = Mood.happy) {}