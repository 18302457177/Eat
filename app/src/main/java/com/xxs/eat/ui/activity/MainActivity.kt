package com.xxs.eat.ui.activity

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import com.xxs.eat.R
import com.xxs.eat.databinding.ActivityMainBinding
import com.xxs.eat.ui.fragment.HomeFragment
import com.xxs.eat.ui.fragment.MoreFragment
import com.xxs.eat.ui.fragment.OrderFragment
import com.xxs.eat.ui.fragment.UserFragment

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    val fragments: List<Fragment> = listOf<Fragment>(HomeFragment(), OrderFragment(),
        UserFragment(), MoreFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        window.decorView.setOnApplyWindowInsetsListener { _, insets ->
            val navigationBars = insets.getInsets(WindowInsets.Type.navigationBars())
            binding.llMainActivity.setPadding(0, 0, 0, navigationBars.bottom)
            insets
        }

        initBottomBar()
        changeIndex(0)
    }



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
    private fun initBottomBar(){
        for(i in 0 until binding.mainBottomBar.childCount){
            binding.mainBottomBar.getChildAt(i).setOnClickListener { view ->
                changeIndex(i)
            }
        }
    }
    private fun changeIndex(index:Int) {
        for (i in 0 until binding.mainBottomBar.childCount){
            val child = binding.mainBottomBar.getChildAt(i)
            if(i==index){
                setEnable(child,false)
            }else{
                setEnable(child,true)
            }
            fragmentManager.beginTransaction().replace(R.id.main_content,fragments[index]).commit()
        }
    }

    private fun setEnable(child: View, bool: Boolean) {
        child.isEnabled = bool
        if(child is ViewGroup){
            for(i in 0 until child.childCount){
                child.getChildAt(i).isEnabled = bool
            }
        }
    }

}