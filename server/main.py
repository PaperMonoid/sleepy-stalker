import mysql.connector
from connection import config
from flask import Flask, request
from datetime import datetime

app = Flask("sleepy-stalker")


@app.route("/up", methods=["GET"])
def up():
    return "", 200


@app.route("/user", methods=["GET", "POST"])
def user():
    if request.method == "GET":
        return "", 501
    if request.method == "POST":
        try:
            connection = mysql.connector.connect(**config)
            cursor = connection.cursor()
            insert_user = "INSERT INTO SleepyUser(SleepyUserName) VALUES(%(name)s)"
            data_user = {"name": request.json.get("name")}
            cursor.execute(insert_user, data_user)
            connection.commit()
            cursor.close()
            connection.close()
            return "", 200
        except:
            return "", 500


@app.route("/wakeup", methods=["GET", "POST"])
def wakeup():
    if request.method == "GET":
        return "", 501
    if request.method == "POST":
        try:
            connection = mysql.connector.connect(**config)
            cursor = connection.cursor()
            search_user = (
                "SELECT SleepyUserId FROM SleepyUser WHERE SleepyUserName = %(name)s"
            )
            data_user = {"name": request.json.get("name")}
            cursor.execute(search_user, data_user)
            user_id = cursor.fetchone()
            if user_id is None:
                return 500, ""
            add_wake_up = (
                "INSERT INTO WakeUp"
                "(SleepyUserId, WakeUpTime, WakeUpReason)"
                "VALUES(%(userId)s, %(time)s, %(reason)s)"
            )
            data_wake_up = {
                "userId": user_id[0],
                "time": (
                    request.json.get("time")
                    or datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                ),
                "reason": request.json.get("reason") or "natural",
            }
            cursor.execute(add_wake_up, data_wake_up)
            connection.commit()
            cursor.close()
            connection.close()
            return "", 200
        except:
            return "", 500


@app.route("/sleep", methods=["GET", "POST"])
def sleep():
    if request.method == "GET":
        return "", 501
    if request.method == "POST":
        try:
            connection = mysql.connector.connect(**config)
            cursor = connection.cursor()
            search_user = (
                "SELECT SleepyUserId FROM SleepyUser WHERE SleepyUserName = %(name)s"
            )
            data_user = {"name": request.json.get("name")}
            cursor.execute(search_user, data_user)
            user_id = cursor.fetchone()
            if user_id is None:
                return 500, ""
            add_sleep = (
                "INSERT INTO Sleep"
                "(SleepyUserId, SleepTime, Food, Exercise, Stress, Mood)"
                "VALUES(%(userId)s, %(time)s, %(food)s, %(exercise)s, %(stress)s, %(mood)s)"
            )
            data_sleep = {
                "userId": user_id[0],
                "time": (
                    request.json.get("time")
                    or datetime.now().strftime("%Y-%m-%d %H:%M:%S")
                ),
                "food": request.json.get("food") or "low",
                "exercise": request.json.get("exercise") or "low",
                "stress": request.json.get("stress") or "low",
                "mood": request.json.get("mood") or "happy",
            }
            cursor.execute(add_sleep, data_sleep)
            connection.commit()
            cursor.close()
            connection.close()
            return "", 200
        except:
            return "", 500
