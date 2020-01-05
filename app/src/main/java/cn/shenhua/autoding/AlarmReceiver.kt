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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * 闹钟广播接收类，系统启动广播接收类，保证{@link AlarmService}正常运行
 *
 * Created by shenhua on 2019-12-27.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        /**
         * 特别注意，本action与manifest中申明等android:action务必保持一致，否则无法接收到闹钟触发广播。
         * 此问题通常出现在更改本类所在包名后产生
         */
        const val ALARM_RECEIVER_ACTION = "cn.shenhua.autoding.AlarmReceiver"
        const val ALARM_RECEIVER_ACTION_BOOT = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let {
            if ((ALARM_RECEIVER_ACTION_BOOT.equals(intent.action))) {
                context.startService(Intent(context, AlarmService::class.java))
            }
            if (ALARM_RECEIVER_ACTION.equals(intent.action)) {
                Ding(context).ding()
            }
        }
    }
}