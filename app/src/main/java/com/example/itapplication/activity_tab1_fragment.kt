package com.example.itapplication

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.itapplication.*
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_datails_recipe.*
import java.util.*
import kotlin.collections.ArrayList

class activity_tab1_fragment : Fragment(), CustomAdapterListener {

    lateinit var mCustomAdapter: CustomAdapter
    lateinit var mAnimalList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.activity_tab1, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // CustomAdapterの生成と設定
        val listView : ListView = view.findViewById<ListView>(R.id.listview)
//
//        //ストレージインスタンス取得(初期化)
//        val storageref: StorageReference = FirebaseStorage.getInstance().reference
//
//

        //データベースのインスタンス取得(初期化)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        //データベース内容保存map
        var seasonmap = mutableMapOf<String?, String?>()

        val cal: Calendar = Calendar.getInstance()
        val month: Int = cal.get(Calendar.MONTH) + 1

        var season = "recipetab/".plus("season/").plus("$month").plus("月")
//材料
        database.child("$season").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (dataSnapshot in datasnapshot.children) {
                    seasonmap.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }

                var i = 1
                seasonmap.forEach { (key, value) ->

                    when {
                        i == 1 -> {
                            mAnimalList = arrayListOf(Recipe("$value",30,3))
//                            //ストレージ画像リンク呼び出し
//                            var storageimage = storageref.child("image/" + "$value" + ".png")
//                            //ストレージ画像Imageviewへ表示
//                            GlideApp.with(this@activity_tab1_fragment).load(storageimage).into(foodimageView)

                        }
                        else -> {
                            mAnimalList.add(Recipe("$value",30,3))
//                            //ストレージ画像リンク呼び出し
//                            var storageimage = storageref.child("image/" + "$value" + ".png")
//                            //ストレージ画像Imageviewへ表示
//                            GlideApp.with(this@activity_tab1_fragment).load(storageimage).into(foodimageView)

                        }
                    }

//                    println("$key は $value 円です。")
                    i++
                }

//        // MainActivity自身をListenerとして渡す
                mCustomAdapter = CustomAdapter(requireContext(), mAnimalList)
                listView.adapter = mCustomAdapter


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

//            //データベース読み込み失敗時の処理
//            override fun onCancelled(error: DatabaseError) {
//                //トーストで表示
//                Toast.makeText(
//                    baseContext, "読み込み失敗",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
        })






//        // データ一覧の実装
//        val dog = Recipe("七草粥", 10, 30)
//        val cat = Recipe("おかゆ", 2, 20)
//        val elephant = Recipe("モモラ・モラ", 10, 18)
//        val horse = Recipe("シャーク", 4, 11)
//        val lion = Recipe("タピオカ", 6, 100)
//        mAnimalList = arrayListOf(dog, cat, elephant, horse, lion)
//
        // MainActivity自身をListenerとして渡す
//        mCustomAdapter = CustomAdapter(requireContext(), mAnimalList)
//        listView.adapter = mCustomAdapter

//        listView.setOnItemClickListener { parent, view, position, id ->
////            val fragmentManager = (activity as FragmentActivity).supportFragmentManager
////            val adapter = listView.adapter
//
//            val intent = Intent(requireContext(), datails_recipe::class.java)
////          val intent = Intent(this, datails_recipe::class.java)>
////            Toast.makeText(MainActivity.this, countries[position] + "の編集ボタンが押されました", Toast.LENGTH_SHORT).show();
//            intent.putExtra("sendrecipename", mAnimalList[position].toString());

//        }

//        listView.setOnItemClickListener { parent, view, position, id ->
//
//            val adapter = listView.adapter
//            if (adapter is Recipe) {
//
//                val kakeibo = adapter.getItem(position)
//            }
//        }
    }


    override fun clicked(animal: Recipe) {
        mAnimalList.add(animal)
    }
}