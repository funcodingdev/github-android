package cn.funcoding.github.setting

import cn.funcoding.github.AppContext
import cn.funcoding.github.common.log.logger
import cn.funcoding.github.utils.deviceId

object Configs {

    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        const val clientId = "900b5a57a5381a1060e5"
        const val clientSecret = "ffdcc7c0dddb1f758ff635353017d5f1233736f9"
        const val note = "funcoding.cn"
        const val noteUrl = "http://www.funcoding.cn"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: " + it) }
        }
    }

}