package com.example.itapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomePagerAdapter(fm: FragmentManager, private val fragmentList: List<Fragment>) :  FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

//    override fun restoreChildFragmentState(savedInstanceState: Bundle?) {
//        super.restoreState(savedInstanceState)
//    }

//    init {
//        getItem(0)
//    }

    // 表示するフラグメントを制御する
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    // viewPagerにセットするコンテンツ(フラグメントリスト)のサイズ
    override fun getCount(): Int {
        return fragmentList.size
    }
}