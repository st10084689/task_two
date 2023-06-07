package com.example.poe_task_2

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var sAuth: FirebaseAuth
    private lateinit var UserEmail: EditText
    private lateinit var UserPassword: EditText
    private lateinit var UserConfirmPassword: EditText
    private val TAG = "SignIn"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
        sAuth = FirebaseAuth.getInstance()
    }

    private fun init() {//initializes the variables
        UserEmail = findViewById(R.id.edtTxtEmail)
        UserPassword = findViewById(R.id.SignUpPassword)
        UserConfirmPassword = findViewById(R.id.SignUpConfirmPassword)
    }

    fun SignUpUser(view: View) {//onclick to sign in the user
        Log.d(TAG, "SignUpUser: Working")
        try {
            val email = UserEmail.text.toString().trim()
            val password = UserPassword.text.toString().trim()

            Log.d(TAG, "SignUpUser: $email")
            Log.d(TAG, "SignUpUser: $password")

            if (TextUtils.isEmpty(email)) {
                Log.d(TAG, "making a toast ")
                Toast.makeText(this, "Entered email slot is empty", Toast.LENGTH_LONG).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Entered password slot is empty", Toast.LENGTH_SHORT).show()
            }

            sAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task: Task<AuthResult> ->
                    Log.d(TAG, "onComplete: create")
                    if (task.isSuccessful) {
                        Log.d(TAG, "is successful")
                        val toMainView = Intent(this@SignUp, MainView::class.java)
                        startActivity(toMainView)
                        // Optional: Reset all fields
                    } else {
                        Toast.makeText(this@SignUp, "Login failed", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this@SignUp, "error", Toast.LENGTH_LONG).show()
        }
    }
}

