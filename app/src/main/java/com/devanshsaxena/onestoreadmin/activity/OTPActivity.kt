package com.devanshsaxena.onestoreadmin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devanshsaxena.onestoreadmin.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {

    private var verificationId: String? = null
    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var enableButton: Button
    private var counter = 10
    private var handler = Handler()
    private lateinit var runnable: Runnable

    @SuppressLint("MissingInflateLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextInput()

        verificationId = intent.getStringExtra("verificationId")

        binding.resend.setOnClickListener {
            Toast.makeText(this@OTPActivity, "OTP send successfully.", Toast.LENGTH_SHORT).show()
        }

        binding.verifybtn.setOnClickListener {
            binding.progressBarVerify.visibility = View.VISIBLE
            binding.verifybtn.visibility = View.INVISIBLE
            if (binding.userotp1.text.toString().trim().isEmpty() ||
                binding.userotp2.text.toString().trim().isEmpty() ||
                binding.userotp3.text.toString().trim().isEmpty() ||
                binding.userotp4.text.toString().trim().isEmpty() ||
                binding.userotp5.text.toString().trim().isEmpty() ||
                binding.userotp6.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(this@OTPActivity, "OTP is not valid!", Toast.LENGTH_SHORT).show()
            } else {
                verificationId?.let { verificationId ->
                    val code = binding.userotp1.text.toString().trim() +
                            binding.userotp2.text.toString().trim() +
                            binding.userotp3.text.toString().trim() +
                            binding.userotp4.text.toString().trim() +
                            binding.userotp5.text.toString().trim() +
                            binding.userotp6.text.toString().trim()

                    val credential = PhoneAuthProvider.getCredential(verificationId, code)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val preferences = this.getSharedPreferences("useradmin", MODE_PRIVATE)
                                val editor = preferences.edit()

                                editor.putString("number",intent.getStringExtra("phone"))
                                editor.apply()

                                binding.progressBarVerify.visibility = View.VISIBLE
                                binding.verifybtn.visibility = View.INVISIBLE
                                val intent = Intent(this@OTPActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                binding.progressBarVerify.visibility = View.GONE
                                binding.verifybtn.visibility = View.VISIBLE
                                Toast.makeText(this@OTPActivity, "Invalid OTP", Toast.LENGTH_SHORT).show()
                            }
                        }
                }


            }
        }
    }

    private fun editTextInput() {
        binding.userotp1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, Int: Int) {
                binding.userotp2.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.userotp2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, Int: Int) {
                binding.userotp3.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.userotp3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, Int: Int) {
                binding.userotp4.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.userotp4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, Int: Int) {
                binding.userotp5.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.userotp5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, Int: Int) {
                binding.userotp6.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        });
    }
}
