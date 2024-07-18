package com.example.wellness_app


import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChatRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun sendMessage(message: ChatMessage) {
        firestore.collection("group_chat")
            .add(message)
            .addOnSuccessListener {
                // Optional: Handle success if needed
            }
            .addOnFailureListener {
                // Optional: Handle failure if needed
            }
    }

    fun subscribeToMessages(callback: (List<ChatMessage>) -> Unit) {
        firestore.collection("group_chat")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                val chatMessages = mutableListOf<ChatMessage>()
                snapshot?.documents?.forEach { doc ->
                    val message = doc.toObject(ChatMessage::class.java)
                    message?.let {
                        chatMessages.add(it)
                    }
                }
                callback(chatMessages)
            }
    }
}