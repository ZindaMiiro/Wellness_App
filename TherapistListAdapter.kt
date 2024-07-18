package com.example.wellness_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TherapistListAdapter(private val context: Context, private val therapists: List<Therapist>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val therapist = therapists[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.list_item_therapist, null)

        val idTextView = rowView.findViewById<TextView>(R.id.therapist_id_text)
        val nameTextView = rowView.findViewById<TextView>(R.id.therapist_name_text)
        val emailTextView = rowView.findViewById<TextView>(R.id.therapist_email_text)
        val contactTextView = rowView.findViewById<TextView>(R.id.therapist_contact_text)

        idTextView.text = therapist.T_id.toString()
        nameTextView.text = therapist.T_name
        emailTextView.text = therapist.T_email
        contactTextView.text = therapist.T_contact

        return rowView
    }

    override fun getItem(position: Int): Any {
        return therapists[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return therapists.size
    }
}


