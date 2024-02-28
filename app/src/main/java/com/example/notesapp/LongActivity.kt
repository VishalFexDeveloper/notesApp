package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityLongBinding

class LongActivity : AppCompatActivity() {
    lateinit var binding: ActivityLongBinding
    lateinit var db : DataBassHelperSingUp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DataBassHelperSingUp(this)
        binding.singUpTextBtn.setOnClickListener {
           finish()
        }


        val email = binding.longEmailEdit
        val password = binding.longPasswordEdit

        binding.longSubmitBtn.setOnClickListener {
            try {
                val check = db.checkUserExists(email.text.toString(),password.text.toString())

                if (check){
                    Toast.makeText(this, "Submit successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    val sharedPreferences = getSharedPreferences("checkData", MODE_PRIVATE).edit()
                    sharedPreferences.putBoolean("check",true)
                    sharedPreferences.apply()
                    finish()
                }else{
                    Toast.makeText(this, "not email id", Toast.LENGTH_SHORT).show()
                }

            }catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(this, "Error occurred: ${e.message}", Toast.LENGTH_SHORT).show()

            }
        }



    }
}