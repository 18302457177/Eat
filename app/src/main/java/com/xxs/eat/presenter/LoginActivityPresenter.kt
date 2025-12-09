package com.xxs.eat.presenter

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.j256.ormlite.android.AndroidDatabaseConnection
import com.j256.ormlite.dao.Dao
import com.xxs.eat.model.beans.User
import com.xxs.eat.model.dao.TakeoutOpenHelper
import com.xxs.eat.model.net.ResponseInfo
import com.xxs.eat.ui.activity.LoginActivity
import com.xxs.eat.utils.TakeoutApp
import java.sql.Savepoint

class LoginActivityPresenter(val loginActivity: LoginActivity): NetPresenter() {

    fun loginByPhone(phone: String){
        val loginCall = takeoutService.loginByPhone(phone)
        loginCall.enqueue(callback)
    }

    override fun parserJson(json: String?) {
        val user = Gson().fromJson<User>(json, User::class.java)
        if(user!=null){
            TakeoutApp.sUser = user
            var connection: AndroidDatabaseConnection? = null
            var startPoint: Savepoint? = null
            try {
                val takeoutOpenHelper = TakeoutOpenHelper(loginActivity)
                val userDao: Dao<User, Int> = takeoutOpenHelper.getDao(User::class.java)
//            userDao.createOrUpdate(user)
                connection =
                    AndroidDatabaseConnection(takeoutOpenHelper.writableDatabase, true)
                startPoint = connection.setSavePoint("start")
                connection.isAutoCommit = false
                val userList: List<User> = userDao.queryForAll()
                var isOldUser: Boolean = false
                for(i in 0 until userList.size){
                    val u = userList.get(i)
                    if(u.id == user.id){
                        isOldUser = true
                    }
                }
                if(isOldUser){
                    Log.e("login","老用户更新信息")
                }else{
                    Log.e("login","新用户登录")
                }
                Log.e("login","缓存用户信息到数据库")
                connection.commit(startPoint)
            }catch (e: Exception){
                Log.e("login","出现异常"+e.localizedMessage)
                if(connection!=null){
                    connection.rollback(startPoint)
                }
            }

            loginActivity.onLoginSuccess()
        }else{
            loginActivity.onLoginFailed()

        }
    }



}