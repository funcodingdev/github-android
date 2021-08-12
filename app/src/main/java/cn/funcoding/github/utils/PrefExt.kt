package cn.funcoding.github.utils

import cn.funcoding.github.AppContext
import cn.funcoding.github.common.sharepreferences.PreferencesDelegate
import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(default: T) =
    PreferencesDelegate(AppContext, "", default, R::class.jvmName)