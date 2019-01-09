import mysql.connector
from connection import config
from flask import Flask, request

app = Flask("sleepy-stalker")

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/wakeup", methods=["GET", "POST"])
def wakeup():
    if request.method == 'GET':
        return '', 200
    if request.method == 'POST':
        connection = mysql.connector.connect(**config)
        cursor = connection.cursor()
        query = ("SELECT SleepId FROM Sleep ORDER BY SleepTime DESC LIMIT 1")
        cursor.execute(query)
        sleep = cursor.fetchone()
        if sleep is None:
            return '', 500
        add_wake_up = ("INSERT INTO WakeUp"
                     "(SleepId, WakeUpType)"
                     "VALUES(%(id)s, %(type)s)")
        data_wake_up = {
            "id": sleep[0],
            "type": request.json.get("type")
        }
        cursor.execute(add_wake_up, data_wake_up)
        connection.commit()
        cursor.close()
        connection.close()
        if cursor.lastrowid:
           return '', 200
        else:
            return '', 500

@app.route("/sleep", methods=["GET", "POST"])
def sleep():
    if request.method == 'GET':
        return '', 200
    if request.method == 'POST':
        connection = mysql.connector.connect(**config)
        cursor = connection.cursor()
        add_sleep = ("INSERT INTO Sleep"
                     "(Food, Exercise, Stress, Mood)"
                     "VALUES(%(food)s, %(exercise)s, %(stress)s, %(mood)s)")
        data_sleep = request.json
        cursor.execute(add_sleep, data_sleep)
        connection.commit()
        cursor.close()
        connection.close()
        if cursor.lastrowid:
           return '', 200
        else:
            return '', 500

