package com.example.itapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_recipe_fragment.*

class RecipeFragmentActivity : androidx.fragment.app.Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        RecipeFragmentActivitys()
        return inflater.inflate(R.layout.activity_recipe_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager = (activity as FragmentActivity).supportFragmentManager

        pager.adapter = TabAdapter(fragmentManager)
        tab_layout.setupWithViewPager(pager)
    }


}