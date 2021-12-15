package com.example.itapplication

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_datails_recipe.*


class Datails_recipe : AppCompatActivity() {

    lateinit var mDatailsAdapter: DatailsAdapter
    lateinit var mDataisList: ArrayList<Datails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datails_recipe)


        val listView = findViewById<ListView>(R.id.ingredientList)

//        // CustomAdapterの生成と設定
//        mDatailsAdapter = DatailsAdapter(this, mDataisList)
//        listView.adapter = mDatailsAdapter

        var foodname = intent.getStringExtra("sendrecipename")
        recipename.text = foodname

        //ストレージインスタンス取得(初期化)
        val storageref: StorageReference = FirebaseStorage.getInstance().reference

        //ストレージ画像リンク呼び出し
        var storageimage = storageref.child("image/" + foodname + ".jpg")
        //ストレージ画像Imageviewへ表示
        GlideApp.with(this@Datails_recipe).load(storageimage).into(foodimageView)

        //データベースのインスタンス取得(初期化)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        //データベース内容保存map
        var ingredentmap = mutableMapOf<String?, String?>()

        var ingredent = "recipe/".plus(foodname).plus("/ingredient")

        val lvMenu = findViewById<ListView>(R.id.processList)

        //材料
        database.child("$ingredent").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (dataSnapshot in datasnapshot.children) {
                    ingredentmap.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }
                var i = 1
                ingredentmap.forEach { (key, value) ->

                    when {
                        i == 1 -> {
                            mDataisList = arrayListOf((Datails("$key", "$value")))
                        }
                        else -> {
                            mDataisList.add(Datails("$key", "$value"))
                        }
                    }

//                    println("$key は $value 円です。")
                    i++
                }


                // CustomAdapterの生成と設定
                mDatailsAdapter = DatailsAdapter(this@Datails_recipe, mDataisList)
                listView.adapter = mDatailsAdapter

            }

            //データベース読み込み失敗時の処理
            override fun onCancelled(error: DatabaseError) {
                //トーストで表示
                Toast.makeText(
                    baseContext, "読み込み失敗",
                    Toast.LENGTH_LONG
                ).show()
            }
        })


        var processmap = mutableMapOf<String?, String?>()
        var process = "recipe/".plus(foodname).plus("/process")
        val processList: MutableList<MutableMap<String, String>> = mutableListOf()
        //手順
        database.child("$process").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (dataSnapshot in datasnapshot.children) {
                    processmap.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }

                var num: Int = 1
                var piyoyo = ""
                processmap.forEach { (key, value1) ->


                    var piyoyo = mutableMapOf("pronum" to "$num", "syousai" to "$value1")


                    processList.add(piyoyo)
                    num++
//                    println("$key は $value 円です。")

                }


                val from = arrayOf("pronum", "syousai")
                val to = intArrayOf(android.R.id.text1, android.R.id.text2)

                val adapter = SimpleAdapter(
                    applicationContext,
                    processList,
                    android.R.layout.simple_list_item_2,
                    from,
                    to
                )
                lvMenu.adapter = adapter
            }


            //データベース読み込み失敗時の処理
            override fun onCancelled(error: DatabaseError) {
                //トーストで表示
                Toast.makeText(
                    baseContext, "読み込み失敗",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        listView.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v != null) {
                    v.getParent().requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

        })

        lvMenu.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v != null) {
                    v.getParent().requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

        })

        button.setOnClickListener {
            //ユーザがお気に入りしているとき
//            if(button.()){
//                button.setImageResource(R.drawable.heart_off)
//            }else{
            button.setImageResource(R.drawable.heart_on)
//            }
        }

//
//        listView.setOnTouchListener(object : View.OnTouchListener() {
//            fun onTouch(v: View, event: MotionEvent?): Boolean {
//                v.getParent().requestDisallowInterceptTouchEvent(true)
//                return false
//            }
//        })


    }
}

