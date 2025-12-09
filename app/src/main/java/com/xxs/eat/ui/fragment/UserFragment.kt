package com.xxs.eat.ui.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xxs.eat.R
import com.xxs.eat.databinding.FragmentUserBinding
import com.xxs.eat.model.beans.User
import com.xxs.eat.ui.activity.LoginActivity
import com.xxs.eat.utils.TakeoutApp


class UserFragment : Fragment() {
    lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        binding.login.setOnClickListener { view ->
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(TakeoutApp.loginFlag){
            TakeoutApp.sUser = User().apply {
                id = 1
                name = "张三"
                balance = 100.50f
                discount = 10
                integral = 1000
                phone = "13800138000"
            }
        }
        if(TakeoutApp.sUser?.id == -1){
            binding.llUserinfo.visibility = View.GONE
            binding.login.visibility = View.VISIBLE
        }else{
            binding.llUserinfo.visibility = View.VISIBLE
            binding.username.text = "欢迎您${TakeoutApp.sUser?.name}"
            binding.phone.text = TakeoutApp.sUser?.phone
        }
    }
}