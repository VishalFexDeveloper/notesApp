package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivitySingUpBinding

class SingUp_Activity : AppCompatActivity() {

    private lateinit var binding : ActivitySingUpBinding
    lateinit var db : DataBassHelperSingUp

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DataBassHelperSingUp(this)

        val sharedPreferences = getSharedPreferences("checkData", MODE_PRIVATE)
        val edits = sharedPreferences.edit()
        edits.putBoolean("check",false)
        edits.apply()
        val userName = binding.userName
        val userEmail = binding.userEmail
        val userPassword = binding.userPassword
        val userNumber = binding.userNumber

        binding.submitBtn.setOnClickListener {
            try {
                db.writableDatabase.use {
                    db.insertNotes(SingUpModal(1, userName.text.toString(), userEmail.text.toString(), userNumber.text.toString(), userPassword.text.toString()))
                    val sharedPreferences = getSharedPreferences("checkData", MODE_PRIVATE).edit()
                    sharedPreferences.putBoolean("check",true)
                    sharedPreferences.apply()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                Toast.makeText(this, "Submit successful", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.longInTextBtn.setOnClickListener {
            startActivity(Intent(this, LongActivity::class.java))
            finish()
        }

    }
}

