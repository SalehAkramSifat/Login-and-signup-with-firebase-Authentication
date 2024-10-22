package com.sifat.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sifat.loginandsignup.databinding.ActivityLogin2Binding
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar

class LoginActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = binding.progressBar

        binding.sign.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidEmail(email) && isValidPassword(password)) {
                    loginUser(email, password)
                } else {
                    Toast.makeText(this, "Enter Correct email/Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter email/password", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun loginUser(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login is Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login is Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

}
