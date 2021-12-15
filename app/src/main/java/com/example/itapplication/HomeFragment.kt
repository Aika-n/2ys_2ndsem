package com.example.itapplication

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home_fragment.*
import java.util.*


@Suppress("UNREACHABLE_CODE")
class HomeFragmentActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

//       if( savedInstanceState != null ){
////        val transaction: FragmentTransaction = fragmentManager!!beginTransaction()
//           val transaction =
//               fragmentManager!!.beginTransaction()
//        transaction.replace(R.id.fragment_container_view_tag, HomeFragmentActivity())
//        transaction.commit()
////            // このActivityは復元されたものなので、
////            // state を使って状態を復元する
////            restoreState( savedInstanceState );
//        }
//        HomePagerAdapter.restoreState(savedInstanceState)
        return inflater.inflate(R.layout.activity_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //データベースのインスタンス取得（初期化）(getInstance()),親ｲﾝｽﾀﾝｽ（getReference("")）
        val database = FirebaseDatabase.getInstance().getReference()
        //ストレージインスタンス取得（初期化）
        val storageref = FirebaseStorage.getInstance().reference
        //データベース内容保存map
        var tisiki = mutableMapOf<String?,String?>()
        //今日の日付取得
        val calendar = Calendar.getInstance()
        //今月の月
        val month= calendar.get(Calendar.MONTH) + 1
        //豆知識ランダム変数
        var mamerandom = 1
        //カウント用
        var count = 1
        //データベース階層
        var str1 = "season/".plus(month.toString() + "月/")
        database.child("$str1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hometextView1.text = month.toString() + "月の豆知識"
                for (dataSnapshot in dataSnapshot.children) {
                    tisiki.put(dataSnapshot.getKey(), dataSnapshot.getValue(String::class.java))
                }
//                setFragmentResult(
//                    "hfrequest_mamekey", bundleOf(
//                        "hfrecipe_mamekey" to "おせち ＜一の重＞"
//                    ))
                //豆知識ランダム変数
                mamerandom = (1..Integer.parseInt(tisiki.size.toString())).random()
//                hometextView2.text = tisiki["豆知識" + mamerandom]
                //ランダム変数の豆知識をテキストに表示
                for((k,v) in tisiki){
                    if(count == mamerandom){
                        hometextView2.text = tisiki["$k"]
                        //texttviewのスクロール
                        hometextView2.setMovementMethod(ScrollingMovementMethod.getInstance())
                        //ストレージ画像呼び出し先
                        var storageimage = storageref.child("image/" + k + ".jpg")
                        //ストレージ画像Imageviewへ表示
                        GlideApp.with(this@HomeFragmentActivity).load(storageimage).into(imageView3)
                        break
                    }else{
                        count++
                    }
                }
            }
            //データベース読み込み失敗時の処理
            override fun onCancelled(error: DatabaseError) {
                hometextView2.text =  "読み込み失敗"
            }
        })
        val fragments1 = arrayListOf(HomeFragment1(), HomeFragment2(), HomeFragment3())
        val adapter = (activity as FragmentActivity).supportFragmentManager
        viewPager1.adapter = HomePagerAdapter(adapter, fragments1)



    }

}