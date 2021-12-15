package com.example.itapplication

import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_datails_recipe.*


// interfaceの実装
interface CustomAdapterListener {
    fun clicked(animal: Recipe)
}

class CustomAdapter(context: Context, var mAnimalList: List<Recipe>)
    : ArrayAdapter<Recipe>(context, 0, mAnimalList) {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Animalの取得
        val animal = mAnimalList[position]

        // レイアウトの設定
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false)
        }


        //ストレージインスタンス取得(初期化)
        val storageref: StorageReference = FirebaseStorage.getInstance().reference

        //ストレージ画像リンク呼び出し
        var storageimage = storageref.child("image/" + animal.name + ".jpg")

        var imageView : ImageView
        imageView  = view!!.findViewById<ImageView>(R.id.image)

        //ストレージ画像Imageviewへ表示
        GlideApp.with(context).load(storageimage).into(imageView)

        //各Viewの設定
//        val imageView = view?.findViewById<ImageView>(R.id.image)
//        imageView?.setImageResource(animal.imageId)

        val name = view?.findViewById<TextView>(R.id.recipename)
        name?.text = "${animal.name}"

        val time = view?.findViewById<TextView>(R.id.time)
        time?.text = "${animal.time} 分"


        val favorite = view?.findViewById<TextView>(R.id.favorite)
        favorite?.text = animal.favorite.toString()

        val button = view?.findViewById<ImageButton>(R.id.button)
        button?.setOnClickListener {
            //ユーザがお気に入りしているとき
//            if(button.()){
//                button.setImageResource(R.drawable.heart_off)
//            }else{
                button.setImageResource(R.drawable.heart_on)
//            }
        }

        val recipelist = view?.findViewById<TextView>(R.id.clicktextView)
        if (recipelist != null) {
            recipelist.setOnClickListener{

//                Recipepoppo()
                
//                val reciname =  mAnimalList[position].name.toString()
//                wakannu(reciname)

                val intent = Intent(context, Datails_recipe::class.java)
                intent.putExtra("sendrecipename", mAnimalList[position].name.toString())
                context.startActivity(intent)
            }
        }

//        val recipelist = view?.findViewById<ListView>(R.id.listview)
//        if (recipelist != null) {
//            recipelist.setOnItemClickListener { parent, view, position, id ->
//                val intent = Intent(context, datails_recipe::class.java)
//                intent.putExtra("sendrecipename", mAnimalList[position].toString());
//                context.startActivity(intent)
//            }
//        }
        return view!!
    }
//
//    // リスト更新用の関数を実装
//    fun updateAnimalList(animalList: List<Recipe>) {
//        mAnimalList = animalList
//        // 再描画
//        notifyDataSetChanged()
//    }

//    fun wakannu(recipename: String) : Fragment {
//
//        val bundle = Bundle()
//        // Key/Pairの形で値をセットする
//        bundle.putString("KEY_POSITION", recipename)
//        // Fragmentに値をセットする
//        val fragment = Datails_recipe_fragment()
//        fragment.setArguments(bundle)
//
//        return fragment
//
//    }

}


//class Recipepoppo : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val DatailsFragment = Datails_recipe_fragment()
//        rereplaceFragment(DatailsFragment)
//    }
//        fun rereplaceFragment(fragment: Fragment) {
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container, fragment)
//            fragmentTransaction.commit()
//
//    }
//}