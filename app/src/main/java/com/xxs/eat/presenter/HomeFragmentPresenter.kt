package com.xxs.eat.presenter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xxs.eat.model.beans.Seller
import com.xxs.eat.model.net.ResponseInfo
import com.xxs.eat.model.net.TakeoutService
import com.xxs.eat.ui.fragment.HomeFragment
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

class HomeFragmentPresenter(val homeFragment: HomeFragment): NetPresenter(){


    fun getHomeInfo() {
        val homeCall = takeoutService.getHomeInfo()
        homeCall.enqueue(object : Callback<ResponseInfo> {
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
                            val json = responseInfo?.data
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
        })

    }
    override fun parserJson(json: String?){
        val gson = Gson()
        var jsonObject = JSONObject(json)
        var nearby = jsonObject.getJSONObject("nearbySellerList")
        val nearbySeller:List<Seller> = gson.fromJson(nearby.toString(),object : TypeToken<List<Seller>>(){}.type)
        Log.e("home",nearby.toString())
        var other = jsonObject.getJSONObject("otherSellerList")
        val otherSeller:List<Seller> = gson.fromJson(other.toString(),object : TypeToken<List<Seller>>(){}.type)
        if(nearbySeller.isNotEmpty()||otherSeller.isNotEmpty()){
            homeFragment.getHomeSuccess(nearbySeller,otherSeller)
        }else{
            homeFragment.onHomeFailed(nearbySeller,otherSeller)
        }



    }


}