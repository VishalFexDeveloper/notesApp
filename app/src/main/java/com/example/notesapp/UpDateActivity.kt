package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Video.Media
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.notesapp.databinding.ActivityUpDateBinding
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executor

class UpDateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpDateBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId = -1

    companion object {
        var imgage: ByteArray = byteArrayOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        val position = intent.getIntExtra("note_Id", -1)
        noteId = position

        if (noteId == -1) {
            finish()
            return
        }

        val note = db.getNoteById(noteId)

        val bitmap = BitmapFactory.decodeByteArray(note?.img, 0, note!!.img.size)
        binding.iconImgUpdate.setImageBitmap(bitmap)
        binding.upDateTitleEditText.setText(note.title)
        binding.upDateWriteEditText.setText(note.content)


        binding.iconImgUpdate.setOnClickListener {
            openGallery()
        }

        binding.upDateSaveBtn.setOnClickListener {
            val newTitle = binding.upDateTitleEditText.text.toString()
            val newContents = binding.upDateWriteEditText.text.toString()
            val updatedNote = NotesData(noteId, newTitle, newContents, imgage)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "changes Save ", Toast.LENGTH_SHORT).show()
        }


    }


    @SuppressLint("IntentReset")
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 14)
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 14 && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
                // Check if URI is not null
                uri?.let { selectedUri ->
                    val inputStream = contentResolver.openInputStream(selectedUri)
                    inputStream?.use { stream ->
                        // Decode the stream into a Bitmap
                        val myBitmap = BitmapFactory.decodeStream(stream)
                        // Compress the bitmap to PNG format
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        // Convert compressed bitmap to ByteArray
                        val byteArray = byteArrayOutputStream.toByteArray()
                        // Set the image data and display the image
                        imgage = byteArray
                        binding.iconImgUpdate.setImageBitmap(myBitmap)
                        Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    // Handle case where URI is null
                    Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                // Handle any exceptions that occur during image processing
                Toast.makeText(this, "Error: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle other cases (e.g., user canceled image selection)
            Toast.makeText(this, "Image selection canceled", Toast.LENGTH_SHORT).show()
        }

    }



}




