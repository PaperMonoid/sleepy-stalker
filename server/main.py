import mysql.connector
from connection import config
from flask import Flask, request
from datetime import datetime

app = Flask("sleepy-stalker")

@app.route("/up", methods=["GET"])
def up():
    return "", 200

@app.route("/wakeup", methods=["GET", "POST"])
def wakeup():
    if request.method == 'GET':
        return '', 200
    if request.method == 'POST':
        try:
            connection = mysql.connector.connect(**config)
            cursor = connection.cursor()
            add_wake_up = ("INSERT INTO WakeUp"
                           "(WakeUpTime, WakeUpReason)"
                           "VALUES(%(time)s, %(reason)s)")
            data_wake_up = {
                "time": (request.json.get("time") or
                         datetime.now().strftime("%Y-%m-%d %H:%M:%S")),
                "reason": request.json.get("reason") or "natural"
            }
            cursor.execute(add_wake_up, data_wake_up)
            connection.commit()
            cursor.close()
            connection.close()
            return '', 200
        except:
            return '', 500

@app.route("/sleep", methods=["GET", "POST"])
def sleep():
    if request.method == 'GET':
        return '', 200
    if request.method == 'POST':
        try:
            connection = mysql.connector.connect(**config)
            cursor = connection.cursor()
            add_sleep = ("INSERT INTO Sleep"
                         "(SleepTime, Food, Exercise, Stress, Mood)"
                         "VALUES(%(time)s, %(food)s, %(exercise)s, %(stress)s, %(mood)s)")
            data_sleep = {
                "time": (request.json.get("time") or
                         datetime.now().strftime("%Y-%m-%d %H:%M:%S")),
                "food": request.json.get("food") or "low",
                "exercise": request.json.get("exercise") or "low",
                "stress": request.json.get("stress") or "low",
                "mood": request.json.get("mood") or "happy"
            }
            cursor.execute(add_sleep, data_sleep)
            connection.commit()
            cursor.close()
            connection.close()
            return '', 200
        except:
            return '', 500

