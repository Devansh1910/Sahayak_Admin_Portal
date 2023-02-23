package com.devanshsaxena.onestoreadmin.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.devanshsaxena.onestoreadmin.databinding.ActivityRegistrationBinding
import com.devanshsaxena.onestoreadmin.model.AdminUserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            openLogin()
        }


        binding.signup.setOnClickListener {
            validateUser()
        }

    }

    private fun validateUser() {
        if(binding.userName.text!!.isEmpty() || binding.userPhoneNumber.text!!.isEmpty() || binding.userAddress.text!!.isEmpty()) {
            Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
        } else {
            val handler = Handler(Looper.getMainLooper())
            val delayMillis = 1 * 60 * 1000
            handler.postDelayed({
                showCompletionDialog()
            }, delayMillis.toLong())
            Toast.makeText(this, "Registration will complete in 15 minutes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCompletionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registration completed")
        builder.setMessage("Your registration is now complete. Please proceed to login.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            storeData()
            openLogin()
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }


    private fun storeData() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        val preferences = this.getSharedPreferences("usersadmin", MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString("number",binding.userPhoneNumber.text.toString())
        editor.putString("name",binding.userName.text.toString())
        editor.putString("address",binding.userAddress.text.toString())
        editor.apply()

        val data = AdminUserModel(useradminName = binding.userName.text.toString(),useradminPhoneNumber = binding.userPhoneNumber.text.toString(), useradminAddress = binding.userAddress.text.toString() )

    Firebase.firestore.collection("usersadmin").document(binding.userPhoneNumber.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"User Registered Successfully", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()

            }
            .addOnFailureListener{
                Toast.makeText(this,"Something went wrong..", Toast.LENGTH_SHORT).show()
                builder.dismiss()
            }
    }
    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}