package com.xxs.eat.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxs.eat.databinding.FragmentGoodsBinding
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoodsBinding.inflate(inflater, container, false)
        goodsFragmentPresenter = GoodsFragmentPresenter(this)
        rvGoodsType = binding.rvGoodsType
        slhlv = binding.slhlv
        goodsAdapter = GoodsAdapter(activity)
        slhlv.adapter = goodsAdapter
        rvGoodsType.layoutManager = LinearLayoutManager(activity)
        rvGoodsType.adapter = GoodsTypeAdapter(activity,this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        goodsFragmentPresenter.getBusinessInfo("1")
    }

    fun onLoadBusinessSuccess(goodsTypeList: List<GoodsTypeInfo>){
        (rvGoodsType.adapter as GoodsTypeAdapter).setDatas(goodsTypeList)

    }


}