package com.xxs.eat.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView
import com.xxs.eat.R
import com.xxs.eat.model.beans.Order
import com.xxs.eat.utils.OrderObservable
import org.json.JSONObject
import java.util.Observable
import java.util.Observer

class OrderRvAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Observer {

        init {
            OrderObservable.instance.addObserver(this)
        }

    // 实现 Observer 接口的 onUpdate 方法
    override fun update(observer: Observable?, data: Any?) {
        // 处理更新逻辑
        val jsonObj : JSONObject = JSONObject(data as  String)
        val pushOrderId = jsonObj.getString("orderId")
        val pushType = jsonObj.getString("type")
        var index = -1
        for(i in 0 until orderList.size){
            val order = orderList.get(i)
            if(order.id.equals(pushOrderId)){
                order.type = pushType
                index = i
            }
        }
        if(index != -1){
            notifyItemChanged( index)
        }
    }

    private var orderList:List<Order> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setOrderData(orderList: List<Order>){
        this.orderList = orderList
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_order_item,parent,false)
        return OrderItemHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as OrderItemHolder).bindData(orderList.get(position))
    }

    inner class OrderItemHolder(item: View):RecyclerView.ViewHolder(item){
        val tvSellerrName: TextView
        val tvOrderType: TextView

        init{
            tvSellerrName = item.findViewById<TextView>(R.id.tv_order_item_seller_name)
            tvOrderType = item.findViewById<TextView>(R.id.tv_order_item_type)
        }

        fun bindData(order: Order){
            tvSellerrName.text = order.seller?.name
            tvOrderType.text = getOrderTypeInfo(order.type)
        }
    }

    private fun getOrderTypeInfo(type: String?): String {
        /**
         * 订单状态
         * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
         */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";

        var typeInfo = ""
        when (type) {
            OrderObservable.ORDERTYPE_UNPAYMENT -> typeInfo = "未支付"
            OrderObservable.ORDERTYPE_SUBMIT -> typeInfo = "已提交订单"
            OrderObservable.ORDERTYPE_RECEIVEORDER -> typeInfo = "商家接单"
            OrderObservable.ORDERTYPE_DISTRIBUTION -> typeInfo = "配送中"
            OrderObservable.ORDERTYPE_SERVED -> typeInfo = "已送达"
            OrderObservable.ORDERTYPE_CANCELLEDORDER -> typeInfo = "取消的订单"
        }
        return typeInfo
    }


}