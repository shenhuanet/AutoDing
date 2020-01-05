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

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { Ding(it.context).ding() }
        iv_go_work.setOnClickListener {
            alarmService.morning()
            Toast.makeText(it.context, "Success", Toast.LENGTH_SHORT).show()
        }
        iv_off_work.setOnClickListener {
            alarmService.night()
            Toast.makeText(it.context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val intent = Intent(applicationContext, AlarmService::class.java)
        applicationContext.bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    private val conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
        }

        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            val serviceBinder = iBinder as AlarmService.AlarmServiceBinder
            alarmService = serviceBinder.service
        }
    }
}
