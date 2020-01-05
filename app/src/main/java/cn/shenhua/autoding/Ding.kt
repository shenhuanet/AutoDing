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

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by shenhua on 2019-12-27.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Ding(private val context: Context) {

    fun ding() {

        Screen(context.applicationContext).wakeup()

        try {
            val manager = context.packageManager
            context.startActivity(manager.getLaunchIntentForPackage("com.alibaba.android.rimet"))
            Log.d("shenhuaLog -- " + AlarmReceiver::class.java.simpleName, "ding")
        } catch (e: Exception) {
            Toast.makeText(context.applicationContext, "未安装", Toast.LENGTH_SHORT).show()
        }
    }
}