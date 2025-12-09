package com.xxs.eat.utils

import com.xxs.eat.utils.OrderObservable
import java.util.Observable

/**
 * Created by lidongzhi on 2016/10/20.
 */
object OrderObservable : Observable() {

    val instance: OrderObservable = this

    /* 订单状态
       * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单*/
    const val ORDERTYPE_UNPAYMENT: String = "10"
    const val ORDERTYPE_SUBMIT: String = "20"
    const val ORDERTYPE_RECEIVEORDER: String = "30"
    const val ORDERTYPE_DISTRIBUTION: String = "40"

    // 骑手状态：接单、取餐、送餐
    const val ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE: String = "43"
    const val ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL: String = "46"
    const val ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL: String = "48"

    const val ORDERTYPE_SERVED: String = "50"
    const val ORDERTYPE_CANCELLEDORDER: String = "60"
    fun newMsgComing(extras: String) {
        setChanged()
        notifyObservers()
    }
}
