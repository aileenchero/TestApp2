@file:Suppress("DEPRECATION")

package com.example.testapp2

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload_photos.*
import java.io.IOException
import java.util.*


class UploadPhotos : AppCompatActivity(),View.OnClickListener {
    private val PICK_IMAGE_REQUEST = 1234

    private var filePath: Uri? = null
    private var storage:FirebaseStorage? = null
    internal var storageReference:StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photos)


        storage=FirebaseStorage.getInstance()
        storageReference=storage!!.reference

        btnChoose.setOnClickListener (this)
        btnUpload.setOnClickListener(this)



    }



    override fun onClick(p0: View) {
        if (p0 === btnChoose)
            showFileChooser()
        else if(p0=== btnUpload)
            uploadFile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST &&
                resultCode== Activity.RESULT_OK &&
                data != null && data.data != null)
        {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filePath)
                imageView_uploads!!.setImageBitmap(bitmap)
            }catch (e:IOException)
            {
                e.printStackTrace()
            }
        }
    }


    private fun uploadFile() {
        if (filePath !=null)
        {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val imageRef = storageReference!!.child("images/"+ UUID.randomUUID().toString())
            imageRef.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "File Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapShot ->
                    val progress =
                        100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                }


        }


    }

    private fun showFileChooser() {
        val intent= Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"SELECT PHOTO"),PICK_IMAGE_REQUEST)
    }
}