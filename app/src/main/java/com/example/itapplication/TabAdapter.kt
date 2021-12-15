package com.example.itapplication

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    //指定した位置にあるFragmentを返す
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return activity_tab1_fragment() }
            1 -> { return activity_tab2_fragment() }
            2 -> { return activity_tab3_fragment() }
            3 -> { return activity_tab4_fragment() }
            4 -> { return activity_tab5_fragment() }
            5 -> { return activity_tab6_fragment() }
            else ->  { return activity_tab1_fragment() }
        }
    }

    //全体のページ数
    override fun getCount(): Int {
        return 6
    }

    //指定した位置にあるタブ用のタイトルを返す
    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> { return "季節" }
            1 -> { return "肉"}
            2 -> { return "魚"}
            3 -> { return "汁物"}
            4 -> { return "ご飯もの"}
            5 -> { return "その他"}
            else ->  { return "肉" }
        }
    }
}