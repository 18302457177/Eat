package com.xxs.eat.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xxs.eat.R
import com.xxs.eat.databinding.FragmentOrderBinding
import com.xxs.eat.model.beans.GoodsInfo
import com.xxs.eat.model.beans.Order
import com.xxs.eat.model.beans.Seller
import com.xxs.eat.presenter.OrderFragmentPresenter
import com.xxs.eat.ui.adapter.OrderRvAdapter
import com.xxs.eat.utils.TakeoutApp


class OrderFragment : Fragment() {

    lateinit var binding: FragmentOrderBinding

//    lateinit var orderPresenter: OrderFragmentPresenter
    lateinit var adapter: OrderRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        binding.rvOrderList.layoutManager = LinearLayoutManager(activity)
        adapter = OrderRvAdapter(activity)
        binding.srlOrder.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                val userId = TakeoutApp.sUser?.id
                if(-1==userId){
                    Toast.makeText(activity,"请先登录",Toast.LENGTH_SHORT).show()
                }else{
                    val mockOrders = listOf(
                        Order().apply {
                            id = "ORDER001"
                            type = "10"
                            seller = Seller(
                                id = 1L,
                                pic = "https://example.com/mcdonalds.jpg",
                                name = "麦当劳",
                                score = "4.5",
                                sale = "月售1000+",
                                ensure = "保",
                                invoice = "票",
                                sendPrice = "¥20",
                                deliveryFee = "¥3",
                                recentVisit = "10分钟前下单",
                                distance = "500m",
                                time = "30分钟",
                                icon = "https://example.com/mcdonalds_icon.png",
                                activityList = emptyList()
                            )
                            rider = Rider().apply {
                                id = 101
                                name = "张三"
                                phone = "13800138001"
                            }
                            goodsInfos = mutableListOf(
                                GoodsInfo().apply {
                                    id = 1
                                    name = "巨无霸汉堡"
                                    icon = "https://example.com/big_mac.jpg"
                                    form = "牛肉饼、生菜、芝士、酸黄瓜、洋葱"
                                    monthSaleNum = 500
                                    isBargainPrice = false
                                    isNew = true
                                    newPrice = "25.0"
                                    oldPrice = 28
                                    sellerId = 101
                                },
                                GoodsInfo().apply {
                                    id = 2
                                    name = "麦辣鸡翅"
                                    icon = "https://example.com/spicy_chicken_wings.jpg"
                                    form = "鸡肉、辣椒调料"
                                    monthSaleNum = 300
                                    isBargainPrice = true
                                    isNew = false
                                    newPrice = "12.0"
                                    oldPrice = 15
                                    sellerId = 101
                                }
                            )
                            distribution = Distribution().apply {
                                type = "商家配送"
                                des = "30分钟内送达"
                            }
                            detail = OrderDetail().apply {
                                username = "李四"
                                phone = "13900139001"
                                address = "北京市朝阳区某某大厦A座101室"
                                pay = "在线支付"
                                time = "2023-12-01 12:30"
                            }
                        },

                        Order().apply {
                            id = "ORDER002"
                            type = "20"
                            seller = Seller(
                                id = 2L,
                                pic = "https://example.com/kfc.jpg",
                                name = "肯德基",
                                score = "4.3",
                                sale = "月售800+",
                                ensure = "保",
                                invoice = "票",
                                sendPrice = "¥15",
                                deliveryFee = "¥2",
                                recentVisit = "20分钟前下单",
                                distance = "800m",
                                time = "25分钟",
                                icon = "https://example.com/kfc_icon.png",
                                activityList = emptyList()
                            )
                            rider = null
                            goodsInfos = mutableListOf(
                                GoodsInfo().apply {
                                    id = 3
                                    name = "薯条(大份)"
                                    icon = "https://example.com/french_fries.jpg"
                                    form = "土豆、植物油、盐"
                                    monthSaleNum = 800
                                    isBargainPrice = false
                                    isNew = false
                                    newPrice = "18.0"
                                    oldPrice = 18
                                    sellerId = 102
                                },
                                GoodsInfo().apply {
                                    id = 4
                                    name = "可乐(大杯)"
                                    icon = "https://example.com/coke.jpg"
                                    form = "碳酸饮料"
                                    monthSaleNum = 1000
                                    isBargainPrice = true
                                    isNew = false
                                    newPrice = "8.0"
                                    oldPrice = 12
                                    sellerId = 102
                                }
                            )
                            distribution = Distribution().apply {
                                type = "到店自取"
                                des = "请在下单后30分钟内到店取餐"
                            }
                            detail = OrderDetail().apply {
                                username = "王五"
                                phone = "13700137001"
                                address = "北京市海淀区某某商场1楼肯德基"
                                pay = "到店支付"
                                time = "2023-12-01 18:45"
                            }
                        }
                    )
                    onOrderSuccess(mockOrders)
//            orderPresenter.getOrderList(userId.toString())
                }
            }
        })
        binding.rvOrderList.adapter = adapter
