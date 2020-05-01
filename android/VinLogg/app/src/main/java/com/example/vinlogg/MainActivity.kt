package com.example.vinlogg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.vinlogg.Fragments.SearchWineFragment
import com.example.vinlogg.Fragments.SettingsFragment
import com.example.vinlogg.Fragments.MyWineListFragment
import com.example.vinlogg.ModelClassVine.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), FetchingApi {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        onNavigationBarBottom()


    }


    //navigation bar buttom to fragments

    fun onNavigationBarBottom() {




        var bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->

            when(item.itemId) {


                R.id.list -> {
                    println("List")
                    // Toast.makeText(this, "List", Toast.LENGTH_SHORT).show()
                    replaceFagment(MyWineListFragment())


                    return@setOnNavigationItemSelectedListener true
                }
                R.id.vine -> {
                    println("Add vine")
                    // Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()
                    replaceFagment(SearchWineFragment())

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settings -> {
                    println("Setting")
                    // Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    replaceFagment(SettingsFragment())
                    return@setOnNavigationItemSelectedListener true

                }

            }


            false

        }

        replaceFagment(MyWineListFragment())



    }




    fun replaceFagment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }


}


