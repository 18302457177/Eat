package com.xxs.eat.utils

import cn.jpush.android.api.JPushInterface
import com.mob.MobApplication
import com.xxs.eat.model.beans.CacheSelectedInfo
import com.xxs.eat.model.beans.User
import java.util.concurrent.CopyOnWriteArrayList


class TakeoutApp: MobApplication () {

    val infos: CopyOnWriteArrayList<CacheSelectedInfo> = CopyOnWriteArrayList()

    companion object{
        var loginFlag = false
        var sUser: User? = User()
        lateinit var sInstance: TakeoutApp
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
        sUser?.id = -1
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

    }
    fun queryCacheSelectedInfoByGoodsId(goodsId: Int): Int {
        var count = 0
        for (i in infos.indices) {
            val info = infos[i]
            if (info.goodsId == goodsId) {
                count = info.count
                break
            }
        }
        return count
    }

    fun queryCacheSelectedInfoByTypeId(typeId: Int): Int {
        var count = 0
        for (i in infos.indices) {
            val info = infos[i]
            if (info.typeId == typeId) {
                count = count + info.count
            }
        }
        return count
    }

    fun queryCacheSelectedInfoBySellerId(sellerId: Int): Int {
        var count = 0
        for (i in infos.indices) {
            val info = infos[i]
            if (info.sellerId == sellerId) {
                count = count + info.count
            }
        }
        return count
    }

    fun addCacheSelectedInfo(info: CacheSelectedInfo?) {
        infos.add(info)
    }

    fun clearCacheSelectedInfo(sellerId: Int) {
        for (i in infos.indices) {
            val info = infos[i]
            if (info.sellerId == sellerId) {
                infos.remove(info)
            }
        }
    }

    fun deleteCacheSelectedInfo(goodsId: Int) {
        for (i in infos.indices) {
            val info = infos[i]
            if (info.goodsId == goodsId) {
                infos.remove(info)
                break
            }
        }
    }

    fun updateCacheSelectedInfo(goodsId: Int, operation: Int) {
        for (i in infos.indices) {
            var info = infos[i]
            if (info.goodsId == goodsId) {
                when (operation) {
                    Constants.ADD -> info.count = info.count + 1
                    Constants.MINUS -> info.count = info.count - 1
                }
            }
        }
    }
}