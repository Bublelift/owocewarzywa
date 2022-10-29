package com.example.owocewarzywa.chat

import android.util.Log
import com.example.owocewarzywa.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy {FirebaseFirestore.getInstance()}

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid}")

    fun updateCurrentUser(name: String = "", bio: String = "", profilePicturePath: String? = null) {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (bio.isNotBlank()) userFieldMap["bio"] = bio
        if (profilePicturePath != null) userFieldMap["profilePicturePath"] = profilePicturePath
        currentUserDocRef.update(userFieldMap)
    }

    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(name = FirebaseAuth.getInstance().currentUser?.displayName ?: "", bio = "", profilePicturePath = null)
                currentUserDocRef.set(newUser).addOnSuccessListener { onComplete() }
            }
            else onComplete()
        }
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        Log.e("FirestoreUtil", currentUserDocRef.get().toString())
        currentUserDocRef.get().addOnSuccessListener { onComplete(it.toObject(User::class.java)!!) }
    }
}