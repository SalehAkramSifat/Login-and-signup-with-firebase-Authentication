package com.sifat.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sifat.loginandsignup.databinding.ActivitySignupBinding
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = binding.progressBar

        binding.directLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity2::class.java)
            startActivity(intent)
            finish()
        }

        binding.signup.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Your Signup Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity2::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val errorMessage = task.exception?.message ?: "Your Signup Unsuccessful"
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter a valid Email/Password", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
