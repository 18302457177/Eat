package com.xxs.eat.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.xxs.eat.R
import com.xxs.eat.model.beans.GoodsInfo
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

class GoodsAdapter(val context: FragmentActivity?): BaseAdapter(), StickyListHeadersAdapter {

    inner class GoodsItemHolder(itemView:View) : View.OnClickListener {
        val ivIcon: ImageView
        val tvName: TextView
         val tvForm: TextView
         val tvMonthSale: TextView
         val tvNewPrice: TextView
         val tvOldPrice: TextView
         val btnAdd: ImageButton
         val btnMinus: ImageButton
         val tvCount: TextView
        init {
            ivIcon = itemView.findViewById(R.id.iv_icon)
            tvName = itemView.findViewById(R.id.tv_name)
            tvForm = itemView.findViewById(R.id.tv_form)
            tvMonthSale = itemView.findViewById(R.id.tv_month_sale)
            tvNewPrice = itemView.findViewById(R.id.tv_newprice)
            tvOldPrice = itemView.findViewById(R.id.tv_oldprice)
            tvCount = itemView.findViewById(R.id.tv_count)
            btnAdd = itemView.findViewById(R.id.ib_add)
            btnMinus = itemView.findViewById(R.id.ib_minus)
            btnAdd.setOnClickListener(this)
            btnMinus.setOnClickListener(this)
        }
        fun bindData(goodsInfo: GoodsInfo) {
            tvName.text = goodsInfo.name
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    fun setDatas(goodsInfoList:List<GoodsInfo>){
           this.goodsList = goodsInfoList
           notifyDataSetChanged()
    }

    var goodsList:List<GoodsInfo> = ArrayList()

    override fun getCount(): Int {
        return goodsList.size
    }

    override fun getItem(position: Int): Any {
        return goodsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var goodsItemHolder:GoodsItemHolder?
        var itemView:View?
        if(convertView==null){
            itemView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false)
            goodsItemHolder = GoodsItemHolder(itemView)
            itemView.tag = goodsItemHolder
        }else{
            itemView = convertView
            goodsItemHolder = itemView.tag as GoodsItemHolder
        }
        goodsItemHolder.bindData(goodsList[position])
        return itemView
    }

    override fun getHeaderView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val goodsInfo:GoodsInfo = goodsList.get(position)
        val typeName = goodsInfo.typeName
        val textView = LayoutInflater.from(context).inflate(R.layout.item_type_header, parent, false) as TextView
        textView.text = "我是商品类别1"
        textView.setTextColor(Color.BLACK)
        return textView
    }

    override fun getHeaderId(position: Int): Long {
        val goodsInfo:GoodsInfo = goodsList.get(position)
        return goodsInfo.typeId.toLong()
    }

}