package com.xxs.eat.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxs.eat.databinding.FragmentGoodsBinding
import com.xxs.eat.model.beans.GoodsInfo
import com.xxs.eat.model.beans.GoodsTypeInfo
import com.xxs.eat.presenter.GoodsFragmentPresenter
import com.xxs.eat.ui.adapter.GoodsAdapter
import com.xxs.eat.ui.adapter.GoodsTypeAdapter
import se.emilsjolander.stickylistheaders.StickyListHeadersListView


class GoodsFragment : Fragment() {

    lateinit var binding: FragmentGoodsBinding

    lateinit var goodsFragmentPresenter: GoodsFragmentPresenter
    lateinit var rvGoodsType: RecyclerView
    lateinit var slhlv: StickyListHeadersListView
    lateinit var goodsAdapter: GoodsAdapter
    lateinit var goodsTypeAdapter: GoodsTypeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoodsBinding.inflate(inflater, container, false)
        goodsFragmentPresenter = GoodsFragmentPresenter(this)
        rvGoodsType = binding.rvGoodsType
        slhlv = binding.slhlv
        goodsAdapter = GoodsAdapter(activity,this)
        slhlv.adapter = goodsAdapter
        rvGoodsType.layoutManager = LinearLayoutManager(activity)
        goodsTypeAdapter = GoodsTypeAdapter(activity,this)
        rvGoodsType.adapter = goodsTypeAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        goodsFragmentPresenter.getBusinessInfo("1")
    }

    fun onLoadBusinessSuccess(goodsTypeList: List<GoodsTypeInfo>,allTypeGoodsList:List<GoodsInfo>){
        goodsTypeAdapter.setDatas(goodsTypeList)
        goodsAdapter.setDatas(allTypeGoodsList)
        slhlv.setOnScrollListener(object : AbsListView.OnScrollListener{
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                val oldPosition = goodsTypeAdapter.selectPosition

                val newTypeId = goodsFragmentPresenter.allTypeGoodsList.get(firstVisibleItem).typeId
                val newPosition = goodsFragmentPresenter.getTypePositionByTypeId(newTypeId)
                if (newPosition != oldPosition){
                    goodsTypeAdapter.selectPosition = newPosition
                    goodsTypeAdapter.notifyDataSetChanged()
                }
            }

            override fun onScrollStateChanged(
                view: AbsListView?,
                scrollState: Int
            ) {

            }
        })
    }


}