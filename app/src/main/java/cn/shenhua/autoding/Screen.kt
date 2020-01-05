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

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.os.PowerManager

/**
 * Created by shenhua on 2020-01-04.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Screen(private val context: Context) {

    @SuppressLint("InvalidWakeLockTag")
    fun wakeup() {
        (context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).apply {
            val kl = newKeyguardLock("unLock")
            kl.disableKeyguard()
        }
        (context.getSystemService(Context.POWER_SERVICE) as PowerManager).apply {
            val wl = newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP xor PowerManager.SCREEN_DIM_WAKE_LOCK,
                "bright"
            )
            wl.acquire(10 * 60 * 1000L /*10 minutes*/)
            wl.release()
        }
    }
}