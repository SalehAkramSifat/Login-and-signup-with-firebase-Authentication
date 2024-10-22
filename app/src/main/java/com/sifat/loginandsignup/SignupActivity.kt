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
import androidx.activity.enableEdgeToEdge
import com.sifat.loginandsignup.databinding.ActivityLogin2Binding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.directLogin.setOnClickListener {
            val intent1 = Intent(this, LoginActivity2::class.java)
            startActivity(intent1)
                finish()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = binding.progressBar

    binding.signup.setOnClickListener {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
        if (isValidEmail(email) && isValidPassword(password)) {
            signUp(email, password)
        } else {
            Toast.makeText(this, "Enter Correct email/Password", Toast.LENGTH_SHORT).show()
        }
        } else {
            Toast.makeText(this, "Enter email/password", Toast.LENGTH_SHORT).show()
        }
    }
}
    private fun signUp(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Create account is Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity2::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Create account is Unsuccessful", Toast.LENGTH_SHORT)
                        .show()
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
