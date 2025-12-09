package com.xxs.eat.presenter

import android.util.Log
import com.xxs.eat.model.net.ResponseInfo
import com.xxs.eat.model.net.TakeoutService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.text.equals

open abstract class NetPresenter {

    val takeoutService: TakeoutService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080/TakeoutService")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        takeoutService = retrofit.create(TakeoutService::class.java)

    }

    abstract fun parserJson(json: String?)

    val callback = object : Callback<ResponseInfo> {
        override fun onResponse(
            call: Call<ResponseInfo?>?,
            response: Response<ResponseInfo?>?
        ) {
            if (response == null) {
                Log.e("home", "服务器没有返回数据")
            } else {
                if (response.isSuccessful()){
                    val responseInfo = response.body()
                    if(responseInfo?.code.equals("0")){
                        var json = responseInfo?.data
                        parserJson(json)
                    }else if(responseInfo?.code.equals("-1")){

                    }
                }else{
                    Log.e("home", "服务器代码出现错误")
                }
            }
        }
        override fun onFailure(
            call: Call<ResponseInfo?>?,
            t: Throwable?
        ) {
            Log.e("home", "没有连上服务器")
        }
    }

}