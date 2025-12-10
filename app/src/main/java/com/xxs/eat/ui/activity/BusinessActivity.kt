package com.xxs.eat.ui.activity

import android.annotation.SuppressLint

import android.content.Context
import android.os.Bundle
import android.view.WindowInsets
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.legacy.app.FragmentPagerAdapter


import com.xxs.eat.R
import com.xxs.eat.databinding.ActivityBusinessBinding
import com.xxs.eat.ui.fragment.CommentsFragment
import com.xxs.eat.ui.fragment.GoodsFragment
import com.xxs.eat.ui.fragment.SellerFragment

class BusinessActivity : AppCompatActivity() {
    lateinit var binding: ActivityBusinessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business)
        window.decorView.setOnApplyWindowInsetsListener { _, insets ->
            val navigationBars = insets.getInsets(WindowInsets.Type.navigationBars())
            binding.flContainer.setPadding(0, 0, 0, navigationBars.bottom)
            insets
        }
        binding.vp.adapter = BusinessFragmentAdapter()
        binding.tabs.setupWithViewPager(binding.vp)
    }

    val fragments = listOf<Fragment>(GoodsFragment(), SellerFragment(), CommentsFragment())


    @SuppressLint("PrivateApi")
    fun checkDeviceHasNavigationBar(context: Context):Boolean{
        var hasNavigationBar = false
        val rs = context.getResources()
        val id = rs.getIdentifier("config_showNavigationBar","bool","android")
        if(id>0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if("1".equals(navBarOverride)){
                hasNavigationBar = false
            }else if("0".equals(navBarOverride)){
                hasNavigationBar = true
            }
        }catch (e:Exception){


        }
        return hasNavigationBar
    }

    fun addImageButton(ib: ImageButton, width: Int, height: Int) {
        binding.flContainer.addView(ib,width, height)
    }

    fun getCartLocation(): IntArray {
        val destLocation = IntArray(2)
        binding.imgCart.getLocationInWindow(destLocation)
        return destLocation
    }

    val titles = listOf<String>("商品", "商家", "评论")
    // 修改 Adapter 类以继承 androidx.fragment.app.FragmentPagerAdapter 正确实现
inner class BusinessFragmentAdapter :
    androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = titles.size

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}

}