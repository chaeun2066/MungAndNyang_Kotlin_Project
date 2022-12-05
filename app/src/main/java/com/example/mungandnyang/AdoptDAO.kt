package com.example.mungandnyang

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AdoptDAO {
    var animalDbReference: DatabaseReference? = null
    var photoDbReference: DatabaseReference? = null
    var reviewDbReference: DatabaseReference? = null
    var storage: FirebaseStorage? = null

    init{
        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        animalDbReference = db.getReference("AdoptList")
        photoDbReference = db.getReference("AdoptPhoto")
        reviewDbReference = db.getReference("ReviewUpload")
        storage = Firebase.storage
    }

    fun insertAnimal(animalVO: AnimalVO?): Task<Void> {
        return animalDbReference!!.push().setValue(animalVO)
    }

    fun insertPhoto(photoVO: PhotoVO): Task<Void>{
        return photoDbReference!!.push().setValue(photoVO)
    }

    fun insertReview(uploadVO: UploadVO): Task<Void>{
        return reviewDbReference!!.push().setValue(uploadVO)
    }

    fun selectReview(): Query? {
        return reviewDbReference
    }
}