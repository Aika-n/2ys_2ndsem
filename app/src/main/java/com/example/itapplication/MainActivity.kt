package com.example.itapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_recipe_fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //buttonを参照
        val homebutton = findViewById<Button>(R.id.HomeButton)
        val recipeButton = findViewById<Button>(R.id.RecipeButton)
        val mannerButton = findViewById<Button>(R.id.MannerButton)

        //FirstFragmentActivityクラスをインスタンス化その下も同様。
        val HomeFragment = HomeFragmentActivity()
        val RecepiFragment = RecipeFragmentActivity()
        val MannerFragment = MannerFragment()

        replaceFragment(HomeFragment)

        //buttonをクリックしたときにreplaceFragmentメソッドを実行
        homebutton.setOnClickListener {
            replaceFragment(HomeFragment)
        }
        recipeButton.setOnClickListener {

            replaceFragment(RecepiFragment)
        }
        mannerButton.setOnClickListener {
            replaceFragment(MannerFragment)
        }
    }

    //R.id.containerに引数で渡されたフラグメントを入れる。
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}


