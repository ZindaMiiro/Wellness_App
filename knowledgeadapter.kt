// KnowledgeAdapter.kt
package com.example.wellness_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KnowledgeAdapter(private val knowledgeList: List<String>) : RecyclerView.Adapter<KnowledgeAdapter.KnowledgeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemknowledge, parent, false)
        return KnowledgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: KnowledgeViewHolder, position: Int) {
        val knowledgeItem = knowledgeList[position]
        holder.bind(knowledgeItem)
    }

    override fun getItemCount(): Int {
        return knowledgeList.size
    }

    inner class KnowledgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val knowledgeTextView: TextView = itemView.findViewById(R.id.knowledgeTextView)

        fun bind(knowledgeItem: String) {
            knowledgeTextView.text = knowledgeItem
        }
    }
}
