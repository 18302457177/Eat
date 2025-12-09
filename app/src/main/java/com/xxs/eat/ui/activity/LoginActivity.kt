package com.xxs.eat.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.j256.ormlite.android.AndroidDatabaseConnection
import com.j256.ormlite.dao.Dao
import com.xxs.eat.databinding.ActivityLoginBinding
import com.xxs.eat.model.beans.User
import com.xxs.eat.model.dao.TakeoutOpenHelper
import com.xxs.eat.presenter.LoginActivityPresenter
import com.xxs.eat.utils.SMSUtil
import com.xxs.eat.utils.TakeoutApp
import java.sql.Savepoint

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    val eventHandler = object : EventHandler(){
        override fun afterEvent(event: Int, result: Int, data: Any?) {
            if(data is Throwable){
                val msg = data.message
                // 在 LoginActivity 中
                Log.e("error",msg.toString())
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "提示信息", Toast.LENGTH_SHORT).show()
                }
            }else{
                if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Log.e("sms","获取成功")
                }else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("sms", "提交成功")
//                    val phone = binding.etUserPhone.text.toString().trim()
//                    loginActivityPresenter.loginByPhone(phone)
                    finish()
                }
            }
        }
    }

//    lateinit var loginActivityPresenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        loginActivityPresenter = LoginActivityPresenter(this)
        initListener()

        SMSSDK.registerEventHandler(eventHandler)
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
    }

    private fun initListener(){
        binding.ivUserBack.setOnClickListener { view ->
            finish()
        }
        binding.tvUserCode.setOnClickListener { view ->
            val phone = binding.etUserPhone.text.toString().trim()
            if(SMSUtil.judgePhoneNums(this,phone)){
                SMSSDK.getVerificationCode("86",phone)
                binding.tvUserCode.isEnabled = false
                Thread(CutDownTask()).start()
            }

        }
        binding.ivLogin.setOnClickListener { view ->
//            val phone = binding.etUserPhone.text.toString().trim()
//            val code = binding.etUserCode.text.toString().trim()
//            if(SMSUtil.judgePhoneNums(this,phone)&& !TextUtils.isEmpty(code)){
//                SMSSDK.submitVerificationCode("86",phone,code)
//            }
//            var connection: AndroidDatabaseConnection? = null
//            var startPoint: Savepoint? = null
//            try {
//                val takeoutOpenHelper = TakeoutOpenHelper(this)
//                val userDao: Dao<User, Int> = takeoutOpenHelper.getDao(User::class.java)
////            userDao.createOrUpdate(user)
//                connection =
//                    AndroidDatabaseConnection(takeoutOpenHelper.writableDatabase, true)
//                startPoint = connection.setSavePoint("start")
//                connection.isAutoCommit = false
//                val userList: List<User> = userDao.queryForAll()
//                var isOldUser: Boolean = false
//                for(i in 0 until userList.size){
//                    val u = userList.get(i)
//                    if(u.id == TakeoutApp.sUser?.id){
//                        isOldUser = true
//                    }
//                }
//                if(isOldUser){
//                    Log.e("login","老用户更新信息")
//                }else{
//                    Log.e("login","新用户登录")
//                }
//                Log.e("login","缓存用户信息到数据库")
//                connection.commit(startPoint)
//            }catch (e: Exception){
//                Log.e("login","出现异常"+e.localizedMessage)
//                if(connection!=null){
//                    connection.rollback(startPoint)
//                }
//            }
            TakeoutApp.sUser = User().apply {
                id = 1
                name = "张三"
                balance = 100.50f
                discount = 10
                integral = 1000
                phone = "13800138000"
            }
            finish()

        }
    }
    fun onLoginSuccess(){
        finish()
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show()

    }
    fun onLoginFailed(){
        Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show()
    }

    companion object{
        val TIME_MINUS = -1
        val TIME_IS_OUT = 0
    }
    val handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg!!.what){
                TIME_MINUS -> binding.tvUserCode.text = "${time}秒"
                TIME_IS_OUT -> {
                    binding.tvUserCode.isEnabled = true
                    binding.tvUserCode.text="点击重发"
                    time = 60
                }
            }
        }
    }

    var time = 60
    inner class CutDownTask:Runnable{
        override fun run() {
            while(time>0){
                handler.sendEmptyMessage(TIME_MINUS)
                SystemClock.sleep(999)
                time--
            }
            handler.sendEmptyMessage(TIME_IS_OUT)
        }
    }
}