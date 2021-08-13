package cn.funcoding.github

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.multidex.MultiDex

private lateinit var INSTANCE: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    override fun attachBaseContext(base: Context?) {
        MultiDex.install(this)
        super.attachBaseContext(base)
    }
}

object AppContext : ContextWrapper(INSTANCE)