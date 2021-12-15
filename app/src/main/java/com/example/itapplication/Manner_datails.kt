package com.example.itapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_datails_recipe.*
import kotlinx.android.synthetic.main.activity_manner_details.*
class Manner_details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_manner_details)
        var non = intent.getStringExtra("send_title")
        mannerdatailstitle.text = non

        var mannertext = findViewById<TextView>(R.id.mannerdatailstext)

        var mannerimage = findViewById<ImageView>(R.id.datailsmannerimageview)
        //ストレージインスタンス取得(初期化)
        val storageref: StorageReference = FirebaseStorage.getInstance().reference

        //ストレージ画像リンク呼び出し
        var storageimage = storageref.child("image/" + non + ".jpg")

        //ストレージ画像Imageviewへ表示
        GlideApp.with(this).load(storageimage).into(mannerimage)

        //データベースのインスタンス取得(初期化)
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        //データベース内容保存map
        var datailsmannermap = mutableMapOf<String?, String?>()

        var manner = "manners/"//.plus("正しい和食の食べ方")

        //材料
        database.child("$manner").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                for (dataSnapshot in datasnapshot.children) {
                    datailsmannermap.put(
                        dataSnapshot.getKey(),
                        dataSnapshot.getValue(String::class.java)
                    )
                }
                mannertext.text = datailsmannermap["$non"]

                //textViewのスクロール
                mannertext.setMovementMethod(ScrollingMovementMethod.getInstance())

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


    }
}