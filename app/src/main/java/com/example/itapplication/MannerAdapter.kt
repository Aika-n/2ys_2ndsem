package com.example.itapplication
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MannerAdapter(context: Context, var mnumList: List<num>)
    : ArrayAdapter<num>(context, 0, mnumList) {

    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val manner = mnumList[position]
        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.manner_list, parent, false)
        }

        //ストレージインスタンス取得(初期化)
        val storageref: StorageReference = FirebaseStorage.getInstance().reference

        //ストレージ画像リンク呼び出し
        var storageimage = storageref.child("image/" + manner.name + ".jpg")

        var imageView : ImageView
        imageView  = view!!.findViewById<ImageView>(R.id.mannerimage)
        //ストレージ画像Imageviewへ表示
        GlideApp.with(context).load(storageimage).into(imageView)

//
//        if (convertView == null) {
//            view = layoutInflater.inflate(R.layout.manner_list, parent, false)
//        }

         //各Viewの設定
//        val imageView = view?.findViewById<ImageView>(R.id.mannerimage)
//        imageView?.setImageResource(manner.imageId)
        val name = view?.findViewById<TextView>(R.id.name)
        name?.text = manner.name
        //val age = view?.findViewById<TextView>(R.id.age)
        //age?.text = "${animal.age} 才"

//        val air = view?.findViewById<TextView>(R.id.textView2)
//        if (air != null) {
//            air.setOnClickListener {
//                //クラス移動に使うオブジェクト
//                var intent = Intent(context, Manner_details::class.java)
//                intent.putExtra("send_title", mnumList[position].name)
//                //ページ移動をする
//                context.startActivity(intent)
//            }
//        }

        val air = view?.findViewById<TextView>(R.id.textView2)
        if (air != null) {
            air.setOnClickListener{


                val intent = Intent(context, Manner_details::class.java)
                intent.putExtra("send_title", mnumList[position].name.toString())
                context.startActivity(intent)
            }
        }
        return view!!
    }
}