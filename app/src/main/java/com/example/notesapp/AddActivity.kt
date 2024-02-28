package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.notesapp.databinding.ActivityAddBinding
import java.io.ByteArrayOutputStream

class AddActivity : AppCompatActivity() {



    private lateinit var binding: ActivityAddBinding
    private lateinit var db: NotesDatabaseHelper

    companion object{
        var imgage:ByteArray = byteArrayOf()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)



        binding.saveBtn.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val contents = binding.WriteEditText.text.toString()

            if (title.isEmpty() && contents.isEmpty()){
                if (title.isEmpty()){
                    binding.titleEditText.error = "please title name"
                }else{
                    binding.WriteEditText.error = "please contents "
                }
            }else{

                    val notes =  NotesData(1, title,contents,imgage)
                    db.insertNotes(notes)
                    finish()
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()

            }

        }

        binding.iconImgAdd.setOnClickListener {
            checkGalleryPermission()
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkGalleryPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
               android.Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                14
            )
        }
    }

    @SuppressLint("IntentReset")
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                        binding.iconImgAdd.setImageBitmap(myBitmap)
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


