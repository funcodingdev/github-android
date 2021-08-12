package cn.funcoding.github.mvp

import android.content.res.Configuration
import android.os.Bundle

interface ILifecycle {
    fun onCreate(savedInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onViewStateRestored(savedInstanceState: Bundle?)

    fun onConfigurationChanged(newConfig: Configuration)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()
}