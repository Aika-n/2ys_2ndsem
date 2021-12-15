package com.example.itapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home_fragment1.*
import kotlinx.android.synthetic.main.activity_home_fragment1.homeimageButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class HomeFragment1 : Fragment() {

    override fun onCreateView(


        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_home_fragment1, container, false)
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
                homef1textView2.text = month.toString() + "月のレシピ:" + seasonrecipe["1"]
                //ストレージ画像呼び出し先
                var storageimage = storageref.child("image/" + seasonrecipe["1"] + ".jpg")
                //ストレージ画像Imageviewへ表示
                GlideApp.with(this@HomeFragment1).load(storageimage).into(homeimageButton)
            }
//データベース読み込み失敗時の処理
            override fun onCancelled(error: DatabaseError) {
                homef1textView2.text = "読み込み失敗"
            }
        })

        //イメージボタンクリック
        homeimageButton.setOnClickListener {
            val intent = Intent(activity, Datails_recipe::class.java)
            //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
            intent.putExtra("sendrecipename", seasonrecipe["1"])
            //画面遷移を開始
            startActivity(intent)
        }
    }
}


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //データベースのインスタンス取得（初期化）(getInstance()),親ｲﾝｽﾀﾝｽ（getReference("")）
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
//        var month = calendar.get(Calendar.MONTH) + 1
//        //データベース階層
//        var str1 = "recipetab/season/".plus(month.toString() + "月")
//        //データベースのデータ取得
//        database.child("$str1").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                //取得データをmap変数へ入力
//                for (dataSnapshot in dataSnapshot.children) {
//                    seasonrecipe.put(
//                        dataSnapshot.getKey(),
//                        dataSnapshot.getValue(String::class.java)
//                    )
//                }
//                hf1recipe = seasonrecipe["1"]
//                hf2recipe = seasonrecipe["2"]
//                hf3recipe = seasonrecipe["3"]
//                setFragmentResult(
//                    "hfrequest_key", bundleOf(
//                        "hf1recipe_key" to hf1recipe,
//                        "hf2recipe_key" to hf2recipe,
//                        "hf3recipe_key" to hf3recipe,
//                        "hfmonth_key" to month.toString()
//                    )
//                )
//                //季節のレシピで今月の１番目のレシピ名を入力
//                homef1textView2.text = month.toString() + "月のレシピ:" + seasonrecipe["1"]
//                //ストレージ画像呼び出し先
//                var storageimage = storageref.child("レシピ画像/" + hf1recipe + ".jpg")
//                //ストレージ画像Imageviewへ表示
//                GlideApp.with(this@HomeFragment1).load(storageimage).into(homeimageButton)
////                setFragmentResult(
////                    "hfrequest_key", bundleOf(
////                        "hf1recipe_key" to hf1recipe,
////                        "hf2recipe_key" to hf2recipe,
////                        "hf3recipe_key" to hf3recipe,
////                        "hfmonth_key" to month
////                    )
////                )
//            }
//
//            //データベース読み込み失敗時の処理
//            override fun onCancelled(error: DatabaseError) {
//                hf1recipe = "読み込み失敗"
//            }
//        })
//
//        //イメージボタンクリック
//        homeimageButton.setOnClickListener {
//            val intent = Intent(activity, Datails_recipe::class.java)
//            //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
//            intent.putExtra("sendrecipename", hf1recipe)
//            //画面遷移を開始
//            startActivity(intent)
//        }
//    }
//}