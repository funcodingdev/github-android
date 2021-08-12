package cn.funcoding.github.common.ext

import android.util.Log
import cn.funcoding.github.common.log.TAG
import cn.funcoding.github.common.no
import cn.funcoding.github.common.yes
import java.io.File

fun File.ensureDir(): Boolean {
    try {
        isDirectory.no {
            isFile.yes {
                delete()
            }
            return mkdirs()

        }
    } catch (e: Exception) {
        Log.w(TAG, e.message ?: "")
    }
    return false
}