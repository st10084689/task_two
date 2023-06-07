package com.example.poe_task_2

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

class MainView : AppCompatActivity() {

    private lateinit var list: TextView
    private lateinit var about: TextView
    private lateinit var main: TextView

    private val TAG = "Main View"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        init()
    }

    private fun init() {//init class to set up the variables
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = MainViewFragment()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()

        list = findViewById(R.id.listText)
        about = findViewById(R.id.AboutTxt)
        main = findViewById(R.id.MainTxt)

        list.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val toList = TaskFragment()
            fragmentTransaction.replace(R.id.fragmentContainer, toList)
            fragmentTransaction.commit()
            main.background = null
            about.background = null
            list.setBackgroundResource(R.drawable.green_circle)
        }

        about.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val toAbout = AboutFragment()
            fragmentTransaction.replace(R.id.fragmentContainer, toAbout)
            fragmentTransaction.commit()
            main.background = null
            list.background = null
            about.setBackgroundResource(R.drawable.green_circle)
        }

        main.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val toMain = MainViewFragment()
            fragmentTransaction.replace(R.id.fragmentContainer, toMain)
            fragmentTransaction.commit()
            about.background = null
            list.background = null
            main.setBackgroundResource(R.drawable.green_circle)
        }
    }
}