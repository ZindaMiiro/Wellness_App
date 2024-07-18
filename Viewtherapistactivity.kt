package com.example.wellness_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewTherapistsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.veiwtherapistsactivity)

        dbHelper = DatabaseHelper(this)
        val therapists = dbHelper.getAllTherapists()

        val tableLayout = findViewById<TableLayout>(R.id.table_layout)

        for (therapist in therapists) {
            val inflater = LayoutInflater.from(this)
            val rowView = inflater.inflate(R.layout.list_item_therapist, null)

            val idTextView = rowView.findViewById<TextView>(R.id.therapist_id_text)
            val nameTextView = rowView.findViewById<TextView>(R.id.therapist_name_text)
            val emailTextView = rowView.findViewById<TextView>(R.id.therapist_email_text)
            val contactTextView = rowView.findViewById<TextView>(R.id.therapist_contact_text)

            idTextView.text = therapist.T_id.toString()
            nameTextView.text = therapist.T_name
            emailTextView.text = therapist.T_email
            contactTextView.text = therapist.T_contact

            tableLayout.addView(rowView)
        }
    }
}


