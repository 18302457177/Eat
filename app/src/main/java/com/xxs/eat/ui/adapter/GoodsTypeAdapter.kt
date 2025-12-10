package com.xxs.eat.ui.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.xxs.eat.R
import com.xxs.eat.model.beans.GoodsTypeInfo
import com.xxs.eat.ui.fragment.GoodsFragment

class GoodsTypeAdapter(val context: FragmentActivity?,val goodsFragment: GoodsFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var goodsTypeList:List<GoodsTypeInfo> = listOf()

    fun setDatas(list: List<GoodsTypeInfo>) {
        this.goodsTypeList = (list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_type, parent,false)
        return GoodsTypeItemHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val goodsTypeItemHolder = holder as GoodsTypeItemHolder
        goodsTypeItemHolder.bindData(goodsTypeList[position],position)
    }

    override fun getItemCount(): Int {
        return goodsTypeList.size
    }
    var selectPosition = 0
    inner class GoodsTypeItemHolder(val item: View): RecyclerView.ViewHolder(item){
        val tvType: TextView
        var mPosition = 0
        val tvRedDotCount: TextView
        lateinit var goodsTypeInfo: GoodsTypeInfo
        init {
            tvType = item.findViewById(R.id.type)
            tvRedDotCount = item.findViewById(R.id.tvRedDotCount)
            item.setOnClickListener {
                selectPosition = mPosition
                notifyDataSetChanged()
                val typeId = goodsTypeInfo.id
                val position = goodsFragment.goodsFragmentPresenter.getGoodsPositionByTypeId(typeId)
                goodsFragment.slhlv.setSelection(position)
            }
        }
        fun bindData(goodsTypeInfo: GoodsTypeInfo,position: Int){
            mPosition = position
            this.goodsTypeInfo = goodsTypeInfo
            if(position == selectPosition){
                item.setBackgroundColor(Color.WHITE)
                tvType.setTextColor(Color.BLACK)
                tvType.setTypeface(Typeface.DEFAULT_BOLD)
            }else{
                item.setBackgroundColor(Color.parseColor("#b9dedcdc"))
                tvType.setTextColor(Color.GRAY)
                tvType.setTypeface(Typeface.DEFAULT)
            }
            tvType.text = goodsTypeInfo.name
            tvRedDotCount.text = goodsTypeInfo.redDotCount.toString()
            if(goodsTypeInfo.redDotCount > 0){
                tvRedDotCount.visibility = View.VISIBLE
            }else{
                tvRedDotCount.visibility = View.GONE
            }
        }
    }
}