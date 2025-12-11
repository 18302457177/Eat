package com.xxs.eat.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageButton

import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.xxs.eat.R
import com.xxs.eat.databinding.ActivityBusinessBinding
import com.xxs.eat.model.beans.Seller
import com.xxs.eat.ui.adapter.CartRvAdapter
import com.xxs.eat.ui.fragment.CommentsFragment
import com.xxs.eat.ui.fragment.GoodsFragment
import com.xxs.eat.ui.fragment.SellerFragment
import com.xxs.eat.utils.PriceFormater

class BusinessActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityBusinessBinding
    lateinit var rvCart: RecyclerView
    var bottomSheetView: View? = null
    lateinit var cartAdapter: CartRvAdapter
    override fun onClick(v: View) {
        when (v.id) {
            R.id.bottom -> {
                showOrHideCart()
            }
        }
    }

    fun showOrHideCart() {
        if (bottomSheetView == null) {
            bottomSheetView = LayoutInflater.from(this)
                .inflate(R.layout.cart_list, binding.root as ViewGroup, false)
            rvCart = bottomSheetView!!.findViewById(R.id.rvCart)
            rvCart.layoutManager = LinearLayoutManager(this)
            cartAdapter = CartRvAdapter(this)
            rvCart.adapter = cartAdapter
            bottomSheetView!!.findViewById<ImageButton>(R.id.tvClear).setOnClickListener {
                var builder = AlertDialog.Builder(this)
                builder.setTitle("确认都不吃了？")
                builder.setPositiveButton("确定",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val goodsFragment: GoodsFragment = fragments[0] as GoodsFragment
                        goodsFragment.goodsFragmentPresenter.clearCart()
                        cartAdapter.notifyDataSetChanged()
                        showOrHideCart()
                        goodsFragment.goodsAdapter.notifyDataSetChanged()
                        clearRedDot()
                        goodsFragment.goodsTypeAdapter.notifyDataSetChanged()
                        updateCarUi()
                    }


                })
                builder.setNegativeButton("取消",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })
                builder.show()
            }
        }

        // 假设你将 bottomSheetView 添加到某个容器中并进行动画展示/隐藏
        if (binding.bottomSheetLayout.visibility == View.VISIBLE) {
            // 隐藏 bottom sheet
            binding.bottomSheetLayout.visibility = View.GONE
        } else {
            // 显示 bottom sheet
            val goodsFragment: GoodsFragment = fragments[0] as GoodsFragment
            val cartList = goodsFragment.goodsFragmentPresenter.getCartList()
            cartAdapter.setCartData(cartList)
            if(cartList.size > 0) {
                binding.bottomSheetLayout.visibility = View.VISIBLE
                binding.bottomSheetLayout.addView(bottomSheetView)
            }
        }
    }
    private fun clearRedDot() {
        val goodsFragment: GoodsFragment = fragments[0] as GoodsFragment
        val goodsTypeList = goodsFragment.goodsFragmentPresenter.goodsTypeList
        for(i in 0 until goodsTypeList.size){
            val goodsTypeInfo = goodsTypeList[i]
            goodsTypeInfo.redDotCount = 0

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business)
        processIntent()
        window.decorView.setOnApplyWindowInsetsListener { _, insets ->
            val navigationBars = insets.getInsets(WindowInsets.Type.navigationBars())
            binding.flContainer.setPadding(0, 0, 0, navigationBars.bottom)
            insets
        }

        binding.vp.adapter = BusinessFragmentAdapter()
        binding.tabs.setupWithViewPager(binding.vp)
        binding.bottom.setOnClickListener(this)
    }

    var hasSelectInfo = false
    lateinit var seller: Seller
    private fun processIntent() {
        if(intent.hasExtra("hasSelectInfo")){
            hasSelectInfo = intent.getBooleanExtra("hasSelectInfo",false)
            seller = intent.getSerializableExtra("seller") as Seller
            binding.tvDeliveryFee.text = "另需配送费"+ PriceFormater.format(seller.deliveryFee.toFloat())
            binding.tvSendPrice.text = "起送费"+ PriceFormater.format(seller.sendPrice.toFloat())
        }
    }

    val fragments = listOf<Fragment>(GoodsFragment(), SellerFragment(), CommentsFragment())


    @SuppressLint("PrivateApi")
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.getResources()
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {


        }
        return hasNavigationBar
    }

    fun addImageButton(ib: ImageButton, width: Int, height: Int) {
        binding.flContainer.addView(ib, width, height)
    }

    fun getCartLocation(): IntArray {
        val destLocation = IntArray(2)
        binding.imgCart.getLocationInWindow(destLocation)
        return destLocation
    }

    fun updateCarUi() {
        var count = 0
        var countPrice = 0.0f
        val goodsFragment: GoodsFragment = fragments[0] as GoodsFragment
        val cartList = goodsFragment.goodsFragmentPresenter.getCartList()
        for (i in 0 until cartList.size) {
            val goodsInfo = cartList[i]
            count += goodsInfo.count
            countPrice += goodsInfo.newPrice.toFloat() * goodsInfo.count
        }
        binding.tvSelectNum.text = count.toString()
        if (count > 0) {
            binding.tvSelectNum.visibility = View.VISIBLE
        } else {
            binding.tvSelectNum.visibility = View.GONE
        }
        binding.tvCountPrice.text = PriceFormater.format(countPrice)

    }

    val titles = listOf<String>("商品", "商家", "评论")

    // 修改 Adapter 类以继承 androidx.fragment.app.FragmentPagerAdapter 正确实现
    inner class BusinessFragmentAdapter :
        androidx.fragment.app.FragmentPagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

        override fun getCount(): Int = titles.size

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }

}