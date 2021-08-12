package cn.funcoding.github.common

import android.content.Context
import java.lang.IllegalArgumentException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreferences属性代理
 * by: 关键字
 */
class PreferencesDelegate<T>(
    private val context: Context,
    private val key: String,
    private val defValue: T,
    private val prefName: String = "default"
) : ReadWriteProperty<Any?, T> {
    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreferences(key)
    }

    private fun findPreferences(key: String): T {
        return when (defValue) {
            is Long -> prefs.getLong(key, defValue)
            is Int -> prefs.getInt(key, defValue)
            is String -> prefs.getString(key, defValue)
            is Float -> prefs.getFloat(key, defValue)
            is Boolean -> prefs.getBoolean(key, defValue)
            else ->
                throw IllegalArgumentException("类型错误")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreferences(key, value)
    }

    private fun putPreferences(key: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else ->
                    throw IllegalArgumentException("类型错误")
            }
            apply()
        }
    }
}