//        orderPresenter = OrderFragmentPresenter(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userId = TakeoutApp.sUser?.id
        if(-1==userId){
            Toast.makeText(activity,"请先登录",Toast.LENGTH_SHORT).show()
        }else{
            val mockOrders = listOf(
                Order().apply {
                    id = "ORDER001"
                    type = "10"
                    seller = Seller(
                        id = 1L,
                        pic = "https://example.com/mcdonalds.jpg",
                        name = "麦当劳",
                        score = "4.5",
                        sale = "月售1000+",
                        ensure = "保",
                        invoice = "票",
                        sendPrice = "¥20",
                        deliveryFee = "¥3",
                        recentVisit = "10分钟前下单",
                        distance = "500m",
                        time = "30分钟",
                        icon = "https://example.com/mcdonalds_icon.png",
                        activityList = emptyList()
                    )
                    rider = Rider().apply {
                        id = 101
                        name = "张三"
                        phone = "13800138001"
                    }
                    goodsInfos = mutableListOf(
                        GoodsInfo().apply {
                            id = 1
                            name = "巨无霸汉堡"
                            icon = "https://example.com/big_mac.jpg"
                            form = "牛肉饼、生菜、芝士、酸黄瓜、洋葱"
                            monthSaleNum = 500
                            isBargainPrice = false
                            isNew = true
                            newPrice = "25.0"
                            oldPrice = 28
                            sellerId = 101
                        },
                        GoodsInfo().apply {
                            id = 2
                            name = "麦辣鸡翅"
                            icon = "https://example.com/spicy_chicken_wings.jpg"
                            form = "鸡肉、辣椒调料"
                            monthSaleNum = 300
                            isBargainPrice = true
                            isNew = false
                            newPrice = "12.0"
                            oldPrice = 15
                            sellerId = 101
                        }
                    )
                    distribution = Distribution().apply {
                        type = "商家配送"
                        des = "30分钟内送达"
                    }
                    detail = OrderDetail().apply {
                        username = "李四"
                        phone = "13900139001"
                        address = "北京市朝阳区某某大厦A座101室"
                        pay = "在线支付"
                        time = "2023-12-01 12:30"
                    }
                },

                Order().apply {
                    id = "ORDER002"
                    type = "20"
                    seller = Seller(
                        id = 2L,
                        pic = "https://example.com/kfc.jpg",
                        name = "肯德基",
                        score = "4.3",
                        sale = "月售800+",
                        ensure = "保",
                        invoice = "票",
                        sendPrice = "¥15",
                        deliveryFee = "¥2",
                        recentVisit = "20分钟前下单",
                        distance = "800m",
                        time = "25分钟",
                        icon = "https://example.com/kfc_icon.png",
                        activityList = emptyList()
                    )
                    rider = null
                    goodsInfos = mutableListOf(
                        GoodsInfo().apply {
                            id = 3
                            name = "薯条(大份)"
                            icon = "https://example.com/french_fries.jpg"
                            form = "土豆、植物油、盐"
                            monthSaleNum = 800
                            isBargainPrice = false
                            isNew = false
                            newPrice = "18.0"
                            oldPrice = 18
                            sellerId = 102
                        },
                        GoodsInfo().apply {
                            id = 4
                            name = "可乐(大杯)"
                            icon = "https://example.com/coke.jpg"
                            form = "碳酸饮料"
                            monthSaleNum = 1000
                            isBargainPrice = true
                            isNew = false
                            newPrice = "8.0"
                            oldPrice = 12
                            sellerId = 102
                        }
                    )
                    distribution = Distribution().apply {
                        type = "到店自取"
                        des = "请在下单后30分钟内到店取餐"
                    }
                    detail = OrderDetail().apply {
                        username = "王五"
                        phone = "13700137001"
                        address = "北京市海淀区某某商场1楼肯德基"
                        pay = "到店支付"
                        time = "2023-12-01 18:45"
                    }
                }
            )
            onOrderSuccess(mockOrders)
//            orderPresenter.getOrderList(userId.toString())
        }

    }
    fun onOrderSuccess(orderList: List<Order>){
        adapter.setOrderData(orderList)
        binding.srlOrder.isRefreshing = false
    }
    fun onOrderFailed(){
        Toast.makeText(activity,"服务器繁忙",Toast.LENGTH_SHORT)
        binding.srlOrder.isRefreshing = false
    }


}