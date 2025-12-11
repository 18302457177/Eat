package com.xxs.eat.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxs.eat.R
import com.xxs.eat.model.beans.GoodsInfo
import com.xxs.eat.ui.activity.BusinessActivity
import com.xxs.eat.ui.fragment.GoodsFragment
import com.xxs.eat.utils.Constants
import com.xxs.eat.utils.PriceFormater
import com.xxs.eat.utils.TakeoutApp

class CartRvAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val goodsFragment: GoodsFragment

    init{
        goodsFragment = (context as BusinessActivity).fragments[0] as GoodsFragment
    }

    var cartList: ArrayList<GoodsInfo> = arrayListOf()

    fun setCartData(cartList: ArrayList<GoodsInfo>) {
        this.cartList = cartList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return CartItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CartItemHolder).bindData(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    inner class CartItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

         val tvName: TextView
         val tvAllPrice: TextView
         val tvCount: TextView
         val ibAdd: ImageButton
         val ibMinus: ImageButton
         lateinit var goodsInfo: GoodsInfo

        init{
            tvName = itemView.findViewById(R.id.tv_name) as TextView
            tvAllPrice = itemView.findViewById(R.id.tv_type_all_price) as TextView
            tvCount = itemView.findViewById(R.id.tv_count) as TextView
            ibAdd = itemView.findViewById(R.id.ib_add) as ImageButton
            ibMinus = itemView.findViewById(R.id.ib_minus) as ImageButton
            ibAdd.setOnClickListener(this)
        }

        fun bindData(goodsInfo: GoodsInfo) {
            this.goodsInfo = goodsInfo
            tvName.text = goodsInfo.name
            tvAllPrice.text = PriceFormater.format(goodsInfo.newPrice * goodsInfo.count.toFloat())
            tvCount.text = goodsInfo.count.toString()

        }

        override fun onClick(v: View?) {
            var isAdd = false
            when(v?.id){
                R.id.ib_add->{
                    isAdd = true
                    doAddOperation()
                }
                R.id.ib_minus->{
                    isAdd = false
                    doMinusOperation()
                }
            }
            processRedDotCount(isAdd)
            (context as BusinessActivity).updateCarUi()
        }

        private fun doMinusOperation() {
            var count = goodsInfo.count

            if(count == 1){
                cartList.remove(goodsInfo)
                if(cartList.size == 0){
                    (context as BusinessActivity).showOrHideCart()
                }
                TakeoutApp.sInstance.deleteCacheSelectedInfo(goodsInfo.id)
            }else{
                TakeoutApp.sInstance.updateCacheSelectedInfo(goodsInfo.id, Constants.MINUS)
            }
            count--
            goodsInfo.count = count
            notifyDataSetChanged()

            goodsFragment.goodsAdapter.notifyDataSetChanged()
        }

        private fun doAddOperation() {

            var count = goodsInfo.count
            TakeoutApp.sInstance.updateCacheSelectedInfo(goodsInfo.id, Constants.ADD)
            count++
            goodsInfo.count = count
            notifyDataSetChanged()

            goodsFragment.goodsAdapter.notifyDataSetChanged()

        }
        private fun processRedDotCount(add: Boolean): Unit {
            val typeId = goodsInfo.typeId
            val typePosition = goodsFragment.goodsFragmentPresenter.getTypePositionByTypeId(typeId)
            val goodsTypeInfo = goodsFragment.goodsFragmentPresenter.goodsTypeList[typePosition]
            var redDotCount = goodsTypeInfo.redDotCount
            if(add){
                redDotCount++
            }else{
                redDotCount--
            }
            goodsTypeInfo.redDotCount = redDotCount
            goodsFragment.goodsTypeAdapter.notifyDataSetChanged()

        }

    }

}