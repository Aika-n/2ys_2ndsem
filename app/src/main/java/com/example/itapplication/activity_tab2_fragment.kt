package com.example.itapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tab2.*

class activity_tab2_fragment : Fragment(), CustomAdapterListener {

    lateinit var mCustomAdapter: CustomAdapter
    lateinit var mAnimalList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.activity_tab2, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // CustomAdapterの生成と設定
        val listView : ListView = view.findViewById<ListView>(R.id.listview2)

        //データベースのインスタンス取得(初期化)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        //データベース内容保存map
        var meetmap = mutableMapOf<String?, String?>()

        var meet = "recipetab/".plus("meet")
//材料
        database.child("$meet").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (dataSnapshot in datasnapshot.children) {
                    meetmap.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }
                var i = 1
                meetmap.forEach { (key, value) ->

                    when {
                        i == 1 -> {
                            mAnimalList = arrayListOf(Recipe("$value", 30, 3))

                        }
                        else -> {
                            mAnimalList.add(Recipe("$value", 30, 3))

                        }
                    }

                    i++
                }

//        // MainActivity自身をListenerとして渡す
                mCustomAdapter = CustomAdapter(requireContext(), mAnimalList)
                listView.adapter = mCustomAdapter


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun clicked(animal: Recipe) {
        mAnimalList.add(animal)
    }


}