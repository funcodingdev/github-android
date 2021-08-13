package cn.funcoding.github.presenter

import cn.funcoding.github.BuildConfig
import cn.funcoding.github.model.account.AccountManager
import cn.funcoding.github.mvp.impl.BasePresenter
import cn.funcoding.github.view.LoginActivity

class LoginPresenter : BasePresenter<LoginActivity>() {

    fun initData() {
        if (BuildConfig.DEBUG) {
            view.onDataInit(BuildConfig.testUserName, BuildConfig.testPassword)
        } else {
            view.onDataInit(AccountManager.username, AccountManager.password)
        }
    }

    fun doLogin(name: String, password: String) {
        AccountManager.username = name
        AccountManager.password = password
        view.onLoginStart()
        AccountManager.login()
            .subscribe({
                view.onLoginSuccess()
            }, {
                view.onLoginError(it)
            })
    }

    fun checkUsername(name: String): Boolean {
        return name.isNotEmpty()
    }

    fun checkPassword(password: String): Boolean {
        return password.isNotEmpty()
    }
}