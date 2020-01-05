/*
 * Copyright (C) 2019  NeuroTech.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package cn.shenhua.autoding

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*
import kotlin.random.Random


/**
 * Created by shenhua on 2019-12-27.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class AlarmService : Service() {

    private val binder = AlarmServiceBinder()
    private lateinit var alarmManager: AlarmManager

    companion object {
        const val ALARM_ID_AM = 2048
        const val ALARM_ID_PM = 2049
    }

    inner class AlarmServiceBinder : Binder() {
        val service: AlarmService
            get() = this@AlarmService
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun morning() {
        val start = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, Random.nextInt(40, 55))
            set(Calendar.SECOND, Random.nextInt(0, 59))
            set(Calendar.MILLISECOND, 0)
        }
        setAlarmTime(start, ALARM_ID_AM)
    }

    fun night() {
        val end = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 17)
            set(Calendar.MINUTE, Random.nextInt(35, 59))
            set(Calendar.SECOND, Random.nextInt(0, 59))
            set(Calendar.MILLISECOND, 0)
        }
        setAlarmTime(end, ALARM_ID_PM)
    }

    private fun setAlarmTime(calendar: Calendar, alarmId: Int) {
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        intent.action = AlarmReceiver.ALARM_RECEIVER_ACTION
        intent.putExtra("alarmId", alarmId)

        val timeMillis = if (calendar.timeInMillis > System.currentTimeMillis()) {
            calendar.timeInMillis;
        } else {
            calendar.timeInMillis + 86400000;
        }

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, timeMillis, 0, pendingIntent)
        Log.d("shenhuaLog -- " + AlarmService::class.java.simpleName, "set alarm success")
    }
}