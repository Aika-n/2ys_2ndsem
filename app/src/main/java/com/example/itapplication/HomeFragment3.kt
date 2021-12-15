package com.example.itapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home_fragment1.*
import kotlinx.android.synthetic.main.activity_home_fragment2.*
import kotlinx.android.synthetic.main.activity_home_fragment2.homeimageButton
import kotlinx.android.synthetic.main.activity_home_fragment3.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import java.util.*

class HomeFragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home_fragment3, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (container != null) {
            container.removeAllViews()
        }

        //データベースのインスタンス取得（初期化）(getInstance()),親ｲﾝｽﾀﾝｽ（getReference("")）
        val database = FirebaseDatabase.getInstance().getReference()
        //ストレージインスタンス取得（初期化）
        val storageref = FirebaseStorage.getInstance().reference
        //データベース内容保存map
        var seasonrecipe = mutableMapOf<String?, String?>()
        //今日の日付取得
        val calendar = Calendar.getInstance()
        //今月の月取得
        var month = calendar.get(Calendar.MONTH) + 1
        //データベース階層
        var str1 = "recipetab/season/".plus(month.toString() + "月")
        //データベースのデータ取得
        database.child("$str1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //取得データをmap変数へ入力
                for (dataSnapshot in dataSnapshot.children) {
                    seasonrecipe.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }
                //季節のレシピで今月の１番目のレシピ名を入力
                homef3textView2.text = month.toString() + "月のレシピ:" + seasonrecipe["3"]
                //ストレージ画像呼び出し先
                var storageimage = storageref.child("image/" + seasonrecipe["3"] + ".jpg")
                //ストレージ画像Imageviewへ表示
                GlideApp.with(this@HomeFragment3).load(storageimage).into(homeimageButton)
            }
            //データベース読み込み失敗時の処理
            override fun onCancelled(error: DatabaseError) {
                homef3textView2.text = "読み込み失敗"
            }
        })

        //イメージボタンクリック
        homeimageButton.setOnClickListener {
            val intent = Intent(activity, Datails_recipe::class.java)
            //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
            intent.putExtra("sendrecipename", seasonrecipe["3"])
            //画面遷移を開始
            startActivity(intent)
        }
    }
}


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //データベースのインスタンス取得（初期化）(getInstance()),親ｲﾝｽﾀﾝｽ（getReference()）
//        val database = FirebaseDatabase.getInstance().getReference()
//        //ストレージインスタンス取得（初期化）
//        val storageref = FirebaseStorage.getInstance().reference
//        //データベース内容保存map
//        var seasonrecipe = mutableMapOf<String?, String?>()
//        //他フラグメントへ渡すデータベースレシピ保存変数
//        var hf1recipe: String? = null
//        var hf2recipe: String? = null
//        var hf3recipe: String? = null
//        //今日の日付取得
//        val calendar = Calendar.getInstance()
//        //今月の月取得
//        var month = calendar.get(Calendar.MONTH) + 12
//        var monthkey :String? = null
//
////        runBlocking {
//        setFragmentResultListener("hfrequest_key2") { requestKey, bundle ->
//            hf1recipe = bundle.getString("hf1recipe_key2")
//            hf2recipe = bundle.getString("hf2recipe_key2")
//            hf3recipe = bundle.getString("hf3recipe_key2")
//            monthkey = bundle.getString("hfmonth_key2")
//        }
////        }
//
//        //データベース階層
//        var str1 = "recipetab/season/".plus(month.toString() + "月")
//        //   データベースのデータ取得
//        database.child("$str1").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                //取得データをmap変数へ入力
//                for (dataSnapshot in dataSnapshot.children) {
//                    seasonrecipe.put(
//                        dataSnapshot.getKey(),
//                        dataSnapshot.getValue(String::class.java)
//                    )
//                }
//
//                when {
//                    hf3recipe != null -> {
//                        homef3textView2.text = monthkey + "月のレシピ:" + hf3recipe
//                        //ストレージ画像呼び出し先
//                        var storageimage =
//                            storageref.child("レシピ画像/" + hf3recipe + ".jpg")
//                        //ストレージ画像Imageviewへ表示
//                        GlideApp.with(this@HomeFragment3).load(storageimage)
//                            .into(homeimageButton)
//                    }
//
//                    seasonrecipe.containsValue("$hf1recipe") == false && seasonrecipe.containsValue("$hf2recipe") == false -> {
//                        hf3recipe = seasonrecipe["1"]
//                        //季節のレシピでかぶってない3番目のレシピ名を入力
//                        homef3textView2.text = month.toString() + "月のレシピ" + hf3recipe
//                        //ストレージ画像呼び出し先
//                        var storageimage = storageref.child("レシピ画像/" + hf3recipe + ".jpg")
//                        //ストレージ画像Imageviewへ表示
//                        GlideApp.with(this@HomeFragment3).load(storageimage).into(homeimageButton)
//                    }
//                    else -> {
//                        if(hf1recipe == seasonrecipe["1"] && hf2recipe == seasonrecipe["2"] ||
//                            hf1recipe == seasonrecipe["2"] && hf2recipe == seasonrecipe["1"]){
//                            hf3recipe == seasonrecipe["3"]
//                        }else if(hf1recipe == seasonrecipe["1"] && hf2recipe == seasonrecipe["3"] || hf1recipe == seasonrecipe["3"] && hf2recipe == seasonrecipe["1"] ){
//                        }
//                        //季節のレシピでかぶってない3番目のレシピ名を入力
//                        homef3textView2.text = month.toString() + "月のレシピ" + hf3recipe
//                        //ストレージ画像呼び出し先
//                        var storageimage = storageref.child("レシピ画像/" + hf3recipe + ".jpg")
//                        //ストレージ画像Imageviewへ表示
//                        GlideApp.with(this@HomeFragment3).load(storageimage).into(homeimageButton)
//                    }
//                }
//
//
//            }
//
//            //データベース読み込み失敗時の処理
//            override fun onCancelled(error: DatabaseError) {
//                homef3textView1.text = "読み込み失敗"
//            }
//        })
//
//
//        //イメージボタンクリック
//        homeimageButton.setOnClickListener {
//            val intent = Intent(activity, Datails_recipe::class.java)
//            //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
//            intent.putExtra("sendrecipename", hf3recipe)
//            //画面遷移を開始
//            startActivity(intent)
//        }
//    }
//}