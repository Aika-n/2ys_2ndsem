package com.example.itapplication
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.*
class MannerFragment : Fragment() {

    lateinit var mMannerAdapter: MannerAdapter
    lateinit var mnumList: ArrayList<num>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_mannerfragment,container,false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)


        val listView = view.findViewById<ListView>(R.id.list_view)

//データベースのインスタンス取得（初期化）(getInstance()),親ｲﾝｽﾀﾝｽ（getReference("")）
        val database = FirebaseDatabase.getInstance().getReference()

        //データベース内容保存map
        var mannermap = mutableMapOf<String?, String?>()

        //データベース階層
        var str1 = "manners/"
        //データベース読み込み
        database.child("$str1").addValueEventListener(object : ValueEventListener {

            //データベース読み込み成功時の処理
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //dataSnapshotのデータをmap変数に入力
                for (dataSnapshot in dataSnapshot.children) {
                    mannermap.put(dataSnapshot.getKey(),
                    dataSnapshot.getValue(String::class.java))
                }

                var i = 1
                mannermap.forEach{ (key, value) ->
                    when {
                        i == 1 -> {
                            mnumList = arrayListOf((num("$key", "$value")))
                        }
                        else -> {
                            mnumList.add(num("$key", "$value"))
                        }
                    }
                    i++
                }

                mMannerAdapter = MannerAdapter(requireContext(), mnumList)
                listView.adapter = mMannerAdapter

            }
            //データベース読み込み失敗時の処理
            override fun onCancelled(error: DatabaseError) {
//                textView.text = "読み込み失敗"
                TODO("Not yet implemented")
            }
        })
//        // データ一覧の実装
//        val dog = num("イヌ", 3, R.drawable.dog)
//        val cat = num("ネコ", 2, R.drawable.cat)
//        val elephant = num("ゾウ", 10, R.drawable.elephant)
//        val horse = num("ウマ", 4, R.drawable.horse)
//        val lion = num("ライオン", 6, R.drawable.lion)
//        mnumList = arrayListOf(dog, cat, elephant, horse, lion)
        // CustomAdapterの生成と設定
//        mMannerAdapter = MannerAdapter(requireContext(), mnumList)
//        listView.adapter = mMannerAdapter
    }
}