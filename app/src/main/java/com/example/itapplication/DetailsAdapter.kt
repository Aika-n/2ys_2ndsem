package com.example.itapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


// interfaceの実装
//interface DatailsAdapterListener {
//    fun clicked(datails: Datails)
//}

class DatailsAdapter(context: Context, var mDataisList: List<Datails>)
    : ArrayAdapter<Datails>(context, 0, mDataisList) {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val datails = mDataisList[position]

        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.ingredientlist_item, parent, false)
        }

        // 各Viewの設定
        val zairyotextView = view?.findViewById<TextView>(R.id.zairyotextView)
        zairyotextView?.text = "${datails.rname}"

        val tantextView = view?.findViewById<TextView>(R.id.tanisitextView)
        tantextView?.text = "${datails.tanisi}"

        return view!!
    }

}
