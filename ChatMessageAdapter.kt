package com.example.wellness_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ChatMessageAdapter(private val messages: MutableList<ChatMessage> = mutableListOf()) :
    RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder>() {

    // ViewHolder class to hold the view references for each chat item
    class ChatMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val senderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val messageContent: TextView = itemView.findViewById(R.id.tvMessageContent)
        val messageTimestamp: TextView = itemView.findViewById(R.id.tvMessageTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatMessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        val message = messages[position]
        holder.senderName.text = message.senderId // Update with appropriate sender info
        holder.messageContent.text = message.message
        holder.messageTimestamp.text = formatTimestamp(message.timestamp)
    }

    override fun getItemCount(): Int = messages.size

    fun submitList(chatMessages: List<ChatMessage>) {
        messages.clear()
        messages.addAll(chatMessages)
        notifyDataSetChanged()
    }

    private fun formatTimestamp(timestamp: Long): String {
        // Format the timestamp to a readable date/time string (you can customize this)
        val sdf = java.text.SimpleDateFormat("hh:mm a, dd MMM yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }
}