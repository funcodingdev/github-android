package cn.funcoding.github.view

import android.os.Bundle
import cn.funcoding.github.R
import cn.funcoding.github.mvp.impl.BaseActivity
import cn.funcoding.github.presenter.LoginPresenter

class LoginActivity : BaseActivity<LoginPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}