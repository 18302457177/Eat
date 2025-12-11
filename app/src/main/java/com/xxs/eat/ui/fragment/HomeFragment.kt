package com.xxs.eat.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxs.eat.R

import com.xxs.eat.databinding.FragmentHomeBinding
import com.xxs.eat.model.beans.Seller
import com.xxs.eat.presenter.HomeFragmentPresenter
import com.xxs.eat.ui.adapter.HomeRvAdapter
import java.util.Arrays
import javax.inject.Inject


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var homeRvAdapter: HomeRvAdapter
    @Inject
    lateinit var homeFragmentPresenter: HomeFragmentPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.rvHome.layoutManager = LinearLayoutManager(activity)
        homeRvAdapter = HomeRvAdapter(activity)
        binding.rvHome.adapter = homeRvAdapter
//        DaggerHomeFragmentComponent.builder().homeFragmentModule(HomeFragmentModule(this)).build().inject(this)

        distance = 120.dp2px()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }
    fun Int.dp2px():Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,toFloat(),resources.displayMetrics).toInt()
    }
    val datas:ArrayList<String> = ArrayList()
    var sum:Int = 0
    var distance: Int = 0
    var alpha:Int = 55
    private fun initData() {
//        for(i in 0 until 20){
//            datas.add("我是商家$i")
//        }
//        homeFragmentPresenter.getHomeInfo()
        val mockSellers = ArrayList<Seller>(
            listOf(
                Seller(
                    id = 1L,
                    pic = "https://example.com/image1.jpg",
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
                    icon = "https://example.com/icon1.png",
                    activityList = emptyList()
                ),
                Seller(
                    id = 2L,
                    pic = "https://example.com/image2.jpg",
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
                    icon = "https://example.com/icon2.png",
                    activityList = emptyList()
                ),
                Seller(
                    id = 3L,
                    pic = "https://example.com/image3.jpg",
                    name = "必胜客",
                    score = "4.7",
                    sale = "月售600+",
                    ensure = "保",
                    invoice = "票",
                    sendPrice = "¥30",
                    deliveryFee = "¥5",
                    recentVisit = "5分钟前下单",
                    distance = "1.2km",
                    time = "40分钟",
                    icon = "https://example.com/icon3.png",
                    activityList = emptyList()
                )
            )
        )
        homeRvAdapter.setData(mockSellers as ArrayList<Seller>)


    }
    val allList:ArrayList<Seller> = ArrayList()
    fun getHomeSuccess(nearbySeller: List<Seller>, otherSeller: List<Seller>) {
        allList.clear()
        allList.addAll(nearbySeller)
        allList.addAll(otherSeller)
        homeRvAdapter.setData(allList)
        binding.rvHome.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                sum+=dy
                Log.e("sum",sum.toString())
                if(sum>distance){
                    alpha = 255
                }else{
                    alpha = (sum*200/distance)
                    alpha+=55
                }
                binding.llTitleContainer.setBackgroundColor(Color.argb(alpha,0x31,0x90,0xe8))


            }
        })
        Toast.makeText(activity,"获取数据成功",Toast.LENGTH_SHORT).show()
    }

    fun onHomeFailed(nearbySeller: List<Seller>, otherSeller: List<Seller>) {
        Toast.makeText(activity,"获取数据失败",Toast.LENGTH_SHORT).show()
    }


}