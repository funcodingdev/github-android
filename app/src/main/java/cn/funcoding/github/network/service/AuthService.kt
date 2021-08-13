package cn.funcoding.github.network.service

import cn.funcoding.github.network.entities.AuthorizationReq
import cn.funcoding.github.network.entities.AuthorizationRsp
import cn.funcoding.github.network.retrofit
import cn.funcoding.github.setting.Configs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

interface AuthApi {
    @PUT("/authorizations/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body req: AuthorizationReq,
        @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint
    ): Observable<AuthorizationRsp>

    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>
}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)