package com.devanshsaxena.onestoreadmin.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.devanshsaxena.onestoreadmin.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var useradminPhoneNumber: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var loginBtn: CardView
    private lateinit var signup: CardView
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val SHARED_PREF_NAME = "MyPref"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        useradminPhoneNumber = findViewById(R.id.userPhoneNumber)
        progressBar = findViewById(R.id.progressBar)
        loginBtn = findViewById(R.id.loginact)
        signup = findViewById(R.id.signupact)

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

        signup.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            if (useradminPhoneNumber.text.toString().trim().length != 10) {
                useradminPhoneNumber.error = "Valid number is required"
                useradminPhoneNumber.requestFocus()
            } else {
                checkDetail(useradminPhoneNumber.text.toString().trim())
            }
        }
    }

    private fun checkDetail(detail: String) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("usersadmin")
            .whereEqualTo("useradminPhoneNumber", detail)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (!task.result.isEmpty) {
                        otpSend()
                    } else {
                        // detail is not present in the database, show a Toast message
                        Toast.makeText(
                            this@LoginActivity,
                            "${useradminPhoneNumber.text} is not registered.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Toast.makeText(
                            this@LoginActivity,
                            "Moving to Registration...",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed({
                            val i = Intent(this@LoginActivity, RegistrationActivity ::class.java)
                            startActivity(i)
                            finish()
                        }, 1000)
                    }
                }
            }
    }

    private fun otpSend() {
        progressBar.visibility = View.VISIBLE
        loginBtn.visibility = View.VISIBLE

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                progressBar.visibility = View.GONE
                loginBtn.visibility = View.VISIBLE
                Toast.makeText(this@LoginActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                progressBar.visibility = View.VISIBLE
                loginBtn.visibility = View.VISIBLE
                val intent = Intent(this@LoginActivity, OTPActivity::class.java)
                intent.putExtra("phone", useradminPhoneNumber.text.toString().trim())
                intent.putExtra("verificationId", verificationId)
                startActivity(intent)
            }
        }

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+91${useradminPhoneNumber.text.toString().trim()}")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@LoginActivity)
            .setCallbacks(mCallbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}
