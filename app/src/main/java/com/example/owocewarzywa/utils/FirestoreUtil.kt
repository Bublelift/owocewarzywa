package com.example.owocewarzywa.utils

import android.content.Context
import android.util.Log
import com.example.owocewarzywa.model.User
import com.example.owocewarzywa.recyclerview.item.PersonItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.xwray.groupie.kotlinandroidextensions.Item

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

    fun addUsersListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("users").addSnapshotListener{querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(
                    "Firestore",
                    "Wyjebało się na firestoreutil.kt 44",
                    firebaseFirestoreException
                )
                return@addSnapshotListener
            }

            val items = mutableListOf<Item>()
            querySnapshot?.documents?.forEach{
                if (it.id != FirebaseAuth.getInstance().currentUser?.uid)
                    items.add(PersonItem(it.toObject(User::class.java)!!, it.id, context))
            }
            onListen(items)
        }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()
}