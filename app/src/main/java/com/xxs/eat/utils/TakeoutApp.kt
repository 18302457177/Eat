package com.xxs.eat.utils

import cn.jpush.android.api.JPushInterface
import com.mob.MobApplication
import com.xxs.eat.model.beans.User

class TakeoutApp: MobApplication () {

    companion object{
        var loginFlag = false
        var sUser: User? = User()
    }

    override fun onCreate() {
        super.onCreate()
        sUser?.id = -1
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

    }
}