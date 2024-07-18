package com.example.wellness_app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuidedMeditationAdapter(
    private val meditations: List<GuidedMeditation>,
    private val onItemClick: (GuidedMeditation) -> Unit
) : RecyclerView.Adapter<GuidedMeditationAdapter.GuidedMeditationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuidedMeditationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guided_meditation, parent, false)
        return GuidedMeditationViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuidedMeditationViewHolder, position: Int) {
        val meditation = meditations[position]
        holder.bind(meditation, onItemClick)
    }

    override fun getItemCount() = meditations.size

    class GuidedMeditationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewDuration: TextView = itemView.findViewById(R.id.textViewDuration)

        fun bind(meditation: GuidedMeditation, onItemClick: (GuidedMeditation) -> Unit) {
            textViewTitle.text = meditation.title
            textViewDescription.text = meditation.description
            textViewDuration.text = "Duration: ${meditation.duration / 1000 / 60} min"
            itemView.setOnClickListener { onItemClick(meditation) }
        }
    }
}