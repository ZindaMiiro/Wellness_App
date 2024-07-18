package com.example.wellness_app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoodEntriesAdapter :
    ListAdapter<MoodEntry, MoodEntriesAdapter.MoodEntryViewHolder>(MoodEntryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodEntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mood_entry_item, parent, false)
        return MoodEntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodEntryViewHolder, position: Int) {
        val moodEntry = getItem(position)
        holder.bind(moodEntry)
    }

    class MoodEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMood: TextView = itemView.findViewById(R.id.tvMood)
        private val tvDiaryEntry: TextView = itemView.findViewById(R.id.tvDiaryEntry)
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)

        fun bind(moodEntry: MoodEntry) {
            tvMood.text = moodEntry.mood
            tvDiaryEntry.text = moodEntry.diary
            tvTimestamp.text = formatTimestamp(moodEntry.timestamp)
        }

        private fun formatTimestamp(timestamp: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = Date(timestamp)
            return sdf.format(date)
        }
    }

    class MoodEntryDiffCallback : DiffUtil.ItemCallback<MoodEntry>() {
        override fun areItemsTheSame(oldItem: MoodEntry, newItem: MoodEntry): Boolean {
            return oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: MoodEntry, newItem: MoodEntry): Boolean {
            return oldItem == newItem
        }
    }
}