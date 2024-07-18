package com.example.wellness_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wellness_app.databinding.ActivityPeerSupportBinding

class PeerSupportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeerSupportBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messages: MutableList<Message> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeerSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupRecyclerView()
        loadPeerSupportMessages()

        binding.buttonSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            title = "Peer Support"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messages)
        binding.recyclerViewMessages.apply {
            layoutManager = LinearLayoutManager(this@PeerSupportActivity)
            adapter = messageAdapter
        }
    }

    private fun loadPeerSupportMessages() {
        // Load peer support messages from your data source and add them to the messages list
        // This is just an example, you need to replace it with actual data retrieval logic
        val initialMessages = listOf(
            Message(1, "Welcome to the peer support group!", "Admin", System.currentTimeMillis()),
            Message(2, "Thanks for joining us", "me", System.currentTimeMillis()),
            Message(3, "Hi everyone, how are you?", "Alice", System.currentTimeMillis()),
            Message(4, "Hello! I'm here to support.", "Bob", System.currentTimeMillis())

        )

        messages.addAll(initialMessages)
        messageAdapter.notifyDataSetChanged()
    }

    private fun sendMessage() {
        val content = binding.editTextMessage.text.toString().trim()
        if (content.isNotEmpty()) {
            val message = Message(messages.size.toLong() + 1, content, "me", System.currentTimeMillis())
            messages.add(message)
            messageAdapter.notifyItemInserted(messages.size - 1)
            binding.recyclerViewMessages.scrollToPosition(messages.size - 1)
            binding.editTextMessage.text.clear()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}