package cn.funcoding.github.model.account

import cn.funcoding.github.network.entities.AuthorizationReq
import cn.funcoding.github.network.entities.AuthorizationRsp
import cn.funcoding.github.network.entities.User
import cn.funcoding.github.network.service.AuthService
import cn.funcoding.github.network.service.UserService
import cn.funcoding.github.utils.fromJson
import cn.funcoding.github.utils.pref
import com.google.gson.Gson
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface OnAccountStateChangeListener {
    fun onLogin(user: User)

    fun onLogout()
}

object AccountManager {
    var authId by pref(-1)
    var username by pref("")
    var password by pref("")
    var token by pref("")

    private var userJson by pref("")

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson)
            }
            return field
        }
        set(value) {
            userJson = if (value == null) {
                ""
            } else {
                Gson().toJson(value)
            }
            field = value
        }

    val onAccountStateChangeListener = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListener.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListener.forEach {
            it.onLogout()
        }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    fun login() = AuthService.createAuthorization(AuthorizationReq())
        .doOnNext {
            if (it.token.isEmpty()) throw AccountException(it)
        }.retryWhen {
            it.flatMap { thr ->
                if (thr is AccountException) {
                    AuthService.deleteAuthorization(thr.authorizationRsp.id)
                } else {
                    Observable.error(thr)
                }
            }
        }.flatMap {
            token = it.token
            authId = it.id
            UserService.getAuthenticatedUser()
        }.map {
            currentUser = it
            notifyLogin(it)
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    fun logout() = AuthService.deleteAuthorization(authId)
        .doOnNext {
            if (it.isSuccessful) {
                authId = -1
                token = ""
                currentUser = null
                notifyLogout()
            } else {
                throw HttpException(it)
            }
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("账户已经登陆")