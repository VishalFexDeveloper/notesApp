package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.logging.Handler

class SpashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)


        val sharedPreferences = getSharedPreferences("checkData", MODE_PRIVATE)

        val check = sharedPreferences.getBoolean("check",false)

        android.os.Handler().postDelayed(Runnable {

            if (check){
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                startActivity(Intent(this,SingUp_Activity::class.java))

            }

            finish()



        },2000)

    }
}