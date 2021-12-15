package com.example.itapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.itapplication.CustomAdapter
import com.example.itapplication.CustomAdapterListener
import com.example.itapplication.R
import com.example.itapplication.Recipe
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tab5.*

class activity_tab5_fragment : Fragment(), CustomAdapterListener {

    lateinit var mCustomAdapter: CustomAdapter
    lateinit var mAnimalList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.activity_tab5, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val listView : ListView? = view.findViewById<ListView>(R.id.listview5)

        //データベースのインスタンス取得(初期化)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        //データベース内容保存map
        var ricemap = mutableMapOf<String?, String?>()

        var rice = "recipetab/".plus("rice")
//材料
        database.child("$rice").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (dataSnapshot in datasnapshot.children) {
                    ricemap.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }
                var i = 1
                ricemap.forEach { (key, value) ->

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
                listView?.adapter = mCustomAdapter


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