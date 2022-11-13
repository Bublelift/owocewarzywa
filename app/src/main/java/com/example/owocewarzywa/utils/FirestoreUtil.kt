package com.example.owocewarzywa.utils

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.owocewarzywa.model.ChatChannel
import com.example.owocewarzywa.model.ChatViewModel
import com.example.owocewarzywa.model.TextMessage
import com.example.owocewarzywa.model.User
import com.example.owocewarzywa.recyclerview.item.PersonItem
import com.example.owocewarzywa.recyclerview.item.TextMessageItem
import com.google.apphosting.datastore.testing.DatastoreTestTrace.FirestoreV1Action.Listen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.xwray.groupie.kotlinandroidextensions.Item

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid}")

    private val chatChannelsCollectionRef = firestoreInstance.collection("chatChannels")

    private var userList = "swing" ?: null

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
                val newUser = User(
                    name = FirebaseAuth.getInstance().currentUser?.displayName ?: "",
                    bio = "",
                    profilePicturePath = null
                )
                currentUserDocRef.set(newUser).addOnSuccessListener { onComplete() }
            } else onComplete()
        }
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        Log.e("FirestoreUtil", currentUserDocRef.get().toString())
        currentUserDocRef.get().addOnSuccessListener { onComplete(it.toObject(User::class.java)!!) }
    }

    fun addUsersListener(context: Context, onListen: (List<Item>) -> Unit): ListenerRegistration {
        return firestoreInstance.collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e(
                        "Firestore",
                        "Dupa na firestoreutil.kt 59",
                        firebaseFirestoreException
                    )
                    return@addSnapshotListener
                }
                val items = mutableListOf<Item>()
                querySnapshot?.documents?.forEach {
                    if (it.id != FirebaseAuth.getInstance().currentUser?.uid)
                        items.add(PersonItem(it.toObject(User::class.java)!!, it.id, context))
                }
                onListen(items)
            }
    }

    fun removeListener(registration: ListenerRegistration) = registration.remove()

    fun getOrCreateChatChannel(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        currentUserDocRef.collection("engagedChatChannels").document(otherUserId).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    onComplete(it["channelId"] as String)
                    return@addOnSuccessListener
                }

                val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
                val newChannel = chatChannelsCollectionRef.document()
                newChannel.set(ChatChannel(mutableListOf(currentUserId, otherUserId)))
                currentUserDocRef.collection("engagedChatChannels")
                    .document(otherUserId)
                    .set(mapOf("channelId" to newChannel.id))

                firestoreInstance.collection("users").document(otherUserId)
                    .collection("engagedChatChannels")
                    .document(currentUserId)
                    .set(mapOf("channelId" to newChannel.id))

                onComplete(newChannel.id)
            }
    }

    fun addChatMessagesListener(channelId: String, context: Context, onListen: (List<Item>) -> Unit) : ListenerRegistration {
        return chatChannelsCollectionRef.document(channelId).collection("messages").orderBy("time")
            .addSnapshotListener{
                querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e("FirestoreException", "Wyjebało się na FirestoreUtil 102", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                
                val items = mutableListOf<Item>()
                querySnapshot!!.documents.forEach {
                    items.add(TextMessageItem(it.toObject(TextMessage::class.java)!!, context))
                }
                onListen(items)
            }
    }

    fun sendMessage(message: TextMessage, channelId: String) {
        chatChannelsCollectionRef.document(channelId).collection("messages").add(message)
    }

    fun getMsgReadStatus(chatroomId: String, onSuccess: (toReadBy: String) -> Unit){
        var toReadBy: String? = null
        chatChannelsCollectionRef.document(chatroomId).get()
            .addOnSuccessListener { if (it.exists()) {
                    toReadBy = it["toReadBy"].toString()
                }
                if (toReadBy == null || toReadBy!!.isBlank())
                    onSuccess("")
                else onSuccess(toReadBy.toString())
            }
    }

    fun setMsgReadStatus(chatroomId: String, status: String) {
        chatChannelsCollectionRef.document(chatroomId).set(mapOf("toReadBy" to status))
    }
}