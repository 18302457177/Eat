package com.xxs.eat.presenter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xxs.eat.model.beans.Order
import com.xxs.eat.model.net.ResponseInfo
import com.xxs.eat.ui.fragment.OrderFragment
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class OrderFragmentPresenter(val orderFragment: OrderFragment): NetPresenter() {
    override fun parserJson(json: String?) {
        val orderList:List<Order> = Gson().fromJson(json, object : TypeToken<List<Order>>() {}.type)
        if(orderList.isNotEmpty()){
            orderFragment.onOrderSuccess(orderList)
        }else{
            orderFragment.onOrderFailed()
        }
    }
    fun getOrderList(userId: String){
        val observable: Observable<ResponseInfo> = takeoutService.getOrderListByRxjava(userId)
//        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : Observer<ResponseInfo> {
//                override fun onCompleted() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(e: Throwable?) {
//                    if(e!=null){
//                        Log.e("rxjava",e.localizedMessage)
//                    }
//                }
//
//                override fun onNext(responseInfo: ResponseInfo?) {
//                    if(responseInfo!=null){
//                        parserJson(responseInfo?.data)
//                    }
//                }
//            })
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                parserJson(it.data)
            },{
                Log.e("rxjava",it.localizedMessage)
            },{
                Log.e("rxjava","onComplete!")
            })
    }
}