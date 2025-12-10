package com.xxs.eat.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xxs.eat.model.beans.GoodsInfo
import com.xxs.eat.model.beans.GoodsTypeInfo
import com.xxs.eat.ui.fragment.GoodsFragment
import org.json.JSONObject

class GoodsFragmentPresenter(val goodsFragment: GoodsFragment) : NetPresenter() {
    val allTypeGoodsList: ArrayList<GoodsInfo> = arrayListOf()
    var goodsTypeList: ArrayList<GoodsTypeInfo> = arrayListOf()

    fun getBusinessInfo(sellerId: String){
        val businessCall = takeoutService.getBusinessInfo(sellerId)
        businessCall.enqueue(callback)

    }

    override fun parserJson(json: String?) {

        val gson = Gson()
        val jsonObj = JSONObject(json)
        val allStr = jsonObj.getString("list")
        goodsTypeList = gson.fromJson(allStr, object : TypeToken<List<GoodsTypeInfo>>() {
        }.type)
        for ( i in 0 until goodsTypeList.size){
            val goodsTypeInfo = goodsTypeList[i]
            val aTypeList:List<GoodsInfo> = goodsTypeInfo.list
            for(j in 0 until aTypeList.size){
                val goodsInfo = aTypeList[j]
                goodsInfo.typeName = goodsTypeInfo.name
                goodsInfo.typeId = goodsTypeInfo.id
            }
            allTypeGoodsList.addAll(goodsTypeInfo.list)
        }
        goodsFragment.onLoadBusinessSuccess(goodsTypeList, allTypeGoodsList)
    }

    fun getGoodsPositionByTypeId(typeId: Int): Int {
        var position = -1
        for (i in 0 until allTypeGoodsList.size){
            val goodsInfo = allTypeGoodsList[i]
            if(goodsInfo.typeId == typeId){
                position = i
                break
            }

        }
        return position
    }

    fun getTypePositionByTypeId(newTypeId: Int): Int {
        var position = -1
        for (i in 0 until goodsTypeList.size){
            val goodsTypeInfo = goodsTypeList[i]
            if(goodsTypeInfo.id == newTypeId) {
                position = i
                break
            }
        }
        return position
    }
}