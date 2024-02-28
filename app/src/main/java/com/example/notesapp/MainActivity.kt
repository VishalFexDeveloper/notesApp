package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityAddBinding
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(),this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addBtn.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logn_out ->{
                    startActivity(Intent(this,SingUp_Activity::class.java))
                    finish()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}