package com.example.poe_task_2

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var RegisterTxt: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var UserName: TextView
    private lateinit var UserPassword: TextView
    private lateinit var LoginBtn: RelativeLayout
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: created")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        Innit()
    }
    private fun Innit() {
        Log.d(TAG, "Innit: Initialized")
        RegisterTxt = findViewById<TextView>(R.id.txtRegister)
        LoginBtn = findViewById<RelativeLayout>(R.id.imgEnterBtn)
        UserName = findViewById<TextView>(R.id.edtTxtEmail)
        UserPassword = findViewById<TextView>(R.id.edtTxtPassword)

        RegisterTxt.setOnClickListener {
            Log.d(TAG, "onClick: To Sign In")
            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)
        }

        LoginBtn.setOnClickListener {
            // Handle Login button click
            loginUser()
        }
    }

    fun loginClick(view: View) {
        Log.d(TAG, "onClick: To Main")
        loginUser()
    }

    private fun loginUser() {
        Log.d(TAG, "loginUser: Working")
        try {
            val email = UserName.text.toString().trim()
            val password = UserPassword.text.toString().trim()

            // Check for empty fields
            if (TextUtils.isEmpty(email)) {
                Log.d(TAG, "making a toast ")
                Toast.makeText(this@MainActivity, "Entered email slot is empty", Toast.LENGTH_LONG).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this@MainActivity, "Entered password slot is empty", Toast.LENGTH_SHORT).show()
            }

            // Add an onCompleteListener
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Log.d(TAG, "ContentIntent")
                    val toMainView = Intent(this@MainActivity, MainView::class.java)
                    startActivity(toMainView)
                    // Optional: Reset all fields
                } else {
                    Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "error", Toast.LENGTH_LONG).show()
        }
    }
}