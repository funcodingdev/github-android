package cn.funcoding.github.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import cn.funcoding.github.mvp.IMvpView
import cn.funcoding.github.mvp.IPresenter

abstract class BasePresenter<out View : IMvpView<BasePresenter<View>>> : IPresenter<View> {
    override lateinit var view: @UnsafeVariance View

    override fun onCreate(savedInstanceState: Bundle?) = Unit

    override fun onSaveInstanceState(outState: Bundle) = Unit

    override fun onViewStateRestored(savedInstanceState: Bundle?) = Unit

    override fun onConfigurationChanged(newConfig: Configuration) = Unit

    override fun onStart() = Unit

    override fun onResume() = Unit

    override fun onPause() = Unit

    override fun onStop() = Unit

    override fun onDestroy() = Unit
}