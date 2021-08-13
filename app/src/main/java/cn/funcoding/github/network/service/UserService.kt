package cn.funcoding.github.network.service

import cn.funcoding.github.network.entities.User
import cn.funcoding.github.network.retrofit
import retrofit2.http.GET
import rx.Observable

interface UserApi {
    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>
}

object UserService: UserApi by retrofit.create(UserApi::class.java)