package com.xxs.eat.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.squareup.picasso.Picasso
import com.xxs.eat.R
import com.xxs.eat.model.beans.Seller
import com.xxs.eat.ui.activity.BusinessActivity
import com.xxs.eat.utils.TakeoutApp
import kotlin.jvm.java

class HomeRvAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        val TYPE_TITLE = 0
        val TYPE_SELLER = 1
    }

    override fun getItemViewType(position: Int): Int {
        if(position==0){
            return TYPE_TITLE
        }else{
            return TYPE_SELLER
        }
        return super.getItemViewType(position)
    }

    val mDatas: ArrayList<Seller> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Seller>){
        mDatas.clear()
        mDatas.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when(viewType) {
            TYPE_TITLE -> return TitleHolder(View.inflate(context, R.layout.item_title, null))
            TYPE_SELLER -> return SellerHolder(
                View.inflate(
                    context,
                    R.layout.item_seller,
                    null
                )
            )
            else -> return TitleHolder(
                View.inflate(
                    context,
                    R.layout.item_title,
                    null
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewType = getItemViewType(position)
        when(viewType) {
            TYPE_TITLE -> (holder as TitleHolder).bindData("我是大哥---------------")
            TYPE_SELLER -> (holder as SellerHolder).bindData(mDatas[position-1])
        }
    }

    override fun getItemCount(): Int {
        if(mDatas.size>0){
            return mDatas.size+1
        }else{
            return 0
        }

    }
    inner class SellerHolder(item:View):RecyclerView.ViewHolder(item){
        val tvTile: TextView
        val ivLogo: ImageView
        val rbScore: RatingBar
        val tvSale: TextView
        val tvSendPrice: TextView
        val tvDistance: TextView

        lateinit var mSeller: Seller

        init{
            tvTile = item.findViewById<TextView>(R.id.tv_title)
            ivLogo = item.findViewById<ImageView>(R.id.seller_logo)
            rbScore = item.findViewById<RatingBar>(R.id.ratingBar)
            tvSale = item.findViewById<TextView>(R.id.tv_home_sale)
            tvSendPrice = item.findViewById<TextView>(R.id.tv_home_send_price)
            tvDistance = item.findViewById<TextView>(R.id.tv_home_distance)
            item.setOnClickListener {
                val intent: Intent = Intent(context, BusinessActivity::class.java)
                var hasSelectInfo = false
                val count = TakeoutApp.sInstance.queryCacheSelectedInfoBySellerId(mSeller.id.toInt())
                if(count>0){
                    hasSelectInfo = true
                }
                intent.putExtra("seller",mSeller)
                intent.putExtra("hasSelectInfo",hasSelectInfo)
                context.startActivity(intent)
            }
        }
        @SuppressLint("SetTextI18n")
        fun bindData(seller: Seller){
            this.mSeller = seller
            tvTile.text = seller.name
            Picasso.with( context).load(seller.icon).into(ivLogo)
            rbScore.rating = seller.score.toFloat()
            tvSale.text = "月售${seller.sale}"
            tvSendPrice.text = "起送价￥${seller.sendPrice}/配送费${seller.deliveryFee}"
            tvDistance.text = seller.distance
        }


    }
    var url_map:HashMap<String,String> = HashMap()
    inner class TitleHolder(item:View):RecyclerView.ViewHolder(item){
        val sliderLayout: SliderLayout
        init{
            sliderLayout  = item.findViewById(R.id.slider)

        }
        fun bindData(data:String){
            if(url_map.size==0){
                url_map.put("Hannibal","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg")
                url_map.put("Big Bang Theory","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p851857696.jpg")
                url_map.put("House of Cards","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1910811083.jpg")
                url_map.put("The Flower","https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1910811083.jpg")
                for((key,value) in url_map){
                    var textSlideView: TextSliderView = TextSliderView(context)
                    textSlideView.description(key).image(value)
                    sliderLayout.addSlider(textSlideView)
                }
            }
            
        }


    }

}