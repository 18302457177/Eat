package com.xxs.eat.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface

class MyReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle: Bundle? = intent?.extras
        if(bundle!=null) {
            val message = bundle.getString(JPushInterface.EXTRA_MESSAGE)
            val extras:String? = bundle.getString(JPushInterface.EXTRA_EXTRA)
            if(!TextUtils.isEmpty(extras)){
                OrderObservable.instance.newMsgComing(extras!!)
            }
        }
    }
}