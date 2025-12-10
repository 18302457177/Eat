package com.xxs.eat.ui.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso
import com.xxs.eat.R
import com.xxs.eat.model.beans.GoodsInfo
import com.xxs.eat.ui.activity.BusinessActivity
import com.xxs.eat.ui.fragment.GoodsFragment
import com.xxs.eat.utils.PriceFormater
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter

class GoodsAdapter(val context: FragmentActivity?,val goodsFragment: GoodsFragment): BaseAdapter(), StickyListHeadersAdapter {

    companion object val DURAION : Long = 1000
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

         lateinit var goodsInfo:GoodsInfo
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
            this.goodsInfo = goodsInfo
            Picasso.with( context).load(goodsInfo.icon).into(ivIcon)
            tvName.text = goodsInfo.name
            tvForm.text = goodsInfo.form
            tvMonthSale.text = "月售${goodsInfo.monthSaleNum}"
            tvNewPrice.text = PriceFormater.format(goodsInfo.newPrice?.toFloat() ?: 0.0f )
            tvOldPrice.text = "￥${goodsInfo.oldPrice}"
            tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            if(goodsInfo.oldPrice>0){
                tvOldPrice.visibility = View.VISIBLE
            }else{
                tvOldPrice.visibility = View.GONE

            }
            tvCount.text = goodsInfo.count.toString()
            if(goodsInfo.count>0){
                tvCount.visibility = View.VISIBLE
                btnMinus.visibility = View.VISIBLE
            }else{
                tvCount.visibility = View.INVISIBLE
                btnMinus.visibility = View.INVISIBLE
            }

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
                else->{
                    processRedDotCount(isAdd)
                }
            }
        }

        private fun doMinusOperation() {
            var count = goodsInfo.count
            if(count == 1){
                val animationSet: AnimationSet = getHidenAnimation()
                tvCount.startAnimation(animationSet)
                btnMinus.startAnimation(animationSet)
            }
            count--
            goodsInfo.count = count
            notifyDataSetChanged()
        }

        private fun doAddOperation() {
            var count = goodsInfo.count
            if(count == 0){
                val animationSet: AnimationSet = getShowAnimation()
                tvCount.startAnimation(animationSet)
                btnMinus.startAnimation(animationSet)
            }
            count++
            goodsInfo.count = count
            notifyDataSetChanged()
            var ib = ImageButton( context)
            ib.setBackgroundResource(R.drawable.button_add)
            val srcLocation = IntArray(2)
            btnAdd.getLocationInWindow(srcLocation)
            ib.x = srcLocation[0].toFloat()
            ib.y = srcLocation[1].toFloat()
            (goodsFragment.activity as BusinessActivity).addImageButton(ib,btnAdd.width,btnAdd.height)
            val destLocation = (goodsFragment.activity as BusinessActivity).getCartLocation()
            val parabolaAnim:AnimationSet = getParabolaAnimation(ib,srcLocation,destLocation)
            ib.startAnimation(parabolaAnim)
        }
        private fun getParabolaAnimation(
            ib: ImageButton,
            srcLocation: IntArray,
            destLocation: IntArray
        ): AnimationSet {
            val parabolaAnim: AnimationSet = AnimationSet(false)
            parabolaAnim.duration = DURAION
            val animationX = TranslateAnimation(
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, destLocation[0].toFloat()-srcLocation[0].toFloat(),
                Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f
            )
            animationX.duration = DURAION
            parabolaAnim.addAnimation(animationX)
            val animationY = TranslateAnimation(
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,0.0f,
                Animation.ABSOLUTE,destLocation[1].toFloat()-srcLocation[1].toFloat()
            )
            animationY.setInterpolator(AccelerateInterpolator())
            animationY.duration = DURAION
            parabolaAnim.addAnimation(animationY)
            parabolaAnim.setAnimationListener(object:Animation.AnimationListener{
                override fun onAnimationEnd(animation: Animation?) {
                    val parent = ib.parent
                    if(parent!=null){
                        (parent as ViewGroup).removeView( ib)
                    }
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
            return parabolaAnim
        }
        fun getHidenAnimation(): AnimationSet {
            var animationSet: AnimationSet = AnimationSet(false)
            animationSet.duration=DURAION
            val alphaAnim: Animation = AlphaAnimation(1.0f, 0.0f)
            alphaAnim.duration = DURAION
            animationSet.addAnimation(alphaAnim)
            val rotateAnim:Animation = RotateAnimation(720.0f,0.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
            rotateAnim.duration = DURAION
            animationSet.addAnimation(rotateAnim)
            val translateAnim:Animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                2.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )

            translateAnim.duration = DURAION
            animationSet.addAnimation(translateAnim)
            return animationSet
        }
        fun getShowAnimation(): AnimationSet {
            var animationSet: AnimationSet = AnimationSet(false)
            animationSet.duration=DURAION
            val alphaAnim: Animation = AlphaAnimation(0.0f, 1.0f)
            alphaAnim.duration = DURAION
            animationSet.addAnimation(alphaAnim)
            val rotateAnim:Animation = RotateAnimation(0.0f,720.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
            rotateAnim.duration = DURAION
            animationSet.addAnimation(rotateAnim)
            val translateAnim:Animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                2.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
            translateAnim.duration = DURAION
            animationSet.addAnimation(translateAnim)
            return animationSet
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