package com.xxs.eat.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xxs.eat.databinding.FragmentGoodsBinding


class CommentsFragment : Fragment() {

    lateinit var binding: FragmentGoodsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoodsBinding.inflate(inflater, container, false)
        val commentsView = TextView(activity)
        commentsView.text = "商品"
        commentsView.gravity = Gravity.CENTER
        commentsView.setTextColor(Color.BLACK)

        return binding.root
    }


